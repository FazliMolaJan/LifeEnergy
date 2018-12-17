package cn.dennishucd.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import cn.dennishucd.FFmpeg4AndroidActivity;
import cn.dennishucd.R;
import cn.dennishucd.utils.PhotoUtil;
import cn.dennishucd.utils.TimeUtil;
import cn.dennishucd.utils.Utils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.media.CamcorderProfile;
import android.media.CameraProfile;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.ViewGroup;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class VideoRecordActivity extends Activity {
	private Camera mCamera;
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;
	private CameraInfo[] mCameraInfo;
	private Button mReturn;
	private ImageView mStart;
	private Button mNext;
	private TextView mRecordTime;
	private ProgressBar mRecordProgressBar;
	private static final int MAX_TIME = 10;
	private static final int MIN_TIME = 2;
	private static final int RECORD_NO = 0; 
	private static final int RECORD_ING = 1; 
	private static final int RECORD_ED = 2;
	private int mRecord_State = 0;
	private float mRecord_Time;
	private MediaRecorder mediaRecorder;
	private int frameindex=0;
	private String mTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_record_activity);
		findViewById();
		setListener();
		init();
	}
	protected void onDestroyed(){
		if(mediaRecorder!=null){
			if(mRecord_State == RECORD_ING){
				mediaRecorder.stop();
				mediaRecorder.reset();
				mediaRecorder.release();
			}
	   }
		if(mCamera!=null){
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}
	
	private void findViewById() {
		mSurfaceView = (SurfaceView)findViewById(R.id.video_record_surface);	
		mReturn = (Button)findViewById(R.id.video_record_return);
		mStart = (ImageView)findViewById(R.id.video_record_start);
		mNext = (Button)findViewById(R.id.video_record_next);
		mRecordTime = (TextView) findViewById(R.id.video_record_time);
		mRecordProgressBar = (ProgressBar) findViewById(R.id.video_record_progressbar);
	}

	private void setListener() {
		mReturn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
			}			
		});
		
		mStart.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if(mRecord_State != RECORD_ING) {
						mRecord_State = RECORD_ING;
						startmediaRecorder();
						new Thread(new Runnable() {

							public void run() {
								mRecord_Time = 0;
								while (mRecord_State == RECORD_ING) {
									if (mRecord_Time >= MAX_TIME) {
										mRecordHandler.sendEmptyMessage(0);
									} else {
										try {
											Thread.sleep(200);
											mRecord_Time += 0.2;
											if (mRecord_State == RECORD_ING) {
												mRecordHandler.
												sendEmptyMessage(1);
											}
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
								}
							}
					  }).start();
				   }
				break;
				case MotionEvent.ACTION_UP:
					if (mRecord_State == RECORD_ING) {
						stopmediaRecorder();
						if (mRecord_Time <= MIN_TIME) {
							Toast.makeText(VideoRecordActivity.this, "录制时间过短",
									Toast.LENGTH_SHORT).show();
							mRecord_State = RECORD_NO;
							mRecord_Time = 0;
							mRecordTime.setText("0″");
							mRecordProgressBar.setProgress(0);
						}
						else{
							
						}
					}
				 break;
				}
				return false;
			}		
		});
		
		mNext.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(mediaRecorder!=null){
					mediaRecorder.stop();
					mediaRecorder.reset();
					mediaRecorder.release();
					mediaRecorder=null;
			   }
				if(mCamera!=null){
					mCamera.stopPreview();
					mCamera.release();
					mCamera = null;
				}
				Intent intent = new Intent();
				intent.setClass(VideoRecordActivity.this, FFmpeg4AndroidActivity.class);
				startActivity(intent);
			}		
		});
	}

	private void init() {
		Utils.ActivityList.add(this);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(new Callback() {		
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				releaseCamera();
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				initpreview();
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {				
			}
		});
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
	}
	
	protected void releaseCamera() {
		if(mCamera!=null){
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}
	
	protected void initpreview() {
		mCamera = Camera.open(CameraInfo.CAMERA_FACING_BACK);
		try {
			mCamera.setPreviewDisplay(mSurfaceHolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setCameraDisplayOrientation(this,CameraInfo.CAMERA_FACING_BACK,mCamera);
        priviewCallBack pre = new priviewCallBack();
        mCamera.setPreviewCallback(pre);
		Camera.Parameters  params = mCamera.getParameters();
		params.setColorEffect(Camera.Parameters.EFFECT_SOLARIZE);
		params.setPreviewSize(640, 480);
		mCamera.startPreview();
		mCamera.setParameters(params);
	}
	 public static void setCameraDisplayOrientation(Activity activity,int cameraId, android.hardware.Camera camera) {
	     android.hardware.Camera.CameraInfo info =new android.hardware.Camera.CameraInfo();
	     android.hardware.Camera.getCameraInfo(cameraId, info);
	     int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
	     int degrees = 0;
	     switch (rotation) {
	         case Surface.ROTATION_0: degrees = 0; break;
	         case Surface.ROTATION_90: degrees = 90; break;
	         case Surface.ROTATION_180: degrees = 180; break;
	         case Surface.ROTATION_270: degrees = 270; break;
	     }
	     int result;
	     if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
	         result = (info.orientation + degrees) % 360;
	         result = (360 - result) % 360;  
	     } else {
	         result = (info.orientation - degrees + 360) % 360;
	     }
	     //camera.setDisplayOrientation(result);
	     camera.setDisplayOrientation(90);
	 }

	private void stopmediaRecorder() {
		if(mediaRecorder!=null){
			if(mRecord_State == RECORD_ING){
				mediaRecorder.stop();
				//mCamera.lock();
				mediaRecorder.reset();
				mediaRecorder.release();
				mediaRecorder=null;
				mRecord_State = RECORD_ED;
				try {
					mCamera.reconnect();
				} catch (IOException e) {
					Toast.makeText(this, "reconect fail", 0).show();
					e.printStackTrace();
				}
			}
		}
	}

	private void startmediaRecorder() {	
		mCamera.unlock();
		mRecord_State = RECORD_ING;
		mediaRecorder = new MediaRecorder();
		mediaRecorder.reset();
	    mediaRecorder.setCamera(mCamera);
	    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);  
	    mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);  
	   /*
	    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
	    mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);  
	    mediaRecorder.setVideoSize(480,480); 
	    mediaRecorder.setVideoEncodingBitRate(10*1024*1024);
	    
	    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC); 
	    mediaRecorder.setAudioChannels(2);
	    mediaRecorder.setAudioSamplingRate(44100);
	    mediaRecorder.setAudioEncodingBitRate(128*1024);    
	    */
	    mediaRecorder.setOrientationHint(90);
	    mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
	    mediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
	    String fileName=Environment.getExternalStorageDirectory()+"/"+"VID_IN.mp4";
		//String fileName=Environment.getExternalStorageDirectory()+"/ImageLife/"+LoginActivity.UID+"/Video/"+mTime+".mp4";
        mediaRecorder.setOutputFile(fileName);
        try {
			mediaRecorder.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        mediaRecorder.start();    
       
	}
	
	 class priviewCallBack implements Camera.PreviewCallback {

	        @Override
	        public void onPreviewFrame(byte[] data, Camera camera) {
	            // Log.w("wwwwwwwww", data[5] + "");
	            // Log.w("支持格式", mCamera.getParameters().getPreviewFormat()+"");
	           // decodeToBitMap(data, camera);
	        }
	    }

	 public void decodeToBitMap(byte[] data, Camera _camera) {
	        Size size = mCamera.getParameters().getPreviewSize();
	        try {
	            YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width,
	                    size.height, null);
	            if (image != null) {
	                ByteArrayOutputStream stream = new ByteArrayOutputStream();
	                image.compressToJpeg(new Rect(0, 0, size.width, size.height), 80, stream);
	                Bitmap bmp = BitmapFactory.decodeByteArray(
	                        stream.toByteArray(), 0, stream.size());
	                Log.w("wwwwwwwww", bmp.getWidth() + " " + bmp.getHeight());
	                Log.w("wwwwwwwww",size.width + " " + size.height);
	                Log.w("wwwwwwwww",
	                        (bmp.getPixel(100, 100) & 0xff) + "  "
	                                + ((bmp.getPixel(100, 100) >> 8) & 0xff) + "  "
	                                + ((bmp.getPixel(100, 100) >> 16) & 0xff));
	                PhotoUtil.saveMyBitmap(bmp,String.valueOf(frameindex));
	                frameindex= frameindex +1;
	                stream.close();
	            }
	        } catch (Exception ex) {
	            Log.e("Sys", "Error:" + ex.getMessage());
	        }
	   }
	 
		Handler mRecordHandler = new Handler() {

			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					if (mRecord_State == RECORD_ING) {
						mRecord_State = RECORD_ED;
						mediaRecorder.stop();
					}
					break;
				case 1:
					mRecordProgressBar.setProgress((int) mRecord_Time);
					mRecordTime.setText((int) mRecord_Time + "″");
					break;
				}
			}
		};

}

package cn.dennishucd.activity;

import cn.dennishucd.R;
import cn.dennishucd.R.id;
import cn.dennishucd.R.layout;
import cn.dennishucd.qrcode.VoiceParser;
import cn.dennishucd.utils.Utils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechListener;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechUser;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

public class VoiceSearchActivity extends Activity {
	
	private static final int MODE_PRIVATE = 0;
	private Context mContext;
	private RecognizerDialog iatDialog;
	private SpeechRecognizer iatRecognizer;
	private SharedPreferences mSharedPreferences;
	private MyRecognizerDialogLister recognizerDialogListener = null;
	private String mWord;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voice_search);	
		mContext = this;
		SpeechUser.getUser().login(this, null, null, "appid=" + "534e3fe2", listener);
		iatDialog =new RecognizerDialog(mContext);
		mSharedPreferences = mContext.getSharedPreferences(mContext.getPackageName(),MODE_PRIVATE);
		GetWordFromVoice();	
	}
	
	public void GetWordFromVoice()
	{
		boolean isShowDialog = mSharedPreferences.getBoolean("iat_show",true);
		if (isShowDialog) {
			showIatDialog();
		} 
		else {
			if(null == iatRecognizer) {
				iatRecognizer=SpeechRecognizer.createRecognizer(mContext);
			}
			if(iatRecognizer.isListening()) {
				iatRecognizer.stopListening();
			} else {}
		}
	}	
	
	public void showIatDialog()
	{			
		if(null == iatDialog) {
			iatDialog =new RecognizerDialog(mContext);
		}
		String engine = mSharedPreferences.getString("iat_engine","iat");
		iatDialog.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
		iatDialog.setParameter(SpeechConstant.DOMAIN, engine); 
		String rate = mSharedPreferences.getString("sf","sf");
		if(rate.equals("rate8k")){
			iatDialog.setParameter(SpeechConstant.SAMPLE_RATE, "8000");
		}
		else {
			iatDialog.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
		}
		if(recognizerDialogListener == null){	
			getRecognizerDialogListener();
		}
		iatDialog.setListener(recognizerDialogListener);
		iatDialog.show();
	}
	
	private void getRecognizerDialogListener()
	{
		recognizerDialogListener=new MyRecognizerDialogLister(mContext);
	}
	
	private SpeechListener listener = new SpeechListener()
	{
		@Override
		public void onData(byte[] arg0) {
		}

		@Override
		public void onCompleted(SpeechError error) {
			if(error != null) {
				System.out.println("user login success");
			}			
		}
		@Override
		public void onEvent(int arg0, Bundle arg1) {
		}		
	};
	
	class MyRecognizerDialogLister implements RecognizerDialogListener
	{
		private Context context;
		public MyRecognizerDialogLister(Context context)
		{
			this.context = context;
		}
		@Override
		public void onResult(RecognizerResult results, boolean isLast) 
		{		
			// TODO Auto-generated method stub
			mWord=VoiceParser.parseIatResult(results.getResultString());
			if(mWord.equals("。"))
				return ;
			Toast.makeText(context, mWord, Toast.LENGTH_LONG).show();
			/*Intent intent = new Intent();
			intent.setClass(mContext, ChatActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("videopath", mWord);
			intent.putExtras(bundle);
			mContext.startActivity(intent);
			*/
			finish();
		}	
		@Override
		public void onError(SpeechError error) {
			// TODO Auto-generated method stub
			int errorCoder = error.getErrorCode();
			switch (errorCoder) {
			case 10118:
				System.out.println("user don't speak anything");
				break;
			case 10204:
				System.out.println("can't connect to internet");
				break;
			default:
				break;
			}
		}
	}
}



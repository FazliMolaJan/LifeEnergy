package cn.dennishucd.result;

public class GridViewResult {
	/**
	 * 用户的ID
	 */
	private String uid;
	/**
	 * 内容的时间
	 */
	private String time;
	/**
	 * 内容的标题
	 */
	private String title;
	/**
	 * 内容的图片
	 */
	private String avatar;
	private String photo;
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getAvatar() {
		// TODO Auto-generated method stub
		return avatar;
	}

	public void setAvatar(String avatar) {
		// TODO Auto-generated method stub
		this.avatar=avatar;
	}
	
}

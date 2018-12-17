package cn.dennishucd.result;

/**
 * 首页内容的实体类
 * 
 * 
 */
public class HomeFriendsResult {
	/**
	 * 用户的ID
	 */
	private String uid;
	/**
	 * 用户的姓名
	 */
	private String name;
	/**
	 * 用户的头像
	 */
	private String avatar;
	/**
	 * 内容的类型
	 */
	private String type;
	/**
	 * 内容的时间
	 */
	private String time;
	/**
	 * 内容的标题
	 */
	private String title;
	/**
	 * 来自什么客户端
	 */
	private String from;
	/**
	 * 评论数量
	 */
	private int comment_count;
	/**
	 * 转发量
	 */
	private int forward_count;
	/**
	 * 赞数量
	 */
	private int like_count;
	/**
	 * 内容的图片
	 */
	private String photo;
	/**
	 * 内容的图片
	 */
	private String video_path;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String string) {
		this.avatar = string;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public int getComment_count() {
		return comment_count;
	}

	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}

	public int getForward_count() {
		// TODO Auto-generated method stub
		return forward_count;
	}
	
	public void setForward_count(int forward_count){
		this.forward_count=forward_count;
	}
	public int getLike_count() {
		return like_count;
	}

	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public void setPath(String video_path){
		this.video_path=video_path;
	}
	
	public String getPath(){
		return this.video_path;
	}



}

package cn.dennishucd.result;

/**
 * 好友生日数据实体类
 * 
 * 
 */
public class FriendsBirthdayResult {
	/**
	 * 好友的ID
	 */
	private String uid;
	/**
	 * 好友的姓名
	 */
	private String name;
	/**
	 * 好友的头像
	 */
	private String avatar;
	/**
	 * 好友的生日
	 */
	private String birthday_date;

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

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getBirthday_date() {
		return birthday_date;
	}

	public void setBirthday_date(String birthday_date) {
		this.birthday_date = birthday_date;
	}
}

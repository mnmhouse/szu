package cn.szu.edu.app.bean;

/**
 * Created by changliang on 14-6-30.
 */
public class PicsBean {
	private String id;
	private String post_title;
	private String comment_count;
	private String my_post_type;
	private String post_limit;
	private String post_url;
	private String likeCount;
	private String thumbnail_url;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPost_title() {
		return post_title;
	}
	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}
	public String getComment_count() {
		return comment_count;
	}
	public void setComment_count(String comment_count) {
		this.comment_count = comment_count;
	}
	public String getMy_post_type() {
		return my_post_type;
	}
	public void setMy_post_type(String my_post_type) {
		this.my_post_type = my_post_type;
	}
	public String getPost_limit() {
		return post_limit;
	}
	public void setPost_limit(String post_limit) {
		this.post_limit = post_limit;
	}
	public String getPost_url() {
		return post_url;
	}
	public void setPost_url(String post_url) {
		this.post_url = post_url;
	}
	public String getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(String likeCount) {
		this.likeCount = likeCount;
	}
	public String getThumbnail_url() {
		return thumbnail_url;
	}
	public void setThumbnail_url(String thumbnail_url) {
		this.thumbnail_url = thumbnail_url;
	}
}

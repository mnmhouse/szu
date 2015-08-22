package cn.szu.edu.app.bean;



/**
 * @author HuangWenwei
 *
 * @date 2014年10月16日
 */
@SuppressWarnings("serial")
public class TweetDetailBean extends Entity {
	private String pubDate;
	private String author;
	private String body;
	private String title;
	private String answerCount;
	private int authorid;
	private String viewCount;
	private String favorite;
	private String portrait;
	private String tags;
	//private List<String> imageArray;
	
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(String answerCount) {
		this.answerCount = answerCount;
	}
	public int getAuthorid() {
		return authorid;
	}
	public void setAuthorid(int authorid) {
		this.authorid = authorid;
	}
	public String getViewCount() {
		return viewCount;
	}
	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}
	public String getFavorite() {
		return favorite;
	}
	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
//	public List<String> getImageArray() {
//		return imageArray;
//	}
//	public void setImageArray(List<String> imageArray) {
//		this.imageArray = imageArray;
//	}
}

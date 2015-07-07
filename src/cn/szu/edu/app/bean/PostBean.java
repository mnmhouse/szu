package cn.szu.edu.app.bean;

import java.io.Serializable;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 帖子实体类
 * 
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年10月9日 下午6:02:47
 * 
 */
@SuppressWarnings("serial")
public class PostBean extends Entity implements Parcelable {

	public final static int CATALOG_ASK = 1;
	public final static int CATALOG_SHARE = 2;
	public final static int CATALOG_OTHER = 3;
	public final static int CATALOG_JOB = 4;
	public final static int CATALOG_SITE = 5;

	private String author;
	private String pubDate;
	private String title;
	private int answerCount;
	private int authorid;
	private String answer;
	private String portrait;
	private String catalogname;
	private List<Image> imageArray;
	private int appclient;
	
	public class Image implements Serializable {
		private int id;
		private String imageurl;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getImageurl() {
			return imageurl;
		}
		public void setImageurl(String imageurl) {
			this.imageurl = imageurl;
		}
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}

	public int getAuthorid() {
		return authorid;
	}

	public void setAuthorid(int authorid) {
		this.authorid = authorid;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getCatalogname() {
		return catalogname;
	}

	public void setCatalogname(String catalogname) {
		this.catalogname = catalogname;
	}

	public List<Image> getImageArray() {
		return imageArray;
	}

	public void setImageArray(List<Image> imageArray) {
		this.imageArray = imageArray;
	}

	public int getAppclient() {
		return appclient;
	}

	public void setAppclient(int appclient) {
		this.appclient = appclient;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(portrait);
        dest.writeString(author);
        dest.writeInt(authorid);
//        dest.writeString(body);
        dest.writeInt(appclient);
//        dest.writeString(commentCount);
        dest.writeString(pubDate);
//        dest.writeString(imgSmall);
//        dest.writeString(imgBig);
//        dest.writeString(attach);
//        dest.writeString(imageFilePath);
//        dest.writeString(audioPath);
//        dest.writeInt(isLike);		
	}
	
}

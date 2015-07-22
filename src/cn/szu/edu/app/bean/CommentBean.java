package cn.szu.edu.app.bean;

public class CommentBean {
	private String id;
	private String content;
	private String pubDate;
	private String client_type;
	private String commentAuthor;
	private String commentAuthorId;
	private Refers refers;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public String getClient_type() {
		return client_type;
	}
	public void setClient_type(String client_type) {
		this.client_type = client_type;
	}
	public String getCommentAuthor() {
		return commentAuthor;
	}
	public void setCommentAuthor(String commentAuthor) {
		this.commentAuthor = commentAuthor;
	}
	public String getCommentAuthorId() {
		return commentAuthorId;
	}
	public void setCommentAuthorId(String commentAuthorId) {
		this.commentAuthorId = commentAuthorId;
	}
	public Refers getRefers() {
		return refers;
	}
	public void setRefers(Refers refers) {
		this.refers = refers;
	}
}

class Refers {
	
	private String refertitle;
	private String referautor;
	
	public String getRefertitle() {
		return refertitle;
	}
	public void setRefertitle(String refertitle) {
		this.refertitle = refertitle;
	}
	public String getReferautor() {
		return referautor;
	}
	public void setReferautor(String referautor) {
		this.referautor = referautor;
	}

}

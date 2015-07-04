package cn.szu.edu.app.bean;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 通知信息实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
@SuppressWarnings("serial")
public class NoticeBean implements Serializable {

	private String noticeid;
	private String brief;
	private String detail;
	private String noticeurl;
	public String getNoticeid() {
		return noticeid;
	}
	public void setNoticeid(String noticeid) {
		this.noticeid = noticeid;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getNoticeurl() {
		return noticeurl;
	}
	public void setNoticeurl(String noticeurl) {
		this.noticeurl = noticeurl;
	}
	
}

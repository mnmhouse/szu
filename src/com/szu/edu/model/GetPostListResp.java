package com.szu.edu.model;

import java.io.Serializable;
import java.util.List;

public class GetPostListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1484023754532889468L;
     private List<PostItem> post_list;
	public List<PostItem> getPost_list() {
		return post_list;
	}
	public void setPost_list(List<PostItem> post_list) {
		this.post_list = post_list;
	}
}

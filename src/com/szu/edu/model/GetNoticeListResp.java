package com.szu.edu.model;

import java.io.Serializable;
import java.util.List;

public class GetNoticeListResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2927386988179725641L;
    private List<NoticeItem> values;
	public List<NoticeItem> getValues() {
		return values;
	}
	public void setValues(List<NoticeItem> values) {
		this.values = values;
	}
}

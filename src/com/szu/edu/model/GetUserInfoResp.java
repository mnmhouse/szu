package com.szu.edu.model;

import java.io.Serializable;

public class GetUserInfoResp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7939132993728152814L;
	private  String uid;
	private  String name ;
	private  String gender ;
	private  String province ;
	private  String city ;
	private  String portrait ;
	private  String fansCoun ;
	private  String favoriteCount ;
	private  String followersCount ;
	private  String returnCode ;
	private  String returnMsg ;
	
	
    private String account;

    private String pwd;

    private boolean isRememberMe;
    
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public boolean isRememberMe() {
		return isRememberMe;
	}
	public void setRememberMe(boolean isRememberMe) {
		this.isRememberMe = isRememberMe;
	}
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public String getFansCoun() {
		return fansCoun;
	}
	public void setFansCoun(String fansCoun) {
		this.fansCoun = fansCoun;
	}
	public String getFavoriteCount() {
		return favoriteCount;
	}
	public void setFavoriteCount(String favoriteCount) {
		this.favoriteCount = favoriteCount;
	}
	public String getFollowersCount() {
		return followersCount;
	}
	public void setFollowersCount(String followersCount) {
		this.followersCount = followersCount;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

}

package cn.szu.edu.app.team.bean;

import cn.szu.edu.app.bean.Entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @version 创建时间：2015年1月20日 上午10:49:39
 * 
 */

@SuppressWarnings("serial")
@XStreamAlias("git")
public class TeamGit extends Entity {

    @XStreamAlias("name")
    private String name;

    @XStreamAlias("path")
    private String path;

    @XStreamAlias("ownerName")
    private String ownerName;

    @XStreamAlias("ownerUserName")
    private String ownerUserName;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getPath() {
	return path;
    }

    public void setPath(String path) {
	this.path = path;
    }

    public String getOwnerName() {
	return ownerName;
    }

    public void setOwnerName(String ownerName) {
	this.ownerName = ownerName;
    }

    public String getOwnerUserName() {
	return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
	this.ownerUserName = ownerUserName;
    }
}

package cn.szu.edu.app.bean;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 帖子实体类列表
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年10月9日 下午6:10:11
 *
 */
@SuppressWarnings("serial")
public class PostBeanList extends Entity implements ListEntity<PostBean> {
	
	public final static String PREF_READED_POST_LIST = "readed_post_list.pref";

	private int pageSize;
	
	private int postCount;
	
	private List<PostBean> postlist = new ArrayList<PostBean>();
	
	public int getPageSize() {
		return pageSize;
	}
	public int getPostCount() {
		return postCount;
	}
	public List<PostBean> getPostlist() {
		return postlist;
	}
	@Override
	public List<PostBean> getList() {
		return postlist;
	}
}

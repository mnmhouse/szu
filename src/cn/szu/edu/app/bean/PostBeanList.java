package cn.szu.edu.app.bean;

import java.util.ArrayList;
import java.util.List;

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
	
	private List<PostBean> post_list = new ArrayList<PostBean>();
	
	public int getPageSize() {
		return pageSize;
	}
	public int getPostCount() {
		return postCount;
	}
	public List<PostBean> getPost_list() {
		return post_list;
	}
	public void setPost_list(List<PostBean> list) {
		post_list = list;
	}
	@Override
	public List<PostBean> getList() {
		return post_list;
	}
}

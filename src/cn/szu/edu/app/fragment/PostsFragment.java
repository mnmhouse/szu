package cn.szu.edu.app.fragment;

import java.io.InputStream;
import java.io.Serializable;

import cn.szu.edu.app.adapter.PostAdapter;
import cn.szu.edu.app.api.remote.OSChinaApi;
import cn.szu.edu.app.base.BaseListFragment;
import cn.szu.edu.app.bean.Post;
import cn.szu.edu.app.bean.PostList;
import cn.szu.edu.app.util.UIHelper;
import cn.szu.edu.app.util.XmlUtils;

import android.view.View;
import android.widget.AdapterView;

/**
 * 问答
 * 
 * @author william_sim
 */
public class PostsFragment extends BaseListFragment<Post> {

    protected static final String TAG = PostsFragment.class.getSimpleName();
    private static final String CACHE_KEY_PREFIX = "postslist_";

    @Override
    protected PostAdapter getListAdapter() {
        return new PostAdapter();
    }

    @Override
    protected String getCacheKeyPrefix() {
        return CACHE_KEY_PREFIX + mCatalog;
    }

    @Override
    protected PostList parseList(InputStream is) throws Exception {
        PostList list = XmlUtils.toBean(PostList.class, is);
        return list;
    }

    @Override
    protected PostList readList(Serializable seri) {
        return ((PostList) seri);
    }

    @Override
    protected void sendRequestData() {
        OSChinaApi.getPostList(mCatalog, mCurrentPage, mHandler);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        Post post = mAdapter.getItem(position);
        if (post != null) {
            UIHelper.showPostDetail(view.getContext(), post.getId(),
                    post.getAnswerCount());
            // 放入已读列表
            saveToReadedList(view, PostList.PREF_READED_POST_LIST, post.getId()
                    + "");
        }
    }
}

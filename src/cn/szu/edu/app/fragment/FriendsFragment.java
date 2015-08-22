package cn.szu.edu.app.fragment;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import cn.szu.edu.app.AppContext;
import cn.szu.edu.app.adapter.FriendAdapter;
import cn.szu.edu.app.api.remote.OSChinaApi;
import cn.szu.edu.app.base.BaseListFragment;
import cn.szu.edu.app.bean.Entity;
import cn.szu.edu.app.bean.Friend;
import cn.szu.edu.app.bean.FriendsList;
import cn.szu.edu.app.bean.Notice;
import cn.szu.edu.app.service.NoticeUtils;
import cn.szu.edu.app.ui.MainActivity;
import cn.szu.edu.app.util.UIHelper;
import cn.szu.edu.app.util.XmlUtils;
import cn.szu.edu.app.viewpagerfragment.NoticeViewPagerFragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

/**
 * 关注、粉丝
 * 
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年11月6日 上午11:15:37
 * 
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FriendsFragment extends BaseListFragment<Friend> {

    public final static String BUNDLE_KEY_UID = "UID";

    protected static final String TAG = FriendsFragment.class.getSimpleName();
    private static final String CACHE_KEY_PREFIX = "friend_list";

    private int mUid;

    @Override
    public void initView(View view) {
        super.initView(view);
    }

    @Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mUid = args.getInt(BUNDLE_KEY_UID, 0);
        }
    }

    @Override
    public void onResume() {
        if (mCatalog == FriendsList.TYPE_FANS
                && mUid == AppContext.getInstance().getLoginUid()) {
            refreshNotice();
        }
        super.onResume();
    }

    private void refreshNotice() {
        Notice notice = MainActivity.mNotice;
        if (notice != null && notice.getNewFansCount() > 0) {
            onRefresh();
        }
    }

    @Override
    protected FriendAdapter getListAdapter() {
        return new FriendAdapter();
    }

    @Override
    protected String getCacheKeyPrefix() {
        return CACHE_KEY_PREFIX + "_" + mCatalog + "_" + mUid;
    }

    @Override
    protected FriendsList parseList(InputStream is) throws Exception {
        FriendsList list = XmlUtils.toBean(FriendsList.class, is);
        return list;
    }

    @Override
    protected FriendsList readList(Serializable seri) {
        return ((FriendsList) seri);
    }

    @Override
    protected boolean compareTo(List<? extends Entity> data, Entity enity) {
        int s = data.size();
        if (enity != null) {
            for (int i = 0; i < s; i++) {
                if (((Friend) enity).getUserid() == ((Friend) data.get(i))
                        .getUserid()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void sendRequestData() {
        OSChinaApi.getFriendList(mUid, mCatalog, mCurrentPage, mHandler);
    }

    @Override
    protected void onRefreshNetworkSuccess() {
        if ((NoticeViewPagerFragment.sCurrentPage == 3 || NoticeViewPagerFragment.sShowCount[3] > 0)
                && mCatalog == FriendsList.TYPE_FANS
                && mUid == AppContext.getInstance().getLoginUid()) {
            NoticeUtils.clearNotice(Notice.TYPE_NEWFAN);
            UIHelper.sendBroadcastForNotice(getActivity());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        Friend item = (Friend) mAdapter.getItem(position);
        if (item != null) {
            if (mUid == AppContext.getInstance().getLoginUid()) {
                UIHelper.showMessageDetail(getActivity(), item.getUserid(),
                        item.getName());
                return;
            }
            UIHelper.showUserCenter(getActivity(), item.getUserid(),
                    item.getName());
        }
    }
}

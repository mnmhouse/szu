package cn.szu.edu.app.viewpagerfragment;

import cn.szu.edu.app.adapter.ViewPageFragmentAdapter;
import cn.szu.edu.app.base.BaseListFragment;
import cn.szu.edu.app.base.BaseViewPagerFragment;
import cn.szu.edu.app.bean.FriendsList;
import cn.szu.edu.app.fragment.FriendsFragment;
import cn.szu.edu.app.R;
import android.os.Bundle;
import android.view.View;

/**
 * 关注、粉丝viewpager页面
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年9月25日 下午2:21:52
 *
 */
public class FriendsViewPagerFragment extends BaseViewPagerFragment {
	
	public static final String BUNDLE_KEY_TABIDX = "BUNDLE_KEY_TABIDX";
	
	private int mInitTabIdx;
	
	private int mUid;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		mInitTabIdx = args.getInt(BUNDLE_KEY_TABIDX, 0);
		mUid = args.getInt(FriendsFragment.BUNDLE_KEY_UID, 0);
	}
	
	@Override
	protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {
		String[] title = getResources().getStringArray(R.array.friends_viewpage_arrays);
		adapter.addTab(title[0], "follower", FriendsFragment.class, getBundle(FriendsList.TYPE_FOLLOWER));
		adapter.addTab(title[1], "following", FriendsFragment.class, getBundle(FriendsList.TYPE_FANS));
		
		mViewPager.setCurrentItem(mInitTabIdx);
	}
	
	private Bundle getBundle(int catalog) {
		Bundle bundle = new Bundle();
		bundle.putInt(BaseListFragment.BUNDLE_KEY_CATALOG, catalog);
		bundle.putInt(FriendsFragment.BUNDLE_KEY_UID, mUid);
		return bundle;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initView(View view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}
}

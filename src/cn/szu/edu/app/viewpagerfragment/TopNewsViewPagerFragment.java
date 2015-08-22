package cn.szu.edu.app.viewpagerfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import cn.szu.edu.app.R;
import cn.szu.edu.app.adapter.ViewPageFragmentAdapter;
import cn.szu.edu.app.base.BaseListFragment;
import cn.szu.edu.app.base.BaseViewPagerFragment;
import cn.szu.edu.app.bean.TweetsList;
import cn.szu.edu.app.fragment.TopNewsFragment;
import cn.szu.edu.app.interf.OnTabReselectListener;
import cn.szu.edu.app.ui.empty.EmptyLayout;
import cn.szu.edu.app.widget.PagerSlidingTabStrip;

/**
 * 动弹界面（包括最新动弹、热门动弹、我的动弹）
 * 
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年9月25日 下午2:21:52
 * 
 */
public class TopNewsViewPagerFragment extends BaseViewPagerFragment implements
        OnTabReselectListener {

    @Override
    protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {
        String[] title = getResources().getStringArray(
                R.array.tweets_viewpage_arrays);
        adapter.addTab(title[0], "new_tweets", TopNewsFragment.class,
                getBundle(TweetsList.CATALOG_LATEST));
//        adapter.addTab(null, "hot_tweets", TopNewsFragment.class,
//                getBundle(TweetsList.CATALOG_HOT));
        adapter.addTab(title[2], "my_tweets", TopNewsFragment.class,
                getBundle(TweetsList.CATALOG_ME));
    }

    private Bundle getBundle(int catalog) {
        Bundle bundle = new Bundle();
        bundle.putInt(BaseListFragment.BUNDLE_KEY_CATALOG, catalog);
        return bundle;
    }

    @Override
    public void onClick(View v) {}

    @Override
    public void initView(View view) {}

    @Override
    public void initData() {}

    @Override
    public void onTabReselect() {
        try {
            int currentIndex = mViewPager.getCurrentItem();
            Fragment currentFragment = getChildFragmentManager().getFragments()
                    .get(currentIndex);
            if (currentFragment != null
                    && currentFragment instanceof OnTabReselectListener) {
                OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
                listener.onTabReselect();
            }
        } catch (NullPointerException e) {
        }
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mTabStrip = (PagerSlidingTabStrip) view
                .findViewById(R.id.pager_tabstrip);
        mTabStrip.setSlidingBlockDrawable(null);

        mViewPager = (ViewPager) view.findViewById(R.id.pager);

        mErrorLayout = (EmptyLayout) view.findViewById(R.id.error_layout);

        mTabsAdapter = new ViewPageFragmentAdapter(getChildFragmentManager(),
                mTabStrip, mViewPager);
        setScreenPageLimit();
        onSetupTabAdapter(mTabsAdapter);
        // if (savedInstanceState != null) {
        // int pos = savedInstanceState.getInt("position");
        // mViewPager.setCurrentItem(pos, true);
        // }
    }
    
}
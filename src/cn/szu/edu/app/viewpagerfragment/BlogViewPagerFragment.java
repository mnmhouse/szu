package cn.szu.edu.app.viewpagerfragment;

import android.os.Bundle;
import android.view.View;
import cn.szu.edu.app.R;
import cn.szu.edu.app.adapter.ViewPageFragmentAdapter;
import cn.szu.edu.app.base.BaseViewPagerFragment;
import cn.szu.edu.app.bean.BlogList;
import cn.szu.edu.app.fragment.BlogFragment;

/**
 * 博客区ViewPager
 * 
 * @author kymjs(kymjs123@gmail.com)
 */
public class BlogViewPagerFragment extends BaseViewPagerFragment {

    @Override
    public void initView(View view) {}

    @Override
    public void initData() {}

    @Override
    public void onClick(View v) {}

    @Override
    protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {
        String[] title = getResources().getStringArray(
                R.array.blogs_viewpage_arrays);
        // 最新博客
        adapter.addTab(title[0], "latest_blog", BlogFragment.class,
                getBundle(BlogList.CATALOG_LATEST));
        // 推荐博客
        adapter.addTab(title[1], "recommend_blog", BlogFragment.class,
                getBundle(BlogList.CATALOG_RECOMMEND));
    }

    /**
     * 基类会根据不同的catalog展示相应的数据
     * 
     * @param catalog
     *            要显示的数据类别
     * @return
     */
    private Bundle getBundle(String catalog) {
        Bundle bundle = new Bundle();
        bundle.putString(BlogFragment.BUNDLE_BLOG_TYPE, catalog);
        return bundle;
    }

}

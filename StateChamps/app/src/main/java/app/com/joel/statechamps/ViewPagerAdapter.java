package app.com.joel.statechamps;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import app.com.joel.statechamps.Tabs.ArticlesTab;
import app.com.joel.statechamps.Tabs.SocialTab;
import app.com.joel.statechamps.Tabs.VideosTab;

/**
 * Created by Joel on 5/5/16.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int NumbOfTabs;

    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabs) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabs;
    }

    @Override
    public Fragment getItem(int position){
        if (position == 0) {
            ArticlesTab articlesTab = new ArticlesTab();
            return articlesTab;
        } else if (position == 1) {
            VideosTab videosTab = new VideosTab();
            return videosTab;
        } else {
            SocialTab socialTab = new SocialTab();
            return socialTab;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }


    @Override
    public int getCount(){
        return NumbOfTabs;
    }

}

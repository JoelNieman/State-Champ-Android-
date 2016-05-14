package app.com.joel.statechamps;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import app.com.joel.statechamps.Tabs.ArticlesFragment;
import app.com.joel.statechamps.Tabs.SocialFragment;
import app.com.joel.statechamps.Tabs.VideosFragment;

/**
 * Created by Joel on 5/5/16.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {



    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position){
        if (position == 0) {
            ArticlesFragment articlesTab = new ArticlesFragment();
            return articlesTab;
        } else if (position == 1) {
            VideosFragment videosTab = new VideosFragment();
            return videosTab;
        } else {
            SocialFragment socialTab = new SocialFragment();
            return socialTab;
        }
    }

    @Override
    public int getCount(){
        return 3;
    }

}

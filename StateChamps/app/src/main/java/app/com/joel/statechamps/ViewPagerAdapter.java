package app.com.joel.statechamps;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import app.com.joel.statechamps.Tabs.ArticlesFragment;
import app.com.joel.statechamps.Tabs.SocialFragment;
import app.com.joel.statechamps.Tabs.VideosFragment;

/**
 * Created by Joel on 5/5/16.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArticlesFragment articlesTab;
    private VideosFragment videosTab;
    private SocialFragment socialTab;


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position){
        if (position == 0) {
            if (articlesTab == null){
                articlesTab = new ArticlesFragment();
                Log.d("Articles Tab", "created");
            }
            return articlesTab;
        } else if (position == 1) {
            if (videosTab == null){
                videosTab = new VideosFragment();
                Log.d("Videos Tab", "created");
            }
            return videosTab;
        } else {
            if (socialTab == null) {
                socialTab = new SocialFragment();
                Log.d("Social Tab", "created");
            }
            return socialTab;
        }
    }

    @Override
    public int getCount(){
        return 3;
    }

}

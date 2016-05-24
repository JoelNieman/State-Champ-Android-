package app.com.joel.statechamps;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout.Tab articles;
    private TabLayout.Tab videos;
    private TabLayout.Tab social;
    private int orientation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Log.d("MainActivity", "onCreateView: called");

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        if (viewPagerAdapter == null) {
            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        }

        viewPager.setAdapter(viewPagerAdapter);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d("MainActivity", "onStart: called");


    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("MainActivity", "onResume: called");

        orientation = getRequestedOrientation();

        if (orientation == 1 && articles == null){
            toolbar.setTitle("Articles");
            toolbar.setTitleTextColor(getResources().getColor(R.color.WhiteColor));

            tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));
            tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.WhiteColor));

            setUpTabs();
            handleTabs();
        }

    }



    public void setUpTabs() {
        articles = tabLayout.newTab();
        videos = tabLayout.newTab();
        social = tabLayout.newTab();

        articles.setIcon(R.drawable.ic_articles_light);
        videos.setIcon(R.drawable.ic_videos_dark);
        social.setIcon(R.drawable.ic_social_dark);

        tabLayout.addTab(articles, 0);
        tabLayout.addTab(videos, 1);
        tabLayout.addTab(social, 2);
    }


    private void handleTabs() {
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        toolbar.setTitle("Articles");
                        articles.setIcon(R.drawable.ic_articles_light);
                        videos.setIcon(R.drawable.ic_videos_dark);
                        social.setIcon(R.drawable.ic_social_dark);
                        break;
                    case 1:
                        toolbar.setTitle("Videos");
                        articles.setIcon(R.drawable.ic_articles_dark);
                        videos.setIcon(R.drawable.ic_videos_light);
                        social.setIcon(R.drawable.ic_social_dark);
                        break;
                    case 2:
                        toolbar.setTitle("Social");
                        articles.setIcon(R.drawable.ic_articles_dark);
                        videos.setIcon(R.drawable.ic_videos_dark);
                        social.setIcon(R.drawable.ic_social_light);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("MainActivity", "onDestroy: MAIN ACTIVITY");
    }


}

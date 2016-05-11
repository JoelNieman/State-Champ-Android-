package app.com.joel.statechamps;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Articles");
        toolbar.setTitleTextColor(getResources().getColor(R.color.WhiteColor));

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        setSupportActionBar(toolbar);

        final TabLayout.Tab articles = tabLayout.newTab();
        final TabLayout.Tab videos = tabLayout.newTab();
        final TabLayout.Tab social = tabLayout.newTab();

        articles.setIcon(R.drawable.ic_articles_light);
        videos.setIcon(R.drawable.ic_videos_dark);
        social.setIcon(R.drawable.ic_social_dark);

        tabLayout.addTab(articles, 0);
        tabLayout.addTab(videos, 1);
        tabLayout.addTab(social, 2);

        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.WhiteColor));

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

}

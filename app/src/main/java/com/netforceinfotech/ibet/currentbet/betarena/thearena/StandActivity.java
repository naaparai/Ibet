package com.netforceinfotech.ibet.currentbet.betarena.thearena;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.netforceinfotech.ibet.R;
import com.netforceinfotech.ibet.dashboard.home.startnewbet.StartNewBetActivity;
import com.netforceinfotech.ibet.general.UserSessionManager;
import com.netforceinfotech.ibet.general.WrapContentViewPager;

public class StandActivity extends AppCompatActivity  implements View.OnClickListener{


    WrapContentViewPager viewPager;
    private Context context;

    CoordinatorLayout coordinatorLayout;
    UserSessionManager userSessionManager;
    int theme ;
    private String tagName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stand);


        context = getApplicationContext();

        userSessionManager = new UserSessionManager(getApplicationContext());
        theme = userSessionManager.getTheme();


        setupTab();
    }



    private void setupTab()
    {

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);

        viewPager = (WrapContentViewPager) findViewById(R.id.pager);
        viewPager.setPagingEnabled(false);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.all));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.top));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.friends));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        if(theme == 0)
        {
            //coordinatorLayout.setBackgroundResource(R.drawable.background_theme5);
            coordinatorLayout.setBackgroundResource(R.drawable.background_theme1);

        }
        else if(theme == 1)
        {

            coordinatorLayout.setBackgroundResource(R.drawable.background_theme2);

        }

        else if(theme == 2)
        {

            coordinatorLayout.setBackgroundResource(R.drawable.background_theme3);

        }
        else if(theme == 3)
        {


            coordinatorLayout.setBackgroundResource(R.drawable.background_theme4);


        }

        else if(theme == 4)
        {


            coordinatorLayout.setBackgroundResource(R.drawable.background_theme5);


        }


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
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
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.buttonStartnewBet:
                //go to new bet
                Intent intent = new Intent(context, StartNewBetActivity.class);
                startActivity(intent);
                break;
        }
    }

}

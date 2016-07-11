package com.netforceinfotech.ibet.dashboard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.netforceinfotech.ibet.MainActivity;
import com.netforceinfotech.ibet.R;
import com.netforceinfotech.ibet.general.UserSessionManager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class Dashboard extends AppCompatActivity {

    private DashboardFragment dashboardFragment;
    private Toolbar toolbar;
    private UserSessionManager userSessionManager;
    private AccountHeader headerResult;
    private String imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setupToolBar();
        setupDashboardFragment();
        userSessionManager = new UserSessionManager(getApplicationContext());
        String id = userSessionManager.getFBID();
        imageURL = "https://graph.facebook.com/" + id + "/picture?type=large";
        setupNavigation(imageURL);

    }

    private void setupNavigation(String imageURL) {
        PrimaryDrawerItem home = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.home);
        PrimaryDrawerItem profile = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.profile);
        PrimaryDrawerItem chart = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.chart);
        PrimaryDrawerItem highestrank = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.highestrank);
        SecondaryDrawerItem friends = (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withName(R.string.friends);
        SecondaryDrawerItem all = (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withName(R.string.all);
        PrimaryDrawerItem richestUsers = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.richestuser);
        PrimaryDrawerItem store = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.store);
        PrimaryDrawerItem setting = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.setting);
        PrimaryDrawerItem language = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.language);
        PrimaryDrawerItem notification = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.notification);
        SecondaryDrawerItem teamNotification = (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withName(R.string.teamnotification);
        SecondaryDrawerItem generalNotification = (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withName(R.string.generalnotification);
        PrimaryDrawerItem sounds = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.sounds);
        PrimaryDrawerItem themes = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.themes);
        PrimaryDrawerItem odds = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.odds);
        PrimaryDrawerItem info = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.info);
        PrimaryDrawerItem removeads = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.removeads);
        PrimaryDrawerItem feedback = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.feedback);
        PrimaryDrawerItem tutorial = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.tutorial);
        PrimaryDrawerItem share = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.share);
        PrimaryDrawerItem logout = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.logout);

//create the drawer and remember the `Drawer` result object
        setheaderImage(imageURL);
        AccountHeader accountHeader = getAccountHeader(imageURL);
        Drawer result = new DrawerBuilder()
                .withAccountHeader(accountHeader)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        home,
                        profile,
                        chart, new DividerDrawerItem(),
                        highestrank, friends, all, new DividerDrawerItem(),
                        richestUsers, friends, all, new DividerDrawerItem(),
                        store,
                        setting,
                        language, new DividerDrawerItem(),
                        notification, teamNotification, generalNotification, new DividerDrawerItem(),
                        sounds,
                        themes,
                        odds,
                        info,
                        removeads,
                        feedback,
                        tutorial,
                        share,
                        logout
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D

                        switch (position) {
                            case 29:
                                LoginManager.getInstance().logOut();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            default:
                                showMessage("Yet to implement" + position);
                                break;
                        }
                        return false;
                    }
                })
                .build();
    }

    private void setheaderImage(final String imageURL) {
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(imageURL).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }

    /*
    @Override
    public Drawable placeholder(Context ctx) {
        return super.placeholder(ctx);
    }

    @Override
    public Drawable placeholder(Context ctx, String tag) {
        return super.placeholder(ctx, tag);
    }
    */
        });
    }

    private AccountHeader getAccountHeader(String imageURL) {
        UserSessionManager userSessionManager = new UserSessionManager(getApplicationContext());
        String name = userSessionManager.getName();
        String email = userSessionManager.getEmail();
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withSelectionListEnabledForSingleProfile(false)
                .withHeaderBackground(R.drawable.background)
                .addProfiles(
                        new ProfileDrawerItem().withName(name).withEmail(email)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        return headerResult;
    }

    private void showMessage(String s) {
        Toast.makeText(Dashboard.this, s, Toast.LENGTH_SHORT).show();
    }

    private void setupToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void replaceFragment(Fragment newFragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.drawer_layout, newFragment, tag);
        transaction.commit();
    }

    private void setupDashboardFragment() {
        String teams = "Ibet";
        getSupportActionBar().setTitle(teams);
        if (dashboardFragment == null) {
            dashboardFragment = new DashboardFragment();
        }
        String tagName = dashboardFragment.getClass().getName();
        replaceFragment(dashboardFragment, tagName);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

}

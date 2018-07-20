package com.alex.playandroid.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.alex.playandroid.R;
import com.alex.playandroid.app.Configure;
import com.alex.playandroid.bean.HomeArticle;
import com.alex.playandroid.fragment.DemoFragment;
import com.alex.playandroid.fragment.HomeFragment;
import com.alex.playandroid.fragment.TypeFragment;
import com.alex.playandroid.fragment.WebsiteFragment;
import com.alex.playandroid.net.api.ArticleApi;
import com.alex.playandroid.net.callback.NetCallback;
import com.alex.playandroid.net.response.NetResponse;
import com.alex.playandroid.utils.LogUtil;
import com.alex.playandroid.utils.SharedPreUtils;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FragmentManager fragmentManager;

    private HomeFragment homeFragment;
    private TypeFragment typeFragment;
    private DemoFragment demoFragment;
    private WebsiteFragment websiteFragment;

    private BottomNavigationBar navigationBar;
    private Toolbar toolbar;
    private MaterialSearchView searchView;

    private Drawer drawer;
    private AccountHeader accountHeader;
    private IProfile userItem;
    private ProfileSettingDrawerItem addAccount;
    private List<PrimaryDrawerItem> drawerItemList;

    private int navigationBarMode;
    private int navigationBarStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActivityManager manager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        int heapSize = manager.getMemoryClass();
        LogUtil.e(TAG,"heapSize:"+heapSize);


        fragmentManager = getSupportFragmentManager();

        initToolbar();
        initSearchView();
        initNavigationBar();


        initUser();
        initHeader();
//        initDrawerItems();

        initDrawer();

        selectTabSelection(0);
    }

    private void initUser() {
        boolean isLogin = SharedPreUtils.getBoolean("isLogin", false, this);
        String username = isLogin ? SharedPreUtils.getString("username", this) : "";
        String email = isLogin ? SharedPreUtils.getString("email",this) : "未登录";
        userItem = new ProfileDrawerItem()
                .withName(username)
                .withEmail(email.equals("")?"":email)
                .withTextColor(Color.DKGRAY)
                .withSelectedTextColorRes(R.color.colorMain)
                .withIcon(R.mipmap.icon_avatar)
                .withSelectable(true);

        addAccount = new ProfileSettingDrawerItem()
                .withName("添加账号")
                .withTextColor(Color.DKGRAY)
                .withIcon(R.mipmap.icon_add)
                .withIdentifier(1);


    }

    private void initHeader() {
        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
//                .withAccountHeader(R.layout.drawer_account_bg)
                .addProfiles(userItem,addAccount)
                .withTextColor(Color.DKGRAY)
                .withCompactStyle(false)
                .withOnAccountHeaderListener((view, profile, currentProfile) -> {
                    switch ((int) profile.getIdentifier()) {
                        case 1:
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            break;
                    }
                    return true;
                })
                .build();
    }

    private PrimaryDrawerItem home,hierarchy,project;

    private void initDrawer(){
         home = new PrimaryDrawerItem().withName("首页").withIcon(R.drawable.selector_drawer_item_home).withIdentifier(1);
         hierarchy = new PrimaryDrawerItem().withName("体系").withIcon(R.drawable.selector_drawer_item_hierarchy).withIdentifier(2);
         project = new PrimaryDrawerItem().withName("项目").withIcon(R.drawable.selector_drawer_item_project).withIdentifier(3);

        DividerDrawerItem driver = new DividerDrawerItem();

        PrimaryDrawerItem collection = new PrimaryDrawerItem().withName("我的收藏").withIcon(R.drawable.selector_drawer_item_collection).withIdentifier(4);
        PrimaryDrawerItem setting = new PrimaryDrawerItem().withName("设置").withIcon(R.drawable.selector_drawer_item_setting).withIdentifier(5);
        drawer = new DrawerBuilder()
                .withAccountHeader(accountHeader)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(home,hierarchy,project,driver,collection,setting)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    switch ((int) drawerItem.getIdentifier()) {
                        case 1:
//                            drawer.closeDrawer();
                            selectTabSelection(0);
                            navigationBar.selectTab(0);
                            break;
                        case 2:
//                            drawer.closeDrawer();
                            selectTabSelection(1);
                            navigationBar.selectTab(1);
                            break;
                        case 3:
//                            drawer.closeDrawer();
                            selectTabSelection(2);
                            navigationBar.selectTab(2);
                            break;
                        case 4:
                            startActivity(new Intent(MainActivity.this, CollectionActivity.class));
                            break;
                        case 5:
                            startActivity(new Intent(MainActivity.this, SettingActivity.class));
                            break;
                    }
                    return false;
                })
                .build();
    }

    private void initDrawerItems(){
        drawerItemList = new ArrayList<>();
        PrimaryDrawerItem collection = new PrimaryDrawerItem().withName("我的收藏").withIcon(R.mipmap.icon_star_blue).withIdentifier(2);
        PrimaryDrawerItem setting = new PrimaryDrawerItem().withName("设置").withIcon(R.mipmap.icon_setting_blue).withIdentifier(3);
        drawerItemList.add(collection);
        drawerItemList.add(setting);
    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_title_left);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorMain));
        setSupportActionBar(toolbar);
    }


    private void initNavigationBar() {
        navigationBar = (BottomNavigationBar) findViewById(R.id.navigation_bar);
        navigationBar
                .setMode(Configure.BOTTOM_NAVIGATION_BAR_MODE)
                .setBackgroundStyle(Configure.BOTTOM_NAVIGATION_BAR_STYLE)
                .setActiveColor(R.color.white) //选中颜色
//                .setInActiveColor("#e9e6e6") //未选中颜色
                .setBarBackgroundColor(R.color.colorMain);//导航栏背景色
        navigationBar
                .addItem(new BottomNavigationItem(R.mipmap.icon_nav_home, "首页"))
                .addItem(new BottomNavigationItem(R.mipmap.icon_nav_type, "体系"))
//                .addItem(new BottomNavigationItem(R.mipmap.icon_nav_website, "导航"))
                .addItem(new BottomNavigationItem(R.mipmap.icon_nav_demo, "项目"))
                .initialise();
        navigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                selectTabSelection(position);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    private void initSearchView(){
        searchView = (MaterialSearchView) findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                LogUtil.e(TAG,"onQueryTextSubmit:"+query);
                search(0,query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.4f;
                getWindow().setAttributes(lp);
            }

            @Override
            public void onSearchViewClosed() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opt_scan:
//                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.article_menu, menu);
        MenuItem item = menu.findItem(R.id.opt_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }


    private void selectTabSelection(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (index) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.content_layout, homeFragment);
                } else {
                    fragmentTransaction.show(homeFragment);
                }
                drawer.setSelection(home,false);
                break;
            case 1:
                if (typeFragment == null) {
                    typeFragment = new TypeFragment();
                    fragmentTransaction.add(R.id.content_layout, typeFragment);
                } else {
                    fragmentTransaction.show(typeFragment);
                }
                drawer.setSelection(hierarchy,false);
                break;
//            case 2:
//                if (websiteFragment == null) {
//                    websiteFragment = new WebsiteFragment();
//                    fragmentTransaction.add(R.id.content_layout, websiteFragment);
//                } else {
//                    fragmentTransaction.show(websiteFragment);
//                }
//                break;
            case 2:
                if (demoFragment == null) {
                    demoFragment = new DemoFragment();
                    fragmentTransaction.add(R.id.content_layout, demoFragment);
                } else {
                    fragmentTransaction.show(demoFragment);
                }
                drawer.setSelection(project,false);
                break;

        }
        fragmentTransaction.commit();
    }

    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (typeFragment != null) {
            fragmentTransaction.hide(typeFragment);
        }
        if (websiteFragment != null) {
            fragmentTransaction.hide(websiteFragment);
        }
        if (demoFragment != null) {
            fragmentTransaction.hide(demoFragment);
        }
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(navigationBarMode!=Configure.BOTTOM_NAVIGATION_BAR_MODE||navigationBarStyle!=Configure.BOTTOM_NAVIGATION_BAR_STYLE){
            navigationBar.setMode(Configure.BOTTOM_NAVIGATION_BAR_MODE);
            navigationBar.setBackgroundStyle(Configure.BOTTOM_NAVIGATION_BAR_STYLE);
            navigationBar.initialise();
        }
    }


    private void search(int page,String s){
        ArticleApi.getInstance().search(0,s).enqueue(new NetCallback<NetResponse<HomeArticle>>() {
            @Override
            public void onSuccess(NetResponse<HomeArticle> data, String msg) {

            }
        });
    }

}

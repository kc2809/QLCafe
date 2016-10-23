package com.example.administrator.qlcafe;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.administrator.qlcafe.fragment.AddOrderFragment;
import com.example.administrator.qlcafe.fragment.MyFragmentPageAdapter;
import com.example.administrator.qlcafe.fragment.MyOrderFragment;
import com.example.administrator.qlcafe.fragment.PaymentFragment;

import java.util.ArrayList;


public class OrderActivity extends FragmentActivity implements  TabHost.OnTabChangeListener,ViewPager.OnPageChangeListener{

    ViewPager viewPager;
    TabHost tabHost;

    MyFragmentPageAdapter myFragmentPageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        addControls();

        initViewPage();
        initTabs();

        addListener();

    }

    private void initViewPage() {
        viewPager = (ViewPager)findViewById(R.id.view);
        ArrayList<Fragment> listFragments = new ArrayList<>();
        listFragments.add(new MyOrderFragment());
        listFragments.add(new AddOrderFragment());
        listFragments.add(new PaymentFragment());

        myFragmentPageAdapter = new MyFragmentPageAdapter(getSupportFragmentManager(),listFragments);

        viewPager.setAdapter(myFragmentPageAdapter);
        viewPager.setOnPageChangeListener(this);
    }

    private void initTabs() {
        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        String[] tabNames = {"My Order","Add Order","Payment"};
        for(int i=0;i<tabNames.length;++i){
            TabHost.TabSpec tabSpec;
            tabSpec = tabHost.newTabSpec(tabNames[i]);
            tabSpec.setIndicator(tabNames[i]);
            tabSpec.setContent(new FakeContent(getApplicationContext()));
            tabHost.addTab(tabSpec);
        }
        tabHost.setOnTabChangedListener(this);
        tabHost.getTabWidget().setBackgroundColor(Color.parseColor("#FFFFFF"));
        setTabColor(tabHost);

    }

    public void setTabColor(TabHost tabHost){
        for(int i=0;i<tabHost.getTabWidget().getChildCount();++i){
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FFFFFF"));
            TextView t = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            t.setTextColor(Color.parseColor("#45000000"));
        }
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.shape_bottom_color);
        TextView t1 = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(android.R.id.title);
        t1.setTextColor(Color.parseColor("#009F9F"));
    }


    private void addControls() {

    }

    private void addListener() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String s) {
        int selectedItem = tabHost.getCurrentTab();
        viewPager.setCurrentItem(selectedItem);
        setTabColor(tabHost);
    }

    public class FakeContent implements  TabHost.TabContentFactory{

        Context context;
        public FakeContent(Context context){
            this.context = context;
        }

        @Override
        public View createTabContent(String s) {
            View fakeView = new View(context);
            fakeView.setMinimumHeight(0);
            fakeView.setMinimumWidth(0);
            return fakeView;
        }
    }
}

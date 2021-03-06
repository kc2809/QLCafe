package com.example.administrator.qlcafe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.administrator.qlcafe.fragment.AddOrderFragment;
import com.example.administrator.qlcafe.fragment.MyFragmentPageAdapter;
import com.example.administrator.qlcafe.fragment.MyOrderFragment;
import com.example.administrator.qlcafe.fragment.PaymentFragment;
import com.example.administrator.qlcafe.model.Table;

import java.util.ArrayList;


public class OrderActivity extends FragmentActivity implements  TabHost.OnTabChangeListener,ViewPager.OnPageChangeListener{

    ViewPager viewPager;
    TabHost tabHost;
    TextView title;

    ImageButton imgBack;

    MyFragmentPageAdapter myFragmentPageAdapter;
    Table banSelected;

    public static boolean isChanged = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        addControls();

        getData();

        initViewPage();
        initTabs();

        addListener();

    }

    public Table getBanSelected(){
        return banSelected;
    }

    private void getData(){
        Intent intent =getIntent();
        Bundle b= intent.getBundleExtra("DATA");
        banSelected = (Table)b.getSerializable("BAN");

        title.setText(banSelected.getLabel() +" -> Order");
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
        tabHost.setCurrentTab(1);
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
        title = (TextView)findViewById(R.id.tvTitle);
        imgBack = (ImageButton)findViewById(R.id.imgBtnBack);
    }

    private void addListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishOrder();
            }
        });
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

        if(isChanged){
            refresh();
            isChanged = false;
        }
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

    public void refresh(){
        myFragmentPageAdapter.replace(new MyOrderFragment(),0);
        myFragmentPageAdapter.notifyDataSetChanged();
    }

    public void finishOrder(){
        Intent intent = getIntent();
        setResult(Constant.REFRESH_ACTIVITY,intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println("TAO NHAN NUT BACK");
        finishOrder();
    }
}

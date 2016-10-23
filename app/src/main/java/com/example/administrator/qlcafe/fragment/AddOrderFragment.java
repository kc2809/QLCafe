package com.example.administrator.qlcafe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.qlcafe.ListProductActivity;
import com.example.administrator.qlcafe.R;

/**
 * Created by Administrator on 10/19/2016.
 */
public class AddOrderFragment extends Fragment {
    Button btnMenuList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_order_layout,container,false);
        btnMenuList = (Button)v.findViewById(R.id.btnMenuList);
        btnMenuList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), ListProductActivity.class),2000);
            }
        });

        return v;
    }
}

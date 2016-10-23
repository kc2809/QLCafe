package com.example.administrator.qlcafe.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.qlcafe.R;

/**
 * Created by Administrator on 10/19/2016.
 */
public class PaymentFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.payment_layout,container,false);

        return v;
    }
}

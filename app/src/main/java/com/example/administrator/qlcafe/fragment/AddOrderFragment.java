package com.example.administrator.qlcafe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.administrator.qlcafe.Constant;
import com.example.administrator.qlcafe.ListTableActivity;
import com.example.administrator.qlcafe.ModifyQuantityActivity;
import com.example.administrator.qlcafe.OrderActivity;
import com.example.administrator.qlcafe.R;
import com.example.administrator.qlcafe.adapter.ProductValueAdapter;
import com.example.administrator.qlcafe.model.Food;
import com.example.administrator.qlcafe.model.Table;

import java.util.ArrayList;

/**
 * Created by Administrator on 10/19/2016.
 */
public class AddOrderFragment extends Fragment implements Constant {
   // Button btnMenuList;
    ListView lvFood;
    EditText edtSearch;
    ArrayList<Food> arrFoodList;
    ProductValueAdapter valueAdapter;
    TextWatcher searchTw;

    Food foodSelected;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_order_layout,container,false);
      //  btnMenuList = (Button)v.findViewById(R.id.btnMenuList);
        edtSearch = (EditText)v.findViewById(R.id.edtSerach);
        lvFood = (ListView)v.findViewById(R.id.lvProductList);

        initDataTest();
        init();

        return v;
    }

    private void init(){
        valueAdapter = new ProductValueAdapter(arrFoodList,getActivity());
        lvFood.setAdapter(valueAdapter);
        edtSearch.addTextChangedListener(searchTw);

        lvFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                foodSelected = valueAdapter.getArrFoodList().get(i);
                Intent in = new Intent(getActivity(), ModifyQuantityActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("FOOD", foodSelected);
                b.putInt("QUANTITY", 0);
                b.putSerializable("BAN", getBanSelected());
                b.putInt("REQUEST", OPEN_MODIFY_ACTIVITY_FOR_ADD);
                in.putExtra("DATA", b);
                startActivityForResult(in, OPEN_MODIFY_ACTIVITY_FOR_ADD);
            }
        });
    }
    private void initDataTest(){
        arrFoodList = ListTableActivity.menuFood;

        searchTw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                valueAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private Table getBanSelected(){
        OrderActivity activity = (OrderActivity)getActivity();
        return activity.getBanSelected();
    }
}

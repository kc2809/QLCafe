package com.example.administrator.qlcafe.fragment;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.qlcafe.Constant;
import com.example.administrator.qlcafe.ListTableActivity;
import com.example.administrator.qlcafe.MainActivity;
import com.example.administrator.qlcafe.OrderActivity;
import com.example.administrator.qlcafe.R;
import com.example.administrator.qlcafe.model.BillDetail;
import com.example.administrator.qlcafe.model.Table;
import com.example.administrator.qlcafe.process.data.ProcessData;

/**
 * Created by Administrator on 10/19/2016.
 */
public class PaymentFragment extends Fragment {

    ScrollView mainView;
    Button btnLoad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.payment_layout,container,false);
        mainView = (ScrollView)v.findViewById(R.id.scrollView);
        btnLoad = (Button)v.findViewById(R.id.buttonLoad);



        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new PaymentTask()).execute();
            }
        });

        return v;
    }

    private Table getBanSelected() {
        OrderActivity activity = (OrderActivity)getActivity();
        return  activity.getBanSelected();
    }

    private void loadDataServer(BillDetail bill){
        TableLayout mTableLayout = new TableLayout(getActivity());
        mTableLayout.setLayoutParams(new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mTableLayout.setStretchAllColumns(true);

        TextView tenTitle = new TextView(getActivity());
        TextView soluongTitle = new TextView(getActivity());
        TextView dongiaTitle = new TextView(getActivity());
        TextView thanhtienTitle = new TextView(getActivity());

        tenTitle.setTypeface(null, Typeface.BOLD);
        soluongTitle.setTypeface(null, Typeface.BOLD);
        dongiaTitle.setTypeface(null, Typeface.BOLD);
        thanhtienTitle.setTypeface(null, Typeface.BOLD);

        tenTitle.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        soluongTitle.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dongiaTitle.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        thanhtienTitle.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        tenTitle.setText("Tên");
        soluongTitle.setText("Số Lượng      ");
        dongiaTitle.setText("Dơn Giá    ");
        thanhtienTitle.setText("Thành tiền          ");
        TableRow rowTitle = new TableRow(getActivity());

        rowTitle.addView(tenTitle);
        rowTitle.addView(soluongTitle);
        rowTitle.addView(dongiaTitle);
        rowTitle.addView(thanhtienTitle);

        mTableLayout.addView(rowTitle);


        for (int count = 0; count < bill.getDsOrder().size(); count++) {
            TableRow row = new TableRow(getActivity());
            row.setLayoutParams((new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)));

            TextView ten = new TextView(getActivity());
            TextView soluong = new TextView(getActivity());
            TextView dongia = new TextView(getActivity());
            TextView thanhtien = new TextView(getActivity());

            soluong.setGravity(Gravity.CENTER);
            dongia.setGravity(Gravity.CENTER);
            thanhtien.setGravity(Gravity.CENTER);

            ten.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            soluong.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dongia.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            thanhtien.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            int id = bill.getDsOrder().get(count).getIdMon();
            ten.setText(ListTableActivity.searchFood(id).getName());
            soluong.setText(bill.getDsOrder().get(count).getSoLuong()+"");
            dongia.setText( String.format("%,d",bill.getDsOrder().get(count).getDongia())+"");
            thanhtien.setText(  String.format("%,d",bill.getDsOrder().get(count).getThanhtien())+"");


            row.addView(ten);
            row.addView(soluong);
            row.addView(dongia);
            row.addView(thanhtien);


            mTableLayout.addView(row);
        }

        //tong thanh tien
        TextView tvThanhTien = new TextView(getActivity());
        tvThanhTien.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tvThanhTien.setTypeface(null, Typeface.BOLD);
        tvThanhTien.setTextSize(20);
        tvThanhTien.setText("Tổng:      " + String.format("%,d",bill.getSum()) + "  VND");

        TableRow.LayoutParams params = (TableRow.LayoutParams)tvThanhTien.getLayoutParams();
        params.span = 4;

        tvThanhTien.setLayoutParams(params);

        TableRow rowTong = new TableRow(getActivity());
        rowTong.addView(tvThanhTien);
        mTableLayout.addView(rowTong);
        //
        mainView.removeAllViews();
        mainView.addView(mTableLayout);
    }


    //---------------
    private class PaymentTask extends AsyncTask<String,Integer,BillDetail> {

        @Override
        protected BillDetail doInBackground(String... strings) {


            String request = "idTable=" +getBanSelected().getId()+
                    "&key="+ MainActivity.getKey();
            String url = Constant.url_payment + request;

            String s =  ProcessData.getInstance().docNoiDung_Tu_URL(url);

       //     String result = ProcessData.getInstance().xmlParseLogin(ProcessData.getInstance().getDomElement(s));
           BillDetail bill =  ProcessData.getInstance().xmlParseBillDetail(ProcessData.getInstance().getDomElement(s));

            return bill;
        }

        @Override
        protected void onPostExecute(BillDetail bill) {
            super.onPostExecute(bill);

       //     finishOrderActivity();
           if(bill.getSum() == 0){
               //flase
               Toast.makeText(getActivity(),"Bàn đã tính tiền rồi. ",Toast.LENGTH_LONG).show();
           }
           else{
               loadDataServer(bill);
           }
        }
    }

}

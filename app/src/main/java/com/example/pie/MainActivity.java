package com.example.pie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import android.content.Intent;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.Color;
//import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
//import android.view.View;
//import android.widget.ImageButton;
//
//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.components.Legend;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.formatter.PercentFormatter;
//import com.github.mikephil.charting.utils.ColorTemplate;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.navigation.NavigationBarView;
//
//import java.util.ArrayList;



//import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

//import android.os.Bundle;
//import android.view.MenuItem;
//import android.widget.TextView;
//
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.http.Path;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.widget.TextView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // ************ VARIABLES *****************
    PieChart pieChart1,pieChart2,pieChart3,pieChart4;
    PieData pieData1,pieData2,pieData3,pieData4;
    List<PieEntry> pieEntryList1 = new ArrayList<>();
    List<PieEntry> pieEntryList2 = new ArrayList<>();
    List<PieEntry> pieEntryList3 = new ArrayList<>();
    List<PieEntry> pieEntryList4 = new ArrayList<>();
    BottomNavigationView bottom;
    int period=3; // the user choose one of the options -> period=week/month/year
    // by default choose period=> month

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //**************** FIND BY ID's *********************
        bottom = findViewById(R.id.BottomNavigation);
        bottom.setSelectedItemId(R.id.BottomNavigation);
        Button button_year=findViewById(R.id.button_year);
        Button button_week=findViewById(R.id.button_week);
        Button button_month=findViewById(R.id.button_month);
        button_year.setOnClickListener(this);
        button_week.setOnClickListener(this);
        button_month.setOnClickListener(this);

        //*************** NAVIGATION BAR *********************
        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.home_frag:
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        return true;
                    case R.id.users_frag:
                        fragment=new Users_Screen();
                        break;
                    case R.id.courses_frag:
                        fragment=new courses_screen();
                        break;
                    case R.id.leaders_frag:
                        fragment=new Leaders_screen();
                        break;
                    case R.id.more_frag:
                        fragment=new More_screen();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main,fragment).commit();
                return true;
            }
        });

//        BarChart barChart;
//        BarData barData;
//        BarDataSet barDataSet;
//        ArrayList barEntries;
//        barEntries = new ArrayList<>();
//        barChart = findViewById(R.id.bar_chart);
//        getEntries(barEntries);
//        barDataSet = new BarDataSet(barEntries, "");
//        barData = new BarData(barDataSet);
//        barChart.setData(barData);
//        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
//        barDataSet.setValueTextColor(Color.BLACK);
//        barDataSet.setValueTextSize(18f);
        BarChart barChart;
        BarData barData;
        BarDataSet barDataSet;
        ArrayList barEntries;
        barEntries = new ArrayList<>();
        barChart = findViewById(R.id.bar_chart);
        getEntries(barEntries);
        barDataSet = new BarDataSet(barEntries, "");
        barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(18f);
       getData(3 );
    }

//*******************************************************************************//
private void getData(int period) {
    // create retrofit builder and pass our base url
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5840/")
            // when sending data in json format we have to add Gson converter factory
            .addConverterFactory(GsonConverterFactory.create())
            // and build our retrofit builder.
            .build();
    // create an instance for our retrofit api class.
    retrofitAPI retrofitAPI = retrofit.create(retrofitAPI.class);

    Call<Integer> callNewActiveParent = retrofitAPI.getNewActiveParent(period);
    Call<Integer> callActivity = retrofitAPI.Activity(period);
    Call<Integer> callNewKids = retrofitAPI.NewKids(period);
    Call<Double> callKidPercentage=retrofitAPI.getKidPercentage(period);
    Call<Double> callPercentageOfNewParents= retrofitAPI.getPercentageOfNewParents(period);
    Call<Double> callPercentageOfActivities=retrofitAPI.getPercentageOfActivities(period);
    Call <HashMap<String, Integer>> callTotalKidsPeCategor=retrofitAPI.TotalKidsPeCategor(period);
    Call <HashMap<Integer, HashMap<String, Integer >>> callTotalKidsPerCategorForBar=retrofitAPI.TotalKidsPerCategorForBar(period);


    // add the http request to queue…
    //********************** second chart ***********************
//
    callPercentageOfNewParents.enqueue(new Callback<Double>() {
        @Override
        public void onResponse(Call<Double> call, Response<Double> response) {
            // ******* CHART NEW PARENT - CHART 2 ************
            Double  percentage2 = response.body();
            TextView circle2=findViewById(R.id.text2);
            circle2.setText(String.valueOf(percentage2.intValue())+"%");

        }

        @Override
        public void onFailure(Call<Double> call, Throwable t) {
            int x=5;
        }
    });
    callNewActiveParent.enqueue(new Callback<Integer>() {
        @Override
        public void onResponse(Call<Integer> call, Response<Integer> response) {
            // ******* CHART NEW PARENT - CHART 2 ************
            Integer  num2 = response.body();
            //System.out.println("***********"+num2);
            pieChart2 = findViewById(R.id.pieChart2);
            List<PieEntry> pieEntryList2 = new ArrayList<>();
            pieChart2.setUsePercentValues(true);
            pieEntryList2.add(new PieEntry(num2, ""));
            pieEntryList2.add(new PieEntry(100-num2, ""));
            pieChart2.setCenterText(String.valueOf(num2));
            PieDataSet pieDataSet2 = new PieDataSet(pieEntryList2, "");
            pieDataSet2.setDrawValues(false);
            pieDataSet2.setColors(Color.rgb(255,165,0),Color.GRAY);
            pieChart2.setHoleRadius(80);
            pieDataSet2.setValueTextSize(12f);
            pieData2= new PieData(pieDataSet2);
            pieChart2.setData(pieData2);
            pieChart2.invalidate();
            pieChart2.getDescription().setEnabled(false);
            pieChart2.getLegend().setEnabled(false);

        }

        @Override
        public void onFailure(Call<Integer> call, Throwable t) {
            int x=5;
        }
    });

    //********************** first chart ***********************

    callActivity.enqueue(new Callback<Integer>() {
        @Override
        public void onResponse(Call<Integer> call, Response<Integer> response) {
            // ******* ACTIVITIES IN HOUR - CHART 1 ************
            List<PieEntry> pieEntryList1 = new ArrayList<>();
            Integer  num1 = response.body();
            //System.out.println("***********"+num1);
            pieChart1 = findViewById(R.id.pieChart1);
            pieChart1.setUsePercentValues(true);
            pieEntryList1.add(new PieEntry(num1, ""));
            pieEntryList1.add(new PieEntry(100-num1, ""));
            pieChart1.setCenterText(String.valueOf(num1));
            PieDataSet pieDataSet1 = new PieDataSet(pieEntryList1, "");
            pieDataSet1.setDrawValues(false);
            pieDataSet1.setColors(Color.rgb(255,165,0),Color.GRAY);
            pieChart1.setHoleRadius(80);
            pieDataSet1.setValueTextSize(12f);
            pieData1 = new PieData(pieDataSet1);
            pieChart1.setData(pieData1);
            pieChart1.invalidate();
            pieChart1.getDescription().setEnabled(false);
            pieChart1.getLegend().setEnabled(false);

        }

        @Override
        public void onFailure(Call<Integer> call, Throwable t) {
//            int x=5;
        }
    });
    callPercentageOfActivities.enqueue(new Callback<Double>() {
        @Override
        public void onResponse(Call<Double> call, Response<Double> response) {
            // ******* CHART NEW PARENT - CHART 1 ************
            Double  percentage1 = response.body();
            TextView circle1=findViewById(R.id.text1);
            circle1.setText(String.valueOf(percentage1.intValue())+"%");
        }

        @Override
        public void onFailure(Call<Double> call, Throwable t) {
            int x=5;
        }
    });

//    //********************** third chart ***********************
//
    callNewKids.enqueue(new Callback<Integer>() {
        @Override
        public void onResponse(Call<Integer> call, Response<Integer> response) {
            // ******* NEW CHILDREN - CHART 3 ************
            Integer  num3 = response.body();
           // System.out.println("***********"+num1);


            List<PieEntry> pieEntryList3 = new ArrayList<>();
            pieChart3 = findViewById(R.id.pieChart3);

         pieChart3.setUsePercentValues(true);
            pieEntryList3.add(new PieEntry(num3, ""));
            pieEntryList3.add(new PieEntry(100-num3, ""));
            pieChart3.setCenterText(String.valueOf(num3));
            PieDataSet pieDataSet3 = new PieDataSet(pieEntryList3, "");
            pieDataSet3.setDrawValues(false);
            pieDataSet3.setColors(Color.rgb(255,165,0),Color.GRAY);
            pieChart3.setHoleRadius(80);
            pieDataSet3.setValueTextSize(12f);
            pieData3 = new PieData(pieDataSet3);
            pieChart3.setData(pieData3);
            pieChart3.invalidate();
            pieChart3.getDescription().setEnabled(false);
            pieChart3.getLegend().setEnabled(false);

        }

        @Override
        public void onFailure(Call<Integer> call, Throwable t) {
            int x=5;
        }
    });
    callKidPercentage.enqueue(new Callback<Double>() {
        @Override
        public void onResponse(Call<Double> call, Response<Double> response) {
            Double  percentage3 = response.body();
            TextView circle2=findViewById(R.id.text3);
            circle2.setText(String.valueOf(percentage3.intValue())+"%");
        }
        @Override
        public void onFailure(Call<Double> call, Throwable t) {
            int x=5;
        }
    });
    ////////////////////////cat///////////
    callTotalKidsPeCategor.enqueue(new Callback<HashMap<String, Integer>>() {
        @Override
        public void onResponse(Call<HashMap<String, Integer>> call, Response<HashMap<String, Integer>> response) {
            // ******* CHART NEW PARENT - CHART 2 ************
            HashMap<String, Integer>  percentage2 = response.body();
            List<PieEntry> pieEntryList4 = new ArrayList<>();
            pieChart4 = findViewById(R.id.pieChart4);
            int num3=10;
            pieChart4.setUsePercentValues(true);
            pieEntryList4.add(new PieEntry(percentage2.get(Integer.toString(0)), "Animals"));
            pieEntryList4.add(new PieEntry(percentage2.get(Integer.toString(1)), "Spase"));
            pieEntryList4.add(new PieEntry(percentage2.get(Integer.toString(2)), "art"));
            // pieChart4.setCenterText(String.valueOf(num3));
            PieDataSet pieDataSet4 = new PieDataSet(pieEntryList4, "");
            pieDataSet4.setDrawValues(false);
            pieDataSet4.setColors(ColorTemplate.JOYFUL_COLORS);
            // pieChart4.setHoleRadius(0);
            pieChart4.setDrawHoleEnabled(false);
            pieDataSet4.setValueTextSize(12f);
            pieData4 = new PieData(pieDataSet4);
            pieChart4.setData(pieData4);
            pieChart4.invalidate();
            pieChart4.getDescription().setEnabled(false);
            pieChart4.getLegend().setEnabled(false);

        }

        @Override
        public void onFailure(Call<HashMap<String, Integer>> call, Throwable t) {
            int x=5;
        }
    });

    callTotalKidsPerCategorForBar.enqueue(new Callback<HashMap<Integer, HashMap<String, Integer >>>() {
        @Override
        public void onResponse(Call<HashMap<Integer, HashMap<String, Integer >>> call, Response<HashMap<Integer, HashMap<String, Integer >>> response) {
//            // *** CHART NEW PARENT - CHART 2 ****
//            HashMap<Integer, HashMap<String, Integer >>  percentage2 = response.body();
//            BarChart barChart= (BarChart) findViewById(R.id.bar_chart);
//            BarData barData1;
//            BarDataSet set1,set2,set3; // for each category
//            ArrayList<BarEntry> barEntries1,barEntries2,barEntries3;
//            barEntries1 = new ArrayList<>();
//            barEntries2 = new ArrayList<>();
//            barEntries3 = new ArrayList<>();
//
//            if(period==3){ // year
//                for(int i=1; i<13;i++){
//                    int x1= percentage2.get(i).get(Integer.toString(0));
//                    int x2= percentage2.get(i).get(Integer.toString(1));
//                    int x3= percentage2.get(i).get(Integer.toString(2));
//
//
////                    barEntries1.add(new BarEntry(i,x1));
////                    barEntries2.add(new BarEntry(i,x2));
////                    barEntries3.add(new BarEntry(i,x3));
//                }
//                getEntries(barEntries1);
//                getEntries(barEntries2);
//                getEntries(barEntries3);
//                set1= new BarDataSet(barEntries1,"Category1");
//                set1.setColors(Color.GREEN);
//
//                set2= new BarDataSet(barEntries2,"Category2");
//                set2.setColors(Color.BLUE);
//
//                set3= new BarDataSet(barEntries3,"Category3");
//                set3.setColors(Color.YELLOW);
//
//                barData1=new BarData(set1,set2,set3);
//                barChart.setData(barData1);
//            }
//

//            BarChart barChart= (BarChart) findViewById(R.id.bar_chart);
//            BarData barData1;
//            BarDataSet set1,set2,set3; // for each category
//            ArrayList<BarEntry> barEntries1,barEntries2,barEntries3;


//            ArrayList barEntries;
//            barEntries = new ArrayList<>();
//            barChart = findViewById(R.id.bar_chart);
//            getEntries(barEntries);
//            barDataSet = new BarDataSet(barEntries, "");
//            barData = new BarData(barDataSet);
//            barChart.setData(barData);
//            barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
//            barDataSet.setValueTextColor(Color.BLACK);
//            barDataSet.setValueTextSize(18f);

        }

        @Override
        public void onFailure(Call<HashMap<Integer, HashMap<String, Integer >>> call, Throwable t) {
            int x=5;
        }
    });

}
////////barchart///////////////


    // this functions set's data for bar chart
    private void getEntries(ArrayList barEntries){

        barEntries.add(new BarEntry(2f, 0));
        barEntries.add(new BarEntry(4f, 1));
        barEntries.add(new BarEntry(6f, 1));
        barEntries.add(new BarEntry(8f, 3));
        barEntries.add(new BarEntry(7f, 4));
        barEntries.add(new BarEntry(3f, 3));

    }

        @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_year:
                period=3;
               getData(3 );
                Toast.makeText(this, "Button 1 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_month:
                period=2;
                getData(2 );
                Toast.makeText(this, "Button 2 clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_week:
                period=1;
               getData(1 );
                Toast.makeText(this, "Button 3 clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }

//    public void setPie() {
//
//       // List<PieEntry> pieEntryList3 = new ArrayList<>();
//        pieChart4 = findViewById(R.id.pieChart4);
//        int num3=10;
//        pieChart4.setUsePercentValues(true);
//        pieEntryList4.add(new PieEntry(num3, "hh"));
//        pieEntryList4.add(new PieEntry(100-num3, "nnn"));
//       // pieChart4.setCenterText(String.valueOf(num3));
//        PieDataSet pieDataSet4 = new PieDataSet(pieEntryList4, "");
//        pieDataSet4.setDrawValues(false);
//        pieDataSet4.setColors(Color.rgb(255,165,0),Color.GRAY);
//       // pieChart4.setHoleRadius(0);
//        pieChart4.setDrawHoleEnabled(false);
//        pieDataSet4.setValueTextSize(12f);
//        pieData4 = new PieData(pieDataSet4);
//        pieChart4.setData(pieData4);
//        pieChart4.invalidate();
//        pieChart4.getDescription().setEnabled(false);
//        pieChart4.getLegend().setEnabled(false);
//    }


}
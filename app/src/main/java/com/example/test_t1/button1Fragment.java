package com.example.test_t1;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class button1Fragment extends Fragment
{
    private View view;
    private LineChart chart;//
    private button2Fragment btn2Fragment;
    private String fac_line1;
    private List csv_list;
    private Iterator iter_csv;
    private  List list_csv;


    ArrayList<issue_data> issueDataList;
    private boolean is_item= false;
    int i;
    int last_index;
    MyAdapter myAdapter;
    ListView listView;
    Handler handler;
    Thread data_run;
    BottomNavigationView bottomNavigationView;



    Animation anim_test;
    Button btn_test;




    @Override
    public void onStop()
    {


        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("data_list", issueDataList);

        bundle.putInt("cur_index",i);
        this.setArguments(bundle);
        data_run.interrupt();
        super.onStop();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)

    {

        issueDataList = new ArrayList<issue_data>();

        i=0;
        if(getArguments() != null)
        {
            Bundle bundle = getArguments();
            issueDataList = bundle.getParcelableArrayList("data_list");
            i= bundle.getInt("cur_index");
        }
        view = inflater.inflate(R.layout.fragment_btn1,container,false);
        listView = (ListView) view.findViewById(R.id.listview);

        handler = new Handler();
        //??????????????????????????? ????????? ??????
        myAdapter = new MyAdapter(getContext(),issueDataList);
        //myAdapter = new ArrayAdapter<String>(getContext(), R.layout.listview_text_color);
        listView.setAdapter(myAdapter);
        ///listView.setTextColor(Color.GRAY);
        Read_csv read_csv = new Read_csv();


        //myAdapter = new ArrayAdapter<String>(this,R.layout.listview_color,ArrayList);
        //listView = (ListView) findViewById(R.id.listview);
        //listView.setAdapter(myAdapter);
        //listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        //myAdapter= new ArrayAdapter<string>(getContext(), R.layout. listview_color );
        //myAdapter = new ArrayAdapter<String>(this,R.layout. listview_color,issueDataList);



        String path ="/data/user/0/com.example.test_t1/files/sample1.csv"; //??????????????? ?????? ?????? ??????????????? ????????????????????? ,??????????????? ????????????????????? ????????? ???????????? ??????
        // ????????? ??????
        //String path ="/?????? ?????????/Download/sample1.csv"; //?????? ??????????????????????????? ??????????????? ????????? ????????????..
        //String path = "/?????? ?????????/Android/media/sample1.csv";


        //String path ="/data/com.example.test_t1/files/sample1.csv";
        //String path = "/storage/emulated/0/Android/media/sample1.csv";  //???


        csv_list = read_csv.readCSV(path);

        list_csv = (List) csv_list.get(0);
        //?????? ?????????
        last_index = list_csv.size();
        iter_csv= list_csv.iterator();
        bottomNavigationView = view.findViewById(R.id.nav_view);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {

                //?????? ???????????????????????? ?????? ?????? ?????? ???
            Bundle bundle = new Bundle();
            bundle.putString("line", myAdapter.getItem(position).getFac_line());
            btn2Fragment = new button2Fragment();
            btn2Fragment.setArguments(bundle);
//            Navigation.findNavController(view).navigate(R.id.action_navigation_btn1_to_navigation_btn2,bundle);//?????? ?????? ???????????? ????????????????????? ?????????
                MainActivity mainActivity = new MainActivity();
                mainActivity.selected3();
                //navigate()??? ???????????? ??????????????? ??????????????? ????????????
                //navigate()??? ???????????? ??????????????? ?????? NavController.navigateup(), NavController.popBackStack()???????????? ???????????? ???????????????????????? ?????? ???.
                //navigate()?????? , ??????????????????
            }
        });

        //????????????
        chart = (LineChart) view.findViewById(R.id.data_chart);
        chart.setDrawGridBackground(true);
        chart.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        chart.setGridBackgroundColor(Color.parseColor("#00FFFFFF"));

// description text
        chart.getDescription().setEnabled(true);
        Description des = chart.getDescription();
        des.setEnabled(true);
        des.setText("Factory DATA");
        des.setTextSize(15f);
        des.setTextColor(Color.WHITE);

// touch gestures (false-????????????)
        chart.setTouchEnabled(false);

// scaling and dragging (false-????????????)
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
//auto scale
        chart.setAutoScaleMinMaxEnabled(false);

// if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        //chart.setData(BarDataSet( ,"??????"));
        //chart.setVisibleYRange(0.03f,0.03f, YAxis.AxisDependency.LEFT);//
//X???
        chart.getXAxis().setDrawGridLines(true);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setEnabled(true);
        chart.getXAxis().setDrawGridLines(false);

//Legend
        Legend l = chart.getLegend();
        l.setEnabled(true);
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setTextSize(12f);
        l.setTextColor(Color.WHITE);

//Y???
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.setTextColor(getResources().getColor(R.color.purple_200));
        leftAxis.setDrawGridLines(false); //?????????
        leftAxis.setGridColor(getResources().getColor(R.color.purple_700));
        leftAxis.setAxisMaximum(0.06f);
        leftAxis.setAxisMinimum(-0.06f);
        //leftAxis.setDrawLimitLinesBehindData(true);

        //????????? ??????
        LimitLine upper_limit = new LimitLine(0.03f, "Max Value");
        upper_limit.setLineWidth(2f);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextSize(10f);
        upper_limit.setTextColor(Color.WHITE);

        leftAxis.setDrawLimitLinesBehindData(true);
        leftAxis.addLimitLine(upper_limit);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

// don't forget to refresh the drawing
        chart.invalidate();

       //????????? ??????????????????
        data_run =new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    //????????? ????????? ????????? ?????????
                    while (i<last_index)
                    {
                        String temp=list_csv.get(i).toString();
                        float num_f = Float.parseFloat(temp);
                        addEntry(num_f);
                        //???????????? num_f?????? ???????????? ????????? ??????

                        if(num_f > 0.03f)
                        {
                            handler.postDelayed(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    add_Item(0,0);
                                    myAdapter.notifyDataSetChanged();
                                    listView.setAdapter(myAdapter);

                                }
                            }, 30);
                        }
                        i++;
                        Thread.sleep(30);
                    }
                } catch (Exception e)
                {
                }
            }
        });

        data_run.start();
        return view;
    }

    public void addEntry(double num) {

        LineData data = chart.getData();

        if (data == null) {
            data = new LineData();
            chart.setData(data);
        }

        ILineDataSet set = data.getDataSetByIndex(0);
        // set.addEntry(...); // can be called as well

        if (set == null) {
            set = createSet();
            data.addDataSet(set);
        }

        data.addEntry(new Entry((float)set.getEntryCount(), (float)num), 0);
        data.notifyDataChanged();

        // let the chart know it's data has changed
        chart.notifyDataSetChanged();

        chart.setVisibleXRangeMaximum(30);
        // this automatically refreshes the chart (calls invalidate())
        chart.moveViewTo(data.getXMax(), 50f, YAxis.AxisDependency.LEFT);
    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "Real-time Line Data");



        set.setLineWidth(1f);
        set.setDrawValues(false);
        set.setValueTextColor(getResources().getColor(R.color.white));
        set.setColor(Color.parseColor("#02F687"));
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setDrawCircles(false);
        set.setHighLightColor(Color.rgb(190, 190, 190));
        set.setDrawFilled(true);        // ?????? ????????? fill(?????????) ??????




        set.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return chart.getAxisLeft().getAxisMinimum();

            }

        });

        //set.setFillDrawable(ContextCompat.getDrawable(.getApplication(), R.drawable.gradient3));

        if (Utils.getSDKInt() >= 18) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.gradient3);
            set.setFillDrawable(drawable);
        } else {
            set.setFillColor(Color.DKGRAY);
        }



        //Set.setDrawFilled(true);
        //set.setFillDrawable(gradientDrawable);

        //LineDataSet set1 = new LineDataSet(values, "DataSet 1");

        //plot.getRenderer().setSeriesPaint(seriesIndex, new GradientPaint(0.0f, 0.0f, new Color(255, 0, 0), 0.0f, 0.0f, new Color(0, 255, 255)));

        //if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            //Drawable drawable = ContextCompat.getDrawable(this, R.drawable.gradient3);
            //Drawable mDrawable = ContextCompat.getDrawable(Context, R.drawable.gradient3);
            //set.setFillDrawable(mDrawable);
            //} else {
            //set.setFillColor(Color.BLACK);
            //}

        return set;
    }

    public void make_Item(int image, String issue ,String fac_line, String moter_name, String day_time ,String moter_state,String solution)
    {
        System.out.println("????????? ??????");
        issueDataList.add(new issue_data(image, issue, fac_line, moter_name, day_time, moter_state, solution));

    }



    public  void add_Item(int server_issue , int server_state) {
        //????????? ?????? ????????? ????????? ????????????????????? ?????????. ??????,??????,?????????, ??????, ????????????, ????????????
        int s_issue = server_issue;
        int s_moter_state = server_state;

        //?????????????????? int?????? ????????????????????? ????????? str?????? ??????- ?????? ?????????????????????

        //?????????str??? add??? ??????
        String[] issues = {"??????", "??????", "??????", "??????", "??????"};
        String[] fac_lines = {"A1", "A2", "A3", "A4", "A5"};
        String[] moter_states = {"??????", "???????????????", "??????????????????", "???????????????", "???????????????"};
        String[] solution = {"????????????", "????????????"};


        make_Item(R.drawable.green, issues[s_issue], "A2", "JHFF1122??????", "2022-08-09", moter_states[s_moter_state], "??????????????????");
        make_Item(R.drawable.yellow, issues[s_issue], "A2", "JHFF1122??????", "2022-08-09", moter_states[s_moter_state], "??????????????????");
        make_Item(R.drawable.red, issues[s_issue], "A2", "JHFF1122??????", "2022-08-09", moter_states[s_moter_state], "??????????????????");

    }


    // ??????????????? ??????

        public void anim() {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim);
            view.startAnimation(animation);
        };


    /*ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listView)
    {
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View view = super.getView(position, convertView, parent);
            TextView tv = (TextView) view.findViewById(android.R.id.text1);
            tv.setTextColor(Color.WHITE);
            return view;
        }
    };

        listView.setAdapter(ArrayAdapter);*/




        //Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.anim);
        //view.startAnimation(animation);

        //anim_test = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim);
        //startAnimation(anim_test);





    }






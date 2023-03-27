package com.example.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.emscontrol.R;
import com.example.emsserver.SocketFactoryR;

import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//import com.alibaba.fastjson.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import lecho.lib.hellocharts.animation.ChartAnimationListener;
import lecho.lib.hellocharts.formatter.LineChartValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleLineChartValueFormatter;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class BFReport extends Fragment {

    private LineChartView lineChart;
    private View root;
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    private Button btntemp;
    private Button btnhumidity;
    private Button btnch2o;
    private Button btnco2;
    private Button btntovc;
    private Button btnpm25;
    private Button btnpm10;
    private JSONObject jsonObject=null;
    @Override
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     }
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle string) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_report, container, false);
        }

        lineChart = (LineChartView)root.findViewById(R.id.lvc_main);
        btntemp=root.findViewById(R.id.temp);
        btnhumidity=root.findViewById(R.id.hemi);
        btnch2o=root.findViewById(R.id.co2);
        btnco2=root.findViewById(R.id.ch2o);
        btntovc=root.findViewById(R.id.tovc);
        btnpm25=root.findViewById(R.id.pm_2_5);
        btnpm10=root.findViewById(R.id.pm_10);
        ///////////////////////
         jsonObject = new JSONObject();
         try {
             jsonObject.put("sendtemp", "sendtemp");
         } catch (JSONException e) {
             e.printStackTrace();
         }
         SocketFactoryR socketFactory = new SocketFactoryR();
         String ReturnValue = socketFactory.ServerSendConn("TUTEMP", jsonObject);
         System.out.println("SignInReturnValue:" + ReturnValue);

         //截取server编码
         String returnhead = ReturnValue.substring(0, 3);
         System.out.println("serverhead:" + returnhead);

         if (ReturnValue != null && returnhead.equals("TE0")) {
             System.out.println("Data Get success");
             String newReturnValue = ReturnValue.substring(12);
             System.out.println("newReturnValue"+newReturnValue);

             convertData(newReturnValue);
         }
         /////////////////////////////
        btntemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonObject = new JSONObject();
                try {
                    jsonObject.put("sendtemp", "sendtemp");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SocketFactoryR socketFactory = new SocketFactoryR();
                String ReturnValue = socketFactory.ServerSendConn("TUTEMP", jsonObject);
                System.out.println("SignInReturnValue:" + ReturnValue);

                //截取server编码
                String returnhead = ReturnValue.substring(0, 3);
                System.out.println("serverhead:" + returnhead);

                if (ReturnValue != null && returnhead.equals("TE0")) {
                    System.out.println("Data Get success");
                    String newReturnValue = ReturnValue.substring(12);
                    System.out.println("newReturnValue"+newReturnValue);

                    convertData(newReturnValue);
                }

            }
        });
        btnhumidity.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 jsonObject = new JSONObject();
                 try {
                     jsonObject.put("sendhumi", "sendhumi");
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
                 SocketFactoryR socketFactory = new SocketFactoryR();
                 String ReturnValue = socketFactory.ServerSendConn("TUHUMI", jsonObject);
                 System.out.println("SignInReturnValue:" + ReturnValue);

                 //截取server编码
                 String returnhead = ReturnValue.substring(0, 3);
                 System.out.println("serverhead:" + returnhead);

                 if (ReturnValue != null && returnhead.equals("TE0")) {
                     System.out.println("Data Get success");
                     String newReturnValue = ReturnValue.substring(12);
                     System.out.println("newReturnValue"+newReturnValue);
                     convertData(newReturnValue);
                 }

             }
         });
        btnch2o.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 jsonObject = new JSONObject();
                 try {
                     jsonObject.put("sendCH2O", "sendCH2O");
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
                 SocketFactoryR socketFactory = new SocketFactoryR();
                 String ReturnValue = socketFactory.ServerSendConn("TUCH2O", jsonObject);
                 System.out.println("SignInReturnValue:" + ReturnValue);

                 //截取server编码
                 String returnhead = ReturnValue.substring(0, 3);
                 System.out.println("serverhead:" + returnhead);

                 if (ReturnValue != null && returnhead.equals("TE0")) {
                     System.out.println("Data Get success");
                     String newReturnValue = ReturnValue.substring(12);
                     System.out.println("newReturnValue"+newReturnValue);
                     convertData(newReturnValue);
                 }

             }
         });
        btnco2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 jsonObject = new JSONObject();
                 try {
                     jsonObject.put("sendco2", "sendco2");
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
                 SocketFactoryR socketFactory = new SocketFactoryR();
                 String ReturnValue = socketFactory.ServerSendConn("TU_CO2", jsonObject);
                 System.out.println("SignInReturnValue:" + ReturnValue);

                 //截取server编码
                 String returnhead = ReturnValue.substring(0, 3);
                 System.out.println("serverhead:" + returnhead);

                 if (ReturnValue != null && returnhead.equals("TE0")) {
                     System.out.println("Data Get success");
                     String newReturnValue = ReturnValue.substring(12);
                     System.out.println("newReturnValue"+newReturnValue);
                     convertData(newReturnValue);
                 }

             }
         });
        btntovc.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 jsonObject = new JSONObject();
                 try {
                     jsonObject.put("sendTOVC", "sendTOVC");
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
                 SocketFactoryR socketFactory = new SocketFactoryR();
                 String ReturnValue = socketFactory.ServerSendConn("TUTOVC", jsonObject);
                 System.out.println("SignInReturnValue:" + ReturnValue);

                 //截取server编码
                 String returnhead = ReturnValue.substring(0, 3);
                 System.out.println("serverhead:" + returnhead);

                 if (ReturnValue != null && returnhead.equals("TE0")) {
                     System.out.println("Data Get success");
                     String newReturnValue = ReturnValue.substring(12);
                     System.out.println("newReturnValue"+newReturnValue);
                     convertData(newReturnValue);
                 }

             }
         });
        btnpm25.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 jsonObject = new JSONObject();
                 try {
                     jsonObject.put("sendPM2.5", "sendPM2.5");
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
                 SocketFactoryR socketFactory = new SocketFactoryR();
                 String ReturnValue = socketFactory.ServerSendConn("TUPM25", jsonObject);
                 System.out.println("SignInReturnValue:" + ReturnValue);

                 //截取server编码
                 String returnhead = ReturnValue.substring(0, 3);
                 System.out.println("serverhead:" + returnhead);

                 if (ReturnValue != null && returnhead.equals("TE0")) {
                     System.out.println("Data Get success");
                     String newReturnValue = ReturnValue.substring(12);
                     System.out.println("newReturnValue"+newReturnValue);
                     convertData(newReturnValue);
                 }

             }
         });
        btnpm10.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 jsonObject = new JSONObject();
                 try {
                     jsonObject.put("sendPM10", "sendPM10");
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
                 SocketFactoryR socketFactory = new SocketFactoryR();
                 String ReturnValue = socketFactory.ServerSendConn("TUPM10", jsonObject);
                 System.out.println("SignInReturnValue:" + ReturnValue);

                 //截取server编码
                 String returnhead = ReturnValue.substring(0, 3);
                 System.out.println("serverhead:" + returnhead);

                 if (ReturnValue != null && returnhead.equals("TE0")) {
                     System.out.println("Data Get success");
                     String newReturnValue = ReturnValue.substring(12);
                     System.out.println("newReturnValue"+newReturnValue);
                     convertData(newReturnValue);
                 }

             }
         });
        return root;
     }

    private void initLineChart(){
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//
        line.setCubic(false);//
//	    line.setStrokeWidth(3);//
        line.setFilled(false);//
        line.setHasLabels(true);//
//		line.setHasLabelsOnlyForSelected(true);//
        line.setHasLines(true);//
        line.setHasPoints(true);//
        LineChartValueFormatter formatter = new SimpleLineChartValueFormatter(2);
//        lines.setFormatter(formatter);
        line.setFormatter(formatter);

        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);
        line.setShape(ValueShape.DIAMOND);
        Axis axisX = new Axis(); //
        axisX.setHasTiltedLabels(true);  //
//	    axisX.setTextColor(Color.WHITE);  //
        axisX.setTextColor(Color.parseColor("#D6D6D9"));//
        axisX.setHasLines(true);
//	    axisX.setName();  //
        axisX.setTextSize(11);//
        axisX.setMaxLabelChars(7); //
        axisX.setValues(mAxisXValues);  //
        data.setAxisXBottom(axisX); //
//	    data.setAxisXTop(axisX);  //
        axisX.setHasLines(true); //x

        // Y轴是根据数据的大小自动设置Y轴上限
        Axis axisY = new Axis();
        axisY.setName("");
        axisY.setTextSize(11);
        data.setAxisYLeft(axisY);
        //data.setAxisYRight(axisY);

        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 3);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);

        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right= 7;
        lineChart.setCurrentViewport(v);
    }

    private void getAxisXLables(ArrayList<String> time){
        for (int i = 0; i < time.size(); i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(time.get(i)));
        }
    }

    private void getAxisPoints(ArrayList<Float> data){
        for (int i = 0; i < data.size(); i++) {
            mPointValues.add(new PointValue(i, data.get(i)));
        }
    }


    private void convertData(String newReturnValue){
        ArrayList<Float>  tempFloat =  new ArrayList<Float>();
        ArrayList<String>  tempTime =  new ArrayList<String>();

        JSONArray tempJA = null;
        try {
            tempJA = new JSONArray(newReturnValue);
            for (int i = 0; i < tempJA.length(); i++) {
                tempFloat.add((float) tempJA.getJSONObject(i).getDouble("item"));
                tempTime.add(tempJA.getJSONObject(i).getString("time"));
            }
            System.out.println("tempFloat:"+tempFloat);
            System.out.println("tempTime:"+tempTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        initChart();
        getAxisXLables(tempTime);
        getAxisPoints(tempFloat);
        initLineChart();
    }

    private void initChart() {
        mPointValues.clear();
    }
}

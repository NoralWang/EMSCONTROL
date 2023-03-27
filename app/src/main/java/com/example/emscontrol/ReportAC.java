package com.example.emscontrol;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.emsserver.SocketFactoryR;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import win.smartown.android.library.tableLayout.TableAdapter;
import win.smartown.android.library.tableLayout.TableLayout;

public class MainActivity2 extends AppCompatActivity {

    private List<Content> contentList;
    private TableLayout tableLayout;
    private Spinner sp_item;
    private  String str_item;
    private  String sendtime;
    private JSONObject jsonObject=null;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        tableLayout = (TableLayout) findViewById(R.id.main_table);
        sp_item =findViewById(R.id.sp_timeitem);
        sp_item.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] languages = getResources().getStringArray(R.array.spinnerTime);
                Log.e("Time", "你点击的item是:" + languages[pos]);
                str_item = sp_item.getSelectedItem().toString();
                System.out.println("str_time"+sendtime);
                if(str_item.equals("Day")){
                    System.out.println("str_timeDay:"+str_item);
                    sendtime="Day";}
                if(str_item.equals("Week")){
                    System.out.println("str_timeWeek:"+str_item);
                    sendtime="Week";}
                if(str_item.equals("Month")){
                    System.out.println("str_timeMonth:"+str_item);
                    sendtime="Month";}
                SocketGetData(sendtime);

                firstRowAsTitle();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        str_item = sp_item.getSelectedItem().toString();
        System.out.println("str_time"+sendtime);
        if(str_item.equals("Day")){
            System.out.println("str_timeDay:"+str_item);
            sendtime="Day";}
        if(str_item.equals("Week")){
            System.out.println("str_timeWeek:"+str_item);
            sendtime="Week";}
        if(str_item.equals("Month")){
            System.out.println("str_timeMonth:"+str_item);
            sendtime="Month";}
        SocketGetData(sendtime);

//        initContent();
        firstRowAsTitle();
//        firstColumnAsTitle();
    }

//    private void initContent() {
//        contentList = new ArrayList<>();
//        //contentList.add(new Content("Temp", "Humi", "CO2", "CH2O", "TOVC", "PM2_5", "PM10","time"));
//        contentList.add(new Content(newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber()));
//        contentList.add(new Content(newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber()));
//        contentList.add(new Content(newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber()));
//        contentList.add(new Content(newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber()));
//        contentList.add(new Content(newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber()));
//        contentList.add(new Content(newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber(), newRandomNumber()));
//    }

    //将第一行作为标题
    private void firstRowAsTitle() {
        //fields是表格中要显示的数据对应到Content类中的成员变量名，其定义顺序要与表格中显示的相同
        final String[] fields = {"Temp", "Humi", "CO2", "CH2O", "TOVC", "PM2_5", "PM10","time"};
        tableLayout.setAdapter(new TableAdapter() {
            @Override
            public int getColumnCount() {
                return fields.length;
            }

            @Override
            public String[] getColumnContent(int position) {
                int rowCount = contentList.size();
                String contents[] = new String[rowCount];
                try {
                    Field field = Content.class.getDeclaredField(fields[position]);
                    field.setAccessible(true);
                    for (int i = 0; i < rowCount; i++) {
                        contents[i] = String.valueOf(field.get(contentList.get(i)));
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return contents;
            }
        });
    }

    //将第一列作为标题
    private void firstColumnAsTitle() {
        tableLayout.setAdapter(new TableAdapter() {
            @Override
            public int getColumnCount() {
                return contentList.size();
            }

            @Override
            public String[] getColumnContent(int position) {
                return contentList.get(position).toArray();
            }
        });
    }

    private String newRandomNumber() {
        return (new Random().nextInt(50) + 50) + "";
    }

    public static class Content {

        private Float Temp;
        private Float Humi;
        private Float CO2;
        private Float CH2O;
        private Float TOVC;
        private Float PM2_5;
        private Float PM10;
        private String time;


        public Content(Float Temp, Float Humi, Float CO2, Float CH2O, Float TOVC, Float PM2_5, Float PM10, String time) {
            this.Temp = Temp;
            this.Humi = Humi;
            this.CO2 = CO2;
            this.CH2O = CH2O;
            this.TOVC = TOVC;
            this.PM2_5 = PM2_5;
            this.PM10 = PM10;
            this.time = time;
        }

        public String[] toArray() {
            return new String[]{String.valueOf(Temp), String.valueOf(Humi), String.valueOf(CO2), String.valueOf(CH2O), String.valueOf(TOVC), String.valueOf(PM2_5), String.valueOf(PM10),time};
        }

    }

    private void SocketGetData(String info)
    {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("SelectValue", info);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SocketFactoryR socketFactory = new SocketFactoryR();
        String ReturnValue = socketFactory.ServerSendConn("REPORT", jsonObject);
        System.out.println("SignInReturnValue:" + ReturnValue);

        //截取server编码
        String returnhead = ReturnValue.substring(0, 3);
        System.out.println("serverhead:" + returnhead);

        if (ReturnValue != null && returnhead.equals("RE0")) {
            System.out.println("Data Get success");
            String newReturnValue = ReturnValue.substring(12);
            System.out.println("newReturnValue" + newReturnValue);
            convertData(newReturnValue);
        }
        else{
            alert = null;
            builder = new AlertDialog.Builder(mContext);
            alert = builder.setIcon(R.drawable.ic_baseline_error_24)
                    .setTitle("System Hint:")
                    .setMessage("Login Error！")
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(mContext, "Please check the Password or account", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .create();             //创建AlertDialog对象
            alert.show();
        }

    }
    private void convertData(String newReturnValue){
        ArrayList<Float>  tempFloat =  new ArrayList<Float>();
        ArrayList<Float>  humiFloat =  new ArrayList<Float>();
        ArrayList<Float>  co2Float =  new ArrayList<Float>();
        ArrayList<Float>  ch2oFloat =  new ArrayList<Float>();
        ArrayList<Float>  tovcFloat =  new ArrayList<Float>();
        ArrayList<Float>  pm2_5Float =  new ArrayList<Float>();
        ArrayList<Float>  pm10Float =  new ArrayList<Float>();
        ArrayList<String>  tempTime =  new ArrayList<String>();

        JSONArray tempJA = null;
        try {
            tempJA = new JSONArray(newReturnValue);
            for (int i = 0; i < tempJA.length(); i++) {

                tempFloat.add((float) tempJA.getJSONObject(i).getDouble("tEMPERTATURE"));
                humiFloat.add((float) tempJA.getJSONObject(i).getDouble("hUMIDITY"));
                co2Float.add((float) tempJA.getJSONObject(i).getDouble("cO2"));
                ch2oFloat.add((float) tempJA.getJSONObject(i).getDouble("cH2O"));
                tovcFloat.add((float) tempJA.getJSONObject(i).getDouble("tOVC"));
                pm2_5Float.add((float) tempJA.getJSONObject(i).getDouble("pM2_5"));
                pm10Float.add((float) tempJA.getJSONObject(i).getDouble("pM2_5"));
                tempTime.add(tempJA.getJSONObject(i).getString("coll_time"));

            }
            System.out.println("tempFloat:"+tempFloat);
            System.out.println("tempTime:"+tempTime);
            contentList = new ArrayList<>();
            System.out.println("11111111111111111111111111111111111111111");
            int length= tempFloat.size();
            System.out.println("Length:"+length);
            for(int j=0;j<length;j++){
                System.out.println("COMMMMMMMMMMMMMMMMMMMMMMMM!!!!!!!!!!!!!!!!");
                contentList.add(new Content(tempFloat.get(j), humiFloat.get(j), co2Float.get(j), ch2oFloat.get(j), tovcFloat.get(j), pm2_5Float.get(j), pm10Float.get(j), tempTime.get(j)));
                System.out.println("JJJJJJJJJJJJJJJJ");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
package com.example.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emscontrol.IFragmentCallBack;
import com.example.emscontrol.R;
import com.example.emsserver.SocketFactoryR;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BFAlarm extends Fragment {
    private static final String TAG = "CCCCCCC";
    private View root;
    private Button buttonSend;
    private IFragmentCallBack fragmentCallBack;

    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private Context mContext;
    private JSONObject jsonObject = null;
    private JSONObject jsonObjectT = null;
    private Spinner alarmaction;
    private Spinner sp_alarmitem;
    private Spinner et_alarmitemid;

    public void setFragmentCallBack(IFragmentCallBack iFragmentCallBack) {
        fragmentCallBack = iFragmentCallBack;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Bundle bundle=getArguments();
//        String string = bundle.getString("message");
//        Log.e(TAG, "onCreate: "+string);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle string) {
        if (root == null) {
            Log.e(TAG, "Alarm page success!");
            root = inflater.inflate(R.layout.fragment_alarm, container, false);
        }
        //创建Alarm threshold
        if (true) {
            EditText et_sensorID = root.findViewById(R.id.et_sensorID);
            EditText et_tvalueM = root.findViewById(R.id.et_tvalueM);
            EditText et_tvalueL = root.findViewById(R.id.et_tvalueL);
            sp_alarmitem = root.findViewById(R.id.sp_alarmitem);
            et_alarmitemid=root.findViewById(R.id.et_alarmitemid);
            Button btnThreshold = root.findViewById(R.id.btnThreshold);


            sp_alarmitem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int pos, long id) {

                    String[] languages = getResources().getStringArray(R.array.spinnerclass);
                    Log.e(TAG, "你点击的item是:" + languages[pos]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Another interface callback
                }
            });

            et_alarmitemid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int pos, long id) {

                    String[] languages = getResources().getStringArray(R.array.alarmclass);
                    Log.e(TAG, "你点击的是alarmid:" + languages[pos]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Another interface callback
                }
            });

            btnThreshold.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String sensorid = et_sensorID.getText().toString();
                    String tvalueM = et_tvalueM.getText().toString();
                    String tvalueL = et_tvalueM.getText().toString();
                    String et_alarmitemid2 = et_alarmitemid.getSelectedItem().toString();

                    String sp_alarmitem2 = sp_alarmitem.getSelectedItem().toString();

                    Log.e("Alarm", "输入的Sensor ID内容为：" + sensorid);
                    Log.e("Alarm", "thresholdvalue：" + tvalueM);
                    Log.e("Alarm", "输入的alarmitemid内容为：" + et_alarmitemid2);
                    Log.e("Alarm", "alarmitem：" + sp_alarmitem2);

                    JSONArray array = new JSONArray();
                    jsonObjectT = new JSONObject();
                    try {
                        jsonObjectT.put("sensorid", sensorid);
                        jsonObjectT.put("tvalueM", tvalueM);
                        jsonObjectT.put("tvalueL", tvalueL);
                        jsonObjectT.put("alarmid", et_alarmitemid2);
                        jsonObjectT.put("alarmitem", sp_alarmitem2);
                        array.put(jsonObjectT);
                        System.out.println("threshold action:" + jsonObjectT);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    SocketFactoryR socketFactory = new SocketFactoryR();
                    String ReturnValue = socketFactory.ServerSendConn("TValue", jsonObjectT);
                    System.out.println("threshold ReturnValue:" + ReturnValue);

                    if (ReturnValue!=null&& ReturnValue== "T00") {

                        alert = null;
                        builder = new AlertDialog.Builder(mContext);
                        alert = builder.setIcon(R.drawable.ic_baseline_check_circle_24)
                                .setTitle("System Hint:")
                                .setMessage("Create Success！")
                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 报错提示
                                        Toast.makeText(mContext, "Threshold Create Success!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .create();             //创建AlertDialog对象
                        alert.show();
                    }
                    else {
                        alert = null;
                        builder = new AlertDialog.Builder(mContext);
                        alert = builder.setIcon(R.drawable.ic_baseline_error_24)
                                .setTitle("System Hint:")
                                .setMessage("Threshold Create Error！")
                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 报错提示
                                        Toast.makeText(mContext, "Threshold ID already exsit!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .create();             //创建AlertDialog对象
                        alert.show();
                    }
                }
            });
        }

        //创建Alarm ID
        if (true) {
            EditText addAlarmtxtView = root.findViewById(R.id.et_alarmid);
            alarmaction = root.findViewById(R.id.sp_alarmaction);
            buttonSend = root.findViewById(R.id.btnaddalarm);
            alarmaction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int pos, long id) {

                    String[] languages = getResources().getStringArray(R.array.alarmaction);
                    Log.e(TAG, "你点击的是:" + languages[pos]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Another interface callback
                }
            });

            buttonSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String alarmid = addAlarmtxtView.getText().toString();
                    String alarmaction2 = alarmaction.getSelectedItem().toString();
                    Log.e("U_ID", "输入的alarmid内容为：" + alarmid);
                    Log.e("U_ID", "输入的alarmid内容为：" + alarmaction2);

                    JSONArray array = new JSONArray();
                    jsonObject = new JSONObject();
                    try {
                        jsonObject.put("alarmid", alarmid);
                        jsonObject.put("alarmAction", alarmaction2);
                        array.put(jsonObject);
                        System.out.println("alarmid:" + jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    SocketFactoryR socketFactory = new SocketFactoryR();
                    String ReturnValue = socketFactory.ServerSendConn("AlarmC", jsonObject);
                    System.out.println("AlarmIDReturnValue:" + ReturnValue);

                    if (ReturnValue!=null&& ReturnValue== "A00") {

                        alert = null;
                        builder = new AlertDialog.Builder(mContext);
                        alert = builder.setIcon(R.drawable.ic_baseline_check_circle_24)
                                .setTitle("System Hint:")
                                .setMessage("Create Success！")
                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 报错提示
                                        Toast.makeText(mContext, "Alarm Create Success!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .create();             //创建AlertDialog对象
                        alert.show();
                    } else {
                        alert = null;
                        builder = new AlertDialog.Builder(mContext);
                        alert = builder.setIcon(R.drawable.ic_baseline_error_24)
                                .setTitle("System Hint:")
                                .setMessage("AlarmID Create Error！")
                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 报错提示
                                        Toast.makeText(mContext, "Alarm ID already exsit!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .create();             //创建AlertDialog对象
                        alert.show();
                    }
                }
            });
        }

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
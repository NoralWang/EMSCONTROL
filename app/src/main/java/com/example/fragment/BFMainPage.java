package com.example.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.emscontrol.IFragmentCallBack;
import com.example.emscontrol.R;
import com.example.emsserver.SocketFactoryR;

import org.json.JSONException;
import org.json.JSONObject;


public class BFMainPage<string> extends Fragment {
    private static final String TAG ="CCCCCCC";
    private View root;
    private Button buttonTans;
    private JSONObject jsonObject=null;
    private IFragmentCallBack fragmentCallBack;
    private Context mContext;

    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private TextView txttemp;
    private TextView txthumidity;
    private TextView txtch2o;
    private TextView txtco2;
    private TextView txttovc;
    private TextView txtpm25;
    private TextView txtpm10;

    public void setFragmentCallBack(IFragmentCallBack iFragmentCallBack){
        fragmentCallBack=iFragmentCallBack;
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
//        Log.e(TAG, "bfonCreate: "+string);
//        SocketFactoryR socketFactory=new SocketFactoryR();
//        String ReturnValue=socketFactory.ServerSendConn("MPData",null);
//        System.out.println("SignInReturnValue:"+ReturnValue);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle string) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_mainpage, container, false);
        }

        buttonTans = root.findViewById(R.id.btnrefersh);
        txttemp=root.findViewById(R.id.txttemp);
        txthumidity=root.findViewById(R.id.txthumidity);
        txtch2o=root.findViewById(R.id.txtch2o);
        txtco2=root.findViewById(R.id.txtco2);
        txttovc=root.findViewById(R.id.txttovc);
        txtpm25=root.findViewById(R.id.txtpm25);
        txtpm10=root.findViewById(R.id.txtpm10);
        refreashData();

        buttonTans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                jsonObject=new JSONObject();
                try {
                    jsonObject.put("sendNew","sendnew");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                SocketFactoryR socketFactory=new SocketFactoryR();
                String ReturnValue=socketFactory.ServerSendConn("MPData",jsonObject);
                System.out.println("SignInReturnValue:"+ReturnValue);

                //截取server编码
                String returnhead = ReturnValue.substring(0, 3);
                System.out.println("serverhead:"+returnhead);

                if(ReturnValue!=null&&returnhead.equals("M00")) {
                    System.out.println("judge success");
                    String newReturnValue = ReturnValue.substring(12);
                    JSONObject toJsonObj = null;
                    String ID = null;
                    String TEMPERATURE = null;
                    String HUMIDITY = null;
                    String CH2O = null;
                    String PM2_5 = null;
                    String PM10 = null;
                    String CO2 = null;
                    String TOVC =null;
                    try {
                        toJsonObj = new JSONObject(newReturnValue);
                        ID = toJsonObj.getString("sensor_id");
                        CH2O =toJsonObj.getString("cH2O");
                        HUMIDITY = toJsonObj.getString("hUMIDITY");
                        TEMPERATURE =toJsonObj.getString("tEMPERTATURE");
                        PM2_5 = toJsonObj.getString("pM2_5");
                        PM10 = toJsonObj.getString("pM10");
                        CO2 = toJsonObj.getString("cO2");
                        TOVC = toJsonObj.getString("tOVC");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    txttemp.setText(TEMPERATURE);
                    txthumidity.setText(HUMIDITY);
                    txtch2o.setText(CH2O);
                    txtco2.setText(CO2);
                    txttovc.setText(TOVC);
                    txtpm10.setText(PM10);
                    txtpm25.setText(PM2_5);
                }
                if(returnhead.equals("M10")){
                    alert = null;

                    builder = new AlertDialog.Builder(mContext);
                    alert = builder.setIcon(R.drawable.ic_baseline_error_24)
                            .setTitle("System Hint:")
                            .setMessage("Data Error！")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(mContext, "Data Null", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .create();             //创建AlertDialog对象
                    alert.show();
                }

            }
        });
        return root;
    }

    public void refreashData() {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("sendNew", "sendnew");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SocketFactoryR socketFactory = new SocketFactoryR();
        String ReturnValue = socketFactory.ServerSendConn("MPData", jsonObject);
        System.out.println("SignInReturnValue:" + ReturnValue);

        //截取server编码
        String returnhead = ReturnValue.substring(0, 3);
        System.out.println("serverhead:" + returnhead);

        if (ReturnValue != null && returnhead.equals("M00")) {
            System.out.println("judge success");
            String newReturnValue = ReturnValue.substring(12);
            JSONObject toJsonObj = null;
            String ID = null;
            String TEMPERATURE = null;
            String HUMIDITY = null;
            String CH2O = null;
            String PM2_5 = null;
            String PM10 = null;
            String CO2 = null;
            String TOVC = null;
            try {
                toJsonObj = new JSONObject(newReturnValue);
                ID = toJsonObj.getString("sensor_id");
                CH2O = toJsonObj.getString("cH2O");
                HUMIDITY = toJsonObj.getString("hUMIDITY");
                TEMPERATURE = toJsonObj.getString("tEMPERTATURE");
                PM2_5 = toJsonObj.getString("pM2_5");
                PM10 = toJsonObj.getString("pM10");
                CO2 = toJsonObj.getString("cO2");
                TOVC = toJsonObj.getString("tOVC");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            txttemp.setText(TEMPERATURE);
            txthumidity.setText(HUMIDITY);
            txtch2o.setText(CH2O);
            txtco2.setText(CO2);
            txttovc.setText(TOVC);
            txtpm10.setText(PM10);
            txtpm25.setText(PM2_5);
        }

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
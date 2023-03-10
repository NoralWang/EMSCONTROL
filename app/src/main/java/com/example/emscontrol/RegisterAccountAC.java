package com.example.emscontrol;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emsserver.SocketFactoryR;
import com.example.emsserver.SocketFactoryT;
import com.example.emsserver.SocketService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterAccountAC extends AppCompatActivity {

    private Button btnCreate;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private Context mContext;
    private JSONObject jsonObject=null;
    //

    private Intent socketIntent;
    private ServiceConnection serviceConnection;
    private SocketFactoryT socketService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = RegisterAccountAC.this;

        EditText useraccount = findViewById(R.id.et_RegAccount);
        EditText userpass = findViewById(R.id.et_Regpass);
        EditText username = findViewById(R.id.et_Regname);
        EditText usersurname = findViewById(R.id.et_RegSurname);
        EditText userpasscfm = findViewById(R.id.et_Regpasscfm);
        EditText useremail = findViewById(R.id.et_Regemail);

        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String uaccount = useraccount.getText().toString();
                String uname = username.getText().toString();
                String usurname = usersurname.getText().toString();
                String upass = userpass.getText().toString();
                String upasscfm = userpasscfm.getText().toString();
                String uemail = useremail.getText().toString();

                //???????????????json????????????server
                JSONArray array = new JSONArray();
                PreparedStatement preparedStatement = null;
                ResultSet rs = null;
                jsonObject = new JSONObject();
                try {
                    jsonObject.put("uaccount", useraccount.getText().toString());
                    jsonObject.put("uname", username.getText().toString());
                    jsonObject.put("usurname", usersurname.getText().toString());
                    jsonObject.put("upass", userpass.getText().toString());
                    jsonObject.put("uemail", useremail.getText().toString());
                    array.put(jsonObject);
                    System.out.println("dayin" + jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //????????????????????????
                if (upasscfm.equals(upass)&&upasscfm!=null) {
                    Log.e("U_Account", "?????????????????????" + uaccount);
                    Log.e("U_Name", "?????????????????????" + uname);
                    Log.e("U_SurName", "?????????????????????" + usurname);
                    Log.e("U_password", "?????????????????????" + upass);
                    Log.e("U_passcfm", "?????????????????????" + upasscfm);
                    Log.e("U_email", "?????????????????????" + uemail);

                    //dialong?????????
                    alert = null;
                    builder = new AlertDialog.Builder(mContext);
                    alert = builder.setIcon(R.drawable.ic_baseline_check_circle_24)
                            .setTitle("System Hint???")
                            .setMessage("Cancel Create User")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(mContext, "Cancel", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterAccountAC.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    bindSocketService();
                                   //??????Socket??????
                                    socketIntent = new Intent(RegisterAccountAC.this, SocketFactoryT.class);
                                    startService(socketIntent);

                                    SocketFactoryR socketFactoryR=new SocketFactoryR();
                                    String ReturnValue=socketFactoryR.ServerSendConn("SignUp",jsonObject);
                                    System.out.println("SignUpReturnValue:"+ReturnValue);

                                    if(ReturnValue=="R00"){
                                        Toast.makeText(mContext, "Register Successful!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterAccountAC.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }).create();             //??????AlertDialog??????
                    alert.show();                    //???????????????

                } else {

                    alert = null;
                    builder = new AlertDialog.Builder(mContext);
                    alert = builder.setIcon(R.drawable.ic_baseline_error_24)
                            .setTitle("System Hint:")
                            .setMessage("Password Is Correct! Please Check")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(mContext, "Cancel", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setPositiveButton("Check", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(mContext, "Please Check Again", Toast.LENGTH_SHORT).show();
                                }
                            }).create();             //??????AlertDialog??????
                    alert.show();                    //???????????????
                }

            }
        });

    }
    private void bindSocketService() {
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    SocketFactoryT.SocketBinder binder = (SocketFactoryT.SocketBinder) iBinder;
                    socketService = binder.getService();
                }
                @Override
                public void onServiceDisconnected(ComponentName componentName) {

                }
            };
            Intent intent = new Intent(RegisterAccountAC.this, SocketFactoryT.class);
            bindService(intent, serviceConnection, BIND_AUTO_CREATE);


    }
}
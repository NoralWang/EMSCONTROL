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

                //打包数据成json方便丢给server
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

                //判断密码是否一致
                if (upasscfm.equals(upass)&&upasscfm!=null) {
                    Log.e("U_Account", "输入的内容为：" + uaccount);
                    Log.e("U_Name", "输入的内容为：" + uname);
                    Log.e("U_SurName", "输入的内容为：" + usurname);
                    Log.e("U_password", "输入的内容为：" + upass);
                    Log.e("U_passcfm", "输入的内容为：" + upasscfm);
                    Log.e("U_email", "输入的内容为：" + uemail);

                    //dialong提示框
                    alert = null;
                    builder = new AlertDialog.Builder(mContext);
                    alert = builder.setIcon(R.drawable.ic_baseline_check_circle_24)
                            .setTitle("System Hint：")
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
                                   //启动Socket服务
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
                            }).create();             //创建AlertDialog对象
                    alert.show();                    //显示对话框

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
                            }).create();             //创建AlertDialog对象
                    alert.show();                    //显示对话框
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
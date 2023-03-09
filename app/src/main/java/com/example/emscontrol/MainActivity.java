package com.example.emscontrol;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emsserver.SocketFactoryR;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    private CheckBox checkBox;
    private boolean permission;
    private Button btn_Register;
    private Button btn_login;
    private JSONObject jsonObject=null;

    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
             }
        mContext = MainActivity.this;
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(this);

        //login 按钮获得密码等数据并登录
        btn_login=findViewById(R.id.btn_login);
        EditText loginid=findViewById(R.id.et_userid);
        EditText password=findViewById(R.id.et_pass);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uloginid=loginid.getText().toString();
                String upass=password.getText().toString();


                Log.e("U_ID", "输入的内容为："+uloginid);
                Log.e("U_password", "输入的内容为："+upass);
                if (permission==true&&uloginid!=""&&upass!=""){
                    //与数据库数据校验
                    JSONArray array=new JSONArray();
                    jsonObject=new JSONObject();
                    try {
                        jsonObject.put("uloginid",loginid.getText().toString());
                        jsonObject.put("upass",password.getText().toString());
                        array.put(jsonObject);
                        System.out.println("LoginInfo"+jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("msg", "///////");
                    SocketFactoryR socketFactory=new SocketFactoryR();
                    String ReturnValue=socketFactory.ServerSendConn("SignIn",jsonObject);
                    System.out.println("SignInReturnValue:"+ReturnValue);
                    String returnhead = ReturnValue.substring(0,3);
                    System.out.println("returnhead:"+returnhead);


                    if(permission!=true) {
                    alert = null;
                    builder = new AlertDialog.Builder(mContext);
                    alert = builder.setIcon(R.drawable.ic_baseline_error_24)
                            .setTitle("System Hint:")
                            .setMessage("Permission Error！")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 报错提示
                                    Toast.makeText(mContext, "Please check the software privacy permission", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create();             //创建AlertDialog对象
                    alert.show();
                }

                if (uloginid==""||upass==""){
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
                    alert.show();                    //显示对话框
                }

                if(returnhead.equals("L00")){
                        alert = null;
                        builder = new AlertDialog.Builder(mContext);
                        alert = builder.setIcon(R.drawable.ic_baseline_check_circle_24)
                                .setTitle("System Hint:")
                                .setMessage("login Success！")
                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 跳入新的activity
//                                        Bundle bundle= new Bundle();
//                                        bundle.putString("userinfo",ReturnValue);
//                                        BFSetting bfSetting=new BFSetting();
//                                        bfSetting.setArguments(bundle);
                                        Intent intent = new Intent(MainActivity.this, HomePageAC.class);

                                        intent.putExtra("userinfo",ReturnValue);
                                        startActivity(intent);
                                    }
                                })
                                .create();             //创建AlertDialog对象
                        alert.show();                  //显示对话框
                    }

                else{
                    alert = null;
                    builder = new AlertDialog.Builder(mContext);
                    alert = builder.setIcon(R.drawable.ic_baseline_error_24)
                            .setTitle("System Hint:")
                            .setMessage("Password or UserID Error！")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 报错提示
                                    Toast.makeText(mContext, "Please check the Password", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create();             //创建AlertDialog对象
                    alert.show();
                    }
                }
            }
        });



        //create new account
        btn_Register=findViewById(R.id.btn_Signup);
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterAccountAC.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onClick(View v) {
        String choose = "";
        if(checkBox.isChecked())choose += checkBox.getText().toString() + "";
        Toast.makeText(this,choose,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.isChecked())
        {
            Toast.makeText(this,buttonView.getText().toString(),Toast.LENGTH_SHORT).show();
            permission=true;
        }
    }

}


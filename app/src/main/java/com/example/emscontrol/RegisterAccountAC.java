package com.example.emscontrol;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterAccountAC extends AppCompatActivity {

    private Button btnCreate;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = RegisterAccountAC.this;

        EditText userpass=findViewById(R.id.et_Regpass);
        EditText username=findViewById(R.id.et_Regname);
        EditText userpasscfm=findViewById(R.id.et_Regpasscfm);
        EditText useremail=findViewById(R.id.et_Regemail);

        btnCreate=findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String uname=username.getText().toString();
                String upass=userpass.getText().toString();
                String upasscfm=userpasscfm.getText().toString();
                String uemail=useremail.getText().toString();

                if(upasscfm.equals(upass)){
                    Log.e("U_Name", "输入的内容为："+uname);
                    Log.e("U_password", "输入的内容为："+upass);
                    Log.e("U_passcfm", "输入的内容为："+upasscfm);
                    Log.e("U_email", "输入的内容为："+uemail);
                    alert = null;
                    builder = new AlertDialog.Builder(mContext);
                    alert = builder.setIcon(R.drawable.ic_baseline_check_circle_24)
                            .setTitle("系统提示：")
                            .setMessage("Password Is Different! Please Check")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(mContext, "Cancel", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(mContext, "Register Successful!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterAccountAC.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }).create();             //创建AlertDialog对象
                    alert.show();                    //显示对话框

                }
                else{
                    Log.e("U_password", "输入的内容为："+upass);
                    Log.e("U_passcfm", "输入的内容为："+upasscfm);
                    Log.e("ERROR", "Password is different");

                    alert = null;
                    builder = new AlertDialog.Builder(mContext);
                    alert = builder.setIcon(R.drawable.ic_baseline_error_24)
                            .setTitle("System Info:")
                            .setMessage("Password Is Different! Please Check")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(mContext, "Cancel", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setPositiveButton("Check", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(mContext, "Check Again", Toast.LENGTH_SHORT).show();
                                }
                            }).create();             //创建AlertDialog对象
                    alert.show();                    //显示对话框
                }

            }
        });


    }
}
package com.example.emscontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fragment.BFAlarm;
import com.example.fragment.BFControl;
import com.example.fragment.BFMainPage;
import com.example.fragment.BFSetting;

public class HomePageAC extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        replaceFragment(new BFMainPage());

        Button btnmain=findViewById(R.id.btnHomePageMain);
        btnmain.setOnClickListener(this);

        Button btnalarm=findViewById(R.id.btnHomePageAlarmID);
        btnalarm.setOnClickListener(this);

        Button btncontrol=findViewById(R.id.btnHomePageControl);
        btncontrol.setOnClickListener(this);

        Button btnmypage=findViewById(R.id.btnHomePageSetting);
        btnmypage.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnHomePageControl:
                Log.e("TAG", "onClick:contorl");
                replaceFragment(new BFControl());
                break;

            case R.id.btnHomePageAlarmID:
                Log.e("TAG", "onClick:alarm");
                replaceFragment(new BFAlarm());
                break;

            case R.id.btnHomePageMain:
                Bundle bundle= new Bundle();
                bundle.putString("message","l like EMS");
                BFMainPage bfMainPage=new BFMainPage();
                bfMainPage.setArguments(bundle);
                bfMainPage.setFragmentCallBack(new IFragmentCallBack() {
                    @Override
                    public void sendMsgToActivity(String msg) {
                        Toast.makeText(HomePageAC.this,msg,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public String getMsgFromActivity(String msg) {
                        return "null";
                    }
                });
                replaceFragment(bfMainPage);
                break;

            case R.id.btnHomePageSetting:
                Log.e("TAG", "onClick:setting");

                Intent intent=getIntent();
                String userinfo = intent.getStringExtra("userinfo");
                System.out.println("user info homepage"+userinfo);

                BFSetting bfSetting=new BFSetting();
                String newinfo=userinfo.substring(12);
                Bundle bundleset=new Bundle();
                bundleset.putString("BFuserinfo",newinfo);
                System.out.println("BFuserinfo:"+newinfo);
                bfSetting.setArguments(bundleset);

//                bfSetting.setFragmentCallBack(new IFragmentCallBack() {
//                    @Override
//                    public void sendMsgToActivity(String msg) {
//                        Toast.makeText(HomePageAC.this,msg,Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public String getMsgFromActivity(String msg) {
//                        return "null";
//                    }
//                });
                replaceFragment(new BFSetting());
                break;

        }
    }

    //动态切换fragment
    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.HomePage_frglayout,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
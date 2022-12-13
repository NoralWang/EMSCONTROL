package com.example.emscontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomePageAC extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button btnmain=findViewById(R.id.btnHomePageMain);
        btnmain.setOnClickListener(this);

        Button btncontrol=findViewById(R.id.btnHomePageControl);
        btncontrol.setOnClickListener(this);

        Button btnmypage=findViewById(R.id.btnHomePageSetting);
        btnmypage.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnHomePageControl:
                replaceFragment(new BFControl());
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
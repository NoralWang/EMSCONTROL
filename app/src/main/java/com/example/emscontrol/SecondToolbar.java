package com.example.emscontrol;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SecondToolbar extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sectoolbar);

        //Toorbar
        Toolbar toolbar=findViewById(R.id.TbHomePage);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.e("Toorbar", "onClick: Return");
                setContentView(R.layout.activity_login);
            }
        });
    }


//        public void ClickEvent(View view) {
//            Log.e("ClickEvent","Click event");
//            setContentView(R.layout.activity_login);
//        }
}


package com.example.emscontrol;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class BFMainPage<string> extends Fragment {
    private static final String TAG ="CCCCCCC";
    private View root;
    private TextView textView;
    private Button buttonTans;
    private Button buttonSend;
    private  IFragmentCallBack fragmentCallBack;

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
        Bundle bundle=getArguments();
        String string = bundle.getString("message");
        Log.e(TAG, "onCreate: "+string);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle string) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_mainpage, container, false);
        }
        textView = root.findViewById(R.id.textview);
        buttonSend = root.findViewById(R.id.btnSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getArguments();
                String string = bundle.getString("message");
                textView.setText("Hello Jessie" + string);
                fragmentCallBack.sendMsgToActivity("Hello,I am from fragment");

                String msg = fragmentCallBack.getMsgFromActivity("null");
                Toast.makeText(BFMainPage.this.getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });


        buttonTans = root.findViewById(R.id.btnTrans);
        buttonTans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = fragmentCallBack.getMsgFromActivity("null");
                Toast.makeText(BFMainPage.this.getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
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
package com.example.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.emscontrol.R;

public class BFControl extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private View rootview;
    private Button btn_ConPcontrol;
    private Button btn_ConPGetMsg;
    private Button btn_ConPGetWifi;

    public BFControl() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BFControl newInstance(String param1, String param2) {
        BFControl fragment = new BFControl();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        btn_ConPcontrol=rootview.findViewById(R.id.btn_ConPcontrol);
//        btn_ConPGetMsg=rootview.findViewById(R.id.btn_ConPGetMsg);
//        btn_ConPGetWifi=rootview.findViewById(R.id.btn_ConPGetWifi);

    }

//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.btn_ConPcontrol:
//                SkipToPage(new BFConToGetMag());
//                break;
//
//            case R.id.btn_ConPGetMsg:
//                SkipToPage(new BFConToConnect());
//                break;
//
//            case R.id.btn_ConPGetWifi:
//                SkipToPage(new BFConToGetWifi());
//                break;
//        }
//    }

//    //动态切换fragment
//    private void SkipToPage(Fragment fragment) {
//
//        FragmentManager fragmentManager=getSupportFragmentManager();
//        FragmentTransaction transaction=fragmentManager.beginTransaction();
//        transaction.replace(R.id.btnHomePageControl,fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootview == null) {
            rootview = inflater.inflate(R.layout.fragment_control, container, false);
        }

        btn_ConPcontrol=rootview.findViewById(R.id.btn_ConPcontrol);
        btn_ConPcontrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .addToBackStack(null)  //将当前fragment加入到返回栈中
//                        .replace(R.id.btnHomePageControl, new BFConToGetMsg(),null)
//                        .commit();
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.layout_control,new BFConToGetMsg());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return rootview;
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
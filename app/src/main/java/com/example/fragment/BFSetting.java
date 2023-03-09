package com.example.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emscontrol.HomePageAC;
import com.example.emscontrol.IFragmentCallBack;
import com.example.emscontrol.MainActivity;
import com.example.emscontrol.R;
import com.example.emsserver.SocketFactoryR;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BFSetting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BFSetting extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private View rootView;
    private Button logout;
    private IFragmentCallBack fragmentCallBack;

    public BFSetting() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public BFSetting newInstance() {
        BFSetting fragment = new BFSetting();
        Bundle bundle=getArguments();

        if(getArguments().getString("BFuserinfo") != null)
        {
            System.out.println("comein");
            String userinfo = bundle.getString("BFuserinfo");
            Log.e("tag", "onCreate: "+userinfo);
        }



//        Bundle bundle=getArguments();
//        String userinfo = bundle.getString("userinfo");
//        System.out.println("BFsetting get"+userinfo);

        return fragment;
    }
    public void setFragmentCallBack(IFragmentCallBack iFragmentCallBack){
        fragmentCallBack=iFragmentCallBack;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        }

        //get user info
        newInstance();
//        Bundle bundle=getArguments();
//        String userinfo = bundle.getString("BFuserinfo");
//        System.out.println("BFsetting get"+userinfo);
//        Log.e("tag", "onCreate: "+userinfo);
        //history



        //logout
        logout = rootView.findViewById(R.id.btn_Logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
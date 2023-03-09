package com.example.emsserver;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Collections;
import java.util.List;


public class SocketFactoryR {

    private PrintWriter pw;
    private static final String HOST = "172.20.10.10";
//    private static final String HOST = "192.168.0.108";
    private static final int PORT = 12345;
    private StringBuilder sb = null;
    private String content=null;
    private Socket socket = null;

    private Context context;
    //定义一个handler对象,用来刷新界面
//    public Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            if (msg.what == 0x123) {
//                sb.append(content);
//                txtshow.setText(sb.toString());
//            }
//        }
//
//        ;
//    };


    public String ServerSendConn(String msgHead,JSONObject jsonObject) {

        try {
            InetAddress address = InetAddress.getLocalHost();
            String ip = address.getHostAddress();
            System.out.println(ip);

            socket = new Socket(HOST, PORT);
            OutputStream os = socket.getOutputStream();//字节输出流
            pw = new PrintWriter(os);//将输出流包装为打印流

            InputStream is = socket.getInputStream();     //获取输入流
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);

                    //获取客户端的IP地址

            pw.write(msgHead + jsonObject);
            pw.flush();
            socket.shutdownOutput();//关闭输出流

            //接收
            if (socket.isConnected()) {
                if (!socket.isInputShutdown()) {
                    content =br.readLine();
                    Log.e("Content", ": " + content);
                    if (content!= null) {
                        Log.e("Content", "readline not null");
                        Log.e("Content", ": " + content);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {socket.close();
                } catch (IOException e) {
                    socket = null;
                    System.out.println("客户端 finally 异常:" + e.getMessage());
                }
            }
        }

        String msgtype = content.substring(6, 9);
        switch (msgtype) {
            case "L00":
                return "L00"+content;//login success
            case "L10":
                return "L10";
            case "R00":
                return "R00"+content;//register success
            case "R10":
                return "R10";
            case "M00":
                return "M00"+content;//main Data success
            case "M10":
                return "M10";
            case "A00":
                return "A00";//alarm success
            case "A10":
                return "A10";
            case "T00":
                return "T00";//theroshold
            case "T10":
                return "T10";
            default:
                return "Error";
          }

    }

}








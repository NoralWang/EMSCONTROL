package com.example.emsserver;


import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class server1 {

    private PrintWriter pw;
    private static final String HOST = "192.168.0.102";
    private static final int PORT = 12345;
    private TextView txtshow;
    private StringBuilder sb = null;
    private String content="";
    private Socket socket = null;


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




//    public void SignupJsonConn(JSONObject jsonObject){
//
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    //1.创建客户端Socket，指定服务器地址和端口
//                    Socket socket = new Socket("192.168.0.102",12345);
//                    //2.获取输出流，向服务器端发送信息
//                    OutputStream os = socket.getOutputStream();//字节输出流
//                    pw = new PrintWriter(os);//将输出流包装为打印流
//                    //获取客户端的IP地址
//                    InetAddress address = InetAddress.getLocalHost();
//                    String ip = address.getHostAddress();
//                    pw.write("SignUp" +jsonObject);
//                    pw.flush();
//                    socket.shutdownOutput();//关闭输出流
//                    socket.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

    public String ServerSendConn(String msgHead,JSONObject jsonObject) {

        String md="000000";
        try {
            socket = new Socket(HOST, PORT);
            OutputStream os = socket.getOutputStream();//字节输出流
            pw = new PrintWriter(os);//将输出流包装为打印流

            InputStream is = socket.getInputStream();     //获取输入流
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            //获取客户端的IP地址
            InetAddress address = InetAddress.getLocalHost();
            String ip = address.getHostAddress();
            pw.write("Signin" + jsonObject);
            pw.flush();
            socket.shutdownOutput();//关闭输出流
            //接收

            if (socket.isConnected()) {
                if (!socket.isInputShutdown()) {
                    content = br.readLine();
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
                return "L00";
            case "L01":
                return "L01";
            default:
                return "Error";
        }

    }
}








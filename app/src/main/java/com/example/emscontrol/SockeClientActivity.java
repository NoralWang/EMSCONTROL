package com.example.emscontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class SockeClientActivity extends AppCompatActivity {

    private  TextView ms_msg;
    private  StringBuilder sb;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch ( msg.what) {
                case 0x1001:
                    ms_msg.setText(sb.toString());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sockettest);

        TextView ms_msg = (TextView) findViewById(R.id.ms_msg);

        new Thread(new Runnable(){

            @Override
            public void run(){
                try {
                    //1.创建客户端Socket，指定服务器地址和端口
                    Socket socket = new Socket("192.168.0.102", 12345);
                    //2.获取输出流，向服务器端发送信息
                    OutputStream os = socket.getOutputStream();//字节输出流
                    PrintWriter pw = new PrintWriter(os);//将输出流包装为打印流
                    //获取客户端的IP地址
                    InetAddress address = InetAddress.getLocalHost();
                    String ip = address.getHostAddress();
                    pw.write(">>> 客户端：~" + ip + "~ 接入服务器！！");
                    pw.flush();
                    socket.shutdownOutput();//关闭输出流
                    socket.close();
                } catch (Exception e ) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
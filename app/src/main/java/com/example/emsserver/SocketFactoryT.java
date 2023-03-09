package com.example.emsserver;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
public class SocketFactoryT extends Service {

    private String charset = "utf-8";
    private int bytesize = 1024;
    private Socket socket;
    private OutputStream outputStream;
    private String ip="192.168.0.102";
    private Integer port=12345;
    private PrintWriter pw;

    @Override
    public IBinder onBind(Intent intent) {
        return  new SocketFactoryT.SocketBinder();
    }

    public class SocketBinder extends Binder {
        /*返回SocketService 在需要的地方可以通过ServiceConnection获取到SocketService  */
        public SocketFactoryT getService() {
            return SocketFactoryT.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*初始化socket*/
        initSocket();
        Log.e("TAG", "onStartCommand: start socket" );
        //接收socket数据
        receiveSocketData();
        return super.onStartCommand(intent, flags, startId);
    }

    public void FactoryConn(String Command){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //1.创建客户端Socket，指定服务器地址和端口
                    Socket socket = new Socket(ip,port);
                    //2.获取输出流，向服务器端发送信息
                    OutputStream os = socket.getOutputStream();//字节输出流
                    pw = new PrintWriter(os);//将输出流包装为打印流
                    //获取客户端的IP地址
                    InetAddress address = InetAddress.getLocalHost();
                    String ip = address.getHostAddress();
                    pw.write("Clinet" +Command);
                    pw.flush();
                    socket.shutdownOutput();//关闭输出流
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void SignupJsonConn(JSONObject jsonObject){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //1.创建客户端Socket，指定服务器地址和端口
                    Socket socket = new Socket(ip,port);
                    //2.获取输出流，向服务器端发送信息
                    OutputStream os = socket.getOutputStream();//字节输出流
                    pw = new PrintWriter(os);//将输出流包装为打印流
                    //获取客户端的IP地址
                    InetAddress address = InetAddress.getLocalHost();
                    String ip = address.getHostAddress();
                    pw.write("SignUp" +jsonObject);
                    pw.flush();
                    socket.shutdownOutput();//关闭输出流
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void initSocket() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //1.创建客户端Socket，指定服务器地址和端口
                    Socket socket = new Socket(ip,port);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void receiveSocketData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStreamReader inputStream = null;
                BufferedInputStream bufferedInputStream = null;
                try {
                    if (socket != null && socket.isConnected()) {
                        //shutdownOutput()不加则会阻塞
                        //socket.shutdownOutput();
                    }
                    while (true) {
                        //接收服务端返回数据流
                        if (socket != null && socket.isConnected()) {
                            Log.e("TAG","socket正在监听端口数据，本地端口号：" + socket.getLocalPort()+"，服务器端口号："+socket.getPort());
                            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                            inputStream = new InputStreamReader(dataInputStream, charset);
                            if (inputStream == null) {
                                continue;
                            }
                            StringBuffer sb = new StringBuffer();
                            int count = 0;
                            char[] buf = new char[bytesize];
                            //一次性接收到所有数据，把每个包的字节拼接在一起，然后统一转为字符串
                            while ((count = inputStream.read(buf, 0, buf.length)) > -1) {
                                sb.append(buf, 0, count);
                                if (count < bytesize) {
                                    break;
                                }
                            }
                            String data=sb.toString();
                            if(data.isEmpty()){
                                return;
                            }
                            Log.e("TAG","socket接收到服务端返回的数据：" + data);
                        }
                    }
                } catch (IOException ex) {
                    try {
                        if (bufferedInputStream != null) bufferedInputStream.close();
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}

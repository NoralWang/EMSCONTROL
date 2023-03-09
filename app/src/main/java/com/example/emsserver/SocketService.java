package com.example.emsserver;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * socket连接服务
 */
public class SocketService extends Service {
    /*字符编码*/
    private String charset = "utf-8";
    private int bytesize = 1024;
    /*socket*/
    private Socket socket;
    /*连接线程*/
    private Thread connectThread;
    private Timer timer = new Timer();
    private OutputStream outputStream;
    private String ip="192.168.0.102";
    private Integer port=12345;
    private TimerTask task;
    /*默认重连*/
    private boolean isReConnect = true;
    /*心跳间隔时间*/
    private int socketinterval = 15;

    @Override
    public IBinder onBind(Intent intent) {
        return  new SocketBinder();
    }

    public class SocketBinder extends Binder {
        /*返回SocketService 在需要的地方可以通过ServiceConnection获取到SocketService  */
        public SocketService getService() {
            return SocketService.this;
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
        //接收socket数据
        receiveSocketData();
        return super.onStartCommand(intent, flags, startId);
    }

    /*初始化socket*/
    private void initSocket() {
        if (socket == null && connectThread == null) {
            connectThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    socket = new Socket();
                    try {
                        /*超时时间为5秒*/
                        socket.connect(new InetSocketAddress(ip, port), 5000);
                        /*连接成功的话,发送心跳包*/
                        sendBeatData();
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (e instanceof SocketTimeoutException) {
                            Log.e("TAG", "socket连接超时，正在重连" );


                            releaseSocket();
                        } else if (e instanceof NoRouteToHostException) {
                            Log.e("TAG", "socket地址不存在" );

                            stopSelf();
                        } else if (e instanceof ConnectException) {
                            Log.e("TAG","socket连接异常或被拒绝，IP地址：" + ip + " 端口号：" + port);
                            stopSelf();
                        }
                    }
                }
            });
            /*启动连接线程*/
            connectThread.start();
        }
    }

    private void receiveSocketData() {
        /*使用子线程接收数据*/
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

    /*发送数据*/
    public void sendData(final String data) {
        if (socket != null && socket.isConnected()) {
            /*发送指令*/
            new Thread(new Runnable() {
                @Override
                public void run() {
                    InputStream inputStream = null;
                    BufferedReader bufferedReader = null;
                    try {
                        if(!socketConnected(socket)) {
                            return;
                        }
                        //1.发送数据到服务端
                        outputStream = socket.getOutputStream();
                        outputStream.write(data.getBytes(charset));
                        outputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            if (bufferedReader != null) bufferedReader.close();
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } else {
            Log.e("TAG","socket连接错误,请重试");
        }
    }
    /*定时发送数据*/
    private void sendBeatData() {
        if (timer == null) {
            timer = new Timer();
        }
        if (task == null) {
            task = new TimerTask() {
                @Override
                public void run() {
                    try {
                        if(!socketConnected(socket)) {
                            return;
                        }
                        //发送心跳数据到服务端
                        outputStream = socket.getOutputStream();
                        outputStream.write("beat".getBytes(charset));
                        outputStream.flush();
                    } catch (Exception e) {
                        /*发送失败说明socket断开了或者出现了其他错误*/
                        Log.e("TAG","socket连接断开，正在重新连接");
                        /*重连*/
                        releaseSocket();
                        e.printStackTrace();
                    }
                }
            };
        }
        timer.schedule(task, 0, socketinterval * 1000);
    }
    /**
     * 判断socket是否连通
     * @param socket
     * @return true表示正常连接， false表示断开连接
     */
    private Boolean socketConnected(Socket socket){
        try{
            //发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
            socket.sendUrgentData(0xFF);
            return true;
        }catch(Exception se){
            return false;
        }
    }
    /*释放资源*/
    private void releaseSocket() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (timer != null) {
            timer.purge();
            timer.cancel();
            timer = null;
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream = null;
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
            }
            socket = null;
        }
        if (connectThread != null) {
            connectThread = null;
        }
        /*重新初始化socket*/
        if (isReConnect) {
            initSocket();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG","socket服务已销毁");
        isReConnect = false;
        releaseSocket();
    }
}

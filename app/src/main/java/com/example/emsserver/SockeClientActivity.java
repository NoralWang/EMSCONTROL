//package com.example.emsserver;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import com.example.emscontrol.R;
//
//
//public class SockeClientActivity extends AppCompatActivity {
//    private EditText SocketIP ;
//    private EditText portID;
//    private Button socketConnect;
//    private StringBuilder sb = null;
//
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0x1001:
//                    SocketIP.setText(sb.toString());
//                    portID.setText(sb.toString());
//                    break;
//            }
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.sockettest);
//
//        socketConnect = findViewById(R.id.socketConnect);
//        EditText SocketIP = findViewById(R.id.SocketIP);
//        System.out.println(SocketIP);
//        EditText portID = findViewById(R.id.portID);
//        System.out.println(portID);
//        socketConnect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String uSocketIP=SocketIP.getText().toString();
//                Integer uportID= Integer.valueOf(portID.getText().toString());
//
//                SocketFactoryR socketFactory=new SocketFactoryR();
//                socketFactory.FactoryConn("Query");
////                new Thread(new Runnable() {
////                    @Override
////                    public void run() {
////                        try {
////                            //1.创建客户端Socket，指定服务器地址和端口
////                            Socket socket = new Socket(uSocketIP,uportID);
////                            //2.获取输出流，向服务器端发送信息
////                            OutputStream os = socket.getOutputStream();//字节输出流
////                            PrintWriter pw = new PrintWriter(os);//将输出流包装为打印流
////                            //获取客户端的IP地址
////                            InetAddress address = InetAddress.getLocalHost();
////                            String ip = address.getHostAddress();
////                            pw.write("ClinetIP" +ip+ "~ 接入服务器！！");
////                            pw.write("Clinet" + "~ 接入服务器！！");
////                            pw.flush();
////                            socket.shutdownOutput();//关闭输出流
////                            socket.close();
////                        } catch (Exception e) {
////                            e.printStackTrace();
////                        }
////                    }
////                }).start();
//
//            }
//        });
//    }
//}
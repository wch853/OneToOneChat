package Server;

import java.io.*;
import java.net.*;
import ServerDataBase.DataManagement;

/**
 * 服务器端验证登录信息线程
 *
 */
public class ServerCheckThread extends Thread {
	Socket socket;
	public ServerCheckThread(Socket socket) {
        this.socket = socket;
    }
	public void run(){
		try {
			InputStream is = socket.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			OutputStream os = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			String receivelogin = dis.readUTF();	//读入接收的账号、密码、IP地址组成的字符串
			String[] userdata = receivelogin.split(" ");	//分解为String数组
			boolean flag = new DataManagement().serverCheckUser(userdata);	//查询用户信息，并返回验证值
			dos.writeBoolean(flag);	//发送对登陆信息的验证
			/**
			 * 打印验证信息
			 */
			System.out.println(flag);
			
			/**
			 * 
			 */
			if(flag==true) {	//如果验证通过，启动发送实时用户信息线程
				new ServersendUserThread(socket).start();;
			}
		} catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}

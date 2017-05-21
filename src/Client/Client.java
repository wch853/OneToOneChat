package Client;

import java.io.*;
import java.net.*;

/**
 * 客户端程序
 *
 */
public class Client {
	DataOutputStream dos;
	DataInputStream dis;
	Socket socket;
	public Client() {
		try {
            socket = new Socket("127.0.0.1", 2016);	
	        dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
        }  catch (IOException e) {
            e.printStackTrace();
        }
	}
	/**
	 * 在登录窗口调用，用于检验账号、密码，返回true或false值
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean checkUser(String username, String password) {	
		String IP = "";
		boolean flag = false;
		try {
			IP = IP + InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		try {
            String sendlogin = username + " " + password + " "  + IP;	//将三项内容作为一个字符串发送，以空格分隔
            dos.writeUTF(sendlogin);
			flag = dis.readBoolean();	//读到的boolean值赋给flag
			/**
			 * 测试
			 */
			if(flag==true) {	//读到true值，启动更新用户列表线程
				new RenewUserThread(socket).start();
			}
			/**
			 * 打印发送的客户端信息
			 */
			System.out.println(sendlogin);
        }  catch (IOException e) {
            e.printStackTrace();
        }
		return flag; //返回flag的值
	}
}

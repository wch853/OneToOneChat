package Server;

import java.io.*;
import java.net.Socket;
import ServerDataBase.DataManagement;
import User.User;

/**
 * 服务器端发送用户实时信息线程
 *
 */
public class ServersendUserThread  extends Thread {
	Socket socket;
	public ServersendUserThread(Socket socket) {
        this.socket = socket;
    }
	public void run() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			while(true) {
				String info = dis.readUTF();	//获取输入的流，用户未下线时为"normal"
				System.out.println(info);	//在控制台打印状态
				if(info.equals("exit")) {	//下线时收到"exit"
					String s = socket.getInetAddress().getHostAddress();
					System.out.println("exit：" + s);	//在控制台打印下线用户的IP地址
					new DataManagement().offlineIP(s);	//在数据库中将下线用户的IP地址写为0
					return;	//结束线程
				}
				User[] data = new DataManagement().getAllUserData(); //查找用户id、IP信息，封装为User类，返回数组
				oos.writeObject(data);	//对象流输出user类数组
				/**
				 * 查看发送的对象数组
				 */
				System.out.println("服务器端发送：");
				for(int i=0; i<data.length; i++) {
					System.out.println(data[i].getId() + " " + data[i].getIP());
				}
				/**
				 * 
				 */
				try {
					sleep(5000);	//设置时间间隔为5秒向客户端发送最新的上下线信息
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}


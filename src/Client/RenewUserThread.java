package Client;

import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import User.User;

/**
 * 客户端更新用户列表线程
 *
 */
public class RenewUserThread extends Thread {
	Socket socket;
	private UDPMessage chat;
	public RenewUserThread(Socket socket) {
        this.socket = socket;
    }
	public void run(){
		try {
			chat = new UDPMessage();	//打开聊天窗口
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			while(true) {
				chat.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {	//点击窗口的关闭按钮
				    	int flag = JOptionPane.showConfirmDialog(null, "确认退出？", "提示", JOptionPane.YES_NO_OPTION);
				    	if(flag == JOptionPane.YES_OPTION) {	//点击确定
				    		try {
								dos.writeUTF("exit");	//向服务器发送“exit”
							} catch (IOException ex) {
								ex.printStackTrace();
							}
				    		chat.dispose();	
				    	}
				    }   
				});
				dos.writeUTF("normal");	//不涉及关闭时，发送“normal”
				User[] userdata = (User[]) ois.readObject();
				/**
				 * 查看接收的对象数组 	
				 */
				System.out.println("客户端接收：");
				for(int i=0; i<userdata.length; i++) {	
					System.out.println(userdata[i].getId() + " " + userdata[i].getIP());
				}
				/**
				 * 
				 */
				String[] online = new String[userdata.length+1];
				String[] offline = new String[userdata.length+1];
				online[0] = "在线用户：";
				offline[0] = "离线用户：";	//定义在线用户和离线用户JList的data数据
				int a=1,  b=1;
				for(int i=0; i<userdata.length; i++) {	
					if(userdata[i].getIP().equals("0")) {
						offline[a++] = userdata[i].getId();	//下线用户只有id
					} else {
						online[b++] = userdata[i].getId() + " " + userdata[i].getIP();	//在线用户为“id IP”
					}
				}
				chat.list1.setListData(online);
				chat.list2.setListData(offline);	//向两个JList中加入数据
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


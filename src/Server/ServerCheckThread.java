package Server;

import java.io.*;
import java.net.*;
import ServerDataBase.DataManagement;

/**
 * ����������֤��¼��Ϣ�߳�
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
			String receivelogin = dis.readUTF();	//������յ��˺š����롢IP��ַ��ɵ��ַ���
			String[] userdata = receivelogin.split(" ");	//�ֽ�ΪString����
			boolean flag = new DataManagement().serverCheckUser(userdata);	//��ѯ�û���Ϣ����������ֵ֤
			dos.writeBoolean(flag);	//���ͶԵ�½��Ϣ����֤
			/**
			 * ��ӡ��֤��Ϣ
			 */
			System.out.println(flag);
			
			/**
			 * 
			 */
			if(flag==true) {	//�����֤ͨ������������ʵʱ�û���Ϣ�߳�
				new ServersendUserThread(socket).start();;
			}
		} catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}

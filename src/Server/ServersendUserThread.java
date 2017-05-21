package Server;

import java.io.*;
import java.net.Socket;
import ServerDataBase.DataManagement;
import User.User;

/**
 * �������˷����û�ʵʱ��Ϣ�߳�
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
				String info = dis.readUTF();	//��ȡ����������û�δ����ʱΪ"normal"
				System.out.println(info);	//�ڿ���̨��ӡ״̬
				if(info.equals("exit")) {	//����ʱ�յ�"exit"
					String s = socket.getInetAddress().getHostAddress();
					System.out.println("exit��" + s);	//�ڿ���̨��ӡ�����û���IP��ַ
					new DataManagement().offlineIP(s);	//�����ݿ��н������û���IP��ַдΪ0
					return;	//�����߳�
				}
				User[] data = new DataManagement().getAllUserData(); //�����û�id��IP��Ϣ����װΪUser�࣬��������
				oos.writeObject(data);	//���������user������
				/**
				 * �鿴���͵Ķ�������
				 */
				System.out.println("�������˷��ͣ�");
				for(int i=0; i<data.length; i++) {
					System.out.println(data[i].getId() + " " + data[i].getIP());
				}
				/**
				 * 
				 */
				try {
					sleep(5000);	//����ʱ����Ϊ5����ͻ��˷������µ���������Ϣ
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}


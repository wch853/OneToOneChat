package Client;

import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import User.User;

/**
 * �ͻ��˸����û��б��߳�
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
			chat = new UDPMessage();	//�����촰��
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			while(true) {
				chat.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {	//������ڵĹرհ�ť
				    	int flag = JOptionPane.showConfirmDialog(null, "ȷ���˳���", "��ʾ", JOptionPane.YES_NO_OPTION);
				    	if(flag == JOptionPane.YES_OPTION) {	//���ȷ��
				    		try {
								dos.writeUTF("exit");	//����������͡�exit��
							} catch (IOException ex) {
								ex.printStackTrace();
							}
				    		chat.dispose();	
				    	}
				    }   
				});
				dos.writeUTF("normal");	//���漰�ر�ʱ�����͡�normal��
				User[] userdata = (User[]) ois.readObject();
				/**
				 * �鿴���յĶ������� 	
				 */
				System.out.println("�ͻ��˽��գ�");
				for(int i=0; i<userdata.length; i++) {	
					System.out.println(userdata[i].getId() + " " + userdata[i].getIP());
				}
				/**
				 * 
				 */
				String[] online = new String[userdata.length+1];
				String[] offline = new String[userdata.length+1];
				online[0] = "�����û���";
				offline[0] = "�����û���";	//���������û��������û�JList��data����
				int a=1,  b=1;
				for(int i=0; i<userdata.length; i++) {	
					if(userdata[i].getIP().equals("0")) {
						offline[a++] = userdata[i].getId();	//�����û�ֻ��id
					} else {
						online[b++] = userdata[i].getId() + " " + userdata[i].getIP();	//�����û�Ϊ��id IP��
					}
				}
				chat.list1.setListData(online);
				chat.list2.setListData(offline);	//������JList�м�������
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


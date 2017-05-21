package Client;

import java.io.*;
import java.net.*;

/**
 * �ͻ��˳���
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
	 * �ڵ�¼���ڵ��ã����ڼ����˺š����룬����true��falseֵ
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
            String sendlogin = username + " " + password + " "  + IP;	//������������Ϊһ���ַ������ͣ��Կո�ָ�
            dos.writeUTF(sendlogin);
			flag = dis.readBoolean();	//������booleanֵ����flag
			/**
			 * ����
			 */
			if(flag==true) {	//����trueֵ�����������û��б��߳�
				new RenewUserThread(socket).start();
			}
			/**
			 * ��ӡ���͵Ŀͻ�����Ϣ
			 */
			System.out.println(sendlogin);
        }  catch (IOException e) {
            e.printStackTrace();
        }
		return flag; //����flag��ֵ
	}
}

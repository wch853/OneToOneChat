package Server;

import java.io.IOException;
import java.net.*;

/**
 * �ͻ��˳���
 *
 */
public class Server {
	Server() {
		try {
			ServerSocket server = new ServerSocket(2016);
			while(true) {	//ʹ������һֱ���ڷ���״̬
				Socket socket = server.accept();	//��������״̬���յ����������������߳�	
				new ServerCheckThread(socket).start();	//������֤��¼��Ϣ�߳�
			}
		} catch (IOException e) {
            e.printStackTrace();
        }
	}
	public static void main(String[] args) {
		new Server();
	}
}


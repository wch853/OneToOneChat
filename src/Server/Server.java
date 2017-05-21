package Server;

import java.io.IOException;
import java.net.*;

/**
 * 客户端程序
 *
 */
public class Server {
	Server() {
		try {
			ServerSocket server = new ServerSocket(2016);
			while(true) {	//使服务器一直处于服务状态
				Socket socket = server.accept();	//处于阻塞状态，收到连接请求则启动线程	
				new ServerCheckThread(socket).start();	//启动验证登录信息线程
			}
		} catch (IOException e) {
            e.printStackTrace();
        }
	}
	public static void main(String[] args) {
		new Server();
	}
}


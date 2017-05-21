package Client;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

/**
 * 点对点聊天窗口
 *
 */
public class UDPMessage extends JFrame implements ActionListener {
	private JTextArea area;	//信息接受文本域
	private JTextField IPtext, sendtext;	//IP文本框、信息发送文本框
	private JButton sendbutton;	//发送按钮
	private DatagramSocket socket;	//数据报套接字
	private JScrollBar bar;	//滚动条
	private JScrollPane js1, js2, js3;
	private JPanel p1, p2;
	JList list1, list2;
	UDPMessage() {
		setTitle("UDP点对点聊天程序");	
		setSize(600, 500);
		setLocationRelativeTo(null);
		area = new JTextArea();
		area.setEditable(false);	//设置文本域不可被编辑
		js1 = new JScrollPane(area);
		bar = js1.getVerticalScrollBar();	//获取滚动面板的垂直滚动条
		add(js1, BorderLayout.CENTER);
		p1 = new JPanel();	//下方IPtext和sendtext的容器
		p1.setLayout(new BorderLayout());	//设置边界布局
		IPtext = new JTextField(9);
		IPtext.setEditable(false);	//设置IP地址不可手动输入，保证数据传输的安全性
		sendtext = new JTextField();
		sendbutton = new JButton("发送");
		list1 = new JList();
		list2 = new JList();
		js2 = new JScrollPane(list1);
		js3 = new JScrollPane(list2);
		p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		p2.add(js2, BorderLayout.CENTER);
		p2.add(js3, BorderLayout.SOUTH);
		add(p2, BorderLayout.WEST);
		p1.add(IPtext, BorderLayout.WEST);
		p1.add(sendtext, BorderLayout.CENTER);
		p1.add(sendbutton, BorderLayout.EAST);
		add(p1, BorderLayout.SOUTH);
		setVisible(true);
		server();	
		list1.addMouseListener(new MouseAdapter() {	//在线用户列表添加事件监听，若被双击，将IP地址填入IPtext
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					String str = (String) list1.getSelectedValue();
					String[] data = str.split(" ");
					IPtext.setText(data[1]);
				}
			}
		});
		sendtext.addActionListener(this);
		sendbutton.addActionListener(this);
	}
	private void server() {
		try {
			socket = new DatagramSocket(2016);	//实例化套接字
			byte[] buf = new byte[1024];
			final DatagramPacket dpl = new DatagramPacket(buf, buf.length);	//接收长度为length的数据包
			Runnable myrun = new Runnable() {	
				public void run() {
					while(true) {	
						try {
							Thread.sleep(100); //设置线程休眠时间100ms
							socket.receive(dpl);	//接收数据包
							int length = dpl.getLength();
							String message = new String(dpl.getData(), 0, length);	//获取数据包的字符串信息
							String IP = dpl.getAddress().getHostAddress();	//获取接收到的数据包发送方的IP地址
							IPtext.setText(IP);
							if(!InetAddress.getLocalHost().getHostAddress().equals(IP)) {	
								Date date = new Date();
								SimpleDateFormat sdf = 
										new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");//设置时间格式
								area.append("收到" + IP + "发送的消息   " + 
										sdf.format(date) + "\n" + message + "\n");
							}
							bar.setValue(bar.getMaximum());	//设置垂直滚动条拉到最下
						} catch (IOException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				};
			};
			new Thread(myrun).start();	//启动以上所定义的线程
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	public void actionPerformed(ActionEvent e) {
		sendMsg();
	}
	private void sendMsg() {
		if(sendtext.getText().equals("")) {	//设置发送内容不能为空
			JOptionPane.showMessageDialog(area, "发送内容不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
		} else {
			try {
				String IP = IPtext.getText();	//获取发送IP地址
				InetAddress address = InetAddress.getByName(IP);
				byte[] data = sendtext.getText().getBytes();	//获取要发送的数据
				DatagramPacket dp = new DatagramPacket(data, data.length, address, 2016);
				String myIP = InetAddress.getLocalHost().getHostAddress();	//获取本机IP地址
				Date date = new Date();
				SimpleDateFormat sdf = 
						new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");//设置时间格式
				area.append("本机" + myIP + "发送消息   " + 
						sdf.format(date) +"\n" + sendtext.getText() + "\n");
				socket.send(dp);	//发送数据包
				sendtext.setText(null);	//发送完成，设置发送框为空
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		new UDPMessage();
	}
}


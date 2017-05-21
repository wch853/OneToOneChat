package Client;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

/**
 * ��Ե����촰��
 *
 */
public class UDPMessage extends JFrame implements ActionListener {
	private JTextArea area;	//��Ϣ�����ı���
	private JTextField IPtext, sendtext;	//IP�ı�����Ϣ�����ı���
	private JButton sendbutton;	//���Ͱ�ť
	private DatagramSocket socket;	//���ݱ��׽���
	private JScrollBar bar;	//������
	private JScrollPane js1, js2, js3;
	private JPanel p1, p2;
	JList list1, list2;
	UDPMessage() {
		setTitle("UDP��Ե��������");	
		setSize(600, 500);
		setLocationRelativeTo(null);
		area = new JTextArea();
		area.setEditable(false);	//�����ı��򲻿ɱ��༭
		js1 = new JScrollPane(area);
		bar = js1.getVerticalScrollBar();	//��ȡ�������Ĵ�ֱ������
		add(js1, BorderLayout.CENTER);
		p1 = new JPanel();	//�·�IPtext��sendtext������
		p1.setLayout(new BorderLayout());	//���ñ߽粼��
		IPtext = new JTextField(9);
		IPtext.setEditable(false);	//����IP��ַ�����ֶ����룬��֤���ݴ���İ�ȫ��
		sendtext = new JTextField();
		sendbutton = new JButton("����");
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
		list1.addMouseListener(new MouseAdapter() {	//�����û��б�����¼�����������˫������IP��ַ����IPtext
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
			socket = new DatagramSocket(2016);	//ʵ�����׽���
			byte[] buf = new byte[1024];
			final DatagramPacket dpl = new DatagramPacket(buf, buf.length);	//���ճ���Ϊlength�����ݰ�
			Runnable myrun = new Runnable() {	
				public void run() {
					while(true) {	
						try {
							Thread.sleep(100); //�����߳�����ʱ��100ms
							socket.receive(dpl);	//�������ݰ�
							int length = dpl.getLength();
							String message = new String(dpl.getData(), 0, length);	//��ȡ���ݰ����ַ�����Ϣ
							String IP = dpl.getAddress().getHostAddress();	//��ȡ���յ������ݰ����ͷ���IP��ַ
							IPtext.setText(IP);
							if(!InetAddress.getLocalHost().getHostAddress().equals(IP)) {	
								Date date = new Date();
								SimpleDateFormat sdf = 
										new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");//����ʱ���ʽ
								area.append("�յ�" + IP + "���͵���Ϣ   " + 
										sdf.format(date) + "\n" + message + "\n");
							}
							bar.setValue(bar.getMaximum());	//���ô�ֱ��������������
						} catch (IOException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				};
			};
			new Thread(myrun).start();	//����������������߳�
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	public void actionPerformed(ActionEvent e) {
		sendMsg();
	}
	private void sendMsg() {
		if(sendtext.getText().equals("")) {	//���÷������ݲ���Ϊ��
			JOptionPane.showMessageDialog(area, "�������ݲ���Ϊ�գ�", "��ʾ", JOptionPane.WARNING_MESSAGE);
		} else {
			try {
				String IP = IPtext.getText();	//��ȡ����IP��ַ
				InetAddress address = InetAddress.getByName(IP);
				byte[] data = sendtext.getText().getBytes();	//��ȡҪ���͵�����
				DatagramPacket dp = new DatagramPacket(data, data.length, address, 2016);
				String myIP = InetAddress.getLocalHost().getHostAddress();	//��ȡ����IP��ַ
				Date date = new Date();
				SimpleDateFormat sdf = 
						new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");//����ʱ���ʽ
				area.append("����" + myIP + "������Ϣ   " + 
						sdf.format(date) +"\n" + sendtext.getText() + "\n");
				socket.send(dp);	//�������ݰ�
				sendtext.setText(null);	//������ɣ����÷��Ϳ�Ϊ��
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


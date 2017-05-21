package Client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * �û���¼����
 *
 */
public class ClientLogin extends JFrame implements ActionListener {
	JLabel prompt1, prompt2;
	JTextField username;
	JPasswordField password;
	JButton login, cancel;
	JPanel p1, p2, p3;
	ClientLogin() {
		setTitle("�û���¼�ͻ���");
		setSize(360, 180);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		prompt1 = new JLabel("�˻�");
		prompt2 = new JLabel("����");
		username = new JTextField(16);
		password = new JPasswordField(16);
		login = new JButton("��¼");
		cancel = new JButton("ȡ��");
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p1.add(prompt1);
		p1.add(username);
		p2.add(prompt2);
		p2.add(password);
		p3.add(login);
		p3.add(cancel);
		add(p1);
		add(p2);
		add(p3);
		setVisible(true);
		login.addActionListener(this);
		cancel.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		String username = this.username.getText().trim();
		String password = new String(this.password.getPassword()).trim();
		boolean flag = false;
		if(e.getSource()==login) {	//�����¼��ť
			flag = new Client().checkUser(username, password);	//��֤�˺ź�����
			if(flag) {
				setVisible(false);
			} else {	//����˺����벻��ȷ
				JOptionPane.showMessageDialog(this, "��¼ʧ��", "��ʾ", JOptionPane.WARNING_MESSAGE);
			}
		} else {	//���ȡ����ť
			System.exit(0);
		}
	}
	public static void main(String[] args) {
		new ClientLogin();
	}
}


package Client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 用户登录界面
 *
 */
public class ClientLogin extends JFrame implements ActionListener {
	JLabel prompt1, prompt2;
	JTextField username;
	JPasswordField password;
	JButton login, cancel;
	JPanel p1, p2, p3;
	ClientLogin() {
		setTitle("用户登录客户端");
		setSize(360, 180);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		prompt1 = new JLabel("账户");
		prompt2 = new JLabel("密码");
		username = new JTextField(16);
		password = new JPasswordField(16);
		login = new JButton("登录");
		cancel = new JButton("取消");
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
		if(e.getSource()==login) {	//点击登录按钮
			flag = new Client().checkUser(username, password);	//验证账号和密码
			if(flag) {
				setVisible(false);
			} else {	//如果账号密码不正确
				JOptionPane.showMessageDialog(this, "登录失败", "提示", JOptionPane.WARNING_MESSAGE);
			}
		} else {	//点击取消按钮
			System.exit(0);
		}
	}
	public static void main(String[] args) {
		new ClientLogin();
	}
}


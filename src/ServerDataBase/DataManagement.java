package ServerDataBase;

import java.sql.*;
import User.User;

/**
 * �����ݿ���еĲ�ѯ����
 *
 */
public class DataManagement {
	private Connection conn = new ServerDataBase().getConnection();
	/**
	 * �����û��������롢ip��ɵ����飬�ֽ������û���������֤������true��falseֵ
	 * @param receiveinfo
	 * @return
	 */
	public boolean serverCheckUser(String[] data) {
		try {
			String query = "select * from user where id = '"+data[0]+"'	and password = '"+data[1]+"'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				String update = "update user set ip = '"+data[2]+"' where id = '"+data[0]+"'";
				stmt.executeUpdate(update);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * ��ѯ���������ݿ��������û���id��ip��ַ��������һ����װ��User����
	 * @return
	 */
	public User[] getAllUserData() {
		int i=0;
		try {
			String query = "select count(*) from user";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				i = rs.getInt(1);	//�û���������
			}
			User[] data = new User[i];
			String query2 = "select * from user";
			ResultSet rs2 = stmt.executeQuery(query2);
			int j = 0;
			while(rs2.next()) {
				String id = rs2.getString(1);
				String IP = rs2.getString(3);
				data[j++] = new User(id, IP);
			}
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * �������û���IP��ַ��Ϊ0
	 * @param s
	 */
	public void offlineIP(String s) {
		try {
			String update = "update user set ip = 0 where ip = '"+s+"'";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(update); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}


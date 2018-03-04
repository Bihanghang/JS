package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {
	public static String driver = "oracle.jdbc.driver.OracleDriver";
	public static String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
	public static String username = "sea";
	public static String password = "sea";
	private static final ThreadLocal<Connection>
		threadLocal = new ThreadLocal<>();
	
	public static Connection getConnection() throws Exception {
		Connection conn = threadLocal.get();
		if (conn == null) {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username,password);
			threadLocal.set(conn);
		}
		return conn;
	}
	public static void conClose() {
		Connection con = threadLocal.get();
		if ( con != null) {
			threadLocal.set(null);
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void close(ResultSet rs,Statement st) {
		if ( rs != null){
			try{rs.close();}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ( st != null){
			try{st.close();}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
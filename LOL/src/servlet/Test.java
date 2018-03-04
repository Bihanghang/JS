package servlet;

import bean.Attendance;
import daoimp.AttendanceDao;

public class Test {
	public static void main(String[] args) {
		AttendanceDao dao = new AttendanceDao();
    	try {
			dao.deleteAttendance("2018-03-16");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
}

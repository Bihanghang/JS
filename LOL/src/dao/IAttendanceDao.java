package dao;


import java.util.List;
import bean.Attendance;

public interface IAttendanceDao {
	public List<Attendance> findEmpAttendanceThisMonth(String empId,String start,String end)throws Exception;
	public void insertAttendance(Attendance attendance)throws Exception;
	public void deleteAttendance(String time)throws Exception;
}

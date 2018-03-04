package daoimp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Attendance;
import dao.IAttendanceDao;
import jdbc.DBUtils;


public class AttendanceDao implements IAttendanceDao{

	@Override
	public List<Attendance> findEmpAttendanceThisMonth(String empId, String start, String end) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Attendance> list = new ArrayList<>();
		String sql = "select * from attendance where impid='"+empId+"' "
				+ "and ATTENDTIME >= to_date('"+start+"','yyyy-mm-dd') "
						+ "and ATTENDTIME <= to_date('"+end+"','yyyy-mm-dd')";
		try {
			con = DBUtils.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while ( rs.next() ){
				Attendance attendance = new Attendance();
				attendance.setId(rs.getString(1));
				attendance.setAttendTime(rs.getDate(2));
				attendance.setAttendState(rs.getInt(3));
				list.add(attendance);
			}
			if ( list != null)
				return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void insertAttendance(Attendance attendance) throws Exception {
		String sql = "insert into attendance values(?,?,?)";
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBUtils.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, attendance.getId());
			ps.setDate(2, (Date) attendance.getAttendTime());
			ps.setInt(3, attendance.getAttendState());
			ps.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.close(null, ps);
			DBUtils.conClose();
		}
	}

	@Override
	public void deleteAttendance(String time) throws Exception {
		String sql = "delete from attendance where impid='123' and  ATTENDTIME = to_date('"+time+"','yyyy-mm-dd')";
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBUtils.getConnection();
			ps = con.prepareStatement(sql);
			ps.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.close(null, ps);
			DBUtils.conClose();
		}
		
	}


}

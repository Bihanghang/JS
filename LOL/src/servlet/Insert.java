package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import bean.Attendance;
import bean.SignList;
import daoimp.AttendanceDao;
@WebServlet("/insert.do")
public class Insert extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
  
        retData(request, response, "GET");  
    }  
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
          
        retData(request, response, "POST");  
    }  
  
      
    /** 
     * 对请求提供返回数据 
     * @param request 
     * @param response 
     * @param method 
     * @return 
     * @throws IOException 
     */  
    private void retData(HttpServletRequest request, HttpServletResponse response,String method) throws IOException{  
          
    	response.setCharacterEncoding("UTF-8");
    	response.setContentType("application/json; charset=utf-8");
    	AttendanceDao dao = new AttendanceDao();
    	
    	String year = request.getParameter("year");
    	String month = request.getParameter("month");
    	String day = request.getParameter("day");
    	String status = request.getParameter("status");
    	String date = year+"-"+month+"-"+day;
    	java.sql.Date sqldate=java.sql.Date.valueOf(date);
    	
    	//如果状态不为0将当前签到日期存入数据库
    	if (!"0".equals(status)) {
    	Attendance attendance = new Attendance();
    	attendance.setAttendState(Integer.parseInt(status));
    	attendance.setAttendTime(sqldate);
    	attendance.setId("123");
    	try {
			dao.insertAttendance(attendance);
		} catch (Exception e3) {
			e3.printStackTrace();
		}
    	}
    	PrintWriter out = null;
    	try {
    	    out = response.getWriter();
    	    out.write(day);
    	} catch (IOException e) {
    	    e.printStackTrace();
    	} finally {
    	    if (out != null) {
    	        out.close();
    	    }
    	}
    }  
}

package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import bean.Attendance;
import bean.SignList;
import daoimp.AttendanceDao;

@WebServlet("/getAttend.do")
public class GetAttend extends HttpServlet{
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
    	
    	//获取当前日期并且转化
    	String mon = request.getParameter("month");
    	String year = request.getParameter("year");
    	String day = request.getParameter("day");
    	String date = year+"-"+mon+"-"+day;

    	//获取当前月份天数以及本月所有签到天数
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
    	Calendar calendar = Calendar.getInstance();
    	int days = 0;
        try {
			calendar.setTime(sdf.parse(date));
			days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}  
    	List<Attendance> list = new ArrayList<>();
    	try {
    		String start = "";
    		String end = "";
    			start =  year+"-"+String.valueOf(Integer.parseInt(mon))+"-"+"01";
    			end = year+"-"+String.valueOf(Integer.parseInt(mon))+"-"+String.valueOf(days);
			list = dao.findEmpAttendanceThisMonth("123", start, end);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    	
    	//将所有签到天数转为json类型并返回
    	List<SignList> signLists = new ArrayList<>();
    	for(Attendance attendance2:list) {
    		SignList signList = new SignList();
    		int date2 = attendance2.getAttendTime().getDate();
        	signList.setSignDay(String.valueOf(date2));
        	signList.setStatus(String.valueOf(attendance2.getAttendState()));
        	signLists.add(signList);
    	}
        String jsonStr=JSON.toJSONString(signLists);
    	PrintWriter out = null;
    	try {
    	    out = response.getWriter();
    	    out.write(jsonStr);
    	} catch (IOException e) {
    	    e.printStackTrace();
    	} finally {
    	    if (out != null) {
    	        out.close();
    	    }
    	}
    }
}

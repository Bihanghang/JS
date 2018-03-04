package bean;

import java.util.Date;

public class Attendance {
	private String id;
	private Date attendTime;
	private int attendState;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getAttendTime() {
		return attendTime;
	}
	public void setAttendTime(Date attendTime) {
		this.attendTime = attendTime;
	}
	public int getAttendState() {
		return attendState;
	}
	public void setAttendState(int attendState) {
		this.attendState = attendState;
	}
	@Override
	public String toString() {
		return "Attendance [id=" + id + ", attendTime=" + attendTime + ", attendState=" + attendState + "]";
	}
	
}

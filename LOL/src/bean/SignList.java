package bean;

public class SignList {
	String signDay;
	String status;
	public String getSignDay() {
		return signDay;
	}
	public void setSignDay(String signDay) {
		this.signDay = signDay;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "SignList [signDay=" + signDay + ", status=" + status + "]";
	}
	
}

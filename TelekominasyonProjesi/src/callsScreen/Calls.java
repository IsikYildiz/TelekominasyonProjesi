package callsScreen;

import java.sql.Date;
import java.sql.Time;

public class Calls {
	private Date callDate;
	private String customerName;
	private String callSubject;
	private Time callStartTime;
	private Time callEndTime;
	private String callSituation;
	public Date getCallDate() {
		return callDate;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCallSubject() {
		return callSubject;
	}
	public void setCallSubject(String callSubject) {
		this.callSubject = callSubject;
	}
	public Time getCallStartTime() {
		return callStartTime;
	}
	public void setCallStartTime(Time callStartTime) {
		this.callStartTime = callStartTime;
	}
	public String getCallSituation() {
		return callSituation;
	}
	public void setCallSituation(String callSituation) {
		this.callSituation = callSituation;
	}
	public Time getCallEndTime() {
		return callEndTime;
	}
	public void setCallEndTime(Time callEndTime) {
		this.callEndTime = callEndTime;
	}
	public Calls(Date callDate,String customerName,String callSubject,Time callStartTime,Time callEndTime,String callSituation){
		this.callDate=callDate;
		this.customerName=customerName;
		this.callSubject=callSubject;
		this.callStartTime=callStartTime;
		this.callEndTime=callEndTime;
		this.callSituation=callSituation;
	}
}

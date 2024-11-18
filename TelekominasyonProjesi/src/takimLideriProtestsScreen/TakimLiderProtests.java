package takimLideriProtestsScreen;

import java.sql.Date;

public class TakimLiderProtests {//Takım liderlerine gelen itirazları tabloya koymak için kullanılan sınıf.
	private Date protestDate;
	private String asistan;
	private String asistanNo; 
	private String protestExplanation;
	private String protestSituation;
	private String protestAnswer;
	
	public TakimLiderProtests(Date protestDate,String asistanNo,String asistan,String protestExplanation,String protestSituation,String protestAnswer) {
		this.protestDate = protestDate;
		this.asistanNo = asistanNo;
		this.asistan = asistan;
		this.protestExplanation= protestExplanation;;
		this.protestSituation = protestSituation;
		this.protestAnswer = protestAnswer;
	}

	public Date getProtestDate() {
		return protestDate;
	}

	public void setProtestDate(Date protestDate) {
		this.protestDate = protestDate;
	}

	public String getAsistan() {
		return asistan;
	}

	public void setAsistan(String asistan) {
		this.asistan = asistan;
	}

	public String getAsistanNo() {
		return asistanNo;
	}

	public void setAsistanNo(String asistanNo) {
		this.asistanNo = asistanNo;
	}

	public String getProtestExplanation() {
		return protestExplanation;
	}

	public void setProtestExplanation(String protestExplanation) {
		this.protestExplanation = protestExplanation;
	}

	public String getProtestSituation() {
		return protestSituation;
	}

	public void setProtestSituation(String protestSituation) {
		this.protestSituation = protestSituation;
	}

	public String getProtestAnswer() {
		return protestAnswer;
	}

	public void setProtestAnswer(String protestAnswer) {
		this.protestAnswer =protestAnswer;
	}	
}

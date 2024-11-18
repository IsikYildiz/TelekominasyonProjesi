package asistanProtestsScreen;

import java.sql.Date;

public class AsistanProtests {
	private Date protestDate;
	private String protestExplanation;
	private String protestSituation;
	private String protestAnswer;
	
	public AsistanProtests(Date protestDate,String protestExplanation,String protestSituation,String protestAnswer) {//Asistan itiraz tablosu için sınıf.
		this.protestDate = protestDate;
		this.protestExplanation = protestExplanation;
		this.protestSituation = protestSituation;
		this.protestAnswer = protestAnswer;
	}

	public Date getProtestDate() {
		return protestDate;
	}

	public void setProtestDate(Date protestDate) {
		this.protestDate = protestDate;
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
		this.protestAnswer = protestAnswer;
	}
}

package bonusesScreen;

import java.sql.Date;

public class Bonuses {//Prim tablosu için sınıf.
	private Date bonusDate;
	private String bonusMin;
	private String bonusSituation;
	private String bonus;
	private String bonusMax;
	
	public Date getBonusDate() {
		return bonusDate;
	}

	public void setBonusDate(Date bonusDate) {
		this.bonusDate = bonusDate;
	}

	public String getBonusMin() {
		return bonusMin;
	}

	public void setBonusMin(String bonusMin) {
		this.bonusMin = bonusMin;
	}

	public String getBonusSituation() {
		return bonusSituation;
	}

	public void setBonusSituation(String bonusSituation) {
		this.bonusSituation = bonusSituation;
	}

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public String getBonusMax() {
		return bonusMax;
	}

	public void setBonusMax(String bonusMax) {
		this.bonusMax = bonusMax;
	}

	public Bonuses(Date bonusDate, String bonusMin,String bonusSituation,String bonus,String bonusMax) {
		this.bonusDate=bonusDate;
		this.bonusMin=bonusMin;
		this.bonusSituation=bonusSituation;
		this.bonus=bonus;
		this.bonusMax=bonusMax;
	}
}

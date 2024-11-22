package data;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import asistanProtestsScreen.AsistanProtests;
import bonusesScreen.Bonuses;
import callsScreen.Calls;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import loginScreen.LoginScreenController;
import takimLideriProtestsScreen.TakimLiderProtests;

public class Data { //Veritabanına yapılan işlemlerin neredeyse tümünden sorumludur.
	public static Connection connect() throws SQLException, ClassNotFoundException { //Veritabanına bağlanır.
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String connectionUrl = "jdbc:sqlserver://LAPTOP-CI872APL\\MSSQLSERVER;databaseName=Telekominasyon;user=sa;password=1234;encrypt=true;trustServerCertificate=true";
		return DriverManager.getConnection(connectionUrl);	
	}
	
	public static int comparePassword(Connection con,String sifre,RadioMenuItem kim) throws SQLException{//Şifre kontrolü yapar ve ilgili ekrana yönlendirir.
		Statement getPassword = con.createStatement();
        ResultSet rs = null;
        if(kim.getText().equals("Asistan")) {
        	rs= getPassword.executeQuery("Select asistanSifre from Asistan where sicilNo=(dbo.findId('"+sifre+"','Asistan'))");
        	while (rs.next()) {
                if (sifre.equals(rs.getString(1)))
                    return 1;
            }
        }
        if(kim.getText().equals("Takım Lideri")) {
        	rs= getPassword.executeQuery("Select liderSifre from TakimLider where liderId=(dbo.findId('"+sifre+"','TakimLider'))");
        	while (rs.next()) {
                if (sifre.equals(rs.getString(1)))
                    return 2;
            }
        }
        return 3;
	}
	
	public static int findId(Connection con,int who) throws SQLException {//Kullanıcının şifresinden veritabanındaki idsini bulur.
		Statement getId = con.createStatement();
		ResultSet rs = null;
		if(who==1) {
			rs= getId.executeQuery("select sicilNo from Asistan where sicilNo=(dbo.findId('"+LoginScreenController.getSifre()+"','Asistan'))");
			while(rs.next()) {
				return rs.getInt(1);
			}
		}
		if(who==2) {
			rs= getId.executeQuery("select liderId from TakimLider where liderId=(dbo.findId('"+LoginScreenController.getSifre()+"','TakimLider'))");
			while(rs.next()) {
				return rs.getInt(1);
			}
		}
		return 0;
	}
	
	public static void addCall(Connection con,LocalDate callDate,TextField customerName,ToggleGroup callSubject,LocalTime callStartTime,LocalTime callEndTime,ToggleGroup callSituation,ToggleGroup customerType) throws SQLException { //Gerekli bilgileri alır ve çağrı ekler.
		RadioMenuItem subject = (RadioMenuItem) callSubject.getSelectedToggle();
		RadioMenuItem situation = (RadioMenuItem) callSituation.getSelectedToggle();
		RadioMenuItem choice = (RadioMenuItem) customerType.getSelectedToggle();
		Date date = java.sql.Date.valueOf(callDate);
		Time startTime = Time.valueOf( callStartTime ) ;
		Time endTime = Time.valueOf( callEndTime ) ;
		PreparedStatement setMusteri;
		PreparedStatement getMusteriId;
		ResultSet musteriId;
		int Id = 0;
		if(choice.getText().equals("Yeni Müşteri")){
			setMusteri= con.prepareStatement("INSERT INTO Musteri(musteriTamAd,sicilNo) values(?,?)");
			setMusteri.setString(1, customerName.getText());
	        setMusteri.setInt(2, findId(con,1));
	        setMusteri.executeUpdate();
	        getMusteriId= con.prepareStatement("Select top 1 musteriId from Musteri Order by musteriId desc");
	        musteriId=getMusteriId.executeQuery();
	        while(musteriId.next()) {
	        	Id=musteriId.getInt(1);
	        }
		}
		else {
			getMusteriId=con.prepareStatement("Select musteriId from Musteri where musteriTamAd='"+customerName.getText()+"'");
			musteriId=getMusteriId.executeQuery();
			while(musteriId.next()) {
	        	Id=musteriId.getInt(1);
	        }
					
		}
        PreparedStatement setGorusme=con.prepareStatement("INSERT INTO Gorusme(gorusmeKonu,gorusmeTarih,gorusmeBaslangicSaati,gorusmeBitisSaati,gorusmeDurum,musteriId) values(?,?,?,?,?,?)");
        setGorusme.setString(1, subject.getText());
        setGorusme.setDate(2, date);
        setGorusme.setTime(3, startTime);
        setGorusme.setTime(4, endTime);
        setGorusme.setString(5, situation.getText());
        setGorusme.setInt(6, Id);
        setGorusme.executeUpdate();
	}
	
	public static void changeCallStuation(Connection con, LocalDate callDate, String callStartTime, String customerName, String newCallSituation) throws SQLException {
		Statement findCustomerId = con.createStatement();
		ResultSet rs = null;
		int customerId=0;
		rs= findCustomerId.executeQuery("select musteriId from Musteri where musteriId=(dbo.findId('"+customerName+"','Musteri'))");
		while(rs.next()) {
			customerId=rs.getInt(1);
		}
		PreparedStatement changeSituation= con.prepareStatement("update Gorusme set gorusmeDurum='"+newCallSituation+"' where gorusmeTarih='"+callDate+"' and gorusmeBaslangicSaati='"+callStartTime+"' and musteriId='"+customerId+"'");
		changeSituation.execute();		
	}
	
	public static Boolean addAsistanProtest(Connection con,TextField protestExplanation,Date protestDate) throws SQLException {//Asistana itiraz ekler.
		PreparedStatement getDate= con.prepareStatement("select itirazTarihi from Itiraz where sicilno=("+findId(con,1)+")");
		ResultSet matchDate=getDate.executeQuery();
		while(matchDate.next()) {
        	if(protestDate.equals(matchDate.getDate(1))) {
        		return false;
        	}
        }
		PreparedStatement setProtest=con.prepareStatement("INSERT INTO Itiraz(itirazAciklamasi,itirazTarihi,sicilNo) values(?,?,?)");
		setProtest.setString(1, protestExplanation.getText());
		setProtest.setDate(2,protestDate);
		setProtest.setInt(3, findId(con,1));
		setProtest.executeUpdate();
		return true;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static List getCallList(Connection con) throws SQLException { //Çağrıları tabloda göstermek için bir listeye kaydeder.
        List<Calls> calls =  new LinkedList<>();
        String sql = "exec listGorusme '"+LoginScreenController.getSifre()+"'";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        while(rs.next()){
            calls.add(new Calls(rs.getDate("gorusmeTarih"),rs.getString("musteriTamAd"),rs.getString("gorusmeKonu"),rs.getTime("gorusmeBaslangicSaati"),rs.getTime("gorusmeBitisSaati"),rs.getString("gorusmeDurum")));
        }
        return calls;
    }
	
	@SuppressWarnings({ "rawtypes" })
	public static List getBonusList(Connection con) throws SQLException{//Primleri tabloda göstermek için bir listeye kaydeder.
		List<Bonuses> bonuses =  new LinkedList<>();
        String sql = "exec listPrim '"+LoginScreenController.getSifre()+"'";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        while(rs.next()){
            bonuses.add(new Bonuses(rs.getDate("primTarih"),rs.getString("primTaban"),rs.getString("primBonusDurum"),rs.getString("primBonus"),rs.getString	("primToplam")));
        }
        return bonuses;
	}
		
	@SuppressWarnings("rawtypes")
	public static List getAsistanProtestsList(Connection con) throws SQLException {//Asistan itirazlarını tabloda göstermek için bir listeye kaydeder.
		List<AsistanProtests> protests =  new LinkedList<>();
        String sql = "exec listItiraz '"+LoginScreenController.getSifre()+"','Asistan'";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        while(rs.next()){
            protests.add(new AsistanProtests(rs.getDate("itirazTarihi"),rs.getString("itirazAciklamasi"),rs.getString("itirazDurum"),rs.getString("itirazCevabi")));
        }
        return protests;
	}
	
	@SuppressWarnings("rawtypes")
	public static List getTakimLideriProtestsList(Connection con) throws SQLException {//Takım liderinin itirazlarını tabloda göstermek için bir listeye kaydeder.
		List<TakimLiderProtests> protests =  new LinkedList<>();
        String sql = "exec listItiraz '"+LoginScreenController.getSifre()+"','TakimLider'";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();   
        while(rs.next()){
        	System.out.print(rs.getString("itirazCevabi"));
        	protests.add(new TakimLiderProtests(rs.getDate("itirazTarihi"),rs.getString("sicilNo"),rs.getString("asistanTamAd"),rs.getString("itirazAciklamasi"),rs.getString("itirazDurum"),rs.getString("itirazCevabi")));
        }
        return protests;
	}
	
	public static String getAsistanItiraz(Connection con) throws ClassNotFoundException, SQLException {//Grup yöneticisine göndermek için asistanın eklediği itirazı stringe dönüştürür.
		PreparedStatement getProtest=con.prepareStatement("select top 1 Asistan.sicilNo,asistanTamAd,itirazTarihi,itirazAciklamasi,itirazDurum,itirazCevabi from Itiraz inner join Asistan on Itiraz.sicilNo=Asistan.sicilNo where Asistan.sicilNo=dbo.findId('"+LoginScreenController.getSifre()+"','Asistan') order by itirazId desc");
		ResultSet rs=getProtest.executeQuery();
		String sonuc = null;
		while(rs.next()) {
			sonuc="SicilNo: "+rs.getString("sicilNo")+", Asistan ad: "+rs.getString("asistanTamAd")+", İtiraz Tarihi: "+rs.getString("itirazTarihi")+", İtiraz Aciklamasi: "+rs.getString("itirazAciklamasi")+", İtiraz Durum: "+rs.getString("itirazDurum");
		}
		return sonuc;
	}
	
	public static String getTakimLiderItiraz(Connection con,Date tarih,int Id) throws SQLException {//Grup yöneticisine göndermek için takım liderinin güncellediği itirazı stringe dönüştürür.
		PreparedStatement getProtest=con.prepareStatement("select top 1 Asistan.sicilNo,asistanTamAd,itirazTarihi,itirazAciklamasi,itirazDurum,itirazCevabi from Itiraz inner join Asistan on Itiraz.sicilNo=Asistan.sicilNo where Asistan.sicilNo="+Id+" and itirazTarihi='"+tarih+"'");
		ResultSet rs=getProtest.executeQuery();
		String sonuc = null;
		while(rs.next()) {
			sonuc="SicilNo: "+rs.getString("sicilNo")+", Asistan ad: "+rs.getString("asistanTamAd")+", İtiraz Tarihi: "+rs.getString("itirazTarihi")+", İtiraz Aciklamasi: "+rs.getString("itirazAciklamasi")+", İtiraz Durum: "+rs.getString("itirazDurum")+", İtiraz Cevabı: "+rs.getString("itirazCevabi");
		}
		return sonuc;
	}
}

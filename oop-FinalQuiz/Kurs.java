/*
 * BISMILLAHIRRAHMANIRRAHIM
 * 
 * @School  : NEU - BILGISAYAR MUHENDISLIGI
 * @Project : Kurs Otomasyonu
 * @Author  : BAYRAM YARIM
 * @Number  : 18010011067
 * @EMail   : byyarim@gmail.com
 * 
 * @File    : Kurs.java
 * @Desc    : Kurs bilgilerinin tutuldugu sýnýftýr.
 * 
 */
package FnlOdev;
/**
 * Kurs bilgilerinin tutulduðu sýnýftýr
 * @param kursID
 * 		  Kurs Numarasý
 * @param kursAD
 * 		  Kursun adý
 * @return Kurs (class)
 * 
 * */
public class Kurs {
	private int kursID;
	private String kursAD;
	
	//Constructor!
	public Kurs(int kursID, String kursAD) {
		super();
		this.kursID = kursID;
		this.kursAD = kursAD;
	}

	//Getter and Setter methods
	public int getKursID() {
		return kursID;
	}

	public void setKursID(int kursID) {
		this.kursID = kursID;
	}

	public String getKursAD() {
		return kursAD;
	}

	public void setKursAD(String kursAD) {
		this.kursAD = kursAD;
	}

	
	

}

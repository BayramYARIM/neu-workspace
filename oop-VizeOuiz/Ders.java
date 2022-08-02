/*
 * BISMILLAHIRRAHMANIRRAHIM
 * 
 * @School  : NEU - BILGISAYAR MUHENDISLIGI
 * @Project : Vize Odev
 * @Author  : BAYRAM YARIM
 * @Number  : 18010011067
 * @EMail   : byyarim@gmail.com
 * 
 * @File    : Ders.java
 * @Desc    : Derslerin tutuldugu sýnýf.
 * 
 */
package VizeOdev;

/**
 * 
 * @apiNote Ders sýnýfýný olusturan kýsýmdýr.
 *
 */
public class Ders {
	
	private int DersBolNo;
	private int DersID;
	private int DersKredi;
	private String DersAd;
	static int _Count=0;
	
	
	public Ders(int dersBolNo, int dersKredi, String dersAd) {
		this.DersBolNo = dersBolNo;
		this.DersID = ++_Count;
		this.DersKredi = dersKredi;
		this.DersAd = dersAd;
		
		System.out.println(String.format("Ders Constructor : %d -> %s Kredi = %d", 
				this.DersID, this.DersAd, this.DersKredi));
	}


	public int getDersBolNo() {
		return DersBolNo;
	}


	public void setDersBolNo(int dersBolNo) {
		DersBolNo = dersBolNo;
	}


	public int getDersID() {
		return DersID;
	}


	public int getDersKredi() {
		return DersKredi;
	}


	public void setDersKredi(int dersKredi) {
		DersKredi = dersKredi;
	}

	public String getDersAd() {
		return DersAd;
	}

	public void setDersAd(String dersAd) {
		DersAd = dersAd;
	}
	
	

}

/*
 * BISMILLAHIRRAHMANIRRAHIM
 * 
 * @School  : NEU - BILGISAYAR MUHENDISLIGI
 * @Project : Vize Odev
 * @Author  : BAYRAM YARIM
 * @Number  : 18010011067
 * @EMail   : byyarim@gmail.com
 * 
 * @File    : Bolum.java
 * @Desc    : Bolumlerin tutuldugu sýnýf.
 * 
 */
package VizeOdev;

/**
 * 
 * @apiNote Bolum sýnýfýný olusturan kýsýmdýr.
 *
 */
public class Bolum {
	
	private int BolNo;
	private String BolAd;
	public Ogrenci[] bolOgrenci;
	public Ders[] bolDers;
	
	
	public Bolum(int bolNo, String bolAd, Ogrenci[] bolOgrenci, Ders[] bolDers) {
		super();
		this.BolNo = bolNo;
		this.BolAd = bolAd;
		this.bolOgrenci = bolOgrenci;
		this.bolDers = bolDers;
	}


	public int getBolNo() {
		return BolNo;
	}

	public void setBolNo(int bolNo) {
		BolNo = bolNo;
	}

	public String getBolAd() {
		return BolAd;
	}

	public void setBolAd(String bolAd) {
		BolAd = bolAd;
	}
	
	
}

/*
 * BISMILLAHIRRAHMANIRRAHIM
 * 
 * @School  : NEU - BILGISAYAR MUHENDISLIGI
 * @Project : Vize Odev
 * @Author  : BAYRAM YARIM
 * @Number  : 18010011067
 * @EMail   : byyarim@gmail.com
 * 
 * @File    : Ogrenci.java
 * @Desc    : Ogrenci bilgilerinin tutuldugu sýnýf.
 * 
 */


package VizeOdev;
/**
 * 
 * @apiNote Ogrenci sýnýfýný olusturan kýsýmdýr.
 *
 */
public class Ogrenci {
	
	private int OgrBolNo;
	private int OgrID;
	private int OgrSinif;
	private String OgrAd;
	private String OgrSoyad;
	static int _Count=0;
	
	//Constructor
	public Ogrenci(int ogrBolNo, int ogrSinif, String ad, String soyad) {
		super();
		this.OgrBolNo = ogrBolNo;
		this.OgrSinif = ogrSinif;
		this.OgrAd = ad;
		this.OgrSoyad = soyad;
		this.OgrID = ++_Count;
		System.out.println(String.format("Ogrenci Constructor : [Bolum=%d] %d -> %s %s sinif %d", 
				this.OgrBolNo, this.OgrID, this.OgrAd, this.OgrSoyad, this.OgrSinif));
	}

	public int getOgrBolNo() {
		return OgrBolNo;
	}

	public void setOgrBolNo(int ogrBolNo) {
		OgrBolNo = ogrBolNo;
	}

	public int getOgrID() {
		return OgrID;
	}

	public int getOgrSinif() {
		return OgrSinif;
	}

	public void setOgrSinif(int ogrSinif) {
		OgrSinif = ogrSinif;
	}

	public String getOgrAd() {
		return OgrAd;
	}

	public void setOgrAd(String ogrAd) {
		OgrAd = ogrAd;
	}

	public String getOgrSoyad() {
		return OgrSoyad;
	}

	public void setOgrSoyad(String ogrSoyad) {
		OgrSoyad = ogrSoyad;
	}
	
	
}

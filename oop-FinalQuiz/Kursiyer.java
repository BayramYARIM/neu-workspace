/*
 * BISMILLAHIRRAHMANIRRAHIM
 * 
 * @School  : NEU - BILGISAYAR MUHENDISLIGI
 * @Project : Kurs Otomasyonu
 * @Author  : BAYRAM YARIM
 * @Number  : 18010011067
 * @EMail   : byyarim@gmail.com
 * 
 * @File    : Kursiyer.java
 * @Desc    : Kursiyer bilgilerinin tutuldugu sýnýftýr.
 * 
 */
package FnlOdev;

import java.util.ArrayList;

public class Kursiyer {
	
	private int kursiyerID;
	private int kursiyerYAS;
	private String kursiyerAdSoyad;
	public ArrayList<Kurs> alinanKurslar;
	
	//Constructor!
	public Kursiyer(int kursiyerID, String kursiyerAdSoyad, int kursiyerYAS, ArrayList<Kurs> alinanKurslar) {
		super();
		this.kursiyerID = kursiyerID;
		this.kursiyerYAS = kursiyerYAS;
		this.kursiyerAdSoyad = kursiyerAdSoyad;
		this.alinanKurslar = alinanKurslar;
	}
	
	
	//Getter and Setter Methods!
	public int getKursiyerID() {
		return kursiyerID;
	}
	public void setKursiyerID(int kursiyerID) {
		this.kursiyerID = kursiyerID;
	}
	public int getKursiyerYAS() {
		return kursiyerYAS;
	}
	public void setKursiyerYAS(int kursiyerYAS) {
		this.kursiyerYAS = kursiyerYAS;
	}
	public String getKursiyerAdSoyad() {
		return kursiyerAdSoyad;
	}
	public void setKursiyerAdSoyad(String kursiyerAdSoyad) {
		this.kursiyerAdSoyad = kursiyerAdSoyad;
	}
	public ArrayList<Kurs> getAlinanKurslar() {
		return alinanKurslar;
	}
	public void setAlinanKurslar(ArrayList<Kurs> alinanKurslar) {
		this.alinanKurslar = alinanKurslar;
	}
	
	
}

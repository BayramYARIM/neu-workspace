package LabQuiz;

public abstract class Ogretmen {
	
	public int ID;
	public String Ad;
	public String Soyad;
	public int Yas;
	public int CS;
	private static int _count= 998;
	
	public Ogretmen(String ad, String soyad, int yas, int cs) {
		this.Ad = ad;
		this.Soyad = soyad;
		this.Yas = yas;
		this.CS = cs;
		_count += 2;
		this.ID = _count;
	}
	
	abstract double PuanHesapla();

	public int getID() {
		return ID;
	}

	public String getAd() {
		return Ad;
	}

	public void setAd(String ad) {
		Ad = ad;
	}

	public String getSoyad() {
		return Soyad;
	}

	public void setSoyad(String soyad) {
		Soyad = soyad;
	}

	public int getYas() {
		return Yas;
	}

	public void setYas(int yas) {
		Yas = yas;
	}

	public int getCS() {
		return CS;
	}

	public void setCS(int cS) {
		CS = cS;
	}

}

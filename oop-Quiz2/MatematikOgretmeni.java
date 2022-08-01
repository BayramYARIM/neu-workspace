package LabQuiz;

public class MatematikOgretmeni extends Ogretmen implements Maas{

	public String Brans;
	
	public MatematikOgretmeni(String ad, String soyad, int yas, int cs) {
		super(ad, soyad, yas, cs);
		this.Brans = "Matematik";
	}

	//Abstract class method
	@Override
	double PuanHesapla() {
		double Rslt=0;
		Rslt = (this.CS * 100) + ((this.Yas * 2) / 3);
		return Rslt;
	}

	//Interface method
	@Override
	public double MaasHesapla() {
		double puan=0, rslt=0;
		puan = PuanHesapla();
		rslt = ((puan * 5) / 7) + 5000;
		return rslt;
	}

	
}

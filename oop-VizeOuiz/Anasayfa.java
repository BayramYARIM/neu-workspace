/*
 * BISMILLAHIRRAHMANIRRAHIM
 * 
 * @School  : NEU - BILGISAYAR MUHENDISLIGI
 * @Project : Vize Odev
 * @Author  : BAYRAM YARIM
 * @Number  : 18010011067
 * @EMail   : byyarim@gmail.com
 * 
 * @File    : Anasayfa.java
 * @Desc    : Programin main blogu.
 * 
 */
package VizeOdev;

import java.util.Scanner;

public class Anasayfa {
	
	static Bolum[] aBolum;
	static int aCntBolum=0;

	/**
	 * @apiNote Menü kýsmýný yazdýran fonksiyondur.
	 * @return void
	 */
	public static void MenuScreen() {
		System.out.println("ÖÐRENCÝ OTOMASYONU");
		System.out.println("---------------------------------");
		System.out.println(String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n", 
				"[1] - Tüm Bölümlerin Bilgilerini Listele",
				"[2] - Bölüm Adýna Göre Arama Yap",
				"[3] - Öðrenci Adýna Göre Arama Yap",
				"[4] - Ders Adýna Göre Arama Yap",
				"[5] - Sýnýf Bilgisine Göre Öðrencileri Bul",
				"[6] - Ders Kredisine Göre Dersleri Bul",
				"[7] - Çýkýþ"));	
	}
	
	/**
	 * @apiNote Giris yapýlan tüm bilgileri yazdýran fonksiyondur
	 * @return void
	 */
	public static void AllInformationPrint() {
		if (aCntBolum > 0)
		{
			for (int i = 0; i < aBolum.length; i++) {
				System.out.println(String.format("Bölüm No : %d\tBölüm Ad:%s", 
						aBolum[i].getBolNo(), aBolum[i].getBolAd()));
				
				System.out.println("Öðrenciler:");				
				for (int j = 0; j < aBolum[i].bolOgrenci.length; j++) {
					System.out.println(String.format("\t%d.Ogrenci: OgrenciID:%d\tAd/Soyad:%s %s\tSýnýf:%d",
							j+1, aBolum[i].bolOgrenci[j].getOgrID(), aBolum[i].bolOgrenci[j].getOgrAd(), aBolum[i].bolOgrenci[j].getOgrSoyad(),
							aBolum[i].bolOgrenci[j].getOgrSinif()));
				}//forLoop-Ogrenci
				
				System.out.println("Dersler:");
				for (int d = 0; d < aBolum[i].bolDers.length; d++) {
					System.out.println(String.format("\t%d.Ders: DersID:%d\tDersAdý:%s\tKredi:%d", 
							d+1,  aBolum[i].bolDers[d].getDersID(), aBolum[i].bolDers[d].getDersAd(), aBolum[i].bolDers[d].getDersKredi()));
				}//forLoop-Ders
				
				System.out.println("=============================================");
			}//forLoop-Bolum					
		}
	}
	
	/**
	 * @apiNote Bölüm adýna göre arama yapan fonksiyondur
	 * @return void
	 */
	public static void SearchDepartmanName() {
		System.out.println("[ BOLUM ADINA GORE ARAMA ]");
		System.out.print("Lutfen bolum adýný giriniz:");
		Scanner rd = new Scanner(System.in);
		String findName = rd.nextLine();
		for (int i = 0; i < aBolum.length; i++) {		
			//System.out.println(String.format("%s - %s", aBolum[i].getBolAd(), findName));
			if (aBolum[i].getBolAd().compareTo(findName) == 0) {
				System.out.println(">>>>> Bölüm Bulundu <<<<<");
				System.out.println(String.format("Bölüm no : %d Bölüm Adý : %s", 
						aBolum[i].getBolNo(), aBolum[i].getBolAd()));
				return;
			}
		}
		System.out.println(String.format("[ %s ] adlý bölüm bulunamadý!", findName));
		return;		
	}
	
	/**
	 * @apiNote Öðrenci adýna göre arama yapan fonksiyondur
	 * @return void
	 */
	public static void SearchStudentName() {
		System.out.println("[ OGRENCI ADINA GORE ARAMA ]");
		System.out.print("Lutfen ögrenci adýný giriniz:");
		boolean bVal=false;
		Scanner rd = new Scanner(System.in);
		String findName = rd.nextLine();
		for (int i = 0; i < aBolum.length; i++) {
			for (int j = 0; j < aBolum[i].bolOgrenci.length; j++) {
				if (aBolum[i].bolOgrenci[j].getOgrAd().compareTo(findName) == 0)
				{
					bVal = true;
					System.out.println(String.format("OgrenciID: %d - Ad/Soyad: %s %s - Sýnýf: %d -> Bölüm: %s", 
							aBolum[i].bolOgrenci[j].getOgrID(), aBolum[i].bolOgrenci[j].getOgrAd(), 
							aBolum[i].bolOgrenci[j].getOgrSoyad(), aBolum[i].bolOgrenci[j].getOgrSinif(),
							aBolum[i].getBolAd()));
				}
			}
		}
		if (bVal == false)
			System.out.println(String.format("[ %s ] adlý ögrenci bulunamadý!", findName));
		return;	
	}
	
	/**
	 * @apiNote Ders adýna göre arama yapan fonksiyondur
	 * @apiNote Bulunan dersler ait olduðu bölüm ile satýr satýr yazdýrýlýr
	 * @apiNote Ders bulunmazsa <b> [ArananDers] </b> adlý ders bulunamadý þeklinde mesaj verir.
	 * @return void
	 */
	public static void SearchLessonName() {
		System.out.println("[ DERS ADINA GORE ARAMA ]");
		System.out.print("Lutfen ders adýný giriniz:");
		boolean bVal=false;
		Scanner rd = new Scanner(System.in);
		String findName = rd.nextLine();
		for (int i = 0; i < aBolum.length; i++) {
			for (int j = 0; j < aBolum[i].bolDers.length; j++) {
				if (aBolum[i].bolDers[j].getDersAd().compareTo(findName) == 0)
				{
					bVal = true;
					System.out.println(String.format("DersID:%d - DersAd: %s - Kredi:%d -> Bölüm: %s",
							aBolum[i].bolDers[j].getDersID(), aBolum[i].bolDers[j].getDersAd(),
							aBolum[i].bolDers[j].getDersKredi(), aBolum[i].getBolAd()));
				}
			}
		}
		if (bVal == false)
			System.out.println(String.format("[ %s ] adlý ders bulunamadý!", findName));
		return;	
	}
	
	/**
	 * @apiNote Öðrenci sýnýfýna göre arama yapan fonksiyondur
	 * @return void
	 */
	public static void SearchStudentClassNumber() {
		System.out.println("[ OGRENCI SINIFA GORE ARAMA ]");
		System.out.print("Lutfen ögrenci sýnýf no giriniz:");
		boolean bVal=false;
		Scanner rd = new Scanner(System.in);
		int classNo = rd.nextInt(); rd.nextLine();
		for (int i = 0; i < aBolum.length; i++) {
			for (int j = 0; j < aBolum[i].bolOgrenci.length; j++) {
				if (aBolum[i].bolOgrenci[j].getOgrSinif() == classNo )
				{
					bVal = true;
					System.out.println(String.format("OgrenciID: %d - Ad/Soyad: %s %s - Sýnýf: %d -> Bölüm: %s", 
							aBolum[i].bolOgrenci[j].getOgrID(), aBolum[i].bolOgrenci[j].getOgrAd(), 
							aBolum[i].bolOgrenci[j].getOgrSoyad(), aBolum[i].bolOgrenci[j].getOgrSinif(),
							aBolum[i].getBolAd()));
				}
			}
		}
		if (bVal == false)
			System.out.println(String.format("[ %d ] sýnýfýna ait ögrenci bulunamadý!", classNo));
		return;	
	}
	
	/**
	 * @apiNote Ders kredisine göre arama yapan fonksiyondur
	 * @apiNote Bulunan dersler ait olduðu bölüm ile satýr satýr yazdýrýlýr
	 * @return void
	 */
	public static void SearchLessonCredit() {
		System.out.println("[ DERS KREDISINE GORE ARAMA ]");
		System.out.print("Lutfen ders adýný giriniz:");
		boolean bVal=false;
		Scanner rd = new Scanner(System.in);
		int aCredit = rd.nextInt(); rd.nextLine();
		for (int i = 0; i < aBolum.length; i++) {
			for (int j = 0; j < aBolum[i].bolDers.length; j++) {
				if (aBolum[i].bolDers[j].getDersKredi() == aCredit)
				{
					bVal = true;
					System.out.println(String.format("DersID: %d - DersAdý: %s - Kredi: %d -> Bölüm: %s",
							aBolum[i].bolDers[j].getDersID(), aBolum[i].bolDers[j].getDersAd(),
							aBolum[i].bolDers[j].getDersKredi(), aBolum[i].getBolAd()));
				}
			}
		}
		if (bVal == false)
			System.out.println(String.format("[ %d ] kredili herhangi bir ders bulunamadý!", aCredit));
		return;	
	}	
	
	public static void main(String[] args) {
	
		Ogrenci[] aOgrenci;
		Ders[] aDers;
		Scanner scan = new Scanner(System.in);
		int aCntOgrenci, aCntDers, iChoice = 0;
		boolean ProExit=true;
		
		System.out.print("Lütfen bölüm sayýsýný giriniz:");
		int _bolNo, _Sinif, _Kredi;
		String _Ad, _Soyad, _DersAd;
		if (scan.hasNextInt()) {
			aCntBolum = scan.nextInt();
			if (aCntBolum > 0) {
				aBolum = new Bolum[aCntBolum];
				for (int i = 0; i < aCntBolum; i++) {
					//BOLUM BILGILERINI ALMA YERI
					System.out.print(String.format("Lütfen %d.bölüm no giriniz:", i+1));
					_bolNo = scan.nextInt(); scan.nextLine();
					System.out.print(String.format("Lütfen %d.bölüm adýný giriniz:", i+1));
					String _bolAd = scan.nextLine();
					
					
					//OGRENCI SAYISINI VE BILGILERI ALMA DONGUSU
					System.out.print(String.format("[%s] bölümüne ait öðrenci sayisini giriniz:", _bolAd));
					aCntOgrenci = scan.nextInt(); scan.nextLine();
					aOgrenci = new Ogrenci[aCntOgrenci];
					for (int j = 0; j < aCntOgrenci; j++) {
						System.out.print(String.format("Lütfen %d.Ögrenci ad giriniz:", j+1));
						_Ad = scan.nextLine();
						System.out.print(String.format("Lütfen %d.Ögrenci soyad giriniz:", j+1));
						_Soyad = scan.nextLine();
						System.out.print(String.format("Lütfen %d.Ögrenci sinif giriniz:", j+1));
						_Sinif = scan.nextInt(); scan.nextLine();
						aOgrenci[j] = new Ogrenci(_bolNo, _Sinif, _Ad, _Soyad);
					}
					
					//DERS SAYISINI VE BILGILERI ALMA DONGUSU
					System.out.print(String.format("[%s] bölümüne ait ders sayisini giriniz:", _bolAd));
					aCntDers = scan.nextInt(); scan.nextLine();
					aDers = new Ders[aCntDers];
					for (int d = 0; d < aCntDers; d++) {
						System.out.print(String.format("Lütfen %d.Ders adýný giriniz:", d+1));
						_DersAd = scan.nextLine();
						System.out.print(String.format("Lütfen %d.Dersin kredisini giriniz:", d+1));
						_Kredi = scan.nextInt(); scan.nextLine();
						aDers[d] = new Ders(_bolNo, _Kredi, _DersAd);
					}
					
					aBolum[i] = new Bolum(_bolNo, _bolAd, aOgrenci, aDers);
					
				}//BOLUM-forLoop
			} else System.out.println("Lütfen 0 dan büyük bir deðer giriniz!");
		} else System.out.println("Lütfen sayisal bir deðer giriniz");
		
		while (ProExit) {
			MenuScreen();
			System.out.print("Lütfen menüden bir iþlem seçiniz:");
			Scanner mn = new Scanner(System.in);
			if (mn.hasNextInt() == true)
			{
				iChoice = mn.nextInt(); 
				switch (iChoice) {
				case 1:
					AllInformationPrint();//1
					break;
				case 2:
					SearchDepartmanName();//OK-2
					break;
				case 3:
					SearchStudentName(); //OK-3
					break;
				case 4:
					SearchLessonName();//OK-4
					break;
				case 5:
					SearchStudentClassNumber();//OK-5
					break;
				case 6:
					SearchLessonCredit(); //OK-6
					break;
				case 7:
					System.out.println("PROGRAMDAN CIKIS YAPILIYOR");
					ProExit = false;
					break;
				default:
					System.out.println("\n !!! YANLIÞ MENU SECIMI !!!");
					break;
				}//switch end
			}//hasNextInt Control!
			else
			{
				System.out.println("\nMENUDEN ISLEM YAPMAK ICIN 1-7 ARALIGINDA BÝR SECIM YAPINIZ!");
			}
		}//while::End
	}//main

}

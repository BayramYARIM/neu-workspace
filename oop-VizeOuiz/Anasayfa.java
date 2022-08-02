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
	 * @apiNote Men� k�sm�n� yazd�ran fonksiyondur.
	 * @return void
	 */
	public static void MenuScreen() {
		System.out.println("��RENC� OTOMASYONU");
		System.out.println("---------------------------------");
		System.out.println(String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n", 
				"[1] - T�m B�l�mlerin Bilgilerini Listele",
				"[2] - B�l�m Ad�na G�re Arama Yap",
				"[3] - ��renci Ad�na G�re Arama Yap",
				"[4] - Ders Ad�na G�re Arama Yap",
				"[5] - S�n�f Bilgisine G�re ��rencileri Bul",
				"[6] - Ders Kredisine G�re Dersleri Bul",
				"[7] - ��k��"));	
	}
	
	/**
	 * @apiNote Giris yap�lan t�m bilgileri yazd�ran fonksiyondur
	 * @return void
	 */
	public static void AllInformationPrint() {
		if (aCntBolum > 0)
		{
			for (int i = 0; i < aBolum.length; i++) {
				System.out.println(String.format("B�l�m No : %d\tB�l�m Ad:%s", 
						aBolum[i].getBolNo(), aBolum[i].getBolAd()));
				
				System.out.println("��renciler:");				
				for (int j = 0; j < aBolum[i].bolOgrenci.length; j++) {
					System.out.println(String.format("\t%d.Ogrenci: OgrenciID:%d\tAd/Soyad:%s %s\tS�n�f:%d",
							j+1, aBolum[i].bolOgrenci[j].getOgrID(), aBolum[i].bolOgrenci[j].getOgrAd(), aBolum[i].bolOgrenci[j].getOgrSoyad(),
							aBolum[i].bolOgrenci[j].getOgrSinif()));
				}//forLoop-Ogrenci
				
				System.out.println("Dersler:");
				for (int d = 0; d < aBolum[i].bolDers.length; d++) {
					System.out.println(String.format("\t%d.Ders: DersID:%d\tDersAd�:%s\tKredi:%d", 
							d+1,  aBolum[i].bolDers[d].getDersID(), aBolum[i].bolDers[d].getDersAd(), aBolum[i].bolDers[d].getDersKredi()));
				}//forLoop-Ders
				
				System.out.println("=============================================");
			}//forLoop-Bolum					
		}
	}
	
	/**
	 * @apiNote B�l�m ad�na g�re arama yapan fonksiyondur
	 * @return void
	 */
	public static void SearchDepartmanName() {
		System.out.println("[ BOLUM ADINA GORE ARAMA ]");
		System.out.print("Lutfen bolum ad�n� giriniz:");
		Scanner rd = new Scanner(System.in);
		String findName = rd.nextLine();
		for (int i = 0; i < aBolum.length; i++) {		
			//System.out.println(String.format("%s - %s", aBolum[i].getBolAd(), findName));
			if (aBolum[i].getBolAd().compareTo(findName) == 0) {
				System.out.println(">>>>> B�l�m Bulundu <<<<<");
				System.out.println(String.format("B�l�m no : %d B�l�m Ad� : %s", 
						aBolum[i].getBolNo(), aBolum[i].getBolAd()));
				return;
			}
		}
		System.out.println(String.format("[ %s ] adl� b�l�m bulunamad�!", findName));
		return;		
	}
	
	/**
	 * @apiNote ��renci ad�na g�re arama yapan fonksiyondur
	 * @return void
	 */
	public static void SearchStudentName() {
		System.out.println("[ OGRENCI ADINA GORE ARAMA ]");
		System.out.print("Lutfen �grenci ad�n� giriniz:");
		boolean bVal=false;
		Scanner rd = new Scanner(System.in);
		String findName = rd.nextLine();
		for (int i = 0; i < aBolum.length; i++) {
			for (int j = 0; j < aBolum[i].bolOgrenci.length; j++) {
				if (aBolum[i].bolOgrenci[j].getOgrAd().compareTo(findName) == 0)
				{
					bVal = true;
					System.out.println(String.format("OgrenciID: %d - Ad/Soyad: %s %s - S�n�f: %d -> B�l�m: %s", 
							aBolum[i].bolOgrenci[j].getOgrID(), aBolum[i].bolOgrenci[j].getOgrAd(), 
							aBolum[i].bolOgrenci[j].getOgrSoyad(), aBolum[i].bolOgrenci[j].getOgrSinif(),
							aBolum[i].getBolAd()));
				}
			}
		}
		if (bVal == false)
			System.out.println(String.format("[ %s ] adl� �grenci bulunamad�!", findName));
		return;	
	}
	
	/**
	 * @apiNote Ders ad�na g�re arama yapan fonksiyondur
	 * @apiNote Bulunan dersler ait oldu�u b�l�m ile sat�r sat�r yazd�r�l�r
	 * @apiNote Ders bulunmazsa <b> [ArananDers] </b> adl� ders bulunamad� �eklinde mesaj verir.
	 * @return void
	 */
	public static void SearchLessonName() {
		System.out.println("[ DERS ADINA GORE ARAMA ]");
		System.out.print("Lutfen ders ad�n� giriniz:");
		boolean bVal=false;
		Scanner rd = new Scanner(System.in);
		String findName = rd.nextLine();
		for (int i = 0; i < aBolum.length; i++) {
			for (int j = 0; j < aBolum[i].bolDers.length; j++) {
				if (aBolum[i].bolDers[j].getDersAd().compareTo(findName) == 0)
				{
					bVal = true;
					System.out.println(String.format("DersID:%d - DersAd: %s - Kredi:%d -> B�l�m: %s",
							aBolum[i].bolDers[j].getDersID(), aBolum[i].bolDers[j].getDersAd(),
							aBolum[i].bolDers[j].getDersKredi(), aBolum[i].getBolAd()));
				}
			}
		}
		if (bVal == false)
			System.out.println(String.format("[ %s ] adl� ders bulunamad�!", findName));
		return;	
	}
	
	/**
	 * @apiNote ��renci s�n�f�na g�re arama yapan fonksiyondur
	 * @return void
	 */
	public static void SearchStudentClassNumber() {
		System.out.println("[ OGRENCI SINIFA GORE ARAMA ]");
		System.out.print("Lutfen �grenci s�n�f no giriniz:");
		boolean bVal=false;
		Scanner rd = new Scanner(System.in);
		int classNo = rd.nextInt(); rd.nextLine();
		for (int i = 0; i < aBolum.length; i++) {
			for (int j = 0; j < aBolum[i].bolOgrenci.length; j++) {
				if (aBolum[i].bolOgrenci[j].getOgrSinif() == classNo )
				{
					bVal = true;
					System.out.println(String.format("OgrenciID: %d - Ad/Soyad: %s %s - S�n�f: %d -> B�l�m: %s", 
							aBolum[i].bolOgrenci[j].getOgrID(), aBolum[i].bolOgrenci[j].getOgrAd(), 
							aBolum[i].bolOgrenci[j].getOgrSoyad(), aBolum[i].bolOgrenci[j].getOgrSinif(),
							aBolum[i].getBolAd()));
				}
			}
		}
		if (bVal == false)
			System.out.println(String.format("[ %d ] s�n�f�na ait �grenci bulunamad�!", classNo));
		return;	
	}
	
	/**
	 * @apiNote Ders kredisine g�re arama yapan fonksiyondur
	 * @apiNote Bulunan dersler ait oldu�u b�l�m ile sat�r sat�r yazd�r�l�r
	 * @return void
	 */
	public static void SearchLessonCredit() {
		System.out.println("[ DERS KREDISINE GORE ARAMA ]");
		System.out.print("Lutfen ders ad�n� giriniz:");
		boolean bVal=false;
		Scanner rd = new Scanner(System.in);
		int aCredit = rd.nextInt(); rd.nextLine();
		for (int i = 0; i < aBolum.length; i++) {
			for (int j = 0; j < aBolum[i].bolDers.length; j++) {
				if (aBolum[i].bolDers[j].getDersKredi() == aCredit)
				{
					bVal = true;
					System.out.println(String.format("DersID: %d - DersAd�: %s - Kredi: %d -> B�l�m: %s",
							aBolum[i].bolDers[j].getDersID(), aBolum[i].bolDers[j].getDersAd(),
							aBolum[i].bolDers[j].getDersKredi(), aBolum[i].getBolAd()));
				}
			}
		}
		if (bVal == false)
			System.out.println(String.format("[ %d ] kredili herhangi bir ders bulunamad�!", aCredit));
		return;	
	}	
	
	public static void main(String[] args) {
	
		Ogrenci[] aOgrenci;
		Ders[] aDers;
		Scanner scan = new Scanner(System.in);
		int aCntOgrenci, aCntDers, iChoice = 0;
		boolean ProExit=true;
		
		System.out.print("L�tfen b�l�m say�s�n� giriniz:");
		int _bolNo, _Sinif, _Kredi;
		String _Ad, _Soyad, _DersAd;
		if (scan.hasNextInt()) {
			aCntBolum = scan.nextInt();
			if (aCntBolum > 0) {
				aBolum = new Bolum[aCntBolum];
				for (int i = 0; i < aCntBolum; i++) {
					//BOLUM BILGILERINI ALMA YERI
					System.out.print(String.format("L�tfen %d.b�l�m no giriniz:", i+1));
					_bolNo = scan.nextInt(); scan.nextLine();
					System.out.print(String.format("L�tfen %d.b�l�m ad�n� giriniz:", i+1));
					String _bolAd = scan.nextLine();
					
					
					//OGRENCI SAYISINI VE BILGILERI ALMA DONGUSU
					System.out.print(String.format("[%s] b�l�m�ne ait ��renci sayisini giriniz:", _bolAd));
					aCntOgrenci = scan.nextInt(); scan.nextLine();
					aOgrenci = new Ogrenci[aCntOgrenci];
					for (int j = 0; j < aCntOgrenci; j++) {
						System.out.print(String.format("L�tfen %d.�grenci ad giriniz:", j+1));
						_Ad = scan.nextLine();
						System.out.print(String.format("L�tfen %d.�grenci soyad giriniz:", j+1));
						_Soyad = scan.nextLine();
						System.out.print(String.format("L�tfen %d.�grenci sinif giriniz:", j+1));
						_Sinif = scan.nextInt(); scan.nextLine();
						aOgrenci[j] = new Ogrenci(_bolNo, _Sinif, _Ad, _Soyad);
					}
					
					//DERS SAYISINI VE BILGILERI ALMA DONGUSU
					System.out.print(String.format("[%s] b�l�m�ne ait ders sayisini giriniz:", _bolAd));
					aCntDers = scan.nextInt(); scan.nextLine();
					aDers = new Ders[aCntDers];
					for (int d = 0; d < aCntDers; d++) {
						System.out.print(String.format("L�tfen %d.Ders ad�n� giriniz:", d+1));
						_DersAd = scan.nextLine();
						System.out.print(String.format("L�tfen %d.Dersin kredisini giriniz:", d+1));
						_Kredi = scan.nextInt(); scan.nextLine();
						aDers[d] = new Ders(_bolNo, _Kredi, _DersAd);
					}
					
					aBolum[i] = new Bolum(_bolNo, _bolAd, aOgrenci, aDers);
					
				}//BOLUM-forLoop
			} else System.out.println("L�tfen 0 dan b�y�k bir de�er giriniz!");
		} else System.out.println("L�tfen sayisal bir de�er giriniz");
		
		while (ProExit) {
			MenuScreen();
			System.out.print("L�tfen men�den bir i�lem se�iniz:");
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
					System.out.println("\n !!! YANLI� MENU SECIMI !!!");
					break;
				}//switch end
			}//hasNextInt Control!
			else
			{
				System.out.println("\nMENUDEN ISLEM YAPMAK ICIN 1-7 ARALIGINDA B�R SECIM YAPINIZ!");
			}
		}//while::End
	}//main

}

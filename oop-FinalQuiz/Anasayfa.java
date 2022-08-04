/*
 * BISMILLAHIRRAHMANIRRAHIM
 * 
 * @School  : NEU - BILGISAYAR MUHENDISLIGI
 * @Project : Kurs Otomasyonu
 * @Author  : BAYRAM YARIM
 * @Number  : 18010011067
 * @EMail   : byyarim@gmail.com
 * 
 * @File    : Anasayfa.java
 * @Desc    : Programin main blogu.
 * 
 */

package FnlOdev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;


public class Anasayfa {
	
	static ArrayList<Kursiyer> alKursiyerler = new ArrayList<Kursiyer>();
	static ArrayList<Kurs> alKurslar = new ArrayList<Kurs>();
	
	/**
	 * Men� k�sm�n� yazd�ran fonksiyondur.
	 * @return void
	 */
	
	public static void MenuScreen() {
		System.out.println("|-------------------------------------------------------|");
		System.out.println("|                    KURS OTOMASYONU                    |");
		System.out.println("|-------------------------------------------------------|");
		System.out.println(String.format("|%s\n|%s\n|%s\n|%s\n|%s\n|%s\n|%s\n|%s\n|%s", 
				" 1 � Kurs Ekle",
				" 2 � Kurs Listele",
				" 3 � Kursiyer Ekle",
				" 4 � Kursiyer Ara",
				" 5 � Kursiyer Sil",
				" 6 � Kursiyerleri Listele",
				" 7 � Kursiyerleri Ayr�nt�l� Listele",
				" 8 - Kursiyerin �deyece�i Tutar� Hesapla",
				" 9 - ��k��"));
		System.out.println("|-------------------------------------------------------|");
	}
	
	/**
	 * Kurs bilgilerini dosyadan(kurs.txt) okuyan fonksiyondur.
	 * Okunan bilgileri ArrayList tipinde ki Kurs dizisine ekler.
	 * 
	 * @return void
	 * 
	 * */
	public static void ReadFileKursTXT() throws IOException {
		File f = new File("kurs.txt");
		if (f.exists() == false) return;
		BufferedReader bfRd = new BufferedReader(new FileReader("kurs.txt", Charset.forName("UTF-8")));
		String rLine="";	
		try {
			while(bfRd.ready()) { //dosya sonuna kadar okuma islemi
				rLine = bfRd.readLine();//Sat�r sat�r okuma islemi
				//System.out.println(rLine);
				String[] Id_Name = rLine.split("-"); //okunan satiri ID ve NAME olarak bol
				alKurslar.add(new Kurs(Integer.parseInt(Id_Name[0]), Id_Name[1])); //Listeye ekleme islemi
				
			}//While::End
		}//TRY
		catch (Exception e) {
			System.out.println("Kurs: File IO Exception:"+e.getMessage());
		}
		bfRd.close();
	}
	
	/**
	 * Kursiyer bilgilerini ve alm�� oldu�u kurs bilgilerini dosyadan(kursiyer.txt) okuyan fonksiyondur.
	 * Okunan bilgileri ArrayList tipinde ki Kursiyer dizisine ekler.
	 * 
	 * @return void
	 * 
	 * */
	public static void ReadFileKursiyerTXT() throws IOException {
		File f = new File("kursiyer.txt");
		if (f.exists() == false) return;
		BufferedReader bfRd = new BufferedReader(new FileReader("kursiyer.txt", Charset.forName("UTF-8")));
		String rLine="";
		String[] kursiyerLine;
		String[] kursLine;
		int kLen, iLen;
		ArrayList<Kurs> tempKurs = new ArrayList<Kurs>();
		try {
			while(bfRd.ready()) { //dosya sonuna kadar okuma islemi
				rLine = bfRd.readLine();//Sat�r sat�r okuma islemi		
				kLen=0; iLen=0;
				if (rLine.charAt(0) == '*')
				{
					tempKurs = new ArrayList<>();
					rLine = rLine.replace("*", "");
					kursiyerLine = rLine.split("-");
					kLen = kursiyerLine.length;
					//[0] --> ID, [1] --> AD-SOYAD, [2] --> YAS	
					alKursiyerler.add(new Kursiyer(Integer.parseInt(kursiyerLine[kLen-3]), 
					kursiyerLine[kLen-2], Integer.parseInt(kursiyerLine[kLen-1]), tempKurs));
				}
				else
				{
					if (rLine.charAt(0) == '%')
					{
						rLine = rLine.replace("%", "");
						kursLine = rLine.split("-");
						iLen = kursLine.length;
						//[0] --> ID, [1] --> KURS ADI
						tempKurs.add(new Kurs(Integer.parseInt(kursLine[iLen-2]), kursLine[iLen-1]));
					}					
				}//else-if				
			}//While::End
		}//TRY
		catch (Exception e) {
			System.out.println("Kursiyer Error: File IO Exception:"+e.getMessage());
		}//catch		
		bfRd.close();
	}//void(@ReadFileKursiyer)
	
	/**
	 * Kurs ID kontrol fonksiyonudur.
	 * 
	 * @param id = Fonksiyona g�nderilen ID listede taran�r.
	 * Daha �nce kay�tl� bir ID var ise <b>true</b> yok ise <b>false</b> doner.
	 * 
	 * @return boolean
	 * 
	 * */
	public static boolean KursIDControl(int id) {
		boolean Result=false;
		if (id > 0) {
			for (Kurs kurs : alKurslar) {
				if (kurs.getKursID() == id) {
					Result = true;
					//kname = kurs.getKursAD();
					break;
				}//if id control
			}//forEachLoop
		}//if id > 0		
		return Result;
	}
	
	/**
	 * ArrayList<Kurs> i�eri�indeki t�m verileri sat�r sat�r dosyaya yazar.
	 * 
	 * @param KursData : Kurslara ait (ID, KURS ADI) t�m bilgileri tutan listedir.
	 * 
	 * @return void.
	 * */
	public static void WriteFileKursTXT(ArrayList<Kurs> KursData) throws IOException {
		File cntrl = new File("kurs.txt");
		if (cntrl.exists()) cntrl.delete();
        FileWriter fwWriter = new FileWriter("kurs.txt", Charset.forName("UTF-8"), false);
        for (Kurs kurswr : KursData) {
        	fwWriter.write(kurswr.getKursID()+"-"+kurswr.getKursAD()+"\n");
		}
        fwWriter.close();
    }
	
	/**
	 * ArrayList<Kursiyer> i�eri�indeki t�m verileri sat�r sat�r dosyaya yazar.
	 * 
	 * @param KursiyerData : Kursiyerlere ve ald��� Kurslara ait t�m bilgileri tutan listedir.
	 * 
	 * @return void.
	 * */
	public static void WriteFileKursiyerTXT(ArrayList<Kursiyer> KursiyerData) throws IOException {
		File cntrl = new File("kursiyer.txt");
		if (cntrl.exists()) cntrl.delete();
        FileWriter fwWriter = new FileWriter("kursiyer.txt", Charset.forName("UTF-8"), false);
        for (Kursiyer kursiyer : KursiyerData) {
			fwWriter.write(String.format("*%d-%s-%d\n", kursiyer.getKursiyerID(), kursiyer.getKursiyerAdSoyad(), kursiyer.getKursiyerYAS()));
			for (Kurs kurs : kursiyer.alinanKurslar) {
				fwWriter.write("%"+String.format("%d-%s\n",kurs.getKursID(), kurs.getKursAD()));
			}
		}
        
        fwWriter.close();
    }
		
	/**
	 * Kurs ekleme fonksiyonudur.
	 * 
	 * Kullan�c�dan al�nan
	 * @param ID = Kurs ID
	 * @param AD = Kurs AD
	 * bilgilerini ekler.
	 * 
	 * @return void
	 * */
	public static void KursAdd() throws IOException {
		System.out.println("[-------------------KURS EKLE--------------------]");		
		Scanner scan = new Scanner(System.in);
		
		System.out.print("L�tfen KURS ID giriniz:");		
		int _kursID = scan.nextInt(); scan.nextLine();
		System.out.print("L�tfen KURS AD giriniz:");
		String _kursAD = scan.nextLine();
		
		if (KursIDControl(_kursID) == false)
		{
			Kurs krs = new Kurs(_kursID, _kursAD);
			alKurslar.add(krs);
			System.out.println(String.format("%s adl� kurs basar�yla eklenmi�tir", _kursAD));
		}
		else
		{
			System.out.println(String.format("Bu Kurs ID=> %d daha �nce kay�t edilmi�tir!", _kursID));
		}
	}
	
	/**
	 * Kurslar� listeleyen fonksiyondur.
	 * 
	 * @return void
	 * 
	 * */	
	public static void KursListPrint() {
		System.out.println(String.format("%s\t%s", "Kurs ID", "Kurs AD"));
		for (Kurs kurs : alKurslar) {
			System.out.println(String.format("%d\t%s", kurs.getKursID(), kurs.getKursAD()));
		}
	}
	
	/**
	 * ID si g�nderilen kursun ismini d�nd�ren fonksiyondur.
	 * 
	 * @param id = Kurs ID
	 * @return String
	 * 
	 * */
	public static String GetKursName(int id) {
		String kursName="";
		if (id > 0) {
			for (Kurs kurs : alKurslar) {
				if (kurs.getKursID() == id) {
					kursName = kurs.getKursAD();
					break;
				}//if id control
			}//forEachLoop
		}//if id > 0		
		return kursName;
	}
	
	/**
	 * Kursiyer ID kontrol fonksiyonudur.
	 * 
	 * @param id = Fonksiyona g�nderilen ID listede taran�r.
	 * Daha �nce kay�tl� bir ID var ise <b>true</b> yok ise <b>false</b> doner.
	 * 
	 * @return boolean
	 * 
	 * */
	public static boolean KursiyerIDControl(int id) {
		boolean Result=false;
		if (id > 0) {
			for (Kursiyer kursiyer : alKursiyerler) {
				if (kursiyer.getKursiyerID() == id) {
					Result = true;
					break;
				}//if id control
			}//forEachLoop
		}//if id > 0		
		return Result;
	}	
	
	/**
	 * Kursiyer ekleme fonksiyonudur.
	 * 
	 * Kullan�c�dan al�nan
	 * @param ID = Kursiyer ID
	 * @param AdSoyad = Kursiyer Ad ve Soyad
	 * @param YAS = Kursiyer ya�
	 * bilgilerini ekler.
	 * 
	 * @return void
	 * 
	 * */
	public static void KursiyerAdd() {
		System.out.println("[-------------------KURSIYER EKLE--------------------]");		
		Scanner scan = new Scanner(System.in);	
		System.out.print("L�tfen KURSIYER ID giriniz:");		
		int _kID = scan.nextInt(); scan.nextLine();
		
		if (KursiyerIDControl(_kID) == true)
		{
			System.out.println(String.format("Bu ID=%d ba�ka bir kursiyere verilmi�tir. L�tfen yeni ID giriniz.", _kID));
			return;
		}
		System.out.print("L�tfen KURSIYER AD/SOYAD giriniz:");
		String _kNameSurname = scan.nextLine();
		System.out.print("L�tfen KURSIYER YAS giriniz:");		
		int _kAge = scan.nextInt(); scan.nextLine();
	
		ArrayList<Kurs> nKurs = new ArrayList<Kurs>();
		String _kKursName="";
		
		while(true)
		{
			_kKursName="";
			System.out.println("<-- Sistemde tan�ml� kurslar�n listesi -->");
			KursListPrint();
			System.out.print("L�tfen bir Kurs ID giriniz:");		
			int _kKursID = scan.nextInt(); scan.nextLine();
			_kKursName = GetKursName(_kKursID);
			if (_kKursName != "")
			{
				nKurs.add(new Kurs(_kKursID, _kKursName));
				System.out.println("Kurs bilgisi kursiyere tan�mland�.");
			}
			else
			{
				System.out.println(String.format("Girilen kurs ID = %d sistemde bulunmamaktad�r!", _kKursID));
			}
			
			System.out.println("Kurs eklemek icin 0'a, ��kmak i�in 9 a bas�n�z:");
			int s = scan.nextInt(); scan.nextLine();
			if (s ==9) break;
		}//whileEnd
		
		alKursiyerler.add(new Kursiyer(_kID, _kNameSurname, _kAge, nKurs));
	}
	
	
	/**
	 * Ad ve Soyada g�re arama yapan fonksiyondur.
	 * Aran�lan ifade va ise bilgilerini yazd�r�r.
	 * Aran�lan ad soyad yok ise [****] adl� kursiyer bulunamad� �eklinde mesaj verir.
	 * 
	 * @return void
	 * 
	 * */
	public static void KursiyerSearch() {
		System.out.println("[-------------------KURSIYER ARAMA--------------------]");		
		Scanner scan = new Scanner(System.in);
		System.out.print("L�tfen aranacak KURSIYER AD/SOYAD giriniz:");
		String _kNameSurname = scan.nextLine();
		
		boolean isFound=false;
		for (Kursiyer kursiyer : alKursiyerler) {
			if (kursiyer.getKursiyerAdSoyad().equals(_kNameSurname))
			{
				System.out.println("*** KURSIYER BULUNDU ***");
				System.out.println(String.format("%d\t%s\t%d", kursiyer.getKursiyerID(), kursiyer.getKursiyerAdSoyad(),
						kursiyer.getKursiyerYAS()));
				isFound = true;
				break;
			}
		}
		
		if (isFound == false)
			System.out.println(String.format("%s adl� kursiyer bulunamad�!", _kNameSurname));
	}
	
	
	/**
	 * Kursiyere ait ID var ise bilgilerini silen fonksiyondur.
	 * Aran�lan ID yok ise kursiyer ID bulunamad� �eklinde mesaj verir.
	 * 
	 * @return void
	 * 
	 * */
	public static void KursiyerDelete() {
		System.out.println("[-------------------KURSIYER SIL--------------------]");		
		Scanner scan = new Scanner(System.in);	
		System.out.print("L�tfen SILINECEK KURSIYER ID giriniz:");		
		int _kID = scan.nextInt(); scan.nextLine();
		boolean isFound = false;
		for (Kursiyer kursiyer : alKursiyerler) {
			if (kursiyer.getKursiyerID() == _kID)
			{
				System.out.println("*** KURSIYER SILINDI ***");
				System.out.println(String.format("%d\t%s\t%d", kursiyer.getKursiyerID(), kursiyer.getKursiyerAdSoyad(),
						kursiyer.getKursiyerYAS()));
				alKursiyerler.remove(kursiyer);
						
				isFound = true;
				break;
			}
		}//forEachLoop
		
		if (isFound == false)
			System.out.println(String.format("%d numaral� kursiyer bulunamad�!", _kID));	
	}
	
	/**
	 * T�m kursiyerleri listeleyen fonksiyondur
	 * 
	 * @param detail = False ise sadece kursiyere ait bilgileri listeler.
	 * @param detail = True ise kuriyere ait bilgileri ve ald��� kurslar� listeleme yapar
	 *
	 * @return void
	 * */
	public static void KursiyerListPrint(boolean detail ) {
		System.out.println("[------------------KURSIYER LISTELEME-------------------]");
		if (detail == false)
			System.out.println(" T�m Kursiyerler ");
		else
			System.out.println(" T�m Kursiyerler ve Ald�klar� Kurslar");
		
		for (Kursiyer kursiyer : alKursiyerler) {
			System.out.println(String.format("%d\t%s\t%d", kursiyer.getKursiyerID(), kursiyer.getKursiyerAdSoyad(),
					kursiyer.getKursiyerYAS()));
			if (detail == true)
			{
				for (Kurs alnKurs : kursiyer.alinanKurslar) {
					System.out.println(String.format("\t%d    %s", alnKurs.getKursID(), alnKurs.getKursAD()));
				}
			}
		}//forEachLoop
	}
	
	/**
	 * Kursiyerin ald��� kurslara g�re ayl�k �deme �cretini hesaplayan fonksiyondur.
	 * 
	 * @return void
	 */
	public static void KursiyerPaymentCalculate() {
		System.out.println("[------------KURSIYER UCRET HESAPLA----------------]");		
		Scanner scan = new Scanner(System.in);	
		System.out.print("L�tfen KURSIYER ID giriniz:");		
		int _kID = scan.nextInt(); scan.nextLine();
		boolean isFound = false;
		int kursSize=0;
		for (Kursiyer kursiyer : alKursiyerler) {
			if (kursiyer.getKursiyerID() == _kID)
			{
				System.out.println(String.format("%d\t%s\t%d", kursiyer.getKursiyerID(), kursiyer.getKursiyerAdSoyad(),
						kursiyer.getKursiyerYAS()));
				kursSize = kursiyer.alinanKurslar.size();
				isFound = true;
				break;
			}
		}//forEachLoop
		
		if (isFound == true)
		{
			switch (kursSize) {
			case 0:
				System.out.println("**Herhangi bir kurs almam��t�r!");
				break;
			case 1:
				System.out.println(String.format("Ayl�k �denecek Tutar : %.2f TL", (kursSize * 400.0)));
				System.out.println("**Tek kurs al�nd��� i�in herhangi bir kampanyadan yararlan�lmamaktad�r!");
				break;
			case 2:
				System.out.println(String.format("Ayl�k �denecek Tutar : %.2f TL", ((kursSize-1) * 400) + (400 * 0.85)));
				System.out.println("KAMPANYA-1:  2 kurs alan kursiyerler i�indir.\nBu kursiyerlere ikinci kurs %15 indirimlidir.");
				break;
			case 3:
				System.out.println(String.format("Ayl�k �denecek Tutar : %.2f TL", ((kursSize-1) * 400) + (400 * 0.75)));
				System.out.println("KAMPANYA-2:  3 kurs alan kursiyerler i�indir.\nBu kursiyerlere ���nc� kurs %25 indirimlidir.");
				break;
			default:
				System.out.println(String.format("Ayl�k �denecek Tutar : %.2f TL", (kursSize * 400 * 0.90)));
				System.out.println("KAMPANYA-3:  3 kurs ve �st� alan kursiyerler i�indir.\nBu kursiyerlere her kurs %10 indirimlidir.");
				break;
			}
		}
		else
		{
			System.out.println(String.format("%d numaral� kursiyer bulunamad�!", _kID));
		}
	}

	public static void main(String[] args) throws IOException {
		int iChoice; boolean ProExit=true;

		//txt dosyalar�ndan veriler okunuyor
		ReadFileKursTXT();
		ReadFileKursiyerTXT();
		
		while (ProExit) {
			MenuScreen();
			System.out.print("L�tfen men�den bir i�lem se�iniz:");
			Scanner mn = new Scanner(System.in);
			if (mn.hasNextInt() == true)
			{
				iChoice = mn.nextInt(); 
				switch (iChoice) {
				case 1: //Kurs Ekle
					KursAdd(); 
					break;
				case 2: //Kurs Listele
					KursListPrint(); 
					break;
				case 3: //Kursiyer Ekle
					KursiyerAdd();
					break;
				case 4: //Kursiyer Ara
					KursiyerSearch();
					break;
				case 5: //Kursiyer Sil
					KursiyerDelete();
					break;
				case 6: //Kursiyerleri Listele
					KursiyerListPrint(false);
					break;
				case 7: //Kursiyerleri Ayr�nt�l� Listele
					KursiyerListPrint(true);
					break;
				case 8: //Kursiyerin �deyece�i Tutar� Hesapla
					KursiyerPaymentCalculate();
					break;
				case 9: //C�k�s
					System.out.println("Kurs verileri dosyaya (kurs.txt) kaydediliyor...");
					WriteFileKursTXT(alKurslar);
					
					System.out.println("Kursiyer verileri dosyaya (kursiyer.txt) kaydediliyor...");
					WriteFileKursiyerTXT(alKursiyerler);
					
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
				System.out.println("\nMENUDEN ISLEM YAPMAK ICIN 1-9 ARALIGINDA B�R SECIM YAPINIZ!");
			}
		}//while::End

	}

}

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
	 * Menü kýsmýný yazdýran fonksiyondur.
	 * @return void
	 */
	
	public static void MenuScreen() {
		System.out.println("|-------------------------------------------------------|");
		System.out.println("|                    KURS OTOMASYONU                    |");
		System.out.println("|-------------------------------------------------------|");
		System.out.println(String.format("|%s\n|%s\n|%s\n|%s\n|%s\n|%s\n|%s\n|%s\n|%s", 
				" 1 – Kurs Ekle",
				" 2 – Kurs Listele",
				" 3 – Kursiyer Ekle",
				" 4 – Kursiyer Ara",
				" 5 – Kursiyer Sil",
				" 6 – Kursiyerleri Listele",
				" 7 – Kursiyerleri Ayrýntýlý Listele",
				" 8 - Kursiyerin Ödeyeceði Tutarý Hesapla",
				" 9 - Çýkýþ"));
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
				rLine = bfRd.readLine();//Satýr satýr okuma islemi
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
	 * Kursiyer bilgilerini ve almýþ olduðu kurs bilgilerini dosyadan(kursiyer.txt) okuyan fonksiyondur.
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
				rLine = bfRd.readLine();//Satýr satýr okuma islemi		
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
	 * @param id = Fonksiyona gönderilen ID listede taranýr.
	 * Daha önce kayýtlý bir ID var ise <b>true</b> yok ise <b>false</b> doner.
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
	 * ArrayList<Kurs> içeriðindeki tüm verileri satýr satýr dosyaya yazar.
	 * 
	 * @param KursData : Kurslara ait (ID, KURS ADI) tüm bilgileri tutan listedir.
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
	 * ArrayList<Kursiyer> içeriðindeki tüm verileri satýr satýr dosyaya yazar.
	 * 
	 * @param KursiyerData : Kursiyerlere ve aldýðý Kurslara ait tüm bilgileri tutan listedir.
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
	 * Kullanýcýdan alýnan
	 * @param ID = Kurs ID
	 * @param AD = Kurs AD
	 * bilgilerini ekler.
	 * 
	 * @return void
	 * */
	public static void KursAdd() throws IOException {
		System.out.println("[-------------------KURS EKLE--------------------]");		
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Lütfen KURS ID giriniz:");		
		int _kursID = scan.nextInt(); scan.nextLine();
		System.out.print("Lütfen KURS AD giriniz:");
		String _kursAD = scan.nextLine();
		
		if (KursIDControl(_kursID) == false)
		{
			Kurs krs = new Kurs(_kursID, _kursAD);
			alKurslar.add(krs);
			System.out.println(String.format("%s adlý kurs basarýyla eklenmiþtir", _kursAD));
		}
		else
		{
			System.out.println(String.format("Bu Kurs ID=> %d daha önce kayýt edilmiþtir!", _kursID));
		}
	}
	
	/**
	 * Kurslarý listeleyen fonksiyondur.
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
	 * ID si gönderilen kursun ismini döndüren fonksiyondur.
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
	 * @param id = Fonksiyona gönderilen ID listede taranýr.
	 * Daha önce kayýtlý bir ID var ise <b>true</b> yok ise <b>false</b> doner.
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
	 * Kullanýcýdan alýnan
	 * @param ID = Kursiyer ID
	 * @param AdSoyad = Kursiyer Ad ve Soyad
	 * @param YAS = Kursiyer yaþ
	 * bilgilerini ekler.
	 * 
	 * @return void
	 * 
	 * */
	public static void KursiyerAdd() {
		System.out.println("[-------------------KURSIYER EKLE--------------------]");		
		Scanner scan = new Scanner(System.in);	
		System.out.print("Lütfen KURSIYER ID giriniz:");		
		int _kID = scan.nextInt(); scan.nextLine();
		
		if (KursiyerIDControl(_kID) == true)
		{
			System.out.println(String.format("Bu ID=%d baþka bir kursiyere verilmiþtir. Lütfen yeni ID giriniz.", _kID));
			return;
		}
		System.out.print("Lütfen KURSIYER AD/SOYAD giriniz:");
		String _kNameSurname = scan.nextLine();
		System.out.print("Lütfen KURSIYER YAS giriniz:");		
		int _kAge = scan.nextInt(); scan.nextLine();
	
		ArrayList<Kurs> nKurs = new ArrayList<Kurs>();
		String _kKursName="";
		
		while(true)
		{
			_kKursName="";
			System.out.println("<-- Sistemde tanýmlý kurslarýn listesi -->");
			KursListPrint();
			System.out.print("Lütfen bir Kurs ID giriniz:");		
			int _kKursID = scan.nextInt(); scan.nextLine();
			_kKursName = GetKursName(_kKursID);
			if (_kKursName != "")
			{
				nKurs.add(new Kurs(_kKursID, _kKursName));
				System.out.println("Kurs bilgisi kursiyere tanýmlandý.");
			}
			else
			{
				System.out.println(String.format("Girilen kurs ID = %d sistemde bulunmamaktadýr!", _kKursID));
			}
			
			System.out.println("Kurs eklemek icin 0'a, Çýkmak için 9 a basýnýz:");
			int s = scan.nextInt(); scan.nextLine();
			if (s ==9) break;
		}//whileEnd
		
		alKursiyerler.add(new Kursiyer(_kID, _kNameSurname, _kAge, nKurs));
	}
	
	
	/**
	 * Ad ve Soyada göre arama yapan fonksiyondur.
	 * Aranýlan ifade va ise bilgilerini yazdýrýr.
	 * Aranýlan ad soyad yok ise [****] adlý kursiyer bulunamadý þeklinde mesaj verir.
	 * 
	 * @return void
	 * 
	 * */
	public static void KursiyerSearch() {
		System.out.println("[-------------------KURSIYER ARAMA--------------------]");		
		Scanner scan = new Scanner(System.in);
		System.out.print("Lütfen aranacak KURSIYER AD/SOYAD giriniz:");
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
			System.out.println(String.format("%s adlý kursiyer bulunamadý!", _kNameSurname));
	}
	
	
	/**
	 * Kursiyere ait ID var ise bilgilerini silen fonksiyondur.
	 * Aranýlan ID yok ise kursiyer ID bulunamadý þeklinde mesaj verir.
	 * 
	 * @return void
	 * 
	 * */
	public static void KursiyerDelete() {
		System.out.println("[-------------------KURSIYER SIL--------------------]");		
		Scanner scan = new Scanner(System.in);	
		System.out.print("Lütfen SILINECEK KURSIYER ID giriniz:");		
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
			System.out.println(String.format("%d numaralý kursiyer bulunamadý!", _kID));	
	}
	
	/**
	 * Tüm kursiyerleri listeleyen fonksiyondur
	 * 
	 * @param detail = False ise sadece kursiyere ait bilgileri listeler.
	 * @param detail = True ise kuriyere ait bilgileri ve aldýðý kurslarý listeleme yapar
	 *
	 * @return void
	 * */
	public static void KursiyerListPrint(boolean detail ) {
		System.out.println("[------------------KURSIYER LISTELEME-------------------]");
		if (detail == false)
			System.out.println(" Tüm Kursiyerler ");
		else
			System.out.println(" Tüm Kursiyerler ve Aldýklarý Kurslar");
		
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
	 * Kursiyerin aldýðý kurslara göre aylýk ödeme ücretini hesaplayan fonksiyondur.
	 * 
	 * @return void
	 */
	public static void KursiyerPaymentCalculate() {
		System.out.println("[------------KURSIYER UCRET HESAPLA----------------]");		
		Scanner scan = new Scanner(System.in);	
		System.out.print("Lütfen KURSIYER ID giriniz:");		
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
				System.out.println("**Herhangi bir kurs almamýþtýr!");
				break;
			case 1:
				System.out.println(String.format("Aylýk Ödenecek Tutar : %.2f TL", (kursSize * 400.0)));
				System.out.println("**Tek kurs alýndýðý için herhangi bir kampanyadan yararlanýlmamaktadýr!");
				break;
			case 2:
				System.out.println(String.format("Aylýk Ödenecek Tutar : %.2f TL", ((kursSize-1) * 400) + (400 * 0.85)));
				System.out.println("KAMPANYA-1:  2 kurs alan kursiyerler içindir.\nBu kursiyerlere ikinci kurs %15 indirimlidir.");
				break;
			case 3:
				System.out.println(String.format("Aylýk Ödenecek Tutar : %.2f TL", ((kursSize-1) * 400) + (400 * 0.75)));
				System.out.println("KAMPANYA-2:  3 kurs alan kursiyerler içindir.\nBu kursiyerlere üçüncü kurs %25 indirimlidir.");
				break;
			default:
				System.out.println(String.format("Aylýk Ödenecek Tutar : %.2f TL", (kursSize * 400 * 0.90)));
				System.out.println("KAMPANYA-3:  3 kurs ve üstü alan kursiyerler içindir.\nBu kursiyerlere her kurs %10 indirimlidir.");
				break;
			}
		}
		else
		{
			System.out.println(String.format("%d numaralý kursiyer bulunamadý!", _kID));
		}
	}

	public static void main(String[] args) throws IOException {
		int iChoice; boolean ProExit=true;

		//txt dosyalarýndan veriler okunuyor
		ReadFileKursTXT();
		ReadFileKursiyerTXT();
		
		while (ProExit) {
			MenuScreen();
			System.out.print("Lütfen menüden bir iþlem seçiniz:");
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
				case 7: //Kursiyerleri Ayrýntýlý Listele
					KursiyerListPrint(true);
					break;
				case 8: //Kursiyerin Ödeyeceði Tutarý Hesapla
					KursiyerPaymentCalculate();
					break;
				case 9: //Cýkýs
					System.out.println("Kurs verileri dosyaya (kurs.txt) kaydediliyor...");
					WriteFileKursTXT(alKurslar);
					
					System.out.println("Kursiyer verileri dosyaya (kursiyer.txt) kaydediliyor...");
					WriteFileKursiyerTXT(alKursiyerler);
					
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
				System.out.println("\nMENUDEN ISLEM YAPMAK ICIN 1-9 ARALIGINDA BÝR SECIM YAPINIZ!");
			}
		}//while::End

	}

}

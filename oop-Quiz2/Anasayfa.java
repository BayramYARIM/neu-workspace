package LabQuiz;

import java.util.ArrayList;
import java.util.Scanner;

public class Anasayfa {

	public static void main(String[] args) {
		
		System.out.println("LAB QUIZ2 - BAYRAM YARIM");
		
		int matCount=0;
		ArrayList<MatematikOgretmeni> matOgr = new ArrayList<MatematikOgretmeni>();		
		Scanner scan = new Scanner(System.in);
		String _Ad, _Soyad;
		int _CocukSayisi, _Yas;
		System.out.print("Lütfen matematik ögretmen sayýsýný giriniz:");
		if (scan.hasNextInt()) {
			matCount = scan.nextInt(); scan.nextLine();
			if (matCount > 0) {
				
				for (int i = 0; i < matCount; i++) {
					System.out.print("Lütfen Öðretmenin adýný giriniz:");
					_Ad = scan.nextLine();
					System.out.print("Lütfen Öðretmenin soyadýný giriniz:");
					_Soyad = scan.nextLine();
					System.out.print("Lütfen Öðretmenin yas giriniz:");
					_Yas = scan.nextInt(); scan.nextLine();
					System.out.print("Lütfen Öðretmenin cocuk sayisini giriniz:");
					_CocukSayisi = scan.nextInt(); scan.nextLine();
					
					MatematikOgretmeni Ogr = new MatematikOgretmeni(_Ad, _Soyad, _Yas, _CocukSayisi);					
					matOgr.add(Ogr);
				}//forLoop
			}//MatCount Control
		}//HasNext Control
		
		//Alinan bilgilerin listeleme islemleri
		for (MatematikOgretmeni matematikOgretmeni : matOgr) {
			System.out.println(String.format("ID=%d Ad/Soyad:%s %s, Maas:%.2f Brans:%s",
			matematikOgretmeni.getID(), matematikOgretmeni.getAd(), matematikOgretmeni.getSoyad(),
			matematikOgretmeni.MaasHesapla(), matematikOgretmeni.Brans));
		}
		
		
		//Kullanicidan indis numaralari aliniyor
		
		int _i1=0, _i2=0;
		System.out.println(String.format("Lütfen %d - %d araliginda bir deger giriniz!", 1, matCount));
		System.out.print("Lütfen 1.indis no giriniz:");
		_i1 = scan.nextInt(); scan.nextLine();
		System.out.print("Lütfen 2.indis no giriniz:");
		_i2 = scan.nextInt(); scan.nextLine();
		
		//Ýndis numaralari 0'dan basladigi icin -1 yapildi
		_i1 = _i1-1;
		_i2 = _i2-1;
		if ((_i1 <= matCount) && (_i1 >=0 ) && (_i2 <= matCount) && (_i2 >=0 ))
		{
			//MatematikOgretmeni sinifindan temp degiskenler olusturýldu ve arraylistden alindi
			//Temp degiskenler daha sonra belirlenen indis numaralarina atildi
			MatematikOgretmeni tmp1 = matOgr.get(_i1);
			MatematikOgretmeni tmp2 = matOgr.get(_i2);			
			matOgr.set(_i1, tmp2);
			matOgr.set(_i2, tmp1);			
		}
		else
		{
			System.out.println("HATA: Lütfen gecerli bir indis no giriniz!");
		}
		
		//Yeni liste yazdiriliyor
		for (MatematikOgretmeni matematikOgretmeni : matOgr) {
			System.out.println(String.format("ID=%d Ad/Soyad:%s %s, Maas:%.2f Brans:%s",
			matematikOgretmeni.getID(), matematikOgretmeni.getAd(), matematikOgretmeni.getSoyad(),
			matematikOgretmeni.MaasHesapla(), matematikOgretmeni.Brans));
		}
		

	}

}

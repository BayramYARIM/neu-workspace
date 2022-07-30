"""
Proje  : KARGO KAYIT VE TAKIP OTOMASYON PROJESI
Author : BAYRAM YARIM
No     : 18010011067
Mail   : bayramyarim@ogr.erbakan.edu.tr
Mail   : byyarim@gmail.com
IDE    : Visual Studio Code
Python : 3.8.9 - 64bit
UpDate : 29.05.2021
"""

from io import TextIOWrapper #Dosya okuma ve yazma işlemleri için eklenen kütüphane
import os  #isletim sistemi bilgileri, temizleme fonksiyonu ve dosya kontrolleri icin eklenen kütüphane

#Program versiyon ve derleme numarasi tutulmasi icin eklendi
pVersion = "v1.0.1"
pBuild   = "210531#"
UnitPrice = 3.37
#Program veri kayitlarinin tutuldugu dosya isimleri
fCustomerTXT = "18010011067M.txt"
fCargoTXT    = "18010011067K.txt"
#Ekran uyari mesajlarini renklendirmek icin kullanildi 
scLightRed    = "\033[1;31m"  
scLightGreen  = "\033[1;32m"  
scYellow      = "\033[1;33m"    
scLightPink   = "\033[1;35m"  
scLightCyan   = "\033[1;36m"   
scEnd         = "\033[0m"
#Müşteri kayıtlarının tutulduğu sözlük yapısı
CustomerDict = dict()
CustomerCount = 0
#Kargo kayıtlarının tutulduğu sözlük yapısı
CargoCount = 0
CargoDict = dict()
#Kargoya ait bilgilerin tutuldugu hazır sözlük yapıları
PCargoType = {1:"ZARF", 2:"PAKET", 3:"KOLİ", 4:"DİĞER"}
PPaymentType = {1:"GON.ODEMELİ", 2:"ALICI ODEMELI", 3:"KAPIDA ODEME"}
PCargoStatus = {1:"YENI", 2:"KARGO HAZIR", 3:"TASIMA HALİNDE", 4:"AKTARMADA", 5:"DAGITIMDA", 6:"TESLIM EDILDI", 7:"IPTAL EDİLDİ", 8:"IADE"}

def FileExistControl(fName):
    """
    Parametre olarak gönderilen dosyayı kontrol eder. Varsa TRUE yoksa FALSE dondurulur
    """
    return os.path.exists(fName)

def ScreenClear():    
    """
    Ekran temizleme fonksiyonudur.
    """
    if os.name == 'nt': 
        _ = os.system('cls') #MS Windows
    else: 
        _ = os.system('clear') #Mac or Linux

def ShowMessage(aMessage:str, funcNo:int=0):
    """
    aMessage : Str tipinde bir değişkendir. Gelen mesaj print komutuyla yazdırılır.\n
    funcNo : int tipinde bir değişkendir. Numarası gönderilen fonksiyonu tekrar çagırır=Varsayilan=0'dır
    """        
    print("\n\n"+aMessage.center(80), end="\n\n")
    if (funcNo >=1 and funcNo <= 10):
        x = input(scLightPink+"Ana Menuye donmek icin [ ENTER ] tusuna, islem tekrarı icin [T] tusuna basiniz:".center(80)+scEnd)
    else:
        x = input(scLightPink+"Ana Menuye donmek icin [ ENTER ] tusuna basiniz:".center(80)+scEnd)
    if x=='' or x=="":
        MainMenu()
    elif ((x=="T") or (x == "t")):
        if (funcNo == 1):
            CustomerAdd()
        elif(funcNo == 2):
            CustomerUpdate()
        elif(funcNo == 3):
            CustomerDelete()
        elif(funcNo == 4):
            CustomerList()
        elif(funcNo == 5):
            CargoAdd()
        elif(funcNo == 6):
            CargoUpdate()
        elif(funcNo == 7):
            CargoDelete()
        elif(funcNo == 8):
            CargoList()
        elif(funcNo == 9):
            CargoCalculate()
        elif(funcNo == 10):
            CustomerPayment()
        else:
            ShowMessage(aMessage)
    else:
        ScreenClear()
        ShowMessage("YANLIŞ GİRİŞ") #ReInıt

def ProgramAbout():
    """
    Program hakkında bilgi veren fonksiyondur.\n
    Program ile ilgili tüm bilgiler sözlük(dictionary) tipinde saklanmıştır.
    """
    about_dict = {"Proje":"KARGO KAYIT VE TAKIP OTOMASYON PROJESI", "Versiyon":pVersion, "Derleme":pBuild,
    "Hoca-1":"Doç.Dr.Hüseyin HAKLI", "Hoca-2":"Arş.Gör.Abdülkadir PEKTAŞ","Yazılım":"Bayram YARIM", 
    "Okul":"NEU-Bilgisayar Muhendisligi", "No":"18010011067", "E-Posta":"byyarim@gmail.com", 
    "Yazılım Dili":"Python(3.8.9)", "Son Guncelleme":"29.05.2021"}
    CaptionDesign("PROGRAM HAKKINDA")  
    for key, value in about_dict.items():
        print("\t{:20s} : {}".format(key, value))
    print("*" * 80)
    ShowMessage("")

def ProgramExit():
    """
    Programdan çıkış fonksiyonudur.
    """
    CaptionDesign("PROGRAMDAN ÇIKIŞ")
    print("Programdan çıkış yapılacaktır. Emin misiniz?".center(80), end="\n\n")
    mrExit = input("[ E/e=Evet, H/h=Hayır ]:".center(80))
    if (mrExit.lower() == "e"):
        print(scYellow+"Bizi tercih ettiginiz icin tesekkurler...".center(80)+scEnd)
        exit()
    else:
        MainMenu()

def CaptionDesign(aCaption:str):
    """
    Ekrani temizleme işlemi ve parametre olarak gonderilen string menü başlığını ekranın orta kısmına yazdırma işlemini yapar
    """
    ScreenClear()
    print("*"*80)
    print(scYellow+ " {} ".format(aCaption).center(80) + scEnd)
    print("*"*80)

def CustomerIDControl(cID):
    """
    Gönderilen müşteri ID'yi sözlükte arar ve var ise sonuc olarak\n
    Tuple veri tipinde (True, MüşteriBilgisi, ve Sözlük(Dosya)Sıra Numarası(KEY)) dondurulur.
    """
    for i in CustomerDict.keys():
        if (CustomerDict[i]["ID"] == cID):
            #print(CustomerDict[i])
            return True, CustomerDict[i],i
    return False, dict(),0

def CustomerActiveCargo(cID):
    """
    Parametre olarak gönderilen müşteri ID'ye ait aktif kargo varsa sonuc TRUE değil ise FALSE doner
    """
    for i in CargoDict.keys():
        for _ in CargoDict[i].values():
            #Parametre olarak gönderilen müşterinin Gönd veya Alıcı olması ve kargo durumunun aktif olması durumunu TRUE/FALSE dondurur
            if (((CargoDict[i]["SENDID"] == cID) or (CargoDict[i]["RECVID"] == cID)) and (int(CargoDict[i]["STATUS"]) <= 5)):
                return True
    return False

def WriteFileCustomer():
    """
    Sözlük tipinde tutulan müşterileri dosyaya yazdırma fonksiyonudur.
    """
    with open(fCustomerTXT,"w", encoding="UTF-8") as CusFile:
        for k in CustomerDict.keys():
            CusFile.write("{};{};{};{:.2f};{};\n".format(CustomerDict[k]["ID"], 
            CustomerDict[k]["NAME"], CustomerDict[k]["PHONE"],
            float(CustomerDict[k]["DEBT"]),CustomerDict[k]["ADDRESS"]))

def ReadFileCustomer():
    """
    Dosyada bulunan tüm müsterileri burada okunup sözlük{Dictionary} yapısına aktarma işlemi\n
    Müşteriler iç içe sözlük içinde tutulmaktadır.
    """
    global CustomerDict  #Tum musterilerin tutuldugu sözlük(dict)
    global CustomerCount #Toplam musteri sayisinin tutuldugu degisken
    CustomerDict.clear() #Tum sözlük içindeki veriyi temizliyoruz
    CustomerCount = 0 #Sözlük sayacini sifirliyoruz
    if (FileExistControl(fCustomerTXT) == False): #Dosya yok ise oluşturma işlemi
        with open(fCustomerTXT,"a", encoding="UTF-8") as CF:# a kipinde dosyayı oluşturma
            print("")

    with open(fCustomerTXT,"r", encoding="UTF-8") as CusFile:# r kipinde dosyayı açar
        for line in CusFile: #Dosyayi satir satir okuma islemi
            if (len(line) > 0):                
                TempDict = dict()
                CustomerCount += 1
                line = line[:-2] #satir sonundaki ; ve \n karakterini temizleme
                temp = line.split(";") #kayitlarin arasini noktali virgüle göre ayirmak
                TempDict["ID"] = temp[0]
                TempDict["NAME"] = temp[1]
                TempDict["PHONE"] = temp[2]
                TempDict["DEBT"] = float(temp[3])
                TempDict["ADDRESS"] = temp[4]                
                CustomerDict[CustomerCount] = TempDict

def CustomerAdd():
    """
    Müşteri ekleme fonksiyonudur.
    """
    global CustomerCount    
    CustomerInfo = dict()
    CaptionDesign("MÜŞTERİ EKLE")
    cusID = input("Müşteri ID bilgisini giriniz:")
    if (len(cusID) <= 0):
        ShowMessage( "Müşteri ID bilgisi boş geçilemez!", 1)
    aValue = CustomerIDControl(cusID)
    if (aValue[0] == True):
        ShowMessage( "\n\n{} numarali ID, {} adlı müşteriye kayit edilmiştir".format(cusID, aValue[1]["NAME"]), 1)
    cusName = input("Müşterinin Ad-Soyad bilgisini giriniz:")
    if (len(cusName) <= 0):
        ShowMessage( "Müşteri Ad-Soyad bilgisi boş geçilemez!", 1)
    cusPhone = input("Müşterinin Telefon bilgisini giriniz:")    
    CusAddress = input("Müşterinin Adres bilgisini giriniz:")
    if (len(CusAddress) <= 0):
        ShowMessage("Müşteri Adres bilgisi boş geçilemez!", 1)
    
    mrResult = input("Girişi yapılan müşteri bilgileri kaydedilsin mi? (E/e = Evet, H/h = Hayır):")
    if (mrResult.lower() == "e"):
        CustomerInfo["ID"] = cusID
        CustomerInfo["NAME"] = cusName
        CustomerInfo["PHONE"] = cusPhone
        CustomerInfo["DEBT"] = 0.0
        CustomerInfo["ADDRESS"] = CusAddress        
        CustomerCount += 1
        CustomerDict[CustomerCount] = CustomerInfo
        with open(fCustomerTXT,"a", encoding="UTF-8") as CusFile:
            for j in CustomerInfo.values():
                CusFile.write("{};".format(j))
            CusFile.write("\n")
        ShowMessage("YENI MÜŞTERİ EKLENDİ!",1)
    else:
        ShowMessage("MUSTERI EKLEME ISLEMI IPTAL EDILDI", 1)

def CustomerUpdate():
    """
    Müşteri kaydini düzenleme fonksiyonudur.
    """
    CustomerInfo = dict()
    CaptionDesign("MÜŞTERİ DÜZELT")
    iID = input("Düzenlenecek müşteri ID giriniz:")
    aReturn = CustomerIDControl(iID) #Parametre olarak gönderilen ID , sonuc tuple verisi olarak alınıyor.
    if (aReturn[0] == True): #Parametre olarak gönderilen ID bulunduysa TRUE ise
        tempCus = aReturn[1] #Fonksiyondan gelen musteri bilgileri buraya aktarılıyor
        print("\n\nDüzenlenecek Müşteri Bilgileri".center(80))
        print("{:5s}  {:20s}\t{:15s}\t{}".format("ID","AD-SOYAD","TELEFON","ADRES"))
        print("-"*80)
        print("{:5s}  {:20s}\t{:15s}{}".format(tempCus["ID"], tempCus["NAME"], tempCus["PHONE"], tempCus["ADDRESS"]))        
        print("-"*80)        
        cusName = input("Yeni Ad-Soyad bilgisini giriniz:")
        if (len(cusName) <= 0):
            ShowMessage("Müşteri adı boş geçilemez!", 2)
        cusPhone = input("Yeni telefon bilgisini giriniz:")
        cusAddress = input("Yeni adres bilgisini giriniz:")
        if (len(cusAddress) <= 0):
            ShowMessage("Müşteri adresi boş geçilemez!", 2)
        
        mrResult = input("Girişi yapılan müşteri bilgileri güncellensin mi? (E/e = Evet, H/h = Hayır):")
        if (mrResult.lower() == "e"):
            CustomerInfo["ID"] = tempCus["ID"] #ID degistirilemez, eski ID aktarma yapıyoruz
            CustomerInfo["NAME"] = cusName
            CustomerInfo["PHONE"] = cusPhone
            CustomerInfo["DEBT"] = tempCus["DEBT"] #Eski borc bilgisini degistirmeden aktarıyoruz
            CustomerInfo["ADDRESS"] = cusAddress        
            CustomerDict[aReturn[2]] = CustomerInfo #Fonksiyondan gelen sıra numarasındaki verileri düzenleyip aktarma işlemi
            WriteFileCustomer() #Dosyaya yazdırma işlemi
            ShowMessage("MÜŞTERİ KAYDI DUZENLENDI!",2)
        else:
            ShowMessage("MUSTERI KAYIT DUZENLEME ISLEMI IPTAL EDILDI!",2)
    else:
        ShowMessage("{} numaralı bir müşteri bulunamadı!".format(iID), 2)

def CustomerDelete():
    """
    Müşteri kaydini silme fonksiyonudur.
    """
    CaptionDesign("MÜŞTERİ SİL")
    iID = input("Silinecek müşteri ID giriniz:")
    if (len(iID) <= 0):
        ShowMessage("Müşteri ID boş geçilemez!", 3)
    else:
        aReturn = CustomerIDControl(iID) #Parametre olarak gönderilen ID , sonuc tuple verisi olarak alınıyor.
        if (aReturn[0] == True): #Parametre olarak gönderilen ID bulunduysa TRUE ise
            tempCus = aReturn[1] #Fonksiyondan gelen musteri bilgileri buraya aktarılıyor
            if (CustomerActiveCargo(iID) == True):
                print(scLightRed+"\n{} ID Numarali {} adlı müşterinin aktif kargosu bulunmaktadır. Bu kayıt silinemez!".format(tempCus["ID"], 
                tempCus["NAME"])+scEnd)
                ShowMessage("", 3)

            if (float(tempCus["DEBT"]) > 0):
                print(scLightRed+"\n{} ID Numarali {} adlı müşterinin {:.2f} TL borcu bulunmaktadır. Bu kayıt silinemez!".format(tempCus["ID"], 
                tempCus["NAME"],float(tempCus["DEBT"]))+scEnd)
                ShowMessage("", 3)
            else:
                mrResult = input("{} ID Numarali {} adlı müşteri silinsin mi?(E/e=Evet, H/h:Hayır):".format(tempCus["ID"], tempCus["NAME"]))
                if (mrResult.lower() == "e"):
                    CustomerDict.pop(aReturn[2]) #Sözlükteki müşteri silindi ve yeni veriler dosyaya tekrar yazıldı
                    WriteFileCustomer() #Dosyaya yazdırma işlemi
                    ShowMessage("MÜŞTERİ KAYDI SİLİNDİ!",3)
                else:
                    ShowMessage("SİLME İŞLEMİ İPTAL EDİLDİ!",3)
        else:
            ShowMessage("{} numaralı bir müşteri bulunamadı!".format(iID), 3)

def CustomerList():
    """
    Müsterileri listeleme fonksiyonudur.
    """
    cusCount = 0
    global CustomerDict
    CaptionDesign("MÜŞTERİ LİSTELEME")
    print("{:3s}  {:5s}  {:20s}\t{:15s}\t{:10s}\t{}".format("NO","ID","AD-SOYAD","TELEFON","BORC","ADRES"))
    print("-"*80)
    for k in CustomerDict.keys():
        cusCount += 1
        print("{:03d}  {:5s}  {:20s}\t{:15s}{:.2f}\t\t{}".format(cusCount, CustomerDict[k]["ID"], CustomerDict[k]["NAME"], 
        CustomerDict[k]["PHONE"], float(CustomerDict[k]["DEBT"]), CustomerDict[k]["ADDRESS"]))
    print("-"*80)
    print("Toplam {} müsteri listelendi...".format(cusCount).center(80))
    ShowMessage("", 4)

def ReadFileCargo():
    """
    Dosyada bulunan tüm kargo kayıtları burada okunup sözlük{Dictionary} yapısına aktarma işlemi\n
    """
    global CargoDict  #Tum musterilerin tutuldugu sözlük(dict)
    global CargoCount #Toplam musteri sayisinin tutuldugu degisken
    CargoDict.clear() #Tum sözlük içindeki veriyi temizliyoruz
    CargoCount = 0 #Sözlük sayacini sifirliyoruz
    if (FileExistControl(fCargoTXT) == False): #Dosya yok ise oluşturuluyor
        with open(fCargoTXT,"a", encoding="UTF-8") as CF:# a kipinde dosyayı açar
            print("")

    #Dosya Okuma işlemi
    with open(fCargoTXT,"r", encoding="UTF-8") as CusFile:# r kipinde dosyayı açar
        for line in CusFile: #Dosyayi satir satir okuma islemi
            if (len(line) > 0):                
                TempDict = dict()
                CargoCount += 1
                #Okunan satır liste yapısında tutulmaktadır ve split ile datalar ayrılır.
                line = line[:-2] #satir sonundaki ; ve \n karakterini temizleme
                temp = line.split(";") #kayitlarin arasini noktali virgüle göre ayirmak
                TempDict["CARGOID"]=temp[0] #str
                TempDict["SENDID"]=temp[1] #str
                TempDict["RECVID"]=temp[2] #str
                TempDict["CARGOTYPE"]=int(temp[3]) #int
                TempDict["PAYMENTTYPE"]=int(temp[4]) #int
                TempDict["PPRICE"]=float(temp[5]) #float
                TempDict["DESI"]=float(temp[6]) #float
                TempDict["TPRICE"]=float(temp[7]) #float
                TempDict["STATUS"]=int(temp[8]) #int - YENI KARGO
                CargoDict[CargoCount] = TempDict

def WriteFileCargo():
    """
    Sözlük tipinde tutulan müşterileri dosyaya yazdırma fonksiyonudur.
    """
    with open(fCargoTXT,"w", encoding="UTF-8") as CusFile:
        for k in CargoDict.keys():
            CusFile.write("{};{};{};{};{};{};{};{};{};\n".format(
                CargoDict[k]["CARGOID"],
                CargoDict[k]["SENDID"],CargoDict[k]["RECVID"],
                CargoDict[k]["CARGOTYPE"],CargoDict[k]["PAYMENTTYPE"],
                CargoDict[k]["PPRICE"],CargoDict[k]["DESI"],
                CargoDict[k]["TPRICE"],CargoDict[k]["STATUS"]))

def CargoIDControl(cID):
    """
    Gönderilen kargo ID'yi sözlükte arar ve var ise sonuc olarak\n
    Tuple veri tipinde (True, Kargoya ait bilgiler, ve Sözlük(Dosya)Sıra Numarası(KEY)) dondurulur.
    """
    for i in CargoDict.keys():
        if (CargoDict[i]["CARGOID"] == cID):
            #print(CustomerDict[i])
            return True, CargoDict[i],i
    return False, dict(),0

def CargoAdd():
    """
    Yeni kargo ekleme fonksiyonudur.
    """
    global CargoCount
    global CargoDict
    CargoInfo = dict()
    iProductPrice = 0
    iCargoDesi = 0
    iTotalPrice = 0
    iCargoStatus = 1

    CaptionDesign("KARGO EKLE")
    iCargoID = input("Kargo numarasını giriniz:")
    if (len(iCargoID) <= 0):
        ShowMessage("Kargo ID numarası boş geçilemez!", 5)

    rCargo = CargoIDControl(iCargoID)
    if (rCargo[0] == True):
        ShowMessage("Bu kargo ID daha önce kayıt edilmiştir.", 5)
    
    #Gönderici müşteri bilgi girişi ve kontrolleri
    iSendID = input("Gönderici müşteri ID giriniz:")
    if (len(iSendID) <= 0):
        ShowMessage("Gönderici müşteri ID numarası boş geçilemez!", 5)
    sValue = CustomerIDControl(iSendID)
    if (sValue[0] == True):
        print(scLightCyan+"\tGönderici Müşteri: {}-{}-{}".format(sValue[1]["ID"],sValue[1]["NAME"], sValue[1]["ADDRESS"])+ scEnd)
    else:
        ShowMessage("{} ID numaralı müşteri bulunamadı!".format(iSendID), 5)

    #Alıcı müşteri bilgi girişi ve kontrolleri
    iRecvID = input("Alıcı müşteri ID giriniz:")
    if (len(iRecvID) <= 0):
        ShowMessage("Alıcı müşteri ID numarası boş geçilemez!", 5)
    rValue = CustomerIDControl(iRecvID)
    if (rValue[0] == True):
        print(scLightCyan+"\tAlici Müşteri: {}-{}-{}".format(rValue[1]["ID"],rValue[1]["NAME"], rValue[1]["ADDRESS"])+ scEnd)
    else:
        ShowMessage("{} ID numaralı müşteri bulunamadı!".format(iRecvID), 5)
    
    #Gönderici ve Alıcı aynı müşteri olamaz
    if (iSendID == iRecvID):
        ShowMessage("Gönderici ve Alıcı aynı müşteri olamaz!", 5)

    #Kargo Tipi girişi ve kontrolleri
    try:
        iCargoType = int(input("Kargo tipini giriniz.\n{}:".format(PCargoType)))
    except ValueError:
        ShowMessage("Lütfen kargo tipini sayısal olarak giriniz!", 5)
    if ((iCargoType <= 0) or (iCargoType >= 5)):
        iCargoType = 4 #Varsayılan-DIGER
    print(scLightCyan+"\tKargo Tipi: {}".format(PCargoType[iCargoType])+scEnd)

    #Ödeme Tipi girişi ve kontrolleri
    try:
        iPaymentType = int(input("Ödeme tipini giriniz.\n{}:".format(PPaymentType)))
    except ValueError:
        ShowMessage("Lütfen ödeme tipini sayısal olarak giriniz!", 5)
    if ((iPaymentType <= 0) or (iPaymentType >= 4)):
        iPaymentType = 2 #Varsayılan-ALICI ODEMELI
    print(scLightCyan+"\tÖdeme Tipi: {}".format(PPaymentType[iPaymentType])+scEnd)

    #Ödeme Tipi alici ödemeli ise ürün tutarını kargo fiyatına eklemek gerekiyor
    if (iPaymentType == 3):
        try:
            iProductPrice = float(input("Kargo ürün ücretini giriniz:"))
            print(scLightCyan+"\tÜrün Ücreti: {:.2f} TL".format(iProductPrice)+scEnd)
        except ValueError:
            ShowMessage("Lütfen kargo ürün ücretini sayısal olarak giriniz!", 5)

    #Kargo Desi Miktarı girişi ve kontrolleri
    try:
        iCargoDesi = float(input("Kargo desi miktarını giriniz:"))
    except ValueError:
        ShowMessage("Lütfen desi miktarını sayısal olarak giriniz!", 5)
    if (iCargoDesi <= 0):
        ShowMessage("Desi miktarı 0'dan küçük olamaz!", 5)

    iTotalPrice = (iCargoDesi * UnitPrice) + iProductPrice #Kargo ücreti hesaplama
    print(scLightCyan+"\tToplam Kargo Ücreti: {:.2f} TL".format(iTotalPrice)+scEnd)

    iSecim = input("Girişi yapılan kargo bilgileri kaydedilsin mi? (E/e = Evet, H/h = Hayır): ")
    if (iSecim.lower() == "e"):
        CargoInfo["CARGOID"]=iCargoID #str
        CargoInfo["SENDID"]=iSendID #str
        CargoInfo["RECVID"]=iRecvID #str
        CargoInfo["CARGOTYPE"]=iCargoType #int
        CargoInfo["PAYMENTTYPE"]=iPaymentType #int
        CargoInfo["PPRICE"]=iProductPrice #float
        CargoInfo["DESI"]=iCargoDesi #float
        CargoInfo["TPRICE"]=iTotalPrice #str
        CargoInfo["STATUS"]=iCargoStatus #int - YENI KARGO
        CargoCount+=1
        CargoDict[CargoCount] = CargoInfo

        #Gönderinin ödeme durumuna göre tutar ilgili müşterinin hesabına aktarıyoruz
        if (iPaymentType == 1):
            CustomerDict[sValue[2]]["DEBT"] = float(CustomerDict[sValue[2]]["DEBT"]) + iTotalPrice #Gönderici müşteri hesabına
        else:
            CustomerDict[rValue[2]]["DEBT"] = float(CustomerDict[rValue[2]]["DEBT"]) + iTotalPrice #Alıcı müşteri hesabına
        
        WriteFileCustomer() #Müşteri bilgilerini tekrar güncelleyerek yazdırıyoruz

        #Girişi yapılan bilgiler fCargoTXT("18010011067K.txt") dosyasına kayıt ediliyor
        with open(fCargoTXT,"a", encoding="UTF-8") as CusFile:
            for j in CargoInfo.values():
                CusFile.write("{};".format(j))
            CusFile.write("\n")
        ShowMessage("KARGO KAYDI TAMAMLANDI", 5)
    else:
        ShowMessage("KARGO KAYIT ISLEMI IPTAL EDILDI!", 5)    

def CargoUpdate():
    """
    Kargo kayitlarinin güncellendiği fonksiyondur.
    """
    iProductPrice = 0
    iCargoDesi = 0
    iTotalPrice = 0
    iCargoStatus = 1
    CaptionDesign("KARGO KAYDI DÜZELT")
    iID = input("Kaydı düzenlenecek kargo ID giriniz:")
    if (len(iID) <= 0):
        ShowMessage("Kargo ID boş geçilemez", 6)
    else:
        creturn = CargoIDControl(iID) #Parametre olarak gönderilen kargo numarası ile sonuclar alınıyor(tuple veri tipinde)
        if (creturn[0] == True):
            tempCargo = creturn[1]
            tempCID = creturn[2]
            #Bulunan kargo kaydı ekranda gösteriliyor
            print("{:5s}\t{}\t{}\t{}\t{}\t{}\t{}".format("ID","K.TIP","ODEME TIPI","U.UCRETI","DESI","T.UCRET","DURUM"))
            print("-"*80)
            for i in tempCargo.keys():
                print("{:5s}\t{}\t{:s}\t{:.2f}\t\t{:.1f}\t{:.2f}\t{:s}".format(tempCargo["CARGOID"],
                PCargoType[tempCargo["CARGOTYPE"]],PPaymentType[tempCargo["PAYMENTTYPE"]], tempCargo["PPRICE"],
                tempCargo["DESI"],tempCargo["TPRICE"],PCargoStatus[tempCargo["STATUS"]]), end="\n\n")
                #Gönderici müşterinin ID sorgulanması ve bilgilerinin alınması
                aSendValue = CustomerIDControl(tempCargo["SENDID"])
                if (aSendValue[0] == True):
                    sID = aSendValue[2] #Gonderici musterinin sıra Numarasını değişkene alıyoruz
                    print("Gönd : {}-{}-{}".format(CustomerDict[sID]["ID"], CustomerDict[sID]["NAME"], CustomerDict[sID]["ADDRESS"]))
                #Alıcı müşterinin ID sorgulanması ve bilgilerinin alınması
                aRecvValue = CustomerIDControl(tempCargo["RECVID"])
                if (aRecvValue[0] == True):
                    rID = aRecvValue[2] #Alıcı musterinin sıra Numarasını değişkene alıyoruz
                    print("Alıcı: {}-{}-{}".format(CustomerDict[rID]["ID"], CustomerDict[rID]["NAME"], CustomerDict[rID]["ADDRESS"]))
                print("\n")
                print("="*80)
                mrResult = input("{} ID Numarali kargo kaydı guncellensin mi?(E/e=Evet, H/h:Hayır):".format(tempCargo["CARGOID"]))
                if (mrResult.lower() == "e"):
                    #Guncellenecek alt yapılar
                    #Kargo Tipi girişi ve kontrolleri
                    
                    try:
                        iCargoStatus = int(input("Yeni kargo durumunu giriniz.\n{}:".format(PCargoStatus)))
                    except ValueError:
                        ShowMessage("Lütfen kargo durumunu sayısal olarak giriniz!", 6)
                    if ((iCargoStatus <= 0) or (iCargoStatus >= 9)):
                        iCargoStatus = 1 #Varsayılan-YENI KARGO
                    print(scLightCyan+"\tKargo Durumu: {}".format(PCargoStatus[iCargoStatus])+scEnd)
                    
                    try:
                        iCargoType = int(input("Yeni kargo tipini giriniz.\n{}:".format(PCargoType)))
                    except ValueError:
                        ShowMessage("Lütfen kargo tipini sayısal olarak giriniz!", 6)
                    if ((iCargoType <= 0) or (iCargoType >= 5)):
                        iCargoType = 4 #Varsayılan-DIGER
                    print(scLightCyan+"\tKargo Tipi: {}".format(PCargoType[iCargoType])+scEnd)

                    #Ödeme Tipi girişi ve kontrolleri
                    try:
                        iPaymentType = int(input("Yeni ödeme tipini giriniz.\n{}:".format(PPaymentType)))
                    except ValueError:
                        ShowMessage("Lütfen ödeme tipini sayısal olarak giriniz!", 6)
                    if ((iPaymentType <= 0) or (iPaymentType >= 4)):
                        iPaymentType = 2 #Varsayılan-ALICI ODEMELI
                    print(scLightCyan+"\tÖdeme Tipi: {}".format(PPaymentType[iPaymentType])+scEnd)

                    #Ödeme Tipi alici ödemeli ise ürün tutarını kargo fiyatına eklemek gerekiyor
                    if (iPaymentType == 3):
                        try:
                            iProductPrice = float(input("Yeni kargo ürün ücretini giriniz:"))
                            print(scLightCyan+"\tÜrün Ücreti: {:.2f} TL".format(iProductPrice)+scEnd)
                        except ValueError:
                            ShowMessage("Lütfen kargo ürün ücretini sayısal olarak giriniz!", 6)

                    #Kargo Desi Miktarı girişi ve kontrolleri
                    try:
                        iCargoDesi = float(input("Yeni desi miktarını giriniz:"))
                    except ValueError:
                        ShowMessage("Lütfen desi miktarını sayısal olarak giriniz!", 6)
                    if (iCargoDesi <= 0):
                        ShowMessage("Desi miktarı 0'dan küçük olamaz!", 6)

                    iTotalPrice = (iCargoDesi * UnitPrice) + iProductPrice #Kargo ücreti hesaplama
                    print(scLightCyan+"\tToplam Kargo Ücreti: {:.2f} TL".format(iTotalPrice)+scEnd)

                    #Eski kayıt bilgilerden gelen...
                    #Ödeme tipine göre ilgili müşterinin borcundan kargo tutar miktarı düşülüyor
                    if (tempCargo["PAYMENTTYPE"] == 1):
                        CustomerDict[sID]["DEBT"] = float(CustomerDict[sID]["DEBT"]) - float(tempCargo["TPRICE"])
                    else:
                        CustomerDict[rID]["DEBT"] = float(CustomerDict[rID]["DEBT"]) - float(tempCargo["TPRICE"])
                    WriteFileCustomer() #Müşteri bilgileri güncellendi ve dosyaya yazdırılıyor

                    #tempCargo["CARGOID"] = xx # Guncellenmedi
                    #tempCargo["SENDID"]= xx #Guncellenmedi
                    #tempCargo["RECVID"]= xx #Guncellenmedi
                    tempCargo["CARGOTYPE"]=iCargoType #int
                    tempCargo["PAYMENTTYPE"]=iPaymentType #int
                    tempCargo["PPRICE"]=iProductPrice #float
                    tempCargo["DESI"]=iCargoDesi #float
                    tempCargo["TPRICE"]=iTotalPrice #str
                    tempCargo["STATUS"]=iCargoStatus #int - YENI KARGO

                    if (iPaymentType == 1):
                        CustomerDict[sID]["DEBT"] = float(CustomerDict[sID]["DEBT"]) + iTotalPrice
                    else:
                        CustomerDict[rID]["DEBT"] = float(CustomerDict[rID]["DEBT"]) + iTotalPrice
                    WriteFileCustomer() #Müşteri bilgileri güncellendi ve dosyaya yazdırılıyor

                    CargoDict[tempCID] = tempCargo
                    WriteFileCargo() #guncellenen kayıtlar tekrar dosyaya yazdırılıyor

                    ShowMessage("KAYIT GUNCELLENDI!",6)
                else:
                    ShowMessage("GUNCELLEME İŞLEMİ İPTAL EDİLDİ!",6)
        else:
            ShowMessage("{} numaralı bir kargo kaydı bulunamadı!".format(iID), 6)


def CargoDelete():
    """
    Kargo kayitlarinin silindiği fonksiyondur
    """
    CaptionDesign("KARGO SİL")
    iID = input("Silinecek kargo ID giriniz:")
    if (len(iID) <= 0):
        ShowMessage("Kargo ID boş geçilemez", 7)
    else:
        creturn = CargoIDControl(iID) #Parametre olarak gönderilen kargo numarası ile sonuclar alınıyor(tuple veri tipinde)
        if (creturn[0] == True):
            tempCargo = creturn[1]
            #Bulunan kargo kaydı ekranda gösteriliyor
            print("{:5s}\t{}\t{}\t{}\t{}\t{}\t{}".format("ID","K.TIP","ODEME TIPI","U.UCRETI","DESI","T.UCRET","DURUM"))
            print("-"*80)
            for i in tempCargo.keys():
                print("{:5s}\t{}\t{:s}\t{:.2f}\t\t{:.1f}\t{:.2f}\t{:s}".format(tempCargo["CARGOID"],
                PCargoType[tempCargo["CARGOTYPE"]],PPaymentType[tempCargo["PAYMENTTYPE"]], tempCargo["PPRICE"],
                tempCargo["DESI"],tempCargo["TPRICE"],PCargoStatus[tempCargo["STATUS"]]), end="\n\n")
                #Gönderici müşterinin ID sorgulanması ve bilgilerinin alınması
                aSendValue = CustomerIDControl(tempCargo["SENDID"])
                if (aSendValue[0] == True):
                    sID = aSendValue[2] #Gonderici musterinin sıra Numarasını değişkene alıyoruz
                    print("Gönd : {}-{}-{}".format(CustomerDict[sID]["ID"], CustomerDict[sID]["NAME"], CustomerDict[sID]["ADDRESS"]))
                #Alıcı müşterinin ID sorgulanması ve bilgilerinin alınması
                aRecvValue = CustomerIDControl(tempCargo["RECVID"])
                if (aRecvValue[0] == True):
                    rID = aRecvValue[2] #Alıcı musterinin sıra Numarasını değişkene alıyoruz
                    print("Alıcı: {}-{}-{}".format(CustomerDict[rID]["ID"], CustomerDict[rID]["NAME"], CustomerDict[rID]["ADDRESS"]))
                print("\n")
                print("="*80)
                mrResult = input("{} ID Numarali kargo kaydı silinsin mi?(E/e=Evet, H/h:Hayır):".format(tempCargo["CARGOID"]))
                if (mrResult.lower() == "e"):
                    #Ödeme tipine göre ilgili müşterinin borcundan kargo tutar miktarı düşülüyor
                    if (tempCargo["PAYMENTTYPE"] == 1):
                        CustomerDict[sID]["DEBT"] = float(CustomerDict[sID]["DEBT"]) - float(tempCargo["TPRICE"])
                    else:
                        CustomerDict[rID]["DEBT"] = float(CustomerDict[rID]["DEBT"]) - float(tempCargo["TPRICE"])
                    WriteFileCustomer() #Müşteri bilgileri güncelleniyor

                    CargoDict.pop(creturn[2]) #Sözlükteki kargo kaydı silindi
                    WriteFileCargo() #silinen kayıt ile veriler tekrar dosyaya yazdırılıyor

                    ShowMessage("KARGO KAYDI SİLİNDİ!",7)
                else:
                    ShowMessage("SİLME İŞLEMİ İPTAL EDİLDİ!",7)
        else:
            ShowMessage("{} numaralı bir kargo kaydı bulunamadı!".format(iID), 7)

def CargoList():
    """
    Kargo kayitlarinin listelendiği fonksiyondur
    """
    CaptionDesign("KARGO LİSTELE")
    print("{:3s} {:5s}\t{}\t{}\t{}\t{}\t{}\t{}".format("NO","ID","K.TIP","ODEME TIPI","U.UCRETI","DESI","T.UCRET","DURUM"))
    print("-"*80)
    cargoInc=0
    for i in CargoDict.keys():
        cargoInc+=1
        print("{:03d} {:5s}\t{}\t{:s}\t{:.2f}\t\t{:.1f}\t{:.2f}\t{:s}".format(cargoInc, CargoDict[i]["CARGOID"],
        PCargoType[CargoDict[i]["CARGOTYPE"]],PPaymentType[CargoDict[i]["PAYMENTTYPE"]], CargoDict[i]["PPRICE"],
        CargoDict[i]["DESI"],CargoDict[i]["TPRICE"],PCargoStatus[CargoDict[i]["STATUS"]]), end="\n\n")
        #Gönderici müşterinin ID sorgulanması ve bilgilerinin alınması
        aSendValue = CustomerIDControl(CargoDict[i]["SENDID"])
        if (aSendValue[0] == True):
            sID = aSendValue[2] #Gonderici musterinin sıra Numarasını değişkene alıyoruz
            print("Gönd : {}-{}-{}".format(CustomerDict[sID]["ID"], CustomerDict[sID]["NAME"], CustomerDict[sID]["ADDRESS"]))
        #Alıcı müşterinin ID sorgulanması ve bilgilerinin alınması
        aRecvValue = CustomerIDControl(CargoDict[i]["RECVID"])
        if (aRecvValue[0] == True):
            rID = aRecvValue[2] #Alıcı musterinin sıra Numarasını değişkene alıyoruz
            print("Alıcı: {}-{}-{}".format(CustomerDict[rID]["ID"], CustomerDict[rID]["NAME"], CustomerDict[rID]["ADDRESS"]))
        print("\n")
        print("="*80)

    print("-"*80)
    ShowMessage("", 8)

def CargoCalculate():
    """
    Desi veya (En-Boy-Yükseklik) ölçülerine göre kargo ücreti hesaplama fonksiyonudur
    """
    iCalculate = 0.0
    CaptionDesign("KARGO DESI VE TUTAR HESAPLA")
    print("Hesaplama Yöntemi Seçiniz".center(80))
    print(scLightGreen+"[1]: Desi miktarı girerek hesaplama(Varsayılan)".center(80)+scEnd)
    print(scLightPink+"[2]: En - Boy - Yükselik girerek hesaplama     ".center(80)+scEnd)
    try:
        iPro = int(input("Lütfen hesaplama yöntemi giriniz:"))
    except ValueError:
        ShowMessage("Lütfen yöntem girişini sayısal olarak yapınız!", 9)
    
    if (iPro == 2):
        print("Lutfen degerleri santimetre(cm) cinsinden giriniz!")
        try:
            en = int(input("EN:"))
            boy = int(input("BOY:"))
            yukseklik = int(input("YUKSEKLIK:"))
        except ValueError:
            ShowMessage("Lütfen değerleri sayısal olarak giriniz!", 9)
        if ((en <= 0) or (boy <= 0) or (yukseklik <= 0)):
            ShowMessage("EN, BOY ve YUKSEKLIK değerleri 0'dan küçük olamaz", 9)        
        iCalculate = (((en * boy * yukseklik)/3000) * UnitPrice)
    else:
        try:
            iDesi = float(input("Desi miktarini giriniz:"))
        except ValueError:
            ShowMessage("Lütfen desi girişini sayısal olarak yapınız!", 9)
        
        if (iDesi <= 0):
            ShowMessage("Desi değeri 0'dan küçük olamaz", 9)        
        iCalculate = iDesi * UnitPrice
    
    print("Hesaplanan Tutar = {:.2f} TL".format(iCalculate))
    ShowMessage("", 9)

def CustomerPayment():
    """
    Müşteriye ait hesap bakiyesinden yapılan ödemeyi düşürme fonksiyonudur
    """
    CaptionDesign("MÜŞTERİ BORÇ ÖDE")
    iID = input("Ödeme yapılacak müşteri ID giriniz:")
    if (len(iID) <= 0):
        ShowMessage("Müşteri ID boş geçilemez!", 10)
    else:
        aReturn = CustomerIDControl(iID) #Parametre olarak gönderilen ID , sonuc tuple verisi olarak alınıyor.
        if (aReturn[0] == True): #Parametre olarak gönderilen ID bulunduysa TRUE ise
            tempCus = aReturn[1] #Fonksiyondan gelen musteri bilgileri buraya aktarılıyor
            iCusNo = aReturn[2] #Fonksiyondan gelen müşteri sıra no

            if (float(tempCus["DEBT"]) <= 0):
                print(scLightRed+"\n{} ID Numarali {} adlı müşterinin borcu bulunmamaktadır!".format(tempCus["ID"], tempCus["NAME"])+scEnd)
                ShowMessage("", 10)
            else:
                print("-"*80)
                print("ID\tAd-Soyad\tBorç Miktarı")
                print("-"*80)
                print("{}\t{}\t{:.2f}".format(tempCus["ID"], tempCus["NAME"], float(tempCus["DEBT"])))
                print("-"*80)
                try:
                    iPayment = float(input("Ödeme tutarını giriniz:").center(80))
                except ValueError:
                    ShowMessage("Lütfen ödeme miktarını sayısal olarak giriniz!", 10)
                
                if (iPayment <= 0):
                    ShowMessage("Ödeme miktarı 0'dan küçük olamaz!", 10)
                elif (iPayment > float(tempCus["DEBT"])):
                    ShowMessage("Ödeme miktarı borç miktarından büyük olamaz!", 10)
                else:                    
                    mrResult = input("Hesap bakiyesinden {:.2f} tutarı düşülsün mü?(E/e=Evet, H/h:Hayır):".format(iPayment))
                    if (mrResult.lower() == "e"):
                        print("Eski hesap bakiyesi  : {:.2f}".format(tempCus["DEBT"]))
                        print("Ödeme yapilan miktar : {:.2f}".format(iPayment))
                        CustomerDict[iCusNo]["DEBT"] = float(CustomerDict[iCusNo]["DEBT"]) - iPayment
                        print("Yeni hesap bakiyesi  : {:.2f}".format(CustomerDict[iCusNo]["DEBT"]))
                        WriteFileCustomer()
                        ShowMessage("MÜŞTERİ BORÇ ÖDEME İŞLEMİ TAMAMLANDI!",10)
                    else:
                        ShowMessage("BORÇ ÖDEME İŞLEMİ İPTAL EDİLDİ!",10)
        else:
            ShowMessage("{} numaralı bir müşteri bulunamadı!".format(iID), 10)

def MainMenu():
    """
    Ana menü ve programın kontrol edildiği fonksiyondur.\n
    """
    WarnMessage = ""
    WarnStatus = False
    #Menuler ve iceriği sözlük tipinde tutulmuştur.
    MenuDict = {1:"MUSTERI EKLE", 2:"MUSTERI DUZELT", 3:"MUSTERI SIL", 4:"MUSTERI LISTELE", 5:"KARGO EKLE", 
    6:"KARGO DUZELT", 7:"KARGO SIL", 8:"KARGO LISTELE", 9:"KARGO DESI VE TUTAR HESAPLA", 
    10: "MUSTERI BORC ODE", 11: "PROGRAM HAKKINDA", 12:"CIKIS"}

    ScreenClear()
    print("*"*80)
    print(scYellow+ " KARGO KAYIT VE TAKİP OTOMASYONU ".center(80) + scEnd)
    print("Version:{} - Build:{}".format(pVersion, pBuild).center(80))
    print("*"*80)
    for key, value in MenuDict.items():
        print("\t\t\t[ {:2d} ] : {}".format(key, value))
    print("*"*80)

    ReadFileCustomer() #Program acilisinda dosyadan musteriler bir kez okunmaktadır
    ReadFileCargo() #Dosyadan kargo kayıtlari bir kez okunmaktadır
    
    while(1):
        try:
            iMenuSel = int(input("LUTFEN MENUDEN ISLEM SECINIZ:"))
        except ValueError:
            WarnStatus = True
            WarnMessage = "!!! HATALI MENU GIRISI !!!"
        
        if (WarnStatus == False):
            if (iMenuSel == 1):
                CustomerAdd()
            elif (iMenuSel == 2):
                CustomerUpdate()
            elif (iMenuSel == 3):
                CustomerDelete()
            elif (iMenuSel == 4):
                CustomerList()
            elif (iMenuSel == 5):
                CargoAdd()
            elif (iMenuSel == 6):
                CargoUpdate()
            elif (iMenuSel == 7):
                CargoDelete()
            elif (iMenuSel == 8):
                CargoList()
            elif (iMenuSel == 9):
                CargoCalculate()
            elif (iMenuSel == 10):
                CustomerPayment()
            elif (iMenuSel == 11):
                ProgramAbout()
            elif (iMenuSel == 12):
                ProgramExit()
            else:
                ShowMessage("HATALI MENU GIRISI")
        else:
            ShowMessage(WarnMessage)
        
MainMenu()
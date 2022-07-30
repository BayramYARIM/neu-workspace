"""
Proje  : MAYIN TARLASI
Author : BAYRAM YARIM
No     : 18010011067
Mail   : bayramyarim@ogr.erbakan.edu.tr
Mail   : byyarim@gmail.com
IDE    : Visual Studio Code
Python : 3.8.9 - 64bit
UpDate : 29.05.2021
"""

import os  #konsol temizleme fonksiyonu icin eklendi.
import random #Rastgele sayi üretmek icin

#Ekran uyari mesajlarini ve mayinlari renklendirmek icin kullanildi 
scLightRed    = "\033[1;31m"  
scLightGreen  = "\033[1;32m"  
scYellow      = "\033[1;33m"    
scLightPink   = "\033[1;35m"  
scLightCyan   = "\033[1;36m"   
scEnd         = "\033[0m" 

def ScreenClear():    
    """
    Ekran temizleme fonksiyonudur.
    """
    if os.name == 'nt': 
        _ = os.system('cls') #MS Windows
    else: 
        _ = os.system('clear') #Mac or Linux

def CreateMainScreen():
    """
    Ana ekran olusturma fonksiyonudur.\n
    Kullanıcıdan oyun ile ilgili bilgiler alınır ve gerekli yapılandırmalar yapılır.
    """    
    global MatrisSize 
    global GameMode
    global MinesList
    global MinesFlag 
    global MinesCount
    global ClearMode

    ScreenClear()

    print("="*80)
    print("MAYIN TARLASI".center(80))
    print("="*80)
    try:
        MatrisSize = int(input("Lütfen bir boyut giriniz (Varsayılan=10):"))
    except ValueError:
        MatrisSize = 10 #Sayısal harici bir giriş yapılırsa varsayılan 10 olarak ayarlanıyor

    if ((MatrisSize <=9) or (MatrisSize >= 40)):
        MatrisSize = 10
    try:
        GameMode = int(input("Lütfen bir oyun modu seciniz (0=Gizli, 1=Acik):"))
    except ValueError:
        GameMode = 0 #Sayısal harici bir giriş yapılırsa varsayılan 0-Gizli olarak ayarlanıyor
    
    if ((GameMode <= 0) or (GameMode >= 2)):
        GameMode = 0
    
    ClearMode = input("Oyun sirasinda ekran temizlensin mi? (E/e=Evet, H/h=Hayir):")

    if ((ClearMode.lower() == "e")):
        ClearMode = "e"
    else:
        ClearMode = "h"

    MinesList = [["0" for i in range(MatrisSize)] for y in range(MatrisSize)]
    MinesFlag = [["" for i in range(MatrisSize)] for y in range(MatrisSize)]
    MinesCount = round((MatrisSize * MatrisSize) *0.3 )

    if (GameMode == 1):
        print(scYellow+"Oyun Modu: ACIK, Boyut: {} X {}, Mayın Sayısı: {}".format(MatrisSize, MatrisSize, MinesCount)+scEnd)
    else:
        print(scYellow+"Oyun Modu: GIZLI, Boyut: {} X {}, Mayın Sayısı: {}".format(MatrisSize, MatrisSize, MinesCount)+scEnd)
    print("="*80)
    
def CreateMines():
    """
    Mayinlari olusturma fonksiyonu\n
    Deger olarak girilen [boyut]X[boyut] %30'u kadar mayin olusturulur.\n
    Olusturulan mayinlara X harfi set ediliyor.
    """    
    count = 0
    while count < int(MinesCount):
        val = random.randint(0, (MatrisSize * MatrisSize)-1) 
        #Rastgele olarak mayinlari yerlestir
        row = val // MatrisSize
        col = val % MatrisSize
        if MinesList[row][col] != "X":
            count = count + 1
            MinesList[row][col] = "X"
    
    MinesColumnValues()

def CreateMinesDesign():
    """
    Olusturulan matrisi ve mayinlari ekranda sütur ve satır numarası ile yazdırır.\n
    Oyun moduna göre ==> mod=gizli ise mayinlar {?} ile mod=açık ise  mayinlar {X} ile gösterilir. 
    """
    if ((ClearMode.lower() == "e")):
        ScreenClear()
    #UST BAR CIZGI VE SAYILARIN YAZDIRILMASI
    for line in range(1,MatrisSize+1):
        if (line<=1):
            print("     ", end=" ")
        print("{:2d}".format(line) , end="  ")
    print("\n")
    for line in range(1,MatrisSize+1):
        if (line<=1):
            print("   ", end=" ")
        print("----", end="")
    #SOL BAR CIZGI VE SAYILARIN YAZDIRILMASI
    print("\n")
    rowData=""
    for row in range(MatrisSize):
        rowData=""
        for col in range(MatrisSize):
            if (GameMode == 1): #oyun modu açik, mayinlari acik sekilde yazdir
                #rowData = rowData + "   " + 
                if (MinesFlag[row][col] == "F"):
                    rowData = rowData + "   " + scYellow + MinesList[row][col] + scEnd
                elif (MinesList[row][col] == "X"):
                    rowData = rowData + "   " + scLightRed + MinesList[row][col] + scEnd
                else:
                    rowData = rowData + "   " + MinesList[row][col]
            else:
                if (MinesFlag[row][col] == "F"):
                    rowData = rowData + "   " + scYellow + MinesList[row][col] + scEnd
                else:
                    rowData = rowData + "   " + "?"
        
        print("{:2d} ".format(row+1)+"|" +rowData+" "+"\n")
    
    for line in range(1,MatrisSize+1):
        if (line<=1):
            print("   ", end=" ")
        print("----", end="")
    print("\n")


def MinesSumAdd(row:int, col:int):
    """
    Parametre olarak gönderilen satır ve sütünda mayın yok ise mayin bulunan konumdaki sayi +1 artırılır\n
    """
    if (MinesList[row][col] != "X"):
        MinesList[row][col] = str(int(MinesList[row][col])+1)


def MinesColumnValues():
    """
    Tüm mayin alanini(satir ve sutun olarak) dolaşır\n
    Mayin olanin hücrenin cevresindeki konumlara 1 ekler.\n
    Alt fonksiyon olarak ==> MinesSumAdd() çagırılır.
    """
    for row in range(MatrisSize):
        for col in range(MatrisSize):
            if (MinesList[row][col] == "X"):
                decCol = col-1
                incCol = col+1
                decRow = row-1
                incRow = row+1

                if ((decRow != -1) and (decCol != -1)): #0,0
                    MinesSumAdd(decRow, decCol)
                if ((decRow != -1)): #0,1
                    MinesSumAdd(decRow, col)
                if ((decRow != -1) and (incCol < MatrisSize)): #0,2
                    MinesSumAdd(decRow, incCol)
                if ((incRow < MatrisSize)): #2,1
                    MinesSumAdd(incRow, col)

                if ((decCol != -1)): #1,0
                    MinesSumAdd(row, decCol)
                if ((incCol < MatrisSize)): #1,2
                    MinesSumAdd(row, incCol)
                if ((decCol != -1) and (incRow < MatrisSize)): #2,0
                    MinesSumAdd(incRow, decCol)
                if ((incCol < MatrisSize) and (incRow < MatrisSize)): #2,2
                    MinesSumAdd(incRow,incCol)

def MinesRowColShow(row:int, col:int):
    """
    Parametre olarak gönderilen satır ve sütünda mayın yok ise {MinesFlag} listesindeki konum set edilir\n
    """
    if (MinesList[row][col] != "X"):
        MinesFlag[row][col] = "F"

def MinesFlagController(row:int, col:int):
    """
    Gonderilen satir ve sutun numarasi daha onceden isaretlenmis ise
    geriye TRUE donduruluyor ve kullaniciya mesaj verdieiliyor\n
    Isaretlenmemisse sonuc false ve gonderilen satır sutun numarasi isaretleniyor
    """
    if (MinesFlag[row][col] == "F"):
        return True
    else:
        MinesFlag[row][col] = "F"
        return False

def MinesController(row:int, col:int):
    """
    Gonderilen satir ve sutun numarasinda mayin varsa sonuc TRUE degilse FALSE donduruluyor\n
    TRUE sonucuna gore kullanici mayini isaretlemis oluyor ve oyun bitiyor\n
    FALSE ise kullanici oyunu devam ettiriyor\n
    """
    if (MinesList[row][col] == "X"):
        return True
    else:
        return False

def GameWinController():
    """
    Oyundaki tüm satir ve sütun kontrol ediliyor.\n
    Mayin haricindeki tüm alanlar isaretlenmis ise kullanici oyunu kazaniyor
    """
    gameWin = 0
    for row in range(MatrisSize):
        for col in range(MatrisSize):
            if (MinesFlag[row][col] == "F"):
                gameWin += 1
    if (gameWin == ((MatrisSize*MatrisSize)-MinesCount)):
        return True
    else:
        return False

def GameTryAgain():
    """
    Oyunun herhangi bir sekilde sonlanmasinda bu fonksiyon cagirilir\n
    Kullanici girisine göre oyun tekrar baslatilir ya da sonlanir\n
    """
    tryGame = input("Tekrar oynamak ister misiniz? (E-e: Evet, H-h: Hayır):")
    if (tryGame.lower() == "e"):
        return True
    else:
        print("Tekrar görüsmek dilegiyle...")
        exit()
        #return False

def GameController():
    """
    Ana oyun kontrolü fonksiyonu
    """
    global GameMode
    GameCounter = 0
    WarnMessage = ""
    MessageStatus = False
    
    CreateMainScreen()
    CreateMines()
    CreateMinesDesign()
    
    while(True): 
        try:
            row = int(input("Lütfen satır numarasi giriniz:"))
        except ValueError: #int bir deger girilmez ise hata mesaji verdir
            MessageStatus = True
        
        try:
            col = int(input("Lütfen sütun numarasi giriniz:"))
        except ValueError: #int bir deger girilmez ise hata mesaji verdir
            MessageStatus = True

        #Sayısal bir satır veya sütun girilmemiş ise
        #Satır ve sutun değeri matris boyutlarının dışında ise
        #Satır veya sütun değeri 0'dan küçük ise 
        if ((MessageStatus == True) or (row <= 0) or (row > MatrisSize) or (col <= 0) or (col > MatrisSize)):
            WarnMessage = "Lütfen gecerli satır ve sütun numarasi giriniz!"
            MessageStatus = True
        else: #if (MessageStatus == False):
            row = row-1
            col = col-1
            if (MinesFlagController(row, col) == True):
                WarnMessage = "Bu alan daha önce isaretlenmis ve açılmıştır -> (Satır:{}, Sütun:{})".format(row+1,col+1)
                MessageStatus = True
            else:            
                if (MinesController(row, col)== True):
                    GameMode = 1
                    CreateMinesDesign()
                    print(scLightRed+"MAALESEF OYUNU KAYBETTINIZ. OYUN PUANI: {} ".format(GameCounter)+scEnd)                
                    if GameTryAgain():
                        GameController()
                else:
                    GameCounter += 1
                    #MinesSelectedShow(row,col)
                    MinesRowColShow(row, col)
                    CreateMinesDesign()
                    if (GameWinController() == True):
                        print(scLightGreen+"TEBRIKLER OYUNU KAZANDINIZ...OYUN PUANI: {}".format(GameCounter)+scEnd)
                        if GameTryAgain():
                            GameController()

        if (MessageStatus == True):
            CreateMinesDesign()
            print(scLightRed + WarnMessage + scEnd)
            WarnMessage = ""
            MessageStatus = False

GameController()
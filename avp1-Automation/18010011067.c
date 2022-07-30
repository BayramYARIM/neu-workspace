/*  BISMILLAHIRRAHMANIRRAHIM
 *  @Project : KARGO TAKIP OTOMASYONU - BAYRAM YARIM - 18010011067
 *  @Author  : Bayram YARIM [byyarim@gmail.com]
 *  @File    : 18010011067.c
 *  @Desc    : SchoolWork-2
 *  @Create  : 09.01.2021 20:45
 *  @Update  : 16.01.2021 15:24
 *  @Version : v1.0.1
 *  @Build   : 210110#
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#define ProVersion "1.0.1"
#define ProBuild   "210110#"
#define UnitPrice 2.67
/*Kargo 1 Desi Birim Fiyati*/
#define clrscr system("@cls");
/*Ekrani temizleme fonksiyonu*/

int CustSize=0, CargoSize=0;

void MainMenu();
/*Program acilisinda calisan prosedur menu secim islemleri burada yapiliyor*/
void AddCustomer();
/*Yeni Musteri eklemek icin cagirilan prosedur*/
void UpdateCustomer();
/*Musteri ID sine gore arama yapan ve bulunan var ise uzerinde guncellemeler yapan prosedur*/
void DeleteCustomer();
/*Musteri ID sine gore arama yapan ve bulunan var ise silme islemi yapan prosedur*/
void ListCustomer();
/*Kayitli tum musterileri ekrana yazdiran prosedur*/
int GetCustomerFreeID();
/*
 * Silinmis musteri sırasını yeni kayıt ekleme anında sorgulayan prosedur
 * Var ise eski sira numarasi donuyor ve yeni kayıt bu siraya ekleniyor
 * Donus tipi : int
*/
bool GetCustomerRecordControl(int CustomerID);
/*
 * Yeni musteri ekleme aninda cagirilan prosedur
 * Girilen yeni ID eski kayitlar kontrol edilerek sorgulaniyor
 * Benzer ID var ise donus tipi TRUE , yok ise FALSE olarak sonuclaniyor
 * Sonuca göre ekranda kullaniciya mesaj veriliyor.
*/
void AddCargo();
/*Yeni kargo kaydi eklemek icin cagirilan prosedure*/
void UpdateCargo();
/*Kayitli kargolarin durumlarini güncellemek icin kullanilan prosedure*/
void DeleteCargo();
/*Girilen ID ye kargo var ise silme islemini yapan prosedure*/
void ListCargo();
/*Kayitli kargolari ekrana yazdiran prosedure*/
int GetCargoFreeID();
/*
 * Silinmis kargo sırasını yeni kayıt ekleme anında sorgulayan prosedur
 * Var ise eski sira numarasi donuyor ve yeni kayıt bu siraya ekleniyor
 * Donus tipi : int
*/
bool GetCargoRecordControl(int CargoID);
/*
 * Yeni kargo ekleme aninda cagirilan prosedur
 * Girilen yeni ID eski kayitlar kontrol edilerek sorgulaniyor
 * Benzer ID var ise donus tipi TRUE , yok ise FALSE olarak sonuclaniyor
 * Sonuca göre ekranda kullaniciya mesaj veriliyor.
*/
void GetCustomerInfo(int Type, int CustomerID);
/*Kargolarin listelendigi ekranda Gonderen ve Alici musteri bilgilerini ekrana yazdiran prosedure*/
void AddDebtCustomer(int CustomerID, float DebtPrice);
/*ID numarasi gonderilen musterinin hesabına kargo tutarini ekleyen veya cikaran prosedur*/
void PaymentDebtCustomer();
/*Arattirilan musteri numarasında borcu var ise ödeme yapan prosedure*/
char* PPaymetType(int PT);
/*Kargonun odeme tipini string olarak donduren fonksiyon*/
char* PCargoType(int CT);
/*Kargonun tipini string olarak donduren fonksiyon*/
char* PCargoStatus(int CS);
/*Kargonun durumunu string olarak donduren fonksiyon*/
void ProgramAbout();
/*Program hakkindaki bilgileri gosteren prosedur*/
void ProgramExit();
/*Program cikis isleminde cagirilan prosedur*/
void ShowMessage();
/*Ekrana bilgileri yazdiran prosedure*/
void DesiCalculate();
/*Kargo desisine gore ucret hesaplama fonksiyonu
 * 1 - Desiye gore hesaplama
 * 2 - En-Boy-Yukseklik degerlerine gore hesaplama
*/

typedef struct tsCustomer
{
    int State;
    int ID;
    char Name[20];
    char Surname[20];
    char Phone1[12];
    char Address[25];
    float SumDebt;
    //char IdentityNumbe[11], EMail[50], Phone2[12], City[15], BillingAddress[50];
} TCustomer;

TCustomer *Customer;

typedef struct tsCargo
{
    int State;
    int ID;
    TCustomer *SenderCust;
    TCustomer *ReceiveCust;
    float Desi;
    float TotalPrice;
    float ProductPrice;
    int CargoStatus;
    int PaymentType;
    int CargoType;
    //char SDateTime[10], UDateTime[10];
    //bool SMS;
} TCargo;
TCargo *Cargo;


int main()
{
    MainMenu();
    return 0;
}

void MainMenu()
{
    int MenuSelect;
    clrscr;
    printf("*******************************************************************************\n");
    printf("\t\t\tKARGO KAYIT ve TAKIP OTOMASYONU\n");
    printf("\t\t\tVersion:%s-Build:%s\n",ProVersion, ProBuild);
    printf("*******************************************************************************\n");
    printf("\t\t\t [1] : MUSTERI EKLE\n");
    printf("\t\t\t [2] : MUSTERI DUZELT\n");
    printf("\t\t\t [3] : MUSTERI SIL\n");
    printf("\t\t\t [4] : MUSTERI LISTELE\n");
    printf("\t\t\t [5] : KARGO EKLE\n");
    printf("\t\t\t [6] : KARGO DUZELT\n");
    printf("\t\t\t [7] : KARGO SIL\n");
    printf("\t\t\t [8] : KARGO LISTELE\n");
    printf("\t\t\t [9] : KARGO DESI ve TUTAR HESAPLA\n");
    printf("\t\t\t[b/B]: MUSTERI BORC ODE\n");
    printf("\t\t\t[h/H]: PROGRAM HAKKINDA\n");
    printf("\t\t\t[e/E]: CIKIS\n");
    printf("*******************************************************************************\n");
    printf("\t\t\tMENUDEN ISLEM SECINIZ:\n");
    MenuSelect = GetKeyboardValue();
    switch(MenuSelect)
    {
    case 49: //1
        AddCustomer();
        break;

    case 50: //2
        UpdateCustomer();
        break;

    case 51: //3
        DeleteCustomer();
        break;

    case 52: //4
        ListCustomer();
        break;

    case 53: //5
        AddCargo();
        break;

    case 54: //6
        UpdateCargo();
        break;

    case 55: //7
        DeleteCargo();
        break;

    case 56: //8
        ListCargo();
        break;

    case 57: //9
        DesiCalculate();
        break;

    case 'B':
    case 'b':
        PaymentDebtCustomer();
        break;

    case 'H': case 'h': case 'A': case 'a':
        ProgramAbout();
        break;

    case 'E': case 'e': case 'Q': case 'q':
        ProgramExit();
        break;
    default:
        MainMenu();
        break;
    }
}

void ShowMessage(int MenuNo, char *eMessage)
{
    printf("\n\n\n");
    printf("%20s%s\n\n"," " ,eMessage);
    printf("\t\tAna menuye donmek icin ENTER tusuna basiniz...");
    int iSelect = GetKeyboardValue();
    switch(iSelect)
    {
    case 27: //ESC
    case 13: //Enter
        MainMenu();
        break;
    case 32:
        switch (MenuNo)
        {
        case 1: AddCustomer(); break;
        case 2: UpdateCustomer(); break;
        case 3: DeleteCustomer(); break;
        //case 4: ListCustomer(); break;
        case 5: AddCargo(); break;
        case 6: UpdateCargo(); break;
        case 7: DeleteCargo(); break;
        //case 8: ListCargo();
        case 9: DesiCalculate(); break;
        case 11: PaymentDebtCustomer(); break;
        default: MainMenu(); break;
        }
        break;
    default:
        MainMenu();
        ShowMessage(0, eMessage);
        break;
    }
}

void DesiCalculate()
{
    clrscr;
    int _Key;
    float _desi;
    int _en, _boy, _yukseklik;
    printf("*******************************************************************************\n");
    printf("%20s KARGO TAKIP OTOMASYONU-> KARGO UCRET HESAPLAMA\n","");
    printf("*******************************************************************************\n");
    printf("Hesaplama Yontemi Seciniz:\n");
    printf("[1] Desi Miktari Girerek Hesaplama:    \n");
    printf("[2] En-Boy-Yukseklik Girerek Hesaplama:\n");
    do{
        _Key = GetKeyboardValue();
        switch (_Key)
        {
        case 49:
            printf("Desi Miktarini Giriniz:");
            scanf("%f", &_desi);
            printf("Hesaplanan Tutar: %.2f TL\n", UnitPrice * _desi );
            ShowMessage(9, "Tekrar islem yapmak icin SPACE tusuna basiniz!");
            break;
        case 50:
            printf("Lutfen degerleri santimetre(cm) cinsinden giriniz!\n");
            printf("En :");
            scanf("%d", &_en);
            printf("Boy:");
            scanf("%d", &_boy);
            printf("Yukseklik:");
            scanf("%d", &_yukseklik);
            printf("Hesaplanan Tutar: %.2f TL\n", ((_en * _boy * _yukseklik)/3000) * UnitPrice);
            ShowMessage(9, "Tekrar islem yapmak icin SPACE tusuna basiniz!");
            break;
        }
    }while(_Key!=49 || _Key!=50);
}

char* PCargoType(int CT)
{
    char *_CT= "nullCT";
    switch(CT)
    {
    case 1 :
        _CT ="Zarf";
        break;
    case 2 :
        _CT ="Paket";
        break;
    case 3 :
        _CT ="Koli";
        break;
    default:
        _CT ="Diger";
        break;
    }
    return _CT;
}

char* PPaymetType(int PT)
{
    char *_pt="nullPT";
    switch(PT)
    {
    case 1 :
        _pt = "Gon.Odemeli";
        break;
    case 2 :
        _pt = "Alici Odemeli";
        break;
    case 3 :
        _pt = "Kapida Odemeli";
        break;
    default:
        _pt = "Alici Odemeli";//NoNe
        break;
    }
    return _pt;
}

char* PCargoStatus(int CS)
{
    char *_CS="nullCS";
    switch(CS)
    {
    case 0:
        _CS="Yeni";
        break;
    case 1:
        _CS="Kargo Hazir";
        break;
    case 2:
        _CS="Yolda";
        break;
    case 3:
        _CS="Aktarmada";
        break;
    case 4:
        _CS="Dagitimda";
        break;
    case 5:
        _CS="Teslim Edildi";
        break;
    case 6:
        _CS="Iptal Edildi";
        break;
    case 7:
        _CS="Iade";
        break;
    default:
        //_CS="NoNeCS";
        _CS="Aktarmada!";
        break;
    }
    return _CS;
}

void AddDebtCustomer(int CustomerID, float DebtPrice)
{
    int i=0;
    for(i=0; i<CustSize; i++)
    {
        if (((Customer+i)->State > 0) && ((Customer+i)->ID == CustomerID))
        {
            (Customer+i)->SumDebt += DebtPrice;
            break;
        }
    }
}

void AddCargo()
{
    clrscr;
    int fc;
    TCargo *tmpCargo = (TCargo*) malloc(sizeof(TCargo));
    if (tmpCargo == NULL) printf("Memory error: tmpCargo");
    TCustomer *SCust = (TCustomer *) malloc(sizeof(TCustomer));
    if (SCust == NULL) printf("Memory error: SenderCustomer");
    TCustomer *RCust = (TCustomer *) malloc(sizeof(TCustomer));
    if (RCust == NULL) printf("Memory error: ReceiveCustomer");
    printf("*******************************************************************************\n");
    printf("%20s KARGO TAKIP OTOMASYONU-> KARGO KAYIT EKLEME\n","");
    printf("*******************************************************************************\n");
    tmpCargo->CargoStatus = 1; //New Cargo
    printf("Kargo ID Giriniz:");
    scanf("%d", &(tmpCargo->ID));

    if (GetCargoRecordControl(tmpCargo->ID) == true)
    {
        free(tmpCargo);
        ShowMessage(5, "Bu ID daha once baska bir kayita verilmis\n\t\tYeniden denemek icin SPACE tusuna basiniz");
        return;
    }

    printf("Gonderici Musteri ID Giriniz:");
    scanf("%d", &(SCust->ID));
    if (GetCustomerRecordControl(SCust->ID) == false)
    {
        free(tmpCargo);
        ShowMessage(5, "Boyle bir musteri bulunamadi!\n\t\tTekrar kargo eklemek icin SPACE tusuna basiniz!");
        return;
    }
    tmpCargo->SenderCust = SCust;
    GetCustomerInfo(1,SCust->ID);

    printf("Alici Musteri ID Giriniz:");
    scanf("%d", &(RCust->ID));
    if (GetCustomerRecordControl(RCust->ID) == false)
    {
        free(tmpCargo);
        ShowMessage(5,"Boyle bir musteri bulunamadi!\n\t\tTekrar kargo eklemek icin SPACE tusuna basiniz!");
        return;
    }
    GetCustomerInfo(2,RCust->ID);
    tmpCargo->ReceiveCust = RCust;

    if (SCust->ID == RCust->ID)
    {
        free(tmpCargo);
        ShowMessage(5, "Benzer musteri ID ile birbirine gonderim yapilamaz!\n\t\tTekrar kargo eklemek icin SPACE tusuna basiniz!");
        return;
    }

    printf("Kargo Tipi Giriniz(1=Zarf, 2=Paket, 3=Koli, 4=Diger):");
    scanf("%d", &(tmpCargo->CargoType));
    if ((tmpCargo->CargoType <= 0) || (tmpCargo->CargoType >4)) tmpCargo->CargoType=4; //Default
    printf("Odeme Tipini Giriniz(1:Gon.Odemeli, 2=Alici Odemeli, 3=Kapida Odeme):");
    scanf("%d", &(tmpCargo->PaymentType));
    if ((tmpCargo->PaymentType <= 0) || (tmpCargo->PaymentType >3)) tmpCargo->CargoType=2;//Default
    tmpCargo->ProductPrice=0;
    if (tmpCargo->PaymentType == 3)
    {
        printf("%20s","Kargo Urun Ucretini Giriniz:");
        scanf("%f", &(tmpCargo->ProductPrice));
    }
    printf("Kargo Desi Giriniz:");
    scanf("%f", &(tmpCargo->Desi));
    printf("*******************************************************************************\n\n");
    tmpCargo->TotalPrice = (float)(((tmpCargo->CargoType) * (tmpCargo->Desi) * UnitPrice ) + (tmpCargo->ProductPrice));
    tmpCargo->State = 1;

    printf("\n\n%30sGirilen bilgiler kaydedilsin mi?\n\n%30s[E/e]=Evet, [H/h]=Hayir :","","");
    do
    {
        fc = GetKeyboardValue();
        switch(fc)
        {
        case 'E':
        case 'e':
            Cargo = (TCargo*) realloc(Cargo, sizeof(TCargo) * (CargoSize+1));
            Cargo->SenderCust = (TCustomer*) malloc(sizeof(TCustomer));
            Cargo->ReceiveCust = (TCustomer*) malloc(sizeof(TCustomer));
            (Cargo+CargoSize)->State = tmpCargo->State;
            (Cargo+CargoSize)->ID = tmpCargo->ID;
            (Cargo+CargoSize)->CargoType = tmpCargo->CargoType;
            (Cargo+CargoSize)->CargoStatus = tmpCargo->CargoStatus;
            (Cargo+CargoSize)->Desi = tmpCargo->Desi;
            (Cargo+CargoSize)->PaymentType = tmpCargo->PaymentType;
            (Cargo+CargoSize)->ProductPrice = tmpCargo->ProductPrice;
            (Cargo+CargoSize)->TotalPrice = tmpCargo->TotalPrice;
            (Cargo+CargoSize)->SenderCust = SCust;
            (Cargo+CargoSize)->ReceiveCust = RCust;
            CargoSize++;
            if ((tmpCargo)->PaymentType == 1) //Odeme gonderene ait ise borcu hesabina yaz
                AddDebtCustomer(SCust->ID, tmpCargo->TotalPrice);
            else
                AddDebtCustomer(RCust->ID, tmpCargo->TotalPrice); //Odeme aliciya ait ise borcu hesabina yaz
            ShowMessage(5, "Kayit ekleme islemi tamamlandi!\n\t\tYeniden kargo kaydi eklemek icin SPACE tusuna basiniz!");
            break;
        case 'H':
        case 'h':
            free(tmpCargo);
            ShowMessage(5,"Kayit ekleme iptal edildi\n\t\tYeniden kargo kaydi eklemek icin SPACE tusuna basiniz!");
            break;
        }
    }
    while ( fc!='E' || fc!='e' || fc!='H' || fc!='h');

    ShowMessage(0, " ");
}

void UpdateCargo()
{
    clrscr;
    int idNumber, j, ch, tempPT;
    float tempPrice=0;
    bool searchOK=false;
    printf("*******************************************************************************\n");
    printf("%20s KARGO TAKIP OTOMASYONU-> KARGO GUNCELLE\n","");
    printf("*******************************************************************************\n");
    printf("%45s","Kargo No Giriniz:");
    scanf(" %d", &idNumber);
    for(j=0; j<CargoSize; j++)
    {
        if (((Cargo+j)->State > 0) && ((Cargo+j)->ID == idNumber))
        {
            printf("\n\n\n[SIRA]\t[ID NO]\t\t[KARGO TIP]\t[KARGO TUTAR]\t[KARGO DURUM]\n");
            printf("*******************************************************************************\n");
            printf("%.2d\t%d\t\t%s\t\t%.2f\t\t%s\n", j+1, (Cargo+j)->ID, PCargoType((Cargo+j)->CargoType),
                   (Cargo+j)->TotalPrice, PCargoStatus((Cargo+j)->CargoStatus));
            searchOK = true;
            break;
        }
    }
    if (searchOK == true)
    {
        printf("\n\n%30sBulunan kayit duzenlensin mi?\n\n%30s[E/e]=Evet, [H/h]=Hayir :","","");
        do
        {
            ch = GetKeyboardValue();
            switch(ch)
            {
            case 'E':
            case 'e':

                /*Eski kayıtlardaki tutarı eklenen musterilerin hesabından geri cıkart!*/
                tempPrice = (Cargo+j)->TotalPrice;
                tempPT = (Cargo+j)->PaymentType;
                if (tempPT == 1)
                {
                    AddDebtCustomer((Cargo+j)->SenderCust->ID, -(tempPrice));
                }
                else
                {
                    AddDebtCustomer((Cargo+j)->ReceiveCust->ID, -(tempPrice));
                }

                printf("\n\n");
                printf("Yeni Kargo ID Giriniz:");
                scanf("%d", &((Cargo+j)->ID));
                printf("Yeni Kargo Tipini Giriniz(1=Zarf, 2=Paket, 3=Koli, 4=Diger):");
                scanf("%d", &((Cargo+j)->CargoType));
                printf("Yeni Kargo Durumunu Giriniz:\n");
                printf("0=Yeni, 1=Kargo Hazir, 2=Yolda, 3=Aktarmada, 4=Dagitimda, 5=Teslim Edildi, 6=Iptal Edildi, 7=Iade:");
                scanf("%d", &((Cargo+j)->CargoStatus));
                printf("Yeni Odeme Tipini Giriniz(1:Gon.Odemeli, 2=Alici Odemeli, 3=Kapida Odeme):");
                scanf("%d", &((Cargo+j)->PaymentType));
                if ((Cargo+j)->PaymentType == 3)
                {
                    printf("Yeni Urun Fiyatini Giriniz:");
                    scanf("%f", &((Cargo+j)->ProductPrice));
                }
                printf("Yeni Desi Miktarini Giriniz:");
                scanf("%f", &((Cargo+j)->Desi));
                (Cargo+j)->State = 1; //Update Record


                //yeni hesaplanan miktari odeme tipine gore ilgili musteriye aktar
                (Cargo+j)->TotalPrice = (float)((((Cargo+j)->CargoType) * ((Cargo+j)->Desi) * UnitPrice ) + ((Cargo+j)->ProductPrice));
                if ((Cargo+j)->PaymentType == 1) //1->Gonderen Odemeli
                {
                    AddDebtCustomer((Cargo+j)->SenderCust->ID, (Cargo+j)->TotalPrice);
                }
                else //2-3 -> Alici Odemeli
                {
                    AddDebtCustomer((Cargo+j)->ReceiveCust->ID, (Cargo+j)->TotalPrice);
                }
                ShowMessage(6,"Kayit duzenleme islemi tamamlandi!\n\t\tYeniden islem yapmak icin SPACE tusuna basiniz!\n");
                break;
            case 'H':
            case 'h':
                ShowMessage(6,"Kayit duzenleme iptal edildi!\n\t\tYeniden islem yapmak icin SPACE tusuna basiniz!");
                break;
            default:
                break;
            }
        }
        while ( ch!='E' || ch!='e' || ch!='H' || ch!='h');
    }
    else
    {
        printf("\n\n%20s%d NUMARAYA AIT BIR KARGO KAYDI BULUNAMADI!","",idNumber);
    }
    ShowMessage(6, "Yeniden islem yapmak icin  SPACE tusuna basiniz!");
}

void DeleteCargo()
{
    clrscr;
    int idNumber, j, ch;
    bool searchOK=false;
    printf("*******************************************************************************\n");
    printf("%20s KARGO TAKIP OTOMASYONU-> KARGO SIL\n","");
    printf("*******************************************************************************\n");
    printf("%45s","Kargo No Giriniz:");
    scanf(" %d", &idNumber);
    for(j=0; j<CargoSize; j++)
    {
        if (((Cargo+j)->State > 0) && ((Cargo+j)->ID == idNumber))
        {
            printf("\n\n\n[SIRA]\t[ID NO]\t\t[KARGO TIP]\t[KARGO TUTAR]\t[KARGO DURUM]\n");
            printf("*******************************************************************************\n");
            printf("%.2d\t%d\t\t%s\t\t%.2f\t\t%s\n", j+1, (Cargo+j)->ID, PCargoType(idNumber), (Cargo+j)->TotalPrice, PCargoStatus(idNumber));
            searchOK = true;
            break;
        }
    }
    if (searchOK == true)
    {
        printf("\n\n%30sBulunan kargo kaydi silinsin mi?\n\n%30s[E/e]=Evet, [H/h]=Hayir :","","");
        do
        {
            ch = GetKeyboardValue();
            switch(ch)
            {
            case 'E':
            case 'e':
                (Cargo+j)->State = -1;
                (Cargo+j)->ID = -1;
                (Cargo+j)->Desi = 0;
                (Cargo+j)->CargoType = -1;
                (Cargo+j)->CargoStatus = -1;
                (Cargo+j)->PaymentType = -1;
                (Cargo+j)->ProductPrice = 0;
                (Cargo+j)->TotalPrice = 0;

                ShowMessage(7, "Kayit silme islemi tamamlandi!\n\t\tYeniden islem yapmak icin SPACE tusuna basiniz!");
                break;

            case 'H':
            case 'h':
                ShowMessage(7,"Kayit silme iptal edildi.\n\t\tYeniden islem yapmak icin SPACE tusuna basiniz!");
                break;
            default:
                break;
            }
        }
        while ( ch!='E' || ch!='e' || ch!='H' || ch!='h');
    }
    else
    {
        printf("\n\n%20s%d NUMARAYA AIT KARGO BULUNAMADI!","",idNumber);
    }

    ShowMessage(7, "Yeniden islem yapmak icin SPACE tusuna basiniz!");
}

void ListCargo()
{
    clrscr;
    int ListCount=0, j=0;
    printf("*******************************************************************************\n");
    printf("%20s KARGO TAKIP OTOMASYONU-> KARGO LISTELEME\n"," ");
    printf("*******************************************************************************\n");
    printf("[SIRA]\t[ ID ]\t[KAR.TIPI]\t[DESI]\t[ODEME]\t[TUTAR]\t\t[DURUM]\n");
    printf("*******************************************************************************\n");
    for(j=0; j<CargoSize; j++)
    {
        if ((Cargo+j)->State > 0)
        {
            ListCount += 1;
            printf("%d\t%d\t%s\t%.2f\t%s\t%.2f TL\t%s\n\n", j+1, (Cargo+j)->ID,
                   PCargoType((Cargo+j)->CargoType),
                   (Cargo+j)->Desi, PPaymetType((Cargo+j)->PaymentType),
                   (Cargo+j)->TotalPrice, PCargoStatus((Cargo+j)->CargoStatus));
            GetCustomerInfo(1, (Cargo+j)->SenderCust->ID);
            GetCustomerInfo(2, (Cargo+j)->ReceiveCust->ID);
            printf("\n==============================================================================\n");
        }
    }
    printf("\n*******************************************************************************\n");

    printf("%30sTOPLAM %d KAYIT LISTELENDI\n", "", ListCount);
    ShowMessage(0," ");
}

void GetCustomerInfo(int Type, int CustomerID)
{
    int i; char *ct;
    if (Type == 1)
        ct = "GONDEREN ->>";
    else
        ct = "ALICI    ->>";
    for(i=0; i<CustSize; i++)
    {
        if (((Customer+i)->State > 0) && ((Customer+i)->ID == CustomerID))
        {
            printf("%s %d -> %s\t%s\t%s\t%.2f\n", ct,
                   (Customer+i)->ID, (Customer+i)->Name, (Customer+i)->Surname, (Customer+i)->Address,
                   (Customer+i)->SumDebt);
            break;
        }
    }
}

int GetCustomerFreeID()
{
    /*
    * Daha once silinmis alana yeni musteri eklemek icin kontrol
    * struct sirasini donduren prosedurdur.
    */
    int i,result=-1;
    for(i=0; i<CustSize; i++)
    {
        if (((Customer+i)->State == -1) && ((Customer+i)->ID == -1))
        {
            result = i;
            break;
        }
    }
    return result;
}

int GetCargoFreeID()
{
    /*
    * Daha once silinmis alana yeni kargo kaydi eklemek icin kontrol
    * struct sirasini donduren prosedurdur.
    */
    int i,result=-1;
    for(i=0; i<CargoSize; i++)
    {
        if (((Cargo+i)->State == -1) && ((Cargo+i)->ID == -1))
        {
            result = i;
            break;
        }
    }
    return result;
}

bool GetCargoRecordControl(int CargoID)
{
    /*
    * Parametre olarak girilen kargo numarasini (int CargoID)
    * diger kayitlarda arayan ve buldugunda true bulamadiginda false
    * donduren fonksiyondur
    */
    int i;
    bool result=false;
    for(i=0; i<CargoSize; i++)
    {
        if (((Cargo+i)->State > 0) && ((Cargo+i)->ID == CargoID))
        {
            result = true;
            break;
        }
    }
    return result;
}

bool GetCustomerRecordControl(int CustomerID)
{
    /*
    * Parametre olarak girilen musteri numarasini (int CustomerID)
    * diger kayitlarda arayan ve buldugunda true bulamadiginda false
    * donduren fonksiyondur
    */
    int i;
    bool result=false;
    for(i=0; i<CustSize; i++)
    {
        if (((Customer+i)->State > 0) && ((Customer+i)->ID == CustomerID))
        {
            result = true;
            break;
        }
    }
    return result;
}

void AddCustomer()
{
    clrscr;
    int fID=-1, IDX=0, cKey=-1;
    fID = GetCustomerFreeID(); //Silinen kayit var ise yerine yeni kayit eklemek icin sira no al
    if (fID >= 0)
    {
        IDX=fID;
    }
    else
    {
        //Customer struct yapisinda yeni kayit icin alan ac
        if (CustSize <= 0)
        {
            Customer = (TCustomer *) malloc(sizeof(TCustomer));
        }
        else
        {
            Customer = (TCustomer *) realloc(Customer, (CustSize+1) * sizeof(TCustomer));
            if (Customer == NULL)
            {
                printf("ReAlloc Error :: Not memory Customer!");
                return;
            }
        }
        IDX=CustSize;
    }

    printf("*******************************************************************************\n");
    printf("%20s KARGO TAKIP OTOMASYONU-> MUSTERI KAYIT EKLEME\n","");
    printf("*******************************************************************************\n");
    printf("%40s","Kimlik No Giriniz:");
    scanf("%d", &(Customer+IDX)->ID);
    printf("%40s","Ad Giriniz:");
    scanf("%s", &(Customer+IDX)->Name);
    printf("%40s","Soyad Giriniz:");
    scanf("%s", &(Customer+IDX)->Surname);
    printf("%40s","Telefon Giriniz:");
    scanf("%s", &(Customer+IDX)->Phone1);
    printf("%40s","Adres Giriniz:");
    scanf("%s", &(Customer+IDX)->Address);
    (Customer+IDX)->State=1;
    (Customer+IDX)->SumDebt=0;
    printf("*******************************************************************************\n\n");

    if (GetCustomerRecordControl((Customer+CustSize)->ID) == true)
    {
        (Customer+CustSize)->ID = -1;
        (Customer+CustSize)->State = -1;
        ShowMessage(1, "Bu ID daha baska musteriye ait!\n\t\tTekrar islem yapmak icin SPACE tusuna basiniz!");
        return;
    }

    printf("%20sGirilen bilgiler kaydedilsin mi?\n%25s[E/e]=Evet, [H/h]=Hayir :","","");
    do
    {
        cKey = GetKeyboardValue();
        switch(cKey)
        {
        case 'E':
        case 'e':
            CustSize = CustSize + 1;
            ShowMessage(1, "KAYIT EKLEME ISLEMI TAMAMLANDI!\n\t\tYeniden Musteri eklemek icin SPACE tusuna basiniz!");
        break;

        case 'H':
        case 'h':
            (Customer+CustSize)->ID = -1;
            (Customer+CustSize)->State = -1;
            ShowMessage(1, "KAYIT EKLEME ISLEMI IPTAL EDILDI!\n\t\tYeniden islem yapmak icin SPACE tusuna basiniz!");
            break;
        }
    }
    while ((cKey!='e' || cKey!='E' || cKey!='h' || cKey!='H'));
}

void DeleteCustomer()
{
    clrscr;
    int idNumber, j, ch;
    bool searchOK=false;
    printf("*******************************************************************************\n");
    printf("%20s KARGO TAKIP OTOMASYONU-> MUSTERI SIL\n","");
    printf("*******************************************************************************\n");
    printf("%45s","Kimlik No Giriniz:");
    scanf(" %d", &idNumber);
    for(j=0; j<CustSize; j++)
    {
        if (((Customer+j)->State > 0) && ((Customer+j)->ID == idNumber))
        {
            printf("\n\n\n[SIRA]\t[ID NO]\t[AD\tSOYAD]\t[TELEFON]\t[ADRES]\n");
            printf("*******************************************************************************\n");
            printf("%.2d\t%d\t%s %s\t\t%s\t%s\n", j+1, (Customer+j)->ID, &(Customer+j)->Name, &(Customer+j)->Surname,
                   &(Customer+j)->Phone1, &(Customer+j)->Address);
            searchOK = true;
            break;
        }
    }

    if (searchOK == true)
    {
        printf("\n\n%30sBulunan kayit silinsin mi?\n\n%30s[E/e]=Evet, [H/h]=Hayir :","","");
        do
        {
            ch = GetKeyboardValue();
            switch(ch)
            {
            case 'E':
            case 'e':
                if ((Customer+j)->SumDebt <=0)
                {
                (Customer+j)->State = -1;
                (Customer+j)->ID = -1;
                (Customer+j)->Name[0] = '\0';
                (Customer+j)->Surname[0] = '\0';
                (Customer+j)->Phone1[0] = '\0';
                (Customer+j)->Address[0] = '\0';

                ShowMessage(3, "Kayit silme islemi tamamlandi!\n\t\tYeniden islem yapmak icin SPACE tusuna basiniz!");
                }
                else
                {
                    ShowMessage(3, "Bu musteriye ait borc bulunmaktadir.\n\t\t\tKAYIT SILINEMEZ!\n\t\tYeniden islem yapmak icin SPACE tusuna basiniz!");
                }
                break;

            case 'H':
            case 'h':
                ShowMessage(3,"Kayit silme iptal edildi.\n\t\tYeniden islem yapmak icin SPACE tusuna basiniz!");
                break;
            default:
                break;
            }
        }
        while ( ch!='E' || ch!='e' || ch!='H' || ch!='h');
    }
    else
    {
        printf("\n\n%20s%d NUMARAYA AIT BIR MUSTERI BULUNAMADI!","",idNumber);
    }
    ShowMessage(3,"Yeniden islem yapmak icin SPACE tusuna basiniz!");
}

void UpdateCustomer()
{
    clrscr;
    int idNumber, j, ch;
    bool searchOK=false;
    //TCustomer *updCustomer = (TCustomer*) malloc(sizeof(TCustomer));
    printf("*******************************************************************************\n");
    printf("%20s KARGO TAKIP OTOMASYONU-> MUSTERI DUZENLE\n","");
    printf("*******************************************************************************\n");
    printf("%45s","Kimlik No Giriniz:");
    scanf("%d", &idNumber);
    for(j=0; j<CustSize; j++)
    {
        if (((Customer+j)->State > 0) && ((Customer+j)->ID == idNumber))
        {
            printf("\n\n\n[SIRA]\t[ID NO]\t[AD\tSOYAD]\t[TELEFON]\t[ADRES]\n");
            printf("*******************************************************************************\n");
            printf("%.2d\t%d\t%s %s\t\t%s\t%s\n", j+1, (Customer+j)->ID, (Customer+j)->Name, (Customer+j)->Surname,
                   (Customer+j)->Phone1, (Customer+j)->Address);
            searchOK = true;
            break;
        }
    }

    if (searchOK == true)
    {
        printf("\n\n%30sBulunan kayit duzenlensin mi?\n\n%30s[E/e]=Evet, [H/h]=Hayir :","","");
        do
        {
            ch = GetKeyboardValue();
            switch(ch)
            {
            case 'E':
            case 'e':

                printf("\n\n");
                printf("%40s","Yeni Kimlik No Giriniz:");
                scanf("%d", &((Customer+j)->ID));
                printf("%40s","Yeni Ad Giriniz:");
                scanf("%s", &((Customer+j)->Name));
                printf("%40s","Yeni Soyad Giriniz:");
                scanf("%s", &((Customer+j)->Surname));
                printf("%40s","Yeni Telefon Giriniz:");
                scanf("%s", &((Customer+j)->Phone1));
                printf("%40s","Yeni Adres Giriniz:");
                scanf("%s", &((Customer+j)->Address));
                (Customer+j)->State = 1; //New Record
                ShowMessage(2,"Kayit duzenleme islemi tamamlandi!\n\t\tYeniden islem yapmak icin SPACE tusuna basiniz!\n");
                break;

            case 'H':
            case 'h':
                ShowMessage(2,"Kayit duzenleme iptal edildi!\n\t\tYeniden islem yapmak icin SPACE tusuna basiniz!");
                break;
            default:
                break;
            }
        }
        while ( ch!='E' || ch!='e' || ch!='H' || ch!='h');
    }
    else
    {
        printf("\n\n%20s%d NUMARAYA AIT BIR MUSTERI BULUNAMADI!","",idNumber);
    }

    ShowMessage(2,"Yeniden islem yapmak icin SPACE tusuna basiniz!");
}

void ListCustomer()
{
    clrscr;
    int RecCount=0, j=0;
    printf("*******************************************************************************\n");
    printf("\t\tKARGO TAKIP OTOMASYONU-> MUSTERI LISTELEME\n");
    printf("*******************************************************************************\n");
    printf("[SIRA]\t[ID NO]\t[AD\tSOYAD]\t[TELEFON]\t[ADRES]\t[BORC]\n");
    printf("*******************************************************************************\n");
    for(j=0; j<CustSize; j++)
    {
        if ((Customer+j)->State > 0)
        {
            RecCount += 1;
            printf("%.2d\t%d\t%s %s\t\t%s\t%s\t%.2f TL\n", j+1, (Customer+j)->ID, &(Customer+j)->Name, &(Customer+j)->Surname,
                   &(Customer+j)->Phone1, &(Customer+j)->Address, (Customer+j)->SumDebt);
        }
    }
    printf("\n*******************************************************************************\n");
    printf("%30sTOPLAM %d KAYIT LISTELENDI\n", "", RecCount);
    ShowMessage(0," ");
}

void PaymentDebtCustomer()
{
    clrscr;
    int idNumber, j, ch;
    float PaymentValue=0;
    bool searchOK=false;
    printf("*******************************************************************************\n");
    printf("%20s KARGO TAKIP OTOMASYONU-> MUSTERI BORC ODEME\n","");
    printf("*******************************************************************************\n");
    printf("%45s","Kimlik No Giriniz:");
    scanf("%d", &idNumber);
    for(j=0; j<CustSize; j++)
    {
        if (((Customer+j)->State > 0) && ((Customer+j)->ID == idNumber))
        {
            printf("\n\n\n[SIRA]\t[ID NO]\t[AD\tSOYAD]\t[TELEFON]\t[ADRES]\t[BORC]\n");
            printf("*******************************************************************************\n");
            printf("%.2d\t%d\t%s %s\t\t%s\t%s\t%.2f TL\n", j+1, (Customer+j)->ID, (Customer+j)->Name, (Customer+j)->Surname,
                   (Customer+j)->Phone1, (Customer+j)->Address, (Customer+j)->SumDebt);
            searchOK = true;
            break;
        }
    }

    if (searchOK == true)
    {
        if ((Customer+j)->SumDebt > 0)
        {
            //

        printf("\n\n%30sBulunan musterinin borcu duzenlensin mi?\n\n%30s[E/e]=Evet, [H/h]=Hayir :\n\n","","");
        do
        {
            ch = GetKeyboardValue();
            switch(ch)
            {
            case 'E':
            case 'e':
                printf("%30sOdeme tutarini giriniz:", "");
                scanf("%f", &PaymentValue);
                if (PaymentValue > (Customer+j)->SumDebt)
                    ShowMessage(11,"Odeme miktari borc tutarini gecemez!\n\t\tYeniden islem yapmak icin SPACE tusuna basiniz!\n");
                if (PaymentValue < 0)
                    ShowMessage(11,"Odeme miktari eksi(-) bir tutar olmaz!\n\t\tYeniden islem yapmak icin SPACE tusuna basiniz!\n");
                else
                {
                    printf("%30sEski Hesap Bakiyesi  : %.2f\n","", (Customer+j)->SumDebt);
                    printf("%30sOdenen Yapilan Miktar: %.2f\n","", PaymentValue);
                    AddDebtCustomer((Customer+j)->ID, -(PaymentValue));
                    printf("%30sYeni Hesap Bakiyesi  : %.2f\n","", (Customer+j)->SumDebt);
                }

                ShowMessage(11,"Borc odeme islemi tamamlandi!\n\t\tYeniden islem yapmak icin SPACE tusuna basiniz!\n");
                break;

            case 'H':
            case 'h':
                ShowMessage(11,"Borc odeme islemi iptal edildi!\n\t\tYeniden islem yapmak icin SPACE tusuna basiniz!\n");
                break;
            default:
                break;
            }
        }
        while ( ch!='E' || ch!='e' || ch!='H' || ch!='h');
        }
        else
        {
            ShowMessage(11,"MUSTERIYE AIT BORC 0 TL!\n\t\tYeniden islem yapmak icin SPACE tusuna basiniz!\n");
        }
    }
    else
    {
        printf("\n\n%20s%d NUMARAYA AIT BIR MUSTERI BULUNAMADI!","",idNumber);
    }

    ShowMessage(11,"Yeniden islem yapmak icin SPACE tusuna basiniz!");
}

void ProgramExit()
{
    printf("\n\t\t[ Bizi tercih ettiginiz icin tesekkur ederiz! ]\n\n");
    exit(0);
}

void ProgramAbout()
{
    clrscr;
    printf("================================================================================\n");
    printf("\t\t\tProgram : KARGO TAKIP OTOMASYONU\n");
    printf("\t\t\tVersion : %s\n", ProVersion);
    printf("\t\t\tBuild   : %s\n", ProBuild);
    printf("\t\t\tAuthor  : Bayram YARIM\n");
    printf("\t\t\tSchoolNo: 18010011067\n");
    printf("\t\t\tMail    : byyarim@gmail.com\n");
    printf("================================================================================\n");
    ShowMessage(0," ");
}

int GetKeyboardValue()
{
    int result = getch();
    //printf("Keyboard::%d - %c", result, result);
    return result;
}

/*  BISMILLAHIRRAHMANIRRAHIM
 *  @Project : (*) Karakteri ile harf yazma
 *  @Author  : Bayram YARIM [byyarim@gmail.com]
 *  @File    : 18010011067.c
 *  @Desc    : SchoolWork-1
 *  @Create  : 08.11.2020 01:19
 *  @Update  : 17.11.2020 09:51
 *  @Version : v1.0.0
 */

#include <stdio.h>
#include <stdlib.h>

#define DefaultSize 5
//#define DefaultChar 0x2A

short int SingleOrDouble(short int value)
{
    if (value % 2 == 0)
        return 0;
    else
        return 1;
}

void DrawChar_A(short int MSize)
{
    /* Draw Character - A*/
    /* DrawLine 1=[/] 1=[-] 1=[\]*/
    short int i, j, k, t;
    short int decOne=0;
    decOne = SingleOrDouble(MSize);
    for (i=0; i<MSize; i++)
    {
        for (j=0; j<(MSize-i); j++) printf(" ");
        printf("*"); //Draw Line -> [/]
        if (i == (MSize/2))
        {
            for ( t=0; t<MSize-decOne; t++) printf("*"); //DrawLine [-]
        }
        else
        {
            for (k=0; k<i*2; k++) printf(" ");
        }
        printf("*\n"); //Draw Line -> [\]
    }//for(i)
    printf("\n\n");
}//@DrawChar_A(short)

void DrawChar_B(short int MSize)
{
    /* Draw Character - B*/
    /* DrawLine 1=[|] 3=[-] 2=[)]*/
    short int i, j, k;
    short int decOne;
    short int HSize;
    decOne = SingleOrDouble(MSize);
    HSize = ((MSize-decOne)/2);
    for (i=0; i<MSize; i++)
    {
        printf("*"); //Draw Line -> [|]
        if ((i==0) || (i == HSize) || (i==MSize-1))
        {
            for(j=0; j<MSize-1; j++)
                printf("*"); //Draw Line -> [-]
        }
        else
        {
            for (k=0; k<HSize; k++) printf("  ");
            printf("*"); //Draw Line -> [ )]
        }
        printf("\n");
    }//end::for(i)
    printf("\n\n");
}//@DrawChar_B(short)

void DrawChar_Y(short int MSize)
{
    /* Draw Character - Y*/
    /* DrawLine 1=[\] 1=[/] 1=[|]*/
    short int i, j, k, t;
    short int decOne;
    short int HSize;
    decOne = SingleOrDouble(MSize);
    HSize = ((MSize-decOne)/2);
    for (i=0; i<MSize; i++)
    {
        if (i <= HSize)
        {
            for(j=0; j<i; j++)
                printf(" ");
            printf("*"); //Draw Line -> [\]
            for(k=0; k<(HSize-i); k++)
                printf("  ");
            printf(" *\n"); //Draw Line -> [/]
        }
        else
        {
            for(t=0; t<HSize; t++)
                printf(" ");
            printf(" *\n"); //Draw Line -> [|]
        }
    }
    printf("\n\n");
}//@DrawChar_Y(short)

void DrawChar_R(short int MSize)
{
    /* Draw Character - R*/
    /* DrawLine 1=[|] 2=[-] 1=[)] 1=[\]*/
    short int i, j, k, t;
    short int decOne;
    short int HSize;
    decOne = SingleOrDouble(MSize);
    HSize = ((MSize-decOne)/2);
    for (i=0; i<MSize; i++)
    {
        printf("*"); //Draw Line -> [|]
        if ((i==0) || (i == HSize))
        {
            for(j=0; j<MSize; j++) printf("*"); //Draw Line -> [-]
        }
        else
        {
            if (i > HSize)
            {
                for(t=0; t<i-HSize; t++) printf("  ");
                printf("*"); //Draw Line -> [\]
            }
            else
            {
                for (k=0; k<MSize; k++) printf(" ");
                    printf("*"); //Draw Line -> [)]
            }
        }
        printf("\n");
    }//end::for(i)
    printf("\n\n");
}//@DrawChar_R(short)

void DrawChar_M(short int MSize)
{
    /* Draw Character - M*/
    /* DrawLine 2=[|] 1=[\] 1=[/]*/
    short int i, j, k, t;
    short int decOne=0;
    short int HSize;
    decOne = SingleOrDouble(MSize);
    HSize = ((MSize-decOne)/2);
    for (i=0; i<MSize; i++)
    {
        printf("*"); //Draw Line -> [|]
        for (j=0; j<i; j++) printf(" ");
        printf("*"); //Draw Line -> [\]
        for (k=0; k<(MSize-i)-1; k++) printf("  ");
        printf("*"); //Draw Line -> [/]
        for (t=0; t<i; t++) printf(" ");
        printf("*\n"); //Draw Line -> [|]
    }//for(i)
    printf("\n\n");
}//@DrawChar_I(short)

void DrawChar_I(short int MSize)
{
    /* Write Character - I*/
    /* DrawLine 1=[|] 2=[-] */
    short int i;
    printf("*****\n"); //Draw Line -> [-]
    for (i=0; i<MSize; i++)
    {
        printf("  *\n"); //Draw Line -> [|]
    }//for(i)
    printf("*****\n"); //Draw Line -> [-]
    printf("\n\n");
}//@writeI(short)

void InputText(char *data, short int sz)
{
    if (strlen(data) > 0)
    {
        for (short int n=0; n<strlen(data); n++)
        {
            switch(data[n])
            {
                case 'a' :
                case 'A' : DrawChar_A(sz); break;

                case 'b' :
                case 'B' : DrawChar_B(sz); break;

                case 'i' :
                case 'I' : DrawChar_I(sz); break;

                case 'y' :
                case 'Y' : DrawChar_Y(sz); break;

                case 'r' :
                case 'R' : DrawChar_R(sz); break;

                case 'm' :
                case 'M' : DrawChar_M(sz); break;

                case ' ' : printf("\n"); break;

                default  : printf("???\n"); break;
            }
        }
    }
    else
    {
        printf("Lutfen gecerli bir isim giriniz!\n");
    }
}//@InputText(char*, short)

int main()
{
    short int iSize;
    printf("Lutfen bir boyut giriniz(min=5) : ");
    scanf("%d", &iSize);
    if ((iSize <= 4) || (iSize >= 32765)) iSize = DefaultSize;
    InputText("Bayram YARIM", iSize);
    /*
    printf("BAYRAM\n");
    DrawChar_B(iSize);
    DrawChar_A(iSize);
    DrawChar_Y(iSize);
    DrawChar_R(iSize);
    DrawChar_A(iSize);
    DrawChar_M(iSize);
    printf("YARIM\n");
    DrawChar_Y(iSize);
    DrawChar_A(iSize);
    DrawChar_R(iSize);
    DrawChar_I(iSize);
    DrawChar_M(iSize);
    */
    return 0;
}

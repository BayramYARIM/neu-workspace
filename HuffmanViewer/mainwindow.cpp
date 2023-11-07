/*
 *  BISMILLAHIRRAHMANIRRAHIM
 *  @Project : Algoritmalar Huffman Calculate & Viewer - BAYRAM YARIM - 18010011067
 *  @Author  : Bayram YARIM [byyarim@gmail.com]
 *  @File    : 18010011067.cpp
 *  @Desc    : Huffman Calculate & Viewer
 *  @Create  : 03.05.2023 08:20
 *  @Update  : 04.05.2023 23:23
 *  @Version : v-
 *  @Build   : #-
 *  @OS      : Debian-Linux
 *  @IDE     : Qt Creator - qMake Compiler
 */

#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    QStringList sl;
    sl.append("BINARY");
    sl.append("INT");
    sl.append("HEX");
    sl.append("ASCII");
    sl.append("Frequncy");
    ui->tvView->setColumnCount(5);
    ui->tvView->setRowCount(0);
    //ui->tvView->setStyleSheet("gridline-color: rgb(0, 170, 255); selection-background-color: rgb(255, 85, 127);");
    ui->tvView->setHorizontalHeaderLabels(sl);
    ui->teBinaryData->setText("0100100001100101011011000110110001101111001000000101011101101111011100100110110001100100"
                              "0100100001100101011011000110110001101111001000000101011101101111011100100110110001100100");

}

MainWindow::~MainWindow()
{
    delete ui;
}

/**
 * @brief MainWindow::CleanTextAndComplete
 * @param dataText
 * @brief Clean space character (trim)
 * @brief Clean \-r \-n character (replace)
 * @brief Add mod value insert 0 (append)
 * @return QString
 */
QString MainWindow::CleanTextAndComplete(QString dataText)
{
    QString Result = dataText;
    QString appedText = "";
    int modVal = 0;
    if (Result.length() > 0) {
        Result = Result.trimmed();
        Result.replace(" ", "");
        Result.replace('\n', "");
        Result.replace("\r", "");
        modVal = Result.length() % 8;
        if (modVal != 0){
            for (int i=0; i<8-modVal; i++) {
                appedText+= "0";
            }
        }
    }
    Result = appedText + Result;
    return Result;
}

/**
 * @brief MainWindow::BinaryControl
 * @param Text binary control
 * @return bool(True||False)
 */
bool MainWindow::BinaryControl(QString dataText)
{
    bool Result = true;
    if (dataText.length() > 0)
    {
        for (int i=0; i<dataText.length(); i++) {
            //qDebug() << "Loop: " << i << " --> " << dataText[i];
            if ((dataText[i]!='0') && (dataText[i]!='1'))
            {
                Result = false;
                break;
            }
        }
    }
    return Result;
}

/**
 * @brief MainWindow::BinarySplitData - usedBy RegularExpression
 * @param Binary data - QString
 * @return QStringList
 */

QStringList MainWindow::BinarySplitData(QString dataText)
{
    QRegularExpression re("(.{1,8})");
    QRegularExpressionMatchIterator i = re.globalMatch(dataText);
    QStringList ResultData;
    while (i.hasNext()) {
        QRegularExpressionMatch match = i.next();
        QString binData = match.captured(1);
        ResultData << binData;
        //qDebug() << binData;
    }
    return ResultData;
}

/**
 * @brief MainWindow::ShowMessage
 * @param message
 */
void MainWindow::ShowMessage(QString message)
{
    QMessageBox msgBox;// = new QMessageBox(this);
    msgBox.setWindowTitle("Huffman Viewer");
    msgBox.setText(message);
    msgBox.setIcon(QMessageBox::Information);
    msgBox.exec();
}

int MainWindow::MyCompare(THuffmanTree x, THuffmanTree y)
{
    return x.freq - y.freq;
}


void MainWindow::on_btnHuffmanCalculate_clicked()
{
    QString bd = ui->teBinaryData->toPlainText();
    if (bd.length() > 0)
    {
        bd = CleanTextAndComplete(bd);
        ui->teBinaryData->setText(bd);
        if (BinaryControl(bd))
        {
            QStringList dataList = BinarySplitData(bd);

            if (ui->tvView->rowCount() > 0)
            {
                QAbstractItemModel* const mdl = ui->tvView->model();
                mdl->removeRows(0,mdl->rowCount());
                mdl->removeColumns(1,mdl->columnCount());
            }

            QTableWidgetItem *newItem;

            foreach (QString binValue, dataList) {
                bool ok;
                int intValue = binValue.toInt(&ok, 2);
                QString hexValue = QString::number(intValue, 16);
                //qDebug() << binValue << "IVal:" << intValue << " Value: " << hexValue << " Ascii: " << char(intValue);

                ui->tvView->setRowCount(ui->tvView->rowCount()+1);

                //Binary data -> 10101001
                newItem= new QTableWidgetItem(binValue);
                ui->tvView->setItem(ui->tvView->rowCount()-1, 0, newItem);

                //Integer Value -> 169
                newItem= new QTableWidgetItem();
                newItem->setData(Qt::DisplayRole, intValue);
                ui->tvView->setItem(ui->tvView->rowCount()-1, 1, newItem);

                //Hex Value -> a9
                newItem= new QTableWidgetItem(hexValue.toUpper());
                ui->tvView->setItem(ui->tvView->rowCount()-1, 2, newItem);

                //ASCII Value -> ©
                char nMyChar = static_cast<char>(intValue);
                QChar vvs = char(nMyChar);

                //QChar asciValue =
                newItem= new QTableWidgetItem();
                newItem->setData(Qt::DisplayRole, vvs);
                ui->tvView->setItem(ui->tvView->rowCount()-1, 3, newItem);

                //Frequency Value -> 2, 3 etc..
                newItem= new QTableWidgetItem();
                newItem->setData(Qt::DisplayRole, dataList.count(binValue));
                ui->tvView->setItem(ui->tvView->rowCount()-1, 4, newItem);


                /*Create Hufmann class*/
                THuffmanTree *tht = new THuffmanTree();
                tht->character = vvs;
                tht->freq = dataList.count(binValue);
                tht->left = NULL;
                tht->right = NULL;
            }//foreachLoop
        }
        else
        {
            ShowMessage("Icerik verisi 1 ve 0 lardan olusmuyor.");
        }
    }
    else
    {
        ShowMessage("Binary data verisi eksik! ");
    }
}

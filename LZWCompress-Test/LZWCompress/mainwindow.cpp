/*
 *  BISMILLAHIRRAHMANIRRAHIM
 *  @Project : Algoritmalar- BAYRAM YARIM - 18010011067
 *  @Author  : Bayram YARIM [byyarim@gmail.com]
 *  @File    : mainwindow.cpp
 *  @Desc    : LZW Compressed
 *  @Create  : 17.05.2022 21:55
 *  @Update  : 18.05.2022 23:39
 *  @Version : v-
 *  @Build   : #-
 *  @OS      : Debian-Linux
 *  @IDE     : Qt Creator(4.14.1) QtLib(5.15.2 - qMake Compiler)
 */

#include "mainwindow.h"
#include "ui_mainwindow.h"

#include <QFileDialog>
#include <QTextStream>
#include <QDebug>
#include <QDateTime>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    setGeometry(640, 200, 640, 360);

    ui->rdbASCI->setChecked(true);
    connect(ui->rdbASCI, SIGNAL(clicked()), this, SLOT(RDBControl()));
    ui->rdbUSER->setChecked(false);
    connect(ui->rdbUSER, SIGNAL(clicked()), this, SLOT(RDBControl()));
    ui->leFilePath->setEnabled(false);
    RDBControl();
}

MainWindow::~MainWindow()
{
    delete ui;
}

/**
 * @brief MainWindow::on_btnSelectFile_clicked
 *
 * Open Dialog & Read txt filepath - Dynamic
 */

void MainWindow::on_btnSelectFile_clicked()
{
    QString txtFilename = QFileDialog::getOpenFileName(this,tr("Open LZW Text File"),
    QDir::currentPath(), tr("LZW txt files (*.txt)"));
    ui->leFilePath->setText(txtFilename);
}

void MainWindow::readFileAndLZWCompressed(QString fileName)
{
    QFile file(fileName);
    int wloop=0;
    QString fC; fC.clear();
    QList<int> newData; //= new QList<int>;
    if (file.open(QIODevice::ReadOnly | QIODevice::Text))
    {
        m_startTime = QDateTime::currentMSecsSinceEpoch();
        QTextStream mystream(&file);
        while(!mystream.atEnd())
        {
            wloop++;
            QString rC = mystream.read(1);
            QString temp = fC + rC;
            if (myDict.contains(temp))
            {
                fC = temp;
            }
            else
            {
                newData.append(myDict[fC]);
                myDict.insert(temp, myDict.count());
                fC = rC;
            }
//            qDebug() << "Loop_" << wloop << " --> " << rC;
        }//While Loop

        newData.append(myDict[fC]);
//        qDebug() << newData;
//        for (int var = 0; var < newData.count(); ++var) {
//            qDebug() << newData[var] << " --> " << (QString)newData[var];
//        }
        file.close();
        //       qDebug() << myDict;

        m_endTime = QDateTime::currentMSecsSinceEpoch();
        m_diffTime = m_endTime-m_startTime;
        ui->lblInputSize ->setText(QString("Input Size  : %1 byte (%2 bit.)").arg(wloop).arg(wloop * 8));
        ui->lblOutputSize->setText(QString("Output Size : %1 byte ( %2 bit)").arg(newData.count()).arg(newData.count() * 8));
        ui->lblDiffTime->setText(QString("Work Time : %1 msec.").arg(m_diffTime));

        saveFile(wloop, newData.count());
    }
}

void MainWindow::saveFile(int inputSize, int outputSize)
{
    QString filename = QString("%1-output.txt").arg(QDateTime::currentDateTime().toString("yy-mm-dd-hh-MM-ss"));
        QFile file(filename);
        if (file.open(QIODevice::ReadWrite))
        {
            QTextStream outputStream(&file);
            QString wdata = QString("Input file size : %1 byte - %2 bit\n Output file size : %3 byte - %4 bit\n Work time : %5 msec.")
                    .arg(inputSize).arg(inputSize *8)
                    .arg(outputSize).arg(outputSize *8)
                    .arg(m_diffTime);
            outputStream << wdata ;
        }
}

/**
 * @brief MainWindow::RDBControl
 *
 * Radiobutton checked control
 *
 * ASCI Table -> 0..255 character
 * User Table -> Input Text
 * Dictionary load
 */
void MainWindow::RDBControl()
{
    myDict.clear();
    if (ui->rdbASCI->isChecked())
    {
        ui->teUserTable->setEnabled(false);
        for (int i=0; i<=254; i++) {
            myDict.insert(QChar(i), i);
        }
    } else
    {
        ui->teUserTable->setEnabled(true);
        for (int i=0; i<ui->teUserTable->toPlainText().length(); i++) {
            myDict.insert(QChar(i), i);
        }
    }
}

void MainWindow::on_btnCompress_clicked()
{
    RDBControl();
    readFileAndLZWCompressed(ui->leFilePath->text());
}

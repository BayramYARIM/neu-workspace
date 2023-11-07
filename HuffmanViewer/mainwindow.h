#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QDebug>
#include <QRegularExpression>
#include <QRegularExpressionMatch>
#include <QMessageBox>

class THuffmanTree
{
public:
    THuffmanTree()
    {
        this->freq = 0;
        this->right = NULL;
        this->left = NULL;
    }
    ~THuffmanTree();
    QChar character;
    int freq;
    THuffmanTree *right;
    THuffmanTree *left;
};

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();


    QString CleanTextAndComplete(QString dataText);
    QStringList BinarySplitData(QString dataText);
    bool BinaryControl(QString dataText);
    int FrequencyCalculate(QString dataText);
    void ShowMessage(QString message);
    int MyCompare(THuffmanTree x, THuffmanTree y);

    //tsHuffmanTree *HT;

private slots:
    void on_btnHuffmanCalculate_clicked();

private:
    Ui::MainWindow *ui;
};
#endif // MAINWINDOW_H

#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QMap>

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private slots:
    void on_btnSelectFile_clicked();
    void on_btnCompress_clicked();

public slots:
    void readFileAndLZWCompressed(QString fileName);
    void saveFile(int inputSize, int outputSize);
    void RDBControl();

private:
    Ui::MainWindow *ui;
    QMap<QString, int> myDict;

    int m_startTime, m_endTime, m_diffTime;

};
#endif // MAINWINDOW_H

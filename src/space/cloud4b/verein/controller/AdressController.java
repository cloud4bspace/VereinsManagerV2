package space.cloud4b.verein.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import space.cloud4b.verein.model.verein.adressbuch.Mitglied;
import space.cloud4b.verein.services.DatabaseReader;
import space.cloud4b.verein.services.Observer;
import space.cloud4b.verein.services.Subject;
import space.cloud4b.verein.view.dashboard.DashBoardController;
import space.cloud4b.verein.view.mainframe.MainFrameController;

import java.util.ArrayList;

public class AdressController implements Subject {

    int anzahlMitglieder;
    private DashBoardController dashBoardController;
    private ArrayList<Observer> observerList;

    public AdressController() {
        System.out.println("AdressController erzeugt");
        observerList = new ArrayList<>();
        startTimerActor();
    }
    public void setDashBoardController(DashBoardController dashBoardController) {
        this.dashBoardController = dashBoardController;
    }

    public void updateAnzahlMitglieder(int anzahlMitglieder) {
        this.anzahlMitglieder = anzahlMitglieder;
        System.out.println("Anzahl Mitglieder geändert auf " + anzahlMitglieder);
        Notify();
    }

    public int getAnzahlMitglieder() {
        return anzahlMitglieder;
    }
    public ObservableList<Mitglied> getMitgliederListe() {
        return FXCollections.observableArrayList(DatabaseReader.getMitgliederAsArrayList());
    }

    private void startTimerActor() {
        Runnable blinkRunner = () -> {
            int zaehler = 0;
            while (true) {
                if(DatabaseReader.readAnzahlMitglieder() != anzahlMitglieder) {
                    updateAnzahlMitglieder(DatabaseReader.readAnzahlMitglieder());
                }
                // BlinkApp.getBlinkController().toggle();
                System.out.println("led " + zaehler);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {

                };
            }
        };
        Thread thread = new Thread(blinkRunner);
        thread.setDaemon(true);
        thread.start();
    }
    @Override
    public void Attach(Observer o) {
        observerList.add(o);
    }

    @Override
    public void Dettach(Observer o) {
        observerList.remove(o);
    }

    @Override
    public void Notify() {
        for(int i = 0; i <observerList.size(); i++)
        {
            observerList.get(i).update(this);
        }
    }
}
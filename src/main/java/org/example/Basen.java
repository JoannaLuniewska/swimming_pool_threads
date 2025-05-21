package org.example;

import java.util.ArrayList;

public abstract class Basen {
    protected String nazwa;
    protected int pojemnosc;
    protected ArrayList<Osoba> osobyNaBasenie;
    protected Centrum centrum;
    protected CentrumGUI gui;

    public Basen(String nazwa, int pojemnosc, Centrum centrum, CentrumGUI gui) {
        this.nazwa = nazwa;
        this.pojemnosc = pojemnosc;
        this.osobyNaBasenie = new ArrayList<>();
        this.centrum = centrum;
        this.gui = gui;
    }

    public synchronized boolean wejdz(Osoba osoba) {
        if (czyMoznaWejsc(osoba) && osobyNaBasenie.size() < pojemnosc) {
            osobyNaBasenie.add(osoba);
            aktualizujGUI();
            return true;
        }
        return false;
    }

    public synchronized void opuscBasen(Osoba osoba) {
        osobyNaBasenie.remove(osoba);
        aktualizujGUI();
    }

    public synchronized boolean czyPusty() {
        return osobyNaBasenie.isEmpty();
    }

    public abstract boolean czyMoznaWejsc(Osoba osoba);

    public void aktualizujGUI() {
        if (gui != null) {
            gui.aktualizujBasen(nazwa, new ArrayList<>(osobyNaBasenie));
        }
    }
}

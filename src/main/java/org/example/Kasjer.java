package org.example;

import java.util.ArrayList;
import java.util.List;

public class Kasjer extends Thread {
    private final List<Osoba> kolejkaNormalna = new ArrayList<>();
    private final List<Osoba> kolejkaOczekujaca = new ArrayList<>();
    private Centrum centrum;
    private CentrumGUI gui;

    public Kasjer(CentrumGUI gui) {
        this.gui = gui;
    }

    public void setCentrum(Centrum centrum) {
        this.centrum = centrum;
    }

    public void kupBilet(Osoba osoba) {
        synchronized (this) {
            if (centrum != null && !centrum.isOtwarte()) {
                kolejkaOczekujaca.add(osoba);
                gui.aktualizujKolejke(new ArrayList<>(kolejkaOczekujaca));

                System.out.println("Osoba " + osoba.nrOosby + " czeka do otwarcia centrum.");
            } else {
                kolejkaNormalna.add(osoba);
                gui.aktualizujKolejke(new ArrayList<>(kolejkaNormalna));

                notify(); // obudź kasjera
            }
        }
    }

    public void wznowObsluge() {
        synchronized (this) {
            kolejkaNormalna.addAll(kolejkaOczekujaca);
            kolejkaOczekujaca.clear();
            notify(); // obudź kasjera jeśli czeka
        }
    }

    public void run() {
        while (true) {
            Osoba osoba;

            synchronized (this) {
                while (kolejkaNormalna.isEmpty()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                osoba = kolejkaNormalna.remove(0);
            }

            synchronized (osoba) {
                osoba.maBilet = true;
                osoba.notify();
                //wypisanie w konsoli
                System.out.println("Kasjer sprzedał bilet osobie " + osoba.nrOosby + " Dane: wiek " + osoba.wiek+", VIP: "+ osoba.isVIP()+ ", ma Opiekuna: " + osoba.maOpiekuna);
            }
        }
    }
}


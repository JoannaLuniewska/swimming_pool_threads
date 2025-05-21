package org.example;

//klient.java
import java.util.ArrayList;
import java.util.Collections;

public class Klient extends Thread {
    protected Osoba osoba;
    protected Kasjer kasjer;
    protected ArrayList<Basen> baseny;
    protected Centrum centrum;
    private CentrumGUI gui;

    Klient(Osoba osoba, Kasjer kasjer, ArrayList<Basen> baseny, Centrum centrum, CentrumGUI gui) {
        this.osoba = osoba;
        this.kasjer = kasjer;
        this.baseny = baseny;
        this.centrum = centrum;
        this.gui = gui;
    }

    public void run() {
        try{
            kasjer.kupBilet(osoba);
            synchronized(osoba)
            {
                while(!osoba.maBilet){
                    osoba.wait();
                }
            }

        }catch(InterruptedException e){
            System.out.println("Przerwano");
        }


        synchronized (centrum) {
            while (!centrum.isOtwarte()) {
                try {
                    centrum.wait();
                } catch (InterruptedException e) {
                    System.out.println("Przerwano");
                }
            }
        }
        boolean znalazlBasen = false;

        while (!znalazlBasen) {
            ArrayList<Basen> losoweBaseny = new ArrayList<>(baseny);
            Collections.shuffle(losoweBaseny); // losowa kolejności

            for (Basen b : losoweBaseny) {
                if (b.wejdz(osoba)) {
                    System.out.println(osoba.nrOosby + " weszła na " + b.nazwa);
                    znalazlBasen = true;
                    try {
                        Thread.sleep(10000); // czas spędzony na basenie
                    } catch (InterruptedException e) {
                        System.out.println("Przerwano");
                    }
                    b.opuscBasen(osoba);
                }
            }

            if (!znalazlBasen) {
                try {
                    Thread.sleep(1000); // odczekaj chwilę i próbuj ponownie
                } catch (InterruptedException e) {
                    System.out.println("Przerwano");
                    break;
                }
            }
        }

    }

}


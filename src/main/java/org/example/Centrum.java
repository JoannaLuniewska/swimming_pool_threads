package org.example;

import java.util.ArrayList;

public class Centrum extends Thread {
    private volatile boolean otwarte = true;
    private final ArrayList<Basen> baseny;
    private final Kasjer kasjer;
    public final CentrumGUI gui;

    public Centrum(ArrayList<Basen> baseny, Kasjer kasjer, CentrumGUI gui) {
        this.baseny = baseny;
        this.kasjer = kasjer;
        this.gui = gui;
    }

    public synchronized void otworz() {
        otwarte = true;
        System.out.println("Centrum otwarte!");
        gui.aktualizujStatus(1);
        notifyAll();

        if (kasjer != null) {
            kasjer.wznowObsluge();
        }
    }

    public synchronized void zamknij() {
        otwarte = false;
        System.out.println("Centrum: Zamknięcie. Czekamy aż baseny się opróżnią...");
        gui.aktualizujStatus(2);
    }

    public boolean isOtwarte() {
        return otwarte;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                // Otwarte przez 18 sekund
                Thread.sleep(20000);
                zamknij();

                boolean pusto;
                do {
                    pusto = true;
                    for (Basen b : baseny) {
                        if (!b.czyPusty()) {
                            pusto = false;
                            break;
                        }
                    }
                    if (!pusto) {
                        Thread.sleep(500);
                    }
                } while (!pusto);

                System.out.println("Centrum: Wszystkie baseny puste. Zmiana wody...");
                gui.aktualizujStatus(3);
                Thread.sleep(5000); //  zmiana wody
                otworz();

            } catch (InterruptedException e) {
                System.out.println("Centrum przerwane.");
                break;
            }
        }
    }
}

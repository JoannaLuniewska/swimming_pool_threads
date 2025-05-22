package org.example;
//java corretto 21
//main.java
import java.util.ArrayList;
import java.util.Random;

class GeneratorOsob {
    private static final Random rand = new Random();

    public static Osoba generujOsobe(int nr) {
        int wiek = 1+rand.nextInt(69);
        boolean vip = rand.nextDouble() < 0.3;
        boolean opiekun = wiek < 10;
        boolean pampers = wiek <= 3;
        return new Osoba(wiek, vip, nr, opiekun, pampers);
    }
}
public class Main {
    public static void main(String[] args) {
        Konfiguracja config = Konfiguracja.wczytaj("src/main/resources/config.json");

        CentrumGUI gui = new CentrumGUI();
        gui.setVisible(true);

        Kasjer kasjer = new Kasjer(gui);
        ArrayList<Basen> baseny = new ArrayList<>();
        Centrum cent = new Centrum(baseny, kasjer, gui);

        kasjer.setCentrum(cent);
        kasjer.start();

        baseny.add(new BasenOlimpijski(config.baseny.get("BasenOlimpijski"), cent, gui));
        baseny.add(new BasenRekreacyjny(config.baseny.get("BasenRekreacyjny"), cent, gui));
        baseny.add(new Brodzik(config.baseny.get("Brodzik"), cent, gui));

        cent.start();

        int osoby = 1;
        for (int i = 0; i < config.liczbaOsob; i++) {
            Osoba osoba = GeneratorOsob.generujOsobe(osoby++);
            Klient klient = new Klient(osoba, kasjer, baseny, cent, gui);
            klient.start();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

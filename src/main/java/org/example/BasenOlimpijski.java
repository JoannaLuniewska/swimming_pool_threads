package org.example;

public class BasenOlimpijski extends Basen {
    public BasenOlimpijski(int pojemnosc, Centrum centrum, CentrumGUI gui) {
        super("Olimpijski (18+)", pojemnosc, centrum, gui);
    }

    @Override
    public boolean czyMoznaWejsc(Osoba osoba) {
        return osoba.wiek >= 18;
    }
}

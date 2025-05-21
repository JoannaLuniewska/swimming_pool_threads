package org.example;

//brodzik.java
public class Brodzik extends Basen{
    public Brodzik(int pojemnosc, Centrum c, CentrumGUI gui) {

        super("Brodzik (1-5)", pojemnosc,c, gui);
    }

    public boolean czyMoznaWejsc(Osoba osoba)
    {
        if(osoba.getWiek()<=5 && osoba.isMaOpiekuna()) return true;
        return false;
    }
}


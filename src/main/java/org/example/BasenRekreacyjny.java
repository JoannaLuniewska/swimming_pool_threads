package org.example;

//basen_rekreacyjny.java
public class BasenRekreacyjny extends Basen{

    public BasenRekreacyjny(int pojemnosc, Centrum centr, CentrumGUI gui) {

        super( "Rekreacyjny (avg<=40)",pojemnosc, centr,gui);
    }

    float sredWieku(Osoba osoba) {
        float suma=0;
        for(Osoba os: this.osobyNaBasenie)
        {
            suma+=os.getWiek();
        }
        suma+=osoba.getWiek();
        return (float) suma / (osobyNaBasenie.size()+1);
    }

    @Override
    public boolean czyMoznaWejsc(Osoba osoba) {

        if(sredWieku(osoba)<=40) return true;
        return false;
    }
}


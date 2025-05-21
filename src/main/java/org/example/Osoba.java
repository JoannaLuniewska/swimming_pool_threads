package org.example;

//osoba.java
public class Osoba {
    protected int wiek;
    protected boolean VIP;
    protected int nrOosby;
    protected boolean maOpiekuna;
    protected boolean maPampersa;
    protected boolean maBilet;

    Osoba(int wiek, boolean VIP, int nrOosby, boolean maOpiekuna, boolean maPampersa) {
        this.wiek = wiek;
        this.VIP = VIP;
        this.nrOosby = nrOosby;
        this.maOpiekuna = maOpiekuna;
        this.maPampersa = maPampersa;
        this.maBilet = false;
    }

    int getWiek()
    {
        return wiek;
    }

    int GetNr()
    {
        return nrOosby;
    }

    boolean isVIP()
    {
        return VIP;
    }

    boolean isMaOpiekuna()
    {
        return maOpiekuna;
    }

    boolean isMaPampersa()
    {
        return maPampersa;
    }


}


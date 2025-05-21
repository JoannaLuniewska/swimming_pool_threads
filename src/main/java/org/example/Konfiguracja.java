package org.example;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class Konfiguracja {
    public int liczbaOsob;
    public Map<String, Integer> baseny;

    public static Konfiguracja wczytaj(String sciezka) {
        try (FileReader reader = new FileReader(sciezka)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Konfiguracja.class);
        } catch (IOException e) {
            throw new RuntimeException("Nie udało się wczytać konfiguracji: " + e.getMessage());
        }
    }
}



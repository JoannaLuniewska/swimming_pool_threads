package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class CentrumGUI extends JFrame {
    private JLabel statusCentrum;
    private JPanel kolejkaPanel;
    private Map<String, JPanel> paneleBasenow;
    private Map<String, JLabel> licznikiBasenow;

    public CentrumGUI() {
        setTitle("Centrum Basenowe");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Górny pasek - status
        statusCentrum = new JLabel("Status centrum: OTWARTE", SwingConstants.CENTER);

        statusCentrum.setFont(new Font("Arial", Font.BOLD, 20));
        add(statusCentrum, BorderLayout.NORTH);

        // Panel główny z kolejką i basenami
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        // Kolejka do kasjera
        kolejkaPanel = new JPanel();
        kolejkaPanel.setLayout(new BoxLayout(kolejkaPanel, BoxLayout.Y_AXIS));
        kolejkaPanel.setBorder( BorderFactory.createTitledBorder("Kolejka do kasjera"));
        JScrollPane kolejkaScroll = new JScrollPane(kolejkaPanel);
        kolejkaScroll.setPreferredSize(new Dimension(300, 250));
        mainPanel.add(kolejkaScroll, BorderLayout.WEST );

        // Panele basenów
        JPanel basenyPanel = new JPanel(new GridLayout(1, 3));
        mainPanel.add(basenyPanel, BorderLayout.CENTER);

        paneleBasenow = new HashMap<>();
        licznikiBasenow = new HashMap<>();

        for (String nazwa : new String[]{"Olimpijski (18+)", "Rekreacyjny (avg<=40)", "Brodzik (1-5)"}) {
            JPanel basenPanel = new JPanel();
            basenPanel.setLayout(new BoxLayout(basenPanel, BoxLayout.Y_AXIS));
            basenPanel.setBorder(BorderFactory.createTitledBorder(nazwa));
            
            JScrollPane scroll = new JScrollPane(basenPanel);
            paneleBasenow.put(nazwa, basenPanel);

            JLabel licznik = new JLabel("Liczba osób: 0");
            licznikiBasenow.put(nazwa, licznik);
            basenPanel.add(licznik);

            basenyPanel.add(scroll);
        }
    }

public void aktualizujStatus(int otwarte) {
    SwingUtilities.invokeLater(() -> {
        String status;
        switch (otwarte) {
            case 1:
                status = "OTWARTE";
                break;
            case 2:
                status = "ZAMKNIĘTE – klienci wychodzą";
                break;
            case 3:
                status = "ZAMKNIĘTE – zmiana wody";
                break;
            default:
                status = "NIEZNANY";
        }
        statusCentrum.setText("Status centrum: " + status);
    });

}

    public void aktualizujKolejke(List<Osoba> kolejka) {
        SwingUtilities.invokeLater(() -> {
            kolejkaPanel.removeAll();
            for (Osoba o : kolejka) {
                kolejkaPanel.add(new JLabel(formatOsoba(o)));
            }
            kolejkaPanel.revalidate();
            kolejkaPanel.repaint();
        });
    }

    public void aktualizujBasen(String nazwa, List<Osoba> osoby) {

        SwingUtilities.invokeLater(() -> {
            JPanel panel = paneleBasenow.get(nazwa);
            panel.removeAll();
            JLabel licznik = licznikiBasenow.get(nazwa);
            licznik.setText("Liczba osób: " + osoby.size());
            panel.add(licznik);
            for (Osoba o : osoby) {
                panel.add(new JLabel(formatOsoba(o)));
            }
            panel.revalidate();
            panel.repaint();
        });
    }

    private String formatOsoba(Osoba o) {
        return String.format("#%d (wiek: %d, P:%s, VIP:%s, O:%s)",
                o.nrOosby,
                o.wiek,
                o.maPampersa ? "✓" : "✗",
                o.isVIP() ? "✓" : "✗",
                o.maOpiekuna ? "✓" : "✗"
        );
    }
}

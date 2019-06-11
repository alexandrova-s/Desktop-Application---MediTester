package com.app.gui;


import com.app.Listeners.ButtonListener;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {
    private JButton addBtn;
    private JButton patientsBtn;
    private JButton physiciansBtn;
    private JButton dosageBtn;
    private JButton effectsBtn;
    private JButton addDoctor;

    private ButtonListener panelAddButtonListener;
    private ButtonListener panelEffectButtonListener;
    private ButtonListener panelPatientsButtonListener;
    private ButtonListener panelPhysiciansButtonListener;
    private ButtonListener panelDosageButtonListener;
    private ButtonListener panelDoctorButtonListener;

    private boolean userMode;

    public ButtonPanel(boolean userMode) {

        addBtn = new JButton("Dodaj pacjenta");
        effectsBtn = new JButton("Opisy efektów zdrowotnych");
        patientsBtn = new JButton("Pacjenci");
        physiciansBtn = new JButton("Lekarze");
        dosageBtn = new JButton("Dawki");



        addBtn.addActionListener(e -> {
            panelAddButtonListener.buttonEventOccurred();
        });

        effectsBtn.addActionListener(e -> {
            panelEffectButtonListener.buttonEventOccurred();
        });

        patientsBtn.addActionListener(e -> {
            panelPatientsButtonListener.buttonEventOccurred();
        });

        physiciansBtn.addActionListener(e -> {
            panelPhysiciansButtonListener.buttonEventOccurred();
        });

        dosageBtn.addActionListener(e -> {
            panelDosageButtonListener.buttonEventOccurred();
        });

        setLayout(new FlowLayout(FlowLayout.CENTER));

        setBackground(new Color(156, 223, 232));

        add(addBtn);
        add(patientsBtn);
        add(physiciansBtn);
        add(dosageBtn);
        add(effectsBtn);
        if(userMode == false){

            addDoctor= new JButton("Dodaj lekarza");
            addDoctor.addActionListener(e -> {
                panelDoctorButtonListener.buttonEventOccurred();
            });
            add(addDoctor);
        }

    }

    public void setAddListener(ButtonListener panelButtonListener) {
        this.panelAddButtonListener = panelButtonListener;
    }

    public void setDosageListener(ButtonListener panelButtonListener) {
        this.panelDosageButtonListener = panelButtonListener;
    }

    public void setPatientsListener(ButtonListener panelButtonListener) {
        this.panelPatientsButtonListener = panelButtonListener;
    }

    public void setPhysiciansListener(ButtonListener panelButtonListener) {
        this.panelPhysiciansButtonListener = panelButtonListener;
    }

    public void setEffectListener(ButtonListener panelButtonListener) {
        this.panelEffectButtonListener = panelButtonListener;
    }

    public void setAddDoctorListener(ButtonListener panelButtonListener) {
        this.panelDoctorButtonListener = panelButtonListener;
    }

    public boolean isUserMode() {
        return userMode;
    }

    public void setUserMode(boolean userMode) {
        this.userMode = userMode;
    }
}

package com.app.gui;

import com.app.Listeners.ButtonListener;
import com.app.Listeners.PatientTableSelectionListener;
import com.app.guiModels.PatientTableModel;
import com.app.model.Patient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;

public class PatientsPanel extends JPanel {

    private JButton closeBtn;
    private JButton delBtn;
    private JButton showBtn;
    private JButton doseBtn;
    private JButton effectBtn;
    private ButtonListener panelCloseBtnListener;
    private ButtonListener panelDelBtnListener;
    private ButtonListener panelShowBtnListener;
    private ButtonListener panelDoseBtnListener;
    private ButtonListener panelEffectBtnListener;
    private PatientTableSelectionListener patientTableSelectionListener;
    private JPanel btnpanel;

    private PatientTableModel patientTable;
    private JTable table;

    private boolean userMode;

    public PatientsPanel(boolean userMode) {

        closeBtn = new JButton("Zamknij");
        closeBtn.addActionListener(e -> {
            panelCloseBtnListener.buttonEventOccurred();
        });

        showBtn = new JButton("Pokaż informacje dotyczące pacjenta");
        showBtn.addActionListener(e -> {
            panelShowBtnListener.buttonEventOccurred();
        });

        doseBtn = new JButton("Dodaj podanie leku");
        doseBtn.addActionListener(e -> {
            panelDoseBtnListener.buttonEventOccurred();
        });

        effectBtn = new JButton("Dodaj opis zdrowotny pacjenta");

        effectBtn.addActionListener(e -> {
            panelEffectBtnListener.buttonEventOccurred();
        });

        patientTable = new PatientTableModel();
        table = new JTable(patientTable);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            DefaultListSelectionModel dlsm = (DefaultListSelectionModel)e.getSource();
            patientTableSelectionListener.formListSelectionOccurred(
                    patientTable.getPatientByRow(dlsm.getMinSelectionIndex())
            );
        });
        setLayout(new BorderLayout());
        add(new JScrollPane(table) , BorderLayout.CENTER);

        btnpanel = new JPanel();
        btnpanel.add(doseBtn);
        btnpanel.add(effectBtn);
        btnpanel.add(showBtn);


        if(userMode == false){
            delBtn = new JButton("Usuń");
            delBtn.addActionListener(e -> {
                panelDelBtnListener.buttonEventOccurred();
            });
            btnpanel.add(delBtn);
        }

        btnpanel.add(closeBtn);
        btnpanel.setBackground(new Color(156, 223, 232));
        add(btnpanel, BorderLayout.SOUTH);

        setVisible(true);

        Border insideBorder = BorderFactory.createTitledBorder("TABELA PACJENTÓW");
        Border outsideBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
        setBackground(new Color(156, 223, 232));
    }

    public void setData(List<Patient> db) {
        patientTable.setData(db);
    }
    public void setCloseBtnListener(ButtonListener panelButtonListener) {
        this.panelCloseBtnListener = panelButtonListener;
    }
    public void setDelBtnListener(ButtonListener panelButtonListener) {
        this.panelDelBtnListener = panelButtonListener;
    }
    public void setShowBtnListener(ButtonListener panelButtonListener) {
        this.panelShowBtnListener = panelButtonListener;
    }
    public void setDoseBtnListener(ButtonListener panelButtonListener) {
        this.panelDoseBtnListener = panelButtonListener;
    }
    public void setEffectBtnListener(ButtonListener panelButtonListener) {
        this.panelEffectBtnListener = panelButtonListener;
    }
    public void setPatientTableSelectionListener(PatientTableSelectionListener tableSelectionListener) {
        this.patientTableSelectionListener = tableSelectionListener;
    }

}
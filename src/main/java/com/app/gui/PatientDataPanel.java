package com.app.gui;

import com.app.Listeners.ButtonListener;
import com.app.Listeners.DoseTableSelectionListener;
import com.app.Listeners.EffectTableSelectionListener;
import com.app.guiModels.DosageTableModel;
import com.app.guiModels.EffectTableModel;
import com.app.model.Dose;
import com.app.model.Effect;
import com.app.model.Patient;

import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;


public class PatientDataPanel  extends JPanel{

    private JButton showBtn;
    private ButtonListener panelShowBthListener;
    private JButton closeBtn;
    private ButtonListener panelCloseBthListener;
    private JButton delBtn;
    private ButtonListener panelDelBthListener;
    private JButton addDoseBtn;
    private ButtonListener panelAddDoseBthListener;
    private JButton addEffBtn;
    private ButtonListener panelAddEffBthListener;
    private JPanel btnpanel;
    private JPanel patientDataPanel;
    private JLabel patNameL;
    private JLabel patSurnameL;
    private JLabel patPeselL;
    private JLabel patBirthdayL;

    private JPanel tablesPanel;
    private DosageTableModel doseTable;
    private JTable dTable;
    private DoseTableSelectionListener doseTableSelectionListener;
    private EffectTableModel effectTable;
    private JTable eTable;
    private EffectTableSelectionListener effectTableSelectionListener;

    private boolean userMode;

    public PatientDataPanel(boolean userMode) {

        closeBtn = new JButton("Zamknij");
        closeBtn.addActionListener(e -> {
            panelCloseBthListener.buttonEventOccurred();
        });

        addDoseBtn = new JButton("Dodaj dawkę");
        addDoseBtn.addActionListener(e -> {
            panelAddDoseBthListener.buttonEventOccurred();
        });
        showBtn = new JButton("Przeczytaj opis efektu zdrowotnego");
        showBtn.addActionListener(e -> {
            panelShowBthListener.buttonEventOccurred();
        });

        addEffBtn = new JButton("Dodaj wpis");
        addEffBtn.addActionListener(e -> {
            panelAddEffBthListener.buttonEventOccurred();
        });

        doseTable = new DosageTableModel();
        dTable = new JTable(doseTable);
        dTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dTable.getSelectionModel().addListSelectionListener(e -> {
            DefaultListSelectionModel dlsm = (DefaultListSelectionModel)e.getSource();
            doseTableSelectionListener.formListSelectionOccurred(
                    doseTable.getDoseByRow(dlsm.getMinSelectionIndex())
            );
        });

        effectTable = new EffectTableModel();
        eTable = new JTable(effectTable);
        eTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eTable.getSelectionModel().addListSelectionListener(e -> {
            DefaultListSelectionModel dlsm = (DefaultListSelectionModel)e.getSource();
            effectTableSelectionListener.formListSelectionOccurred(
                    effectTable.getEffectByRow(dlsm.getMinSelectionIndex())
            );
        });

        setLayout(new BorderLayout());

        tablesPanel = new JPanel();
        tablesPanel.add(new JScrollPane(dTable));
        tablesPanel.add(new JScrollPane(eTable));
        add(tablesPanel, BorderLayout.CENTER);
        tablesPanel.setBackground(new Color(156, 223, 232));

        btnpanel = new JPanel();
        btnpanel.setBackground(new Color(156, 223, 232));
        btnpanel.add(addDoseBtn);
        btnpanel.add(addEffBtn);
        btnpanel.add(showBtn);

        if(userMode == false){
            delBtn = new JButton("Usuń");
            delBtn.addActionListener(e -> {
                panelDelBthListener.buttonEventOccurred();
            });
            btnpanel.add(delBtn);
        }

        btnpanel.add(closeBtn);

        add(btnpanel, BorderLayout.SOUTH);

        patientDataPanel = new JPanel();
        patientDataPanel.setBackground(new Color(156, 223, 232));
        patNameL = new JLabel();
        patSurnameL = new JLabel();
        patPeselL = new JLabel();
        patBirthdayL = new JLabel();

        patientDataPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(10, 30, 10, 30);

        gc.gridx = 0;
        patientDataPanel.add(patNameL, gc);
        gc.gridx = 1;
        patientDataPanel.add(patSurnameL, gc);
        gc.gridx = 2;
        patientDataPanel.add(patPeselL, gc);
        gc.gridx = 3;
        patientDataPanel.add(patBirthdayL, gc);

        add(patientDataPanel, BorderLayout.NORTH);

        Border insideBorder = BorderFactory.createTitledBorder("DANE PACJENTA");
        Border outsideBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
        setBackground(new Color(156, 223, 232));
    }
    public void setDataDose(List<Dose> db) {
        doseTable.setData(db);
    }
    public void setDataEffect(List<Effect> db) {
        effectTable.setData(db);
    }
    public void setCloseBtnListener(ButtonListener panelButtonListener) {
        this.panelCloseBthListener = panelButtonListener;
    }
    public void setDelBtnListener(ButtonListener panelButtonListener) {
        this.panelDelBthListener = panelButtonListener;
    }
    public void setAddDoseBtnListener(ButtonListener panelButtonListener) {
        this.panelAddDoseBthListener = panelButtonListener;
    }
    public void setAddEffBtnListener(ButtonListener panelButtonListener) {
        this.panelAddEffBthListener = panelButtonListener;
    }

    public void setDoseTableSelectionListener(DoseTableSelectionListener tableSelectionListener) {
        this.doseTableSelectionListener = tableSelectionListener;
    }

    public void setEffectTableSelectionListener(EffectTableSelectionListener tableSelectionListener) {
        this.effectTableSelectionListener = tableSelectionListener;
    }

    public void setShowBtnListener(ButtonListener panelButtonListener) {
        this.panelShowBthListener = panelButtonListener;
    }

    public void setPatientTitleData(Patient p){
        patNameL.setText("Imię:   " + p.getName());
        patSurnameL.setText("Nazwisko:   " + p.getSurname());
        patBirthdayL.setText("Urodziny:   " + p.getBirthday());
        patPeselL.setText("PESEL:   " + p.getPesel());
    }
}
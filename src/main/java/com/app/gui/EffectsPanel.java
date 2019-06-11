package com.app.gui;

import com.app.Listeners.ButtonListener;
import com.app.Listeners.EffectTableSelectionListener;
import com.app.guiModels.EffectTableModel;
import com.app.model.Doctor;
import com.app.model.Effect;
import com.app.model.Patient;
import com.app.service.MyService;
import com.app.service.MyServiceImpl;

import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxRenderer;


public class EffectsPanel  extends JPanel{

    private JButton closeBtn;
    private JButton showBtn;
    private JButton filtBtn;
    private JButton delBtn;
    private ButtonListener panelDelBtnListener;
    private ButtonListener panelCloseBthListener;
    private ButtonListener panelShowBthListener;
    private ButtonListener panelFiltrBtnListener;

    private JLabel patBoxLabel;
    private JLabel docBoxLabel;

    private EffectTableSelectionListener effectTableSelectionListener;
    private JTable table;
    private JPanel btnpanel;
    private EffectTableModel effectTable;

    private JPanel boxesPanel;
    private JComboBox boxPatients;
    private JComboBox boxDoctors;
    private List<Doctor> doctors;

    private JPanel titlePanel;
    private JLabel doctorL;
    private JLabel patientL;

    public EffectsPanel(boolean userMode) {

        patBoxLabel = new JLabel("Wybierz pacjenta");
        docBoxLabel = new JLabel("Wybierz lekarza");

        closeBtn = new JButton("Zamknij");
        closeBtn.addActionListener(e -> {
            panelCloseBthListener.buttonEventOccurred();
        });

        showBtn = new JButton("Przeczytaj opis efektu zdrowotnego");
        showBtn.addActionListener(e -> {
            panelShowBthListener.buttonEventOccurred();
        });

        filtBtn = new JButton("Filtruj");
        filtBtn.addActionListener(e -> {
            panelFiltrBtnListener.buttonEventOccurred();
        });

        effectTable = new EffectTableModel();
        table = new JTable(effectTable);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            DefaultListSelectionModel dlsm = (DefaultListSelectionModel)e.getSource();
            effectTableSelectionListener.formListSelectionOccurred(
                    effectTable.getEffectByRow(dlsm.getMinSelectionIndex())
            );
        });

        setLayout(new BorderLayout());

        boxPatients = new JComboBox();
        boxDoctors = new JComboBox();

        boxesPanel = new JPanel();
        boxesPanel.add(patBoxLabel);
        boxesPanel.add(boxPatients);
        boxesPanel.add(docBoxLabel);
        boxesPanel.add(boxDoctors);
        boxesPanel.add(filtBtn);

        titlePanel = new JPanel();
        patientL = new JLabel();
        doctorL = new JLabel();
        titlePanel.add(patientL);
        titlePanel.add(doctorL);
        titlePanel.setBackground(new Color(156, 223, 232));
        boxesPanel.add( titlePanel);

        add(boxesPanel, BorderLayout.NORTH);

        add(new JScrollPane(table),BorderLayout.CENTER);

        btnpanel = new JPanel();
        btnpanel.add(showBtn);
        if(userMode == false){
            delBtn = new JButton("Usuń");
            delBtn.addActionListener(e -> {
                panelDelBtnListener.buttonEventOccurred();
            });
            btnpanel.add(delBtn);
        }
        btnpanel.add(closeBtn);
        add(btnpanel, BorderLayout.SOUTH);

        btnpanel.setBackground(new Color(156, 223, 232));
        boxesPanel.setBackground(new Color(156, 223, 232));

        Border insideBorder = BorderFactory.createTitledBorder("TABELA EFEKTÓW ZDROWOTNYCH");
        Border outsideBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
        setBackground(new Color(156, 223, 232));
    }

    public void setBoxes(List<Doctor> doctors, List<Patient> patients){

        DefaultComboBoxModel patModel = new DefaultComboBoxModel();
        patModel.addAll(patients);
        boxPatients.setModel(patModel);
        boxPatients.setRenderer( new PatientRenderer() );
        boxPatients.setEditable(true);
        DefaultComboBoxModel docModel = new DefaultComboBoxModel();
        docModel.addAll(doctors);
        boxDoctors.setModel(docModel);
        boxDoctors.setRenderer( new DoctorRenderer() );
        boxDoctors.setEditable(true);

    }

    public void setTitleData(Patient p, Doctor d){
        if( p == null && d != null){
            patientL.setText("Pacjent :    nie wybrano    " );
            doctorL.setText("Lekarz: " + d.getName() + " " + d.getSurname());
        } else if ( p != null && d == null){
            patientL.setText("Pacjent :" + p.getName() + " " + p.getSurname()+ "    ");
            doctorL.setText("Lekarz:     nie wybrano    " );
        } else if( p != null && d != null){
            patientL.setText("Pacjent :" + p.getName() + " " + p.getSurname()+ "    ");
            doctorL.setText("Lekarz: " + d.getName() + " " + d.getSurname());
        } else {
            patientL.setText("Pacjent :    nie wybrano    " );
            doctorL.setText("Lekarz:     nie wybrano" );
        }
    }


    public Integer getPatientIdFromBox() {
        Patient p= (Patient) boxPatients.getSelectedItem();
        if ( p == null){
            return null;
        } else {
            return p.getId();
        }
    }
    public Integer getDoctorIdFromBox() {
        Doctor d= (Doctor) boxDoctors.getSelectedItem();
        if ( d == null){
            return null;
        } else {
            return d.getId();
        }
    }

    public void clearEffectPanel(){
        boxPatients.setSelectedIndex(-1);
        boxDoctors.setSelectedIndex(-1);
    }

    public Integer getModeEffect(){
        int pID = boxPatients.getSelectedIndex();
        int dID = boxDoctors.getSelectedIndex();
        // wybrano pacjenta
        if(pID != -1 && dID == -1 ){
            return 1;        }
        //wybrano lekarza
        else if(pID == -1 && dID != -1){
            return 2;
        }
        //wybrano oba
        else if(pID != -1 && dID != -1){
            return 3;
        }
        //nie wybrano
        else {
            return 0;
        }
    }

    public void setData(List<Effect> db) {
        effectTable.setData(db);
    }

    public void setCloseBtnListener(ButtonListener panelButtonListener) {
        this.panelCloseBthListener = panelButtonListener;
    }

    public void setShowBtnListener(ButtonListener panelButtonListener) {
        this.panelShowBthListener = panelButtonListener;
    }

    public void setFiltrBtnListener(ButtonListener panelButtonListener) {
        this.panelFiltrBtnListener = panelButtonListener;
    }

    public void setDelBtnListener(ButtonListener panelButtonListener) {
        this.panelDelBtnListener = panelButtonListener;
    }

    public void setEffectTableSelectionListener(EffectTableSelectionListener tableSelectionListener) {
        this.effectTableSelectionListener = tableSelectionListener;
    }

    class DoctorRenderer extends BasicComboBoxRenderer {
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);
            if (value != null) {
                Doctor d = (Doctor) value;
                setText(d.getName() + " " + d.getSurname());
            }
            if (index == -1) {
                Doctor d = (Doctor) value;
                setText("" + d.getId());
            }
            return this;
        }
    }

    class PatientRenderer extends BasicComboBoxRenderer {
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);
            if (value != null) {
                Patient p = (Patient)value;
                setText(p.getName() + " " + p.getSurname());
            }
            if (index == -1) {
                Patient p = (Patient)value;
                setText("" + p.getId());
            }
            return this;
        }
    }

}

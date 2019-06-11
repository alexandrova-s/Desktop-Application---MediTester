package com.app.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.plaf.basic.BasicComboBoxRenderer;


import com.app.Listeners.ButtonListener;
import com.app.model.Doctor;
import com.app.model.Effect;
import com.app.model.Patient;
import com.app.service.MyService;
import com.app.service.MyServiceImpl;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class FormPanelE extends JPanel {

    private JLabel titleLabel;
    private JLabel dateLabel;
    private JLabel effectLabel;
    private JTextArea effectField;
    private JScrollPane effectScroll;

    private JButton addEffectB;
    private JButton closeB;
    private ButtonListener formSaveBtnListener;
    private ButtonListener formDelBtnListener;

    // dane lekarza
    private JLabel doctorNameLabel;
    private JComboBox doctorBox;
    private Integer patIdE;

    private JDatePickerImpl datePicker;

    LocalDateTime today = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formatDateTime = today.format(formatter);


    public FormPanelE() {

        titleLabel = new JLabel("Dodaj opis");
        titleLabel.setFont(new Font("Verdana", 1, 20));

        dateLabel = new JLabel("Data: ");
        dateLabel.setFont(new Font("Verdana", 1, 15));

        UtilDateModel model  = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        effectLabel = new JLabel("Efekt: ");
        effectLabel.setFont(new Font("Verdana", 1, 15));
        effectField = new JTextArea();
        effectField.setPreferredSize(new Dimension(600, 150));
        effectField.setLineWrap(true);
        effectScroll = new JScrollPane(effectField);
        effectScroll.setBounds( 0, 0, 600, 150);
        effectField.setWrapStyleWord(true);

        addEffectB = new JButton("Dodaj");
        addEffectB.addActionListener(e -> {
            formSaveBtnListener.buttonEventOccurred();
        });

        closeB = new JButton("Anuluj");
        closeB.addActionListener(e -> {
            formDelBtnListener.buttonEventOccurred();
        });
        add(effectLabel);
        // dane doktora
        doctorNameLabel = new JLabel("Lekarz opisujący: ");
        doctorNameLabel.setFont(new Font("Verdana", 1, 15));

        doctorBox = new JComboBox();

        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBackground(new Color(156, 223, 232));

        add(addEffectB);
        add(closeB);
        setBackground(new Color(156, 223, 232));
        setMinimumSize(new Dimension(500, 300));
        setMinimumSize(new Dimension(500, 300));
        setSize(500, 300);

        setVisible(true);

        layoutComponents();

    }

    public void setDoctorsBox(List<Doctor> doctors){
        DefaultComboBoxModel docModel = new DefaultComboBoxModel();
        docModel.addAll(doctors);
        doctorBox.setModel(docModel);
        doctorBox.setRenderer( new DoctorRenderer() );
        doctorBox.setEditable(true);
    }

    public void clearFormPanelL(){
        effectField.setText("");
        datePicker.getJFormattedTextField().setText(formatDateTime);
        doctorBox.setSelectedIndex(-1);
    }


    public void setFormSaveBtnListener(ButtonListener listener) {
        this.formSaveBtnListener = listener;
    }

    public void setFormDelBtnListener(ButtonListener listener) {
        this.formDelBtnListener = listener;
    }

    public void layoutComponents() {

        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

/////////////////////// FIRST ROW ///////

        gc.gridy = 0;

        gc.weightx = 2;
        gc.weighty = 0D;

        gc.gridx = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(10, 10, 10, 10);
        add(titleLabel, gc);
        ////////////////////////////////////
        gc.gridy++;
        gc.gridy++;

        gc.weightx = 2;
        gc.weighty = 0D;

        gc.gridx = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        add(dateLabel, gc);

        gc.gridx = 2;
        gc.anchor = GridBagConstraints.LINE_START;
        add(datePicker, gc);
/////////////////////// NEXT ROW ///////

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.CENTER;
        add(effectLabel, gc);

        gc.gridx = 2;
        gc.anchor = GridBagConstraints.LINE_START;
        add(effectScroll, gc);
        /////////////////////// NEXT ROW ///////

        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 0.2;

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.CENTER;
        add(doctorNameLabel, gc);

        gc.gridx = 2;
        gc.anchor = GridBagConstraints.LINE_START;
        add(doctorBox, gc);

/////////////////////// NEXT ROW ///////
        gc.gridy++;

        gc.weightx = 1;
        gc.weighty = 2.0;

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LAST_LINE_END;
        add(addEffectB, gc);

        gc.gridx = 2;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        add(closeB, gc);

    }

    public Effect getEffectFromForm() {
        if (datePicker.getJFormattedTextField().getText() == null) {
            return null;
        }
        else{
            MyService myService = new MyServiceImpl();
            Effect effect = new Effect();
            effect.setDescription(effectField.getText());
            effect.setDateEffect(datePicker.getJFormattedTextField().getText());
            Patient p = myService.findByIdPatient(patIdE);
            effect.setPatientId(p.getId());
            Doctor d = (Doctor)doctorBox.getSelectedItem();
            effect.setDoctorId(d.getId());
            return effect;
        }
    }
    public boolean checkEffectData(){
        if( effectField.getText() != null && datePicker.getJFormattedTextField().getText()!= null && doctorBox.getSelectedItem() != null){
            return true;
        } else{
            return false;
        }
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

    public void setPatIdE(Integer patIdE) {
        this.patIdE = patIdE;
    }

    public class DateLabelFormatter extends AbstractFormatter {
        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }
}

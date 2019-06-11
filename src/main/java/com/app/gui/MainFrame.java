package com.app.gui;

import com.app.model.Doctor;
import com.app.model.Patient;
import com.app.repository.DoctorRepository;
import com.app.repository.DoctorRepositoryImpl;
import com.app.repository.PatientRepository;
import com.app.repository.PatientRepositoryImpl;
import com.app.service.MyService;
import com.app.service.MyServiceImpl;
import com.app.validators.PeselValidator;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MainFrame extends JFrame {

    private ButtonPanel buttonPanel;
    private FormPanel formPanel;
    private PatientsPanel patientsPanel;
    private DoctorsPanel doctorsPanel;
    private DosagePanel dosagePanel;
    private EffectsPanel effectsPanel;
    private Random rand;
    private PatientDataPanel patientDataPanel;
    private JLabel imageLabel;
    private ImageIcon image;
    private MyService myService;
    private FormPanelD formPanelD;
    private FormPanelL formPanelL;
    private FormPanelE formPanelE;
    private int patId;
    private int docId;
    private int dosId;
    private int effId;

    private Integer modeDose;
    private Integer modeEffect;
    private PeselValidator peselValidator;
    private Integer modeComeBackL;
    private Integer modeComeBackE;
    private boolean userMode;


    public MainFrame(boolean userMode) {
        super("Expert MediTester");

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        //Calculate the frame location
        int x = (screenSize.width) / 7;
        int y = (screenSize.height) / 9;

        //Set the new frame location
        setLocation(x, y);

        MyService myService = new MyServiceImpl();
        PatientRepository patientRepository = new PatientRepositoryImpl();
        DoctorRepository doctorRepository = new DoctorRepositoryImpl();

        setLayout(new BorderLayout());

        if(userMode == false){
            formPanelD = new FormPanelD();
        }
        patientDataPanel = new PatientDataPanel(userMode);
        patientsPanel = new PatientsPanel(userMode);
        doctorsPanel = new DoctorsPanel(userMode);
        dosagePanel = new DosagePanel(userMode);
        effectsPanel = new EffectsPanel(userMode);
        formPanel = new FormPanel();
        formPanelL = new FormPanelL();
        formPanelE = new FormPanelE();
        buttonPanel = new ButtonPanel(userMode);

        Random rand = new Random();

        getContentPane().setBackground(new Color(156, 223, 232));
        ImageIcon image = new ImageIcon("logoOk.png");
        JLabel imageLabel = new JLabel(image);

////////////// przycisk dodaj nowego pacjenta //////////////////////////////////
        buttonPanel.setAddListener(() -> {
            boolean is_placebo = rand.nextBoolean();

            if (patientsPanel.isVisible()) {
                patientsPanel.setVisible(false);
            }
            if (doctorsPanel.isVisible()) {
                doctorsPanel.setVisible(false);
            }
            if (imageLabel.isVisible()) {
                imageLabel.setVisible(false);
            }
            if (dosagePanel.isVisible()) {
                dosagePanel.setVisible(false);
            }
            if (effectsPanel.isVisible()) {
                effectsPanel.setVisible(false);
            }
            if (patientDataPanel.isVisible()) {
                patientDataPanel.setVisible(false);
            }
            if (formPanelL.isVisible()) {
                formPanelL.setVisible(false);
            }
            if (formPanelE.isVisible()) {
                formPanelE.setVisible(false);
            }
            if(userMode == false){

                if (formPanelD.isVisible()) {
                    formPanelD.setVisible(false);
                }
            }
            formPanel.setDoctorsBox(myService.getAllDoctor());
            add(formPanel, BorderLayout.CENTER);

            // przydział do grupy pacjętów
            formPanel.setIs_placebo(is_placebo);
            formPanel.setVisible(true);
            setPatId(-1);
            setDocId(-1);
            setDosId(-1);
            setEffId(-1);

        });

        // przycisk w panelu formularza save
        formPanel.setFormSaveBtnListener(() -> {
            int help = 0;
            peselValidator = new PeselValidator(formPanel.getPeselFromForm());
            if ((patientRepository.existsByPesel(formPanel.getPeselFromForm()) == false) && peselValidator.isValid() && formPanel.checkPatientData() == true){
                help =1;
                Patient p = formPanel.getPatienFromForm();
                myService.addPatient(p);
                if (formPanel.isIs_placebo()) {
                    JOptionPane.showMessageDialog(null, "Pacjęta wprowadzono do grupy pobierającej placebo", "Uwaga!",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Pacjęta wprowadzono do grupy pobierającej lek", "Uwaga!",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                formPanel.clearFormPanel();
                formPanel.setVisible(false);
                add(imageLabel, BorderLayout.CENTER);
                imageLabel.setVisible(true);
            }
            if (patientRepository.existsByPesel(formPanel.getPeselFromForm()) && help != 1) {
                JOptionPane.showMessageDialog(null, "Pacjent o podanym numerze PESEL istnieje już w bazie", "Uwaga!",
                        JOptionPane.INFORMATION_MESSAGE);            }

            if (peselValidator.isValid() == false) {
                JOptionPane.showMessageDialog(null, "Podano niepoprawny numer PESEL", "Uwaga!",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            if (formPanel.checkPatientData() == false) {
                JOptionPane.showMessageDialog(null, "Nie wybrano wszystkich danych", "Uwaga!",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // przycisk w formularzu anuluj
        formPanel.setFormDelBtnListener(() -> {
            JOptionPane.showMessageDialog(null, "Zamknięto formularz nie zapisując pacjenta", "Uwaga!",
                    JOptionPane.INFORMATION_MESSAGE);
            formPanel.clearFormPanel();
            formPanel.setVisible(false);
            add(imageLabel, BorderLayout.CENTER);
            imageLabel.setVisible(true);
        });

//////////////////////// przycisk wyswietl pacjetów////////////////////////////////////////////
        buttonPanel.setPatientsListener(() -> {
            if (formPanel.isVisible()) {
                formPanel.setVisible(false);
            }
            if (doctorsPanel.isVisible()) {
                doctorsPanel.setVisible(false);
            }
            if (imageLabel.isVisible()) {
                imageLabel.setVisible(false);
            }
            if (dosagePanel.isVisible()) {
                dosagePanel.setVisible(false);
            }
            if (effectsPanel.isVisible()) {
                effectsPanel.setVisible(false);
            }
            if (patientDataPanel.isVisible()) {
                patientDataPanel.setVisible(false);
            }
            if(userMode == false){

                if (formPanelD.isVisible()) {
                    formPanelD.setVisible(false);
                }
            }
            if (formPanelL.isVisible()) {
                formPanelL.setVisible(false);
            }
            if (formPanelE.isVisible()) {
                formPanelE.setVisible(false);
            }
            patientsPanel.setData(myService.getAllPatient());
            add(patientsPanel, BorderLayout.CENTER);
            patientsPanel.setVisible(true);
            setPatId(-1);
            setDocId(-1);
            setDosId(-1);
            setEffId(-1);
        });

        //dodaj dawkę do pacjenta
        patientsPanel.setDoseBtnListener(() -> {
            if(patId != -1){
                patientsPanel.setVisible(false);
                formPanelL.setDoctorsBox(myService.getAllDoctor());
                add(formPanelL, BorderLayout.CENTER);
                formPanelL.setVisible(true);
                formPanelL.setPatIdL(getPatId());
                setModeComeBackL(0);
            } else {
                JOptionPane.showMessageDialog(null,"Nie wybrano pacjenta","Ważne!",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //dodaj opis do pacjenta
        patientsPanel.setEffectBtnListener(() -> {
            if(patId != -1){
                patientsPanel.setVisible(false);
                formPanelE.setDoctorsBox(myService.getAllDoctor());
                add(formPanelE, BorderLayout.CENTER);
                formPanelE.setVisible(true);
                formPanelE.setPatIdE(getPatId());
                setModeComeBackE(0);
            } else {
                JOptionPane.showMessageDialog(null,"Nie wybrano pacjenta","Ważne!",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // przycisk w pacjentach show
        patientsPanel.setShowBtnListener(() -> {
            if(patId != -1) {
                patientsPanel.setVisible(false);
                Patient p = myService.findByIdPatient(patId);
                patientDataPanel.setPatientTitleData(myService.findByIdPatient(patId));
                patientDataPanel.setDataDose(myService.getAllPatientDoses(p.getId()));
                patientDataPanel.setDataEffect(myService.getAllPatientEffects(p.getId()));
                add(patientDataPanel, BorderLayout.CENTER);
                patientDataPanel.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null,"Nie wybrano pacjenta","Ważne!",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // przycisk w pacjentach usuń
        patientsPanel.setDelBtnListener(() -> {
            if(patId != -1){
                myService.deletePatient(patId);
                patientsPanel.setData(myService.getAllPatient());
                add(patientsPanel, BorderLayout.CENTER);
            } else {
                JOptionPane.showMessageDialog(null,"Nie wybrano pacjenta","Ważne!",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // przycisk w pacjętach zamknij
        patientsPanel.setCloseBtnListener(() -> {
            patientsPanel.setVisible(false);
            add(imageLabel, BorderLayout.CENTER);
            imageLabel.setVisible(true);
        });

        /// klikniecie pacjenta w tabeli
        patientsPanel.setPatientTableSelectionListener(p -> {
            setPatId(p.getId());
        });

/////////////////////////panel dodawania dawki//////////////////////////
        //dodaj dawke do pacjenta
        formPanelL.setFormSaveBtnListener(() -> {
            if(formPanelL.checkDoseData() == true){
                if(formPanelL.checkDoseFormat() == true){
                    myService.addDose(formPanelL.getDoseFromForm());
                    formPanelL.clearFormPanelL();
                } else {
                    JOptionPane.showMessageDialog(null, "Nie poprawny format dawki", "Uwaga!",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Nie wybrano wszystkich danych", "Uwaga!",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            formPanelL.setVisible(false);
            if(modeComeBackL == 0){
                patientsPanel.setVisible(true);
            }
            if(modeComeBackL == 1){
                patientDataPanel.setDataDose(myService.getAllPatientDoses(patId));
                add(patientDataPanel, BorderLayout.CENTER);
                patientDataPanel.setVisible(true);
            }
        });

        // zamknij/anuluj panel
        formPanelL.setFormDelBtnListener(() -> {
            formPanelL.clearFormPanelL();
            formPanelL.setVisible(false);
            if(modeComeBackL == 0){
                patientsPanel.setVisible(true);
            }
            if(modeComeBackL == 1){
                patientDataPanel.setVisible(true);
            }
        });

 /////////////////////////panel dodawania opisów/////////////////////////////
        //dodaj opis do pacjenta
        formPanelE.setFormSaveBtnListener(() -> {
            if(formPanelE.checkEffectData() == true){
                myService.addEffect(formPanelE.getEffectFromForm());
                formPanelE.clearFormPanelL();
            } else {
                JOptionPane.showMessageDialog(null, "Nie wybrano wszystkich danych", "Uwaga!",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            formPanelE.setVisible(false);
            if(modeComeBackE == 0){
                patientsPanel.setVisible(true);
            }
            if(modeComeBackE == 1){
                patientDataPanel.setDataEffect(myService.getAllPatientEffects(patId));
                add(patientDataPanel, BorderLayout.CENTER);
                patientDataPanel.setVisible(true);
            }
        });

        // przycisk zamknij/anuluj panel
        formPanelE.setFormDelBtnListener(() -> {
            formPanelE.clearFormPanelL();
            formPanelE.setVisible(false);
            if(modeComeBackE == 0){
                patientsPanel.setVisible(true);
            }
            if(modeComeBackE == 1){
                patientDataPanel.setVisible(true);
            }
        });

////////////////////////panel dotyczacy szczegółów leczenia pacjenta///////////////////////////////
        //dodaj dawke do pacjenta
        patientDataPanel.setAddDoseBtnListener(() -> {
            if(patId != -1){

                patientDataPanel.setVisible(false);
                formPanelL.setDoctorsBox(myService.getAllDoctor());
                add(formPanelL, BorderLayout.CENTER);
                formPanelL.setVisible(true);
                formPanelL.setPatIdL(getPatId());
                setModeComeBackL(1);
            } else {
                JOptionPane.showMessageDialog(null,"Nie wybrano pacjenta","Ważne!",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //dodaj opis do pacjenta
        patientDataPanel.setAddEffBtnListener(() -> {
            if(patId != -1){
                patientDataPanel.setVisible(false);
                formPanelE.setDoctorsBox(myService.getAllDoctor());
                add(formPanelE, BorderLayout.CENTER);
                formPanelE.setVisible(true);
                formPanelE.setPatIdE(getPatId());
                setModeComeBackE(1);
            } else {
                JOptionPane.showMessageDialog(null,"Nie wybrano pacjenta","Ważne!",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // przycisk w szegółach pokaż opis ////
        patientDataPanel.setShowBtnListener(() -> {
            if (effId == -1){
                JOptionPane.showMessageDialog(null, "Nie wybrano efektu do wyświetlenia", "Uwaga!",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                new DescriptFrame(effId);
            }
        });

        //przycisk usun dawke lub opis
        patientDataPanel.setDelBtnListener(() -> {
            if(dosId != -1){
                myService.deleteDose(dosId);
            }
            if(effId != -1){
                myService.deleteEffect(effId);
            }
            else {
                JOptionPane.showMessageDialog(null,"Nie wybrano elementu do usunięcia","Ważne!",JOptionPane.INFORMATION_MESSAGE);
            }
            patientDataPanel.setDataEffect(myService.getAllPatientEffects(patId));
            patientDataPanel.setDataDose(myService.getAllPatientDoses(patId));
            add(patientDataPanel, BorderLayout.CENTER);
        });

        //anuluj
        patientDataPanel.setCloseBtnListener(() -> {
            patientDataPanel.setVisible(false);
            add(patientsPanel, BorderLayout.CENTER);
            patientsPanel.setVisible(true);
        });

        /// klikniecie dawki
        patientDataPanel.setDoseTableSelectionListener(d -> {
            setDosId(d.getId());
        });

        /// klikniecie efektu
        patientDataPanel.setEffectTableSelectionListener(e -> {
            setEffId(e.getId());
        });

////////////////////// przycisk wyswietl lekarzy///////////////////////////////////////////////
        buttonPanel.setPhysiciansListener(() -> {
            if (formPanel.isVisible()) {
                formPanel.setVisible(false);
            }
            if (patientsPanel.isVisible()) {
                patientsPanel.setVisible(false);
            }
            if (imageLabel.isVisible()) {
                imageLabel.setVisible(false);
            }
            if (dosagePanel.isVisible()) {
                dosagePanel.setVisible(false);
            }
            if (effectsPanel.isVisible()) {
                effectsPanel.setVisible(false);
            }
            if (patientDataPanel.isVisible()) {
                patientDataPanel.setVisible(false);
            }
            if(userMode == false){

                if (formPanelD.isVisible()) {
                    formPanelD.setVisible(false);
                }
            }
            if (formPanelL.isVisible()) {
                formPanelL.setVisible(false);
            }
            if (formPanelE.isVisible()) {
                formPanelE.setVisible(false);
            }
            doctorsPanel.setData(myService.getAllDoctor());
            add(doctorsPanel, BorderLayout.CENTER);
            doctorsPanel.setVisible(true);
            setPatId(-1);
            setDocId(-1);
            setDosId(-1);
            setEffId(-1);
        });

        // przycisk w lekarzach show
        doctorsPanel.setShowBtnListener(() -> {
            if(docId != -1){
                doctorsPanel.setVisible(false);
                patientsPanel.setData(myService.getAllDoctorPatients(docId));
                add(patientsPanel, BorderLayout.CENTER);
                patientsPanel.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null,"Nie wybrano lekarza","Ważne!",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // przycisk w lekarzach usuń
        doctorsPanel.setDelBtnListener(() -> {
            if(docId != -1){
                myService.deleteDoctor(docId);
                doctorsPanel.setData(myService.getAllDoctor());
                add(doctorsPanel, BorderLayout.CENTER);
            } else {
                JOptionPane.showMessageDialog(null,"Nie wybrano lekarza","Ważne!",JOptionPane.INFORMATION_MESSAGE);
            }
        });


        // przycisk w lekarzach zamknij
        doctorsPanel.setCloseBtnListener(() -> {
            doctorsPanel.setVisible(false);
            add(imageLabel, BorderLayout.CENTER);
            imageLabel.setVisible(true);
        });

        /// klikniecie lekarzy w tabli
        doctorsPanel.setDoctorTableSelectionListener(d -> {
            setDocId(d.getId());
        });

//////////////////// przycisk wyswietl dawki/////////////////////////////////////////////
        buttonPanel.setDosageListener(() -> {
            if (formPanel.isVisible()) {
                formPanel.setVisible(false);
            }
            if (patientsPanel.isVisible()) {
                patientsPanel.setVisible(false);
            }
            if (doctorsPanel.isVisible()) {
                doctorsPanel.setVisible(false);
            }
            if (imageLabel.isVisible()) {
                imageLabel.setVisible(false);
            }
            if (effectsPanel.isVisible()) {
                effectsPanel.setVisible(false);
            }
            if (patientDataPanel.isVisible()) {
                patientDataPanel.setVisible(false);
            }
            if(userMode == false){

                if (formPanelD.isVisible()) {
                    formPanelD.setVisible(false);
                }
            }
            if (formPanelL.isVisible()) {
                formPanelL.setVisible(false);
            }
            if (formPanelE.isVisible()) {
                formPanelE.setVisible(false);
            }
            dosagePanel.setBoxes(myService.getAllDoctor(), myService.getAllPatient());
            dosagePanel.setData(myService.getAllDose());
            add(dosagePanel, BorderLayout.CENTER);
            dosagePanel.setVisible(true);
            setPatId(-1);
            setDocId(-1);
            setDosId(-1);
            setEffId(-1);

        });

        // przycisk w dawkach filtruj
        dosagePanel.setFiltrBtnListener(() -> {
            setDosId(-1);
            dosagePanel.setVisible(false);
            setModeDose(dosagePanel.getModeDose());
            if(modeDose == 1){
                if (dosagePanel.getPatientIdFromBox() !=null) {
                    setPatId(dosagePanel.getPatientIdFromBox());
                    dosagePanel.setData(myService.getAllPatientDoses(patId));
                    Patient p = myService.findByIdPatient(patId);
                    Doctor d = null;
                    dosagePanel.setTitleData(p,d);
                }
            }
            //wybrano tylko lekarza
            else if(modeDose == 2){
                if (dosagePanel.getDoctorIdFromBox() != null){
                    setDocId(dosagePanel.getDoctorIdFromBox());
                    dosagePanel.setData(myService.getAllDoctorDoses(docId));
                    Doctor d = myService.findByIdDoctor(docId);
                    Patient p = null;
                    dosagePanel.setTitleData(p,d);
                }
            }
            //wybrano oacjenta i lekarza
            else if(modeDose == 3){
                if(dosagePanel.getPatientIdFromBox() !=null && dosagePanel.getDoctorIdFromBox() != null) {
                    setPatId(dosagePanel.getPatientIdFromBox());
                    setDocId(dosagePanel.getDoctorIdFromBox());
                    dosagePanel.setData(myService.getAllDosesFromPatientAndDoctor(patId, docId));
                    Patient p = myService.findByIdPatient(patId);
                    Doctor d = myService.findByIdDoctor(docId);
                    dosagePanel.setTitleData(p,d);
                }
            }
            ///nie wyvbrano nikogo
            else {
                dosagePanel.setData(myService.getAllDose());
                Patient p =null;
                Doctor d = null;
                dosagePanel.setTitleData(p,d);
            }
            dosagePanel.clearDosePanel();
            add(dosagePanel, BorderLayout.CENTER);
            dosagePanel.setVisible(true);
        });

        // przycisk w lekarzach usuń
        dosagePanel.setDelBtnListener(() -> {
            if(dosId != -1){
                myService.deleteDose(dosId);
                dosagePanel.setData(myService.getAllDose());
                add(dosagePanel, BorderLayout.CENTER);
            } else {
                JOptionPane.showMessageDialog(null,"Nie wybrano dawki","Ważne!",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // przycisk w dawkach zamknij
        dosagePanel.setCloseBtnListener(() -> {
            dosagePanel.clearDosePanel();
            dosagePanel.setVisible(false);
            add(imageLabel, BorderLayout.CENTER);
            imageLabel.setVisible(true);
        });

        /// klikniecie dawki w tabeli
        dosagePanel.setDoseTableSelectionListener(d -> {
            setDosId(d.getId());
        });

///////////////////////// przycisk efektów/////////////////////////////////////////
        buttonPanel.setEffectListener(() -> {
            if (formPanel.isVisible()) {
                formPanel.setVisible(false);
            }
            if (patientsPanel.isVisible()) {
                patientsPanel.setVisible(false);
            }
            if (doctorsPanel.isVisible()) {
                doctorsPanel.setVisible(false);
            }
            if (imageLabel.isVisible()) {
                imageLabel.setVisible(false);
            }
            if (dosagePanel.isVisible()) {
                dosagePanel.setVisible(false);
            }
            if (patientDataPanel.isVisible()) {
                patientDataPanel.setVisible(false);
            }
            if(userMode == false){

                if (formPanelD.isVisible()) {
                    formPanelD.setVisible(false);
                }
            }
            if (formPanelL.isVisible()) {
                formPanelL.setVisible(false);
            }
            if (formPanelE.isVisible()) {
                formPanelE.setVisible(false);
            }
            effectsPanel.setBoxes(myService.getAllDoctor(), myService.getAllPatient());
            effectsPanel.setData(myService.getAllEffect());
            add(effectsPanel, BorderLayout.CENTER);
            effectsPanel.setVisible(true);
            setPatId(-1);
            setDocId(-1);
            setDosId(-1);
            setEffId(-1);

        });

        // przycisk w efektach  filtruj
        effectsPanel.setFiltrBtnListener(() -> {
            setEffId(-1);
            effectsPanel.setVisible(false);
            setModeEffect(effectsPanel.getModeEffect());
            if(modeEffect == 1){
                if (effectsPanel.getPatientIdFromBox() !=null) {
                    setPatId(effectsPanel.getPatientIdFromBox());
                    effectsPanel.setData(myService.getAllPatientEffects(patId));
                    Patient p = myService.findByIdPatient(patId);
                    Doctor d = null;
                    effectsPanel.setTitleData(p,d);
                }
            }
            //wybrano tylko lekarza
            else if(modeEffect == 2){
                if (effectsPanel.getDoctorIdFromBox() != null){
                    setDocId(effectsPanel.getDoctorIdFromBox());
                    effectsPanel.setData(myService.getAllDoctorEffects(docId));
                    Patient p = null;
                    Doctor d = myService.findByIdDoctor(docId);
                    effectsPanel.setTitleData(p,d);
                }
            }
            //wybrano oacjenta i lekarza
            else if(modeEffect == 3){
                if(effectsPanel.getPatientIdFromBox() !=null && effectsPanel.getDoctorIdFromBox() != null) {
                    setPatId(effectsPanel.getPatientIdFromBox());
                    setDocId(effectsPanel.getDoctorIdFromBox());
                    effectsPanel.setData(myService.getAllEffectsFromPatientAndDoctor(patId, docId));
                    Patient p = myService.findByIdPatient(patId);
                    Doctor d = myService.findByIdDoctor(docId);
                    effectsPanel.setTitleData(p,d);
                }
            }
            ///nie wybrano nikogo
            else {
                effectsPanel.setData(myService.getAllEffect());
                Patient p = null;
                Doctor d = null;
                effectsPanel.setTitleData(p,d);
            }
            effectsPanel.clearEffectPanel();
            add(effectsPanel, BorderLayout.CENTER);
            effectsPanel.setVisible(true);
        });
        // przycisk w efektach usuń
        effectsPanel.setDelBtnListener(() -> {
            if(effId != -1){
                myService.deleteEffect(effId);
                effectsPanel.setData(myService.getAllEffect());
                add(effectsPanel, BorderLayout.CENTER);
            } else {
                JOptionPane.showMessageDialog(null,"Nie wybrano opisu","Ważne!",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        // przycisk w efektach zamknij////
        effectsPanel.setCloseBtnListener(() -> {
            effectsPanel.setVisible(false);
            add(imageLabel, BorderLayout.CENTER);
            imageLabel.setVisible(true);
        });

        // przycisk w efektach pokaż opis////
        effectsPanel.setShowBtnListener(() -> {
            if (effId == -1){
                JOptionPane.showMessageDialog(null, "Nie wybrano efektu do wyświetlenia", "Uwaga!",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                new DescriptFrame(effId);
            }
        });

        // klikniecie opisów w tabeli
        effectsPanel.setEffectTableSelectionListener(e -> {
            setEffId(e.getId());
        });

///////////////////////dodaj lekarza////////////////////////
        if(userMode == false){
            buttonPanel.setAddDoctorListener(() -> {
                if (formPanel.isVisible()) {
                    formPanel.setVisible(false);
                }
                if (patientsPanel.isVisible()) {
                    patientsPanel.setVisible(false);
                }
                if (doctorsPanel.isVisible()) {
                    doctorsPanel.setVisible(false);
                }
                if (imageLabel.isVisible()) {
                    imageLabel.setVisible(false);
                }
                if (dosagePanel.isVisible()) {
                    dosagePanel.setVisible(false);
                }
                if (patientDataPanel.isVisible()) {
                    patientDataPanel.setVisible(false);
                }
                if (formPanelL.isVisible()) {
                    formPanelL.setVisible(false);
                }
                if (formPanelE.isVisible()) {
                    formPanelE.setVisible(false);
                }
                if (effectsPanel.isVisible()) {
                    effectsPanel.setVisible(false);
                }
                add(formPanelD, BorderLayout.CENTER);
                formPanelD.setVisible(true);
            });

            ////przycisk zapisz nowego lekarza
            formPanelD.setFormSaveBtnListener(() -> {
                if(formPanelD.checkDoctorData() == true){
                    Doctor d = formPanelD.getDoctorFromForm();
                    int help = 0;
                    if (doctorRepository.existsByPwz(d.getMedicalLicence()) == false && formPanelD.checkPWZ() == true && formPanelD.checkPhoneNr() == true) {
                        help = 1;
                        myService.addDoctor(d);
                        formPanelD.clearFormPanelD();
                        formPanelD.setVisible(false);
                    }
                    if (doctorRepository.existsByPwz(d.getMedicalLicence()) == true && help != 1){
                        JOptionPane.showMessageDialog(null, "Doktor o podanym numerze PWZ istnieje już w bazie", "Uwaga!",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    if (formPanelD.checkPWZ() == false && help != 1){
                        JOptionPane.showMessageDialog(null, "Wprowadzono niepoprawny numer PWZ", "Uwaga!",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    if (formPanelD.checkPhoneNr() == false && help != 1){
                        JOptionPane.showMessageDialog(null, "Wprowadzono niepoprawny numer telefonu", "Uwaga!",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nie wprowadzono wszystkich danych" , "Uwaga!",
                            JOptionPane.INFORMATION_MESSAGE);
                }


            });

            /// przycisk anuluj
            formPanelD.setFormCloseBtnListener(() -> {
                formPanelD.clearFormPanelD();
                formPanelD.setVisible(false);
            });
        }

//////////////// /////////////////koniec przycisków ////////////////////////////////////////////////

        add(buttonPanel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);

        imageLabel.setVisible(true);
        formPanel.setVisible(false);

        if(userMode == false){
            formPanelD.setVisible(false);
        }

        formPanelL.setVisible(false);
        formPanelE.setVisible(false);
        doctorsPanel.setVisible(false);
        patientsPanel.setVisible(false);
        dosagePanel.setVisible(false);
        patientDataPanel.setVisible(false);
        effectsPanel.setVisible(false);

        setSize(1000, 700);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void setPatId(int patId) {
        this.patId = patId;
    }

    public int getPatId() {
        return patId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public void setDosId(int dosId) {
        this.dosId = dosId;
    }

    public void setEffId(int effId) {
        this.effId = effId;
    }

    public void setModeDose(int modeDose) {
        this.modeDose = modeDose;
    }

    public void setModeEffect(Integer modeEffect) {
        this.modeEffect = modeEffect;
    }

    public void setModeComeBackL(Integer modeComeBackL) {
        this.modeComeBackL = modeComeBackL;
    }

    public void setModeComeBackE(Integer modeComeBackE) {
        this.modeComeBackE = modeComeBackE;
    }


}

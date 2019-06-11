package com.app.repository;

import com.app.connectrion.DbConnection;
import com.app.connectrion.DbStatus;
import com.app.connectrion.DbTables;
import com.app.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class PatientRepositoryImpl implements PatientRepository  {
    private Connection connection = DbConnection.getInstance().getConnection();
    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public DbStatus add(Patient patient) {
        PreparedStatement preparedStatement = null;
        try {
            final String sql = String.format("INSERT INTO %s(name, surname, pesel, placebo, birthday, gender, doctorId) " +
                            "VALUES (?,?,?,?,?,?,?)",
                    DbTables.Patient);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,patient.getName());
            preparedStatement.setString(2,patient.getSurname());
            preparedStatement.setString(3,patient.getPesel());
            preparedStatement.setBoolean(4,patient.getPlacebo());
            preparedStatement.setString(5,patient.getBirthday());
            preparedStatement.setString(6,patient.getGender().toString());
            preparedStatement.setInt(7,patient.getDoctorId());
            preparedStatement.execute();
            System.out.println("DODANO");
        } catch (Exception e) {
            logger.error("ERROR MESSAGE - BRAK POLACZENIA Z BAZA DANYCH");
            return DbStatus.ERROR;
        }
        finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return DbStatus.OK;
    }

    @Override
    public DbStatus update(Patient patient) {
        PreparedStatement preparedStatement = null;
        try {
            final String sql = String.format("UPDATE %s SET name = ?, surname = ?, pesel = ?, placebo = ?, birthday = ?, gender = ?, doctor = ?" +
                            "WHERE id = ?",
                    DbTables.Patient);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,patient.getName());
            preparedStatement.setString(2,patient.getSurname());
            preparedStatement.setString(3,patient.getPesel());
            preparedStatement.setBoolean(4,patient.getPlacebo());
            preparedStatement.setString(5,patient.getBirthday());
            preparedStatement.setString(6,patient.getGender().toString());
            preparedStatement.setInt(7,patient.getDoctorId());
            preparedStatement.setInt(8, patient.getId());
            preparedStatement.execute();
            System.out.println("ZEDYTOWANO");
        } catch (Exception e) {
            logger.error("ERROR MESSAGE - BRAK POLACZENIA Z BAZA DANYCH");
            return DbStatus.ERROR;
        }
        finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return DbStatus.OK;
    }

    @Override
    public DbStatus delete(Integer id) {
        PreparedStatement preparedStatement = null;
        try {
            final String sql = String.format("DELETE FROM %s WHERE id = ?", DbTables.Patient);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
            System.out.println("USUNIETO");
        } catch (Exception e) {
            logger.error("ERROR MESSAGE - BRAK POLACZENIA Z BAZA DANYCH");
            return DbStatus.ERROR;
        }
        finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return DbStatus.OK;
    }

    @Override
    public Patient findById(Integer id) {
        Patient patientOptional = null;
        PreparedStatement preparedStatement = null;
        try {
            final String sql = String.format("SELECT * FROM %s WHERE id = ?",
                    DbTables.Patient);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                patientOptional =
                        Patient
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .surname(resultSet.getString(3))
                                .pesel(resultSet.getString(4))
                                .placebo(resultSet.getBoolean(5))
                                .birthday(resultSet.getString(6))
                                .gender(resultSet.getString(7))
                                .doctorId(resultSet.getInt(8))
                                .build();
            }
        } catch (Exception e) {
            logger.error("ERROR MESSAGE - BRAK POLACZENIA Z BAZA DANYCH");
        }
        finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (patientOptional == null){
            logger.info("Zbior pusty");
        }
        return patientOptional;
    }

    @Override
    public List<Patient> findByDoctor(Integer doctorId) {
        List<Patient> patients = null;
        PreparedStatement preparedStatement = null;
        try {
            final String sql = String.format("SELECT * FROM %s WHERE doctorId = ?",
                    DbTables.Patient);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, doctorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            patients = new ArrayList<>();
            while(resultSet.next()){
                patients.add(
                        Patient.builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .surname(resultSet.getString(3))
                                .pesel(resultSet.getString(4))
                                .placebo(resultSet.getBoolean(5))
                                .birthday(resultSet.getString(6))
                                .gender(resultSet.getString(7))
                                .doctorId(resultSet.getInt(8))
                                .build()
                );
            }
        } catch (Exception e) {
            logger.error("ERROR MESSAGE - BRAK POLACZENIA Z BAZA DANYCH");
        }
        finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (patients == null) {
            logger.info("Zbior pusty");
        }
        return patients;
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = null;
        Statement statement = null;
        try {
            final String sql = String.format("SELECT * FROM %s",
                    DbTables.Patient);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            patients = new ArrayList<>();
            while(resultSet.next())
            {
                patients.add(
                        Patient.builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .surname(resultSet.getString(3))
                                .pesel(resultSet.getString(4))
                                .placebo(resultSet.getBoolean(5))
                                .birthday(resultSet.getString(6))
                                .gender(resultSet.getString(7))
                                .doctorId(resultSet.getInt(8))
                                .build()
                );
            }
        } catch (Exception e) {
            logger.error("ERROR MESSAGE - BRAK POLACZENIA Z BAZA DANYCH");
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (patients == null){
            logger.info("Zbior pusty");
        }
        return patients;
    }

    public boolean existsByPesel(String pesel) {
        List<Patient> patients = findAll();
        return patients.stream().anyMatch(patient -> patient.getPesel().equals(pesel));
    }
}

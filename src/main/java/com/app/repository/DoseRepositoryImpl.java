package com.app.repository;

import com.app.connectrion.DbConnection;
import com.app.connectrion.DbStatus;
import com.app.connectrion.DbTables;
import com.app.model.Dose;
import com.app.model.Patient;
import com.app.model.Doctor;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoseRepositoryImpl implements DoseRepository{

    private Connection connection = DbConnection.getInstance().getConnection();
    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public DbStatus add(Dose dose) {
        PreparedStatement preparedStatement = null;
        try {
            final String sql = String.format("INSERT INTO %s(doseValue, dateDose, patientId, doctorId) VALUES(?,?,?,?) ",
                    DbTables.Dose);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1,dose.getDoseValue());
            preparedStatement.setString(2,dose.getDateDose());
            preparedStatement.setInt(3,dose.getPatientId());
            preparedStatement.setInt(4,dose.getDoctorId());
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
    public DbStatus update(Dose dose) {
        PreparedStatement preparedStatement = null;
        try {
            final String sql = String.format("UPDATE %s SET doseValue = ?, dateDose = ?, patientId = ?, doctorId = ? WHERE id = ?"
                    ,DbTables.Dose);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1,dose.getDoseValue());
            preparedStatement.setString(2,dose.getDateDose());
            preparedStatement.setInt(3,dose.getPatientId());
            preparedStatement.setInt(4,dose.getDoctorId());
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
            final String sql = String.format("DELETE FROM %s WHERE id = ?"
                    ,DbTables.Dose);
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
    public Dose findById(Integer id) {
        Dose optionalDose = null;
        PreparedStatement preparedStatement = null;
        try {
            final String sql = String.format("SELECT * FROM %s WHERE id = ?"
                    ,DbTables.Dose);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                optionalDose =
                        Dose
                                .builder()
                                .id(resultSet.getInt(1))
                                .doseValue(resultSet.getDouble(2))
                                .dateDose(resultSet.getString(3))
                                .patientId(resultSet.getInt(4))
                                .doctorId(resultSet.getInt(5))
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
        if (optionalDose == null){
            logger.info("Zbior pusty");
        }
        return optionalDose;
    }

    @Override
    public List<Dose> findByPatient(Integer patientId) {
        List<Dose> dosage = null;
        PreparedStatement preparedStatement = null;
        try {
            final String sql = String.format("SELECT * FROM %s WHERE patientId = ?",
                    DbTables.Dose);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,patientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            dosage = new ArrayList<>();
            while(resultSet.next())  {
                dosage.add(
                        Dose
                                .builder()
                                .id(resultSet.getInt(1))
                                .doseValue(resultSet.getDouble(2))
                                .dateDose(resultSet.getString(3))
                                .patientId(resultSet.getInt(4))
                                .doctorId(resultSet.getInt(5))
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
        if (dosage == null) {
            logger.info("Zbior pusty");
        }
        return dosage;
    }

    @Override
    public List<Dose> findByDoctor(Integer doctorId) {
        List<Dose> dosage = null;
        PreparedStatement preparedStatement = null;
        try {
            final String sql = String.format("SELECT * FROM %s WHERE doctorId = ?",
                    DbTables.Dose);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,doctorId);
            ResultSet resultSet = preparedStatement.executeQuery();

            dosage = new ArrayList<>();
            while(resultSet.next()){
                dosage.add(
                        Dose
                                .builder()
                                .id(resultSet.getInt(1))
                                .doseValue(resultSet.getDouble(2))
                                .dateDose(resultSet.getString(3))
                                .patientId(resultSet.getInt(4))
                                .doctorId(resultSet.getInt(5))
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
        if (dosage == null){
            logger.info("Zbior pusty");
        }
        return dosage;
    }
    @Override
    public List<Dose> findByPatientAndDoctor(Integer patientId,Integer doctorId) {
        List<Dose> dosage = null;
        PreparedStatement preparedStatement = null;
        try {
            final String sql = String.format("SELECT * FROM %s WHERE patientId = ? AND doctorId = ?",
                    DbTables.Dose);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,patientId);
            preparedStatement.setInt(2,doctorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            dosage = new ArrayList<>();
            while(resultSet.next()){
                dosage.add(
                        Dose
                                .builder()
                                .id(resultSet.getInt(1))
                                .doseValue(resultSet.getDouble(2))
                                .dateDose(resultSet.getString(3))
                                .patientId(resultSet.getInt(4))
                                .doctorId(resultSet.getInt(5))
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
        if (dosage == null){
            logger.info("Zbior pusty");
        }
        return dosage;
    }

    @Override
    public List<Dose> findAll() {
        List<Dose> dosage = null;
        Statement statement = null;
        try {
            final String sql = String.format("SELECT * FROM %s",
                    DbTables.Dose);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            dosage = new ArrayList<>();
            while(resultSet.next()) {
                dosage.add(
                        Dose
                                .builder()
                                .id(resultSet.getInt(1))
                                .doseValue(resultSet.getDouble(2))
                                .dateDose(resultSet.getString(3))
                                .patientId(resultSet.getInt(4))
                                .doctorId(resultSet.getInt(5))
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
        if (dosage == null) {
            logger.info("Zbior pusty");
        }
        return dosage;
    }
}

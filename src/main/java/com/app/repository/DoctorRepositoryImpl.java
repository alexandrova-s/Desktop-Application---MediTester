package com.app.repository;

import com.app.connectrion.DbConnection;
import com.app.connectrion.DbStatus;
import com.app.connectrion.DbTables;
import com.app.model.Doctor;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoctorRepositoryImpl implements DoctorRepository  {

    private Connection connection = DbConnection.getInstance().getConnection();
    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public DbStatus add(Doctor doctor) {
        PreparedStatement preparedStatement = null;
        try {
            final String sql = String.format("INSERT INTO %s(name,surname,medicalLicence,phoneNumber) VALUES(?,?,?,?) ",
                    DbTables.Doctor);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,doctor.getName());
            preparedStatement.setString(2,doctor.getSurname());
            preparedStatement.setString(3,doctor.getMedicalLicence());
            preparedStatement.setString(4,doctor.getPhoneNumber());
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
    public DbStatus update(Doctor doctor) {
        PreparedStatement preparedStatement = null;
        try {
            final String sql = String.format("UPDATE %s SET name = ?, surname = ?, medicalLicence = ?, phoneNumber = ? WHERE id = ?"
                    ,DbTables.Doctor);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,doctor.getName());
            preparedStatement.setString(2,doctor.getSurname());
            preparedStatement.setString(3,doctor.getMedicalLicence());
            preparedStatement.setString(4,doctor.getPhoneNumber());
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
                    ,DbTables.Doctor);
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
    public Doctor findById(Integer id) {
        Doctor optionalDoctor = null;
        PreparedStatement preparedStatement = null;
        try {
            final String sql = String.format("SELECT * FROM %s WHERE id = ?"
                    ,DbTables.Doctor);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                optionalDoctor =
                        Doctor
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .surname(resultSet.getString(3))
                                .medicalLicence(resultSet.getString(4))
                                .phoneNumber(resultSet.getString(5))
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
        if (optionalDoctor == null) {
            logger.info("Zbior pusty");
        }
        return optionalDoctor;
    }

    @Override
    public List<Doctor> findAll() {
        List<Doctor> doctors = null;
        Statement statement = null;
        try {
            final String sql = String.format("SELECT * FROM %s",
                    DbTables.Doctor);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            doctors = new ArrayList<>();
            while(resultSet.next()){
                doctors.add(
                        Doctor
                                .builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .surname(resultSet.getString(3))
                                .medicalLicence(resultSet.getString(4))
                                .phoneNumber(resultSet.getString(5))
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
        if (doctors == null){
            logger.info("Zbior pusty");
        }
        return doctors;
    }

    @Override
    public List<Integer> findAllId() {
        List<Integer> doctorsId = null;
        Statement statement = null;
        try {
            final String sql = String.format("SELECT * FROM %s",
                    DbTables.Doctor);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            doctorsId = new ArrayList<>();
            while(resultSet.next()){
                doctorsId.add(resultSet.getInt(1));
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
        if (doctorsId == null){
            logger.info("Zbior pusty");
        }
        return doctorsId;
    }

    public boolean existsByPwz(String medicalLicence) {
        List<Doctor> doctors = findAll();
        return doctors.stream().anyMatch(patient -> patient.getMedicalLicence().equals(medicalLicence));
    }


}


package com.app.repository;

import com.app.connectrion.DbConnection;
import com.app.connectrion.DbStatus;
import com.app.connectrion.DbTables;
import com.app.model.Effect;
import com.app.model.Patient;
import com.app.model.Doctor;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EffectRepositoryImpl implements EffectRepository {
    private Connection connection = DbConnection.getInstance().getConnection();
    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public DbStatus add(Effect effect) {
        PreparedStatement preparedStatement = null;
        try {
            final String sql = String.format("INSERT INTO %s(description, dateEffect, patientId, doctorId) VALUES(?,?,?,?) ",
                    DbTables.Effect);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,effect.getDescription());
            preparedStatement.setString(2,effect.getDateEffect());
            preparedStatement.setInt(3,effect.getPatientId());
            preparedStatement.setInt(4,effect.getDoctorId());
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
    public DbStatus update(Effect effect) {
        PreparedStatement preparedStatement = null;
        try {
            final String sql = String.format("UPDATE %s SET description = ?, dateEffect = ?, patientId = ?, doctorId = ?  WHERE id = ?"
                    ,DbTables.Effect);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,effect.getDescription());
            preparedStatement.setString(2,effect.getDateEffect());
            preparedStatement.setInt(3,effect.getPatientId());
            preparedStatement.setInt(4,effect.getDoctorId());
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
                    ,DbTables.Effect);
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
    public Effect findById(Integer id) {
        Effect optionalEffect = null;
        PreparedStatement preparedStatement = null;
        try {
            final String sql = String.format("SELECT * FROM %s WHERE id = ?"
                    ,DbTables.Effect);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                optionalEffect =
                        Effect
                                .builder()
                                .id(resultSet.getInt(1))
                                .description(resultSet.getString(2))
                                .dateEffect(resultSet.getString(3))
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
        if (optionalEffect == null) {
            logger.info("Zbior pusty");
        }
        return optionalEffect;
    }
    @Override
    public List<Effect> findByPatient(Integer patientId) {
        List<Effect>effects = null;
        PreparedStatement preparedStatement = null;
        try {
            final String sql = String.format("SELECT * FROM %s WHERE patientId = ?",
                    DbTables.Effect);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,patientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            effects = new ArrayList<>();
            while(resultSet.next()){
                effects.add(
                        Effect
                                .builder()
                                .id(resultSet.getInt(1))
                                .description(resultSet.getString(2))
                                .dateEffect(resultSet.getString(3))
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
        if (effects == null){
            logger.info("Zbior pusty");
        }
        return effects;
    }

    @Override
    public List<Effect> findByDoctor(Integer doctorId) {
        List<Effect> effects = null;
        PreparedStatement preparedStatement = null;
        try {
            final String sql = String.format("SELECT * FROM %s WHERE doctorId = ?",
                    DbTables.Effect);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,doctorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            effects = new ArrayList<>();
            while(resultSet.next()) {
                effects.add(
                        Effect
                                .builder()
                                .id(resultSet.getInt(1))
                                .description(resultSet.getString(2))
                                .dateEffect(resultSet.getString(3))
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
        if (effects == null) {
            logger.info("Zbior pusty");
        }
        return effects;
    }

    @Override
    public List<Effect> findByPatientAndDoctor(Integer patientId,Integer doctorId) {
        List<Effect>effects = null;
        PreparedStatement preparedStatement = null;
        try {
            final String sql = String.format("SELECT * FROM %s WHERE patientId = ? AND doctorId = ?",
                    DbTables.Effect);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,patientId);
            preparedStatement.setInt(2,doctorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            effects = new ArrayList<>();
            while(resultSet.next()){
                effects.add(
                        Effect
                                .builder()
                                .id(resultSet.getInt(1))
                                .description(resultSet.getString(2))
                                .dateEffect(resultSet.getString(3))
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
        if (effects == null) {
            logger.info("Zbior pusty");
        }
        return effects;
    }


    @Override
    public List<Effect> findAll() {
        List<Effect> effects = null;
        Statement statement = null;
        try {
            final String sql = String.format("SELECT * FROM %s",
                    DbTables.Effect);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            effects = new ArrayList<>();
            while(resultSet.next()) {
                effects.add(
                        Effect
                                .builder()
                                .id(resultSet.getInt(1))
                                .description(resultSet.getString(2))
                                .dateEffect(resultSet.getString(3))
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
        if (effects == null) {
            logger.info("Zbior pusty");
        }
        return effects;
    }
}

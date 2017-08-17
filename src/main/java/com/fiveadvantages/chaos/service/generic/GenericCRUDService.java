package com.fiveadvantages.chaos.service.generic;

import com.fiveadvantages.chaos.datalayer.dto.CuponDTO;
import com.fiveadvantages.chaos.datalayer.dto.DTO;
import com.fiveadvantages.chaos.datalayer.entity.Entity;
import com.fiveadvantages.chaos.util.Util;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import io.advantageous.boon.core.reflection.BeanUtils;
import io.advantageous.qbit.annotation.PathVariable;

import java.io.Serializable;
import java.util.List;

/**
 * @author Foncho A.K.A. Papi polvora
 */
public class GenericCRUDService<K extends Entity,T extends DTO,ID extends Serializable> {
    private final static String DATABASE_URL = "jdbc:postgresql://localhost:5432/chaos";
    private final static String DATABASE_USER = "postgres";
    private final static String DATABASE_PASSWORD = "root";
    final protected  Class<K> entityClass;
    final protected  Class<T> dtoClass;
    private Dao<K, ID> genericDAO;

    public GenericCRUDService(Class<K> entityClass, Class<T> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

     public List<T> listAll() {
        ConnectionSource connectionSource = null;
        List<T> lista = null;
        try {
            connectionSource = new JdbcPooledConnectionSource( DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);
            this.genericDAO = DaoManager.createDao(connectionSource, entityClass);
            lista = Util.CopyCollectionProperties(this.genericDAO.queryForAll(),dtoClass);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connectionSource != null) {
                try {
                    connectionSource.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return lista;
    }

    public T getById(@PathVariable("id") ID id) {
        ConnectionSource connectionSource = null;
        T dto = null;
        try {
            dto = dtoClass.newInstance();
            connectionSource = new JdbcPooledConnectionSource ( DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);
            this.genericDAO = DaoManager.createDao(connectionSource, entityClass);
            BeanUtils.copyProperties(genericDAO.queryForId(id),dto);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connectionSource != null) {
                try {
                    connectionSource.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return dto;
    }

    public T create(final T dto) {
        ConnectionSource connectionSource = null;

        try {
            K entity = entityClass.newInstance();
            connectionSource = new JdbcPooledConnectionSource (DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);
            this.genericDAO = DaoManager.createDao(connectionSource, entityClass);
            BeanUtils.copyProperties(dto,entity);
            BeanUtils.copyProperties(genericDAO.createIfNotExists(entity),dto);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connectionSource != null) {
                try {
                    connectionSource.close();
                } catch (Exception e) {
                    e.printStackTrace();
            }
            }
        }
        return dto;
    }

    public T updateById(@PathVariable("id") ID id,final T dto) {
        ConnectionSource connectionSource = null;
        System.out.println("ID : "+id);
        try {
            connectionSource = new JdbcPooledConnectionSource ( DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);
            this.genericDAO = DaoManager.createDao(connectionSource, entityClass);
            K entity = genericDAO.queryForId(id);
            BeanUtils.copyProperties(dto,entity);
            genericDAO.update(entity);
            BeanUtils.copyProperties(entity,dto);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connectionSource != null) {
                try {
                    connectionSource.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return dto;
    }

    public String deleteById(@PathVariable("id") ID id) {
        ConnectionSource connectionSource = null;
        String response = "Elemento eliminado con exito";
        try {
            connectionSource = new JdbcPooledConnectionSource ( DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);
            this.genericDAO = DaoManager.createDao(connectionSource, entityClass);
            this.genericDAO.deleteById(id);
        } catch (Exception e) {
            response = "Ocurrio un error al intentar eliminar";
            e.printStackTrace();
        } finally {
            if (connectionSource != null) {
                try {
                    connectionSource.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }
}

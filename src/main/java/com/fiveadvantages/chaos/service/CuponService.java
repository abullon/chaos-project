package com.fiveadvantages.chaos.service;

/**
 * @author Foncho A.K.A. Papi polvora
 */

import ch.qos.logback.classic.Logger;
import com.fiveadvantages.chaos.datalayer.dto.CuponDTO;
import com.fiveadvantages.chaos.datalayer.entity.Cupon;
import com.fiveadvantages.chaos.util.Util;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import io.advantageous.boon.core.reflection.BeanUtils;
import io.advantageous.qbit.annotation.PathVariable;
import io.advantageous.qbit.annotation.RequestMapping;
import io.advantageous.qbit.annotation.RequestMethod;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.List;

@RequestMapping("/cupones")
public class CuponService {

    private Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    private final static String DATABASE_URL = "jdbc:postgresql://localhost:5432/chaos";
    private final static String DATABASE_USER = "postgres";
    private final static String DATABASE_PASSWORD = "root";
    private DateFormat format;
    private Dao<Cupon, String> cuponDAO;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<CuponDTO> listAll() {
        System.out.println("Entre al metodo 1");
        ConnectionSource connectionSource = null;
        List<CuponDTO> lista = null;
        try {
            connectionSource = new JdbcPooledConnectionSource ( DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);
            cuponDAO = DaoManager.createDao(connectionSource, Cupon.class);
            lista = Util.CopyCollectionProperties(cuponDAO.queryForAll(),CuponDTO.class);
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CuponDTO getById(@PathVariable("id") int id) {
        ConnectionSource connectionSource = null;
        CuponDTO cupon = new CuponDTO();
        try {
            connectionSource = new JdbcPooledConnectionSource ( DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);
            cuponDAO = DaoManager.createDao(connectionSource, Cupon.class);
            BeanUtils.copyProperties(cuponDAO.queryForId(String.valueOf(id)),cupon);
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
        return cupon;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public CuponDTO create(final CuponDTO cupon) {
        ConnectionSource connectionSource = null;
        Cupon cuponEntity = new Cupon();
        try {
            connectionSource = new JdbcPooledConnectionSource ( DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);
            cuponDAO = DaoManager.createDao(connectionSource, Cupon.class);
            BeanUtils.copyProperties(cupon,cuponEntity);
            BeanUtils.copyProperties(cuponDAO.createIfNotExists(cuponEntity),cupon);
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
        return cupon;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public CuponDTO updateById(@PathVariable("id") int id,final CuponDTO cupon) {
    ConnectionSource connectionSource = null;
        System.out.println("ID : "+id);
        try {
            connectionSource = new JdbcPooledConnectionSource ( DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);
            cuponDAO = DaoManager.createDao(connectionSource, Cupon.class);
            Cupon c = cuponDAO.queryForId(String.valueOf(id));
            BeanUtils.copyProperties(cupon,c);
            cuponDAO.update(c);
            BeanUtils.copyProperties(c,cupon);
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
        return cupon;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteById(@PathVariable("id") int id) {
        ConnectionSource connectionSource = null;
        String response = "Elemento eliminado con exito";
        try {
            connectionSource = new JdbcPooledConnectionSource ( DATABASE_URL,DATABASE_USER,DATABASE_PASSWORD);
            cuponDAO = DaoManager.createDao(connectionSource, Cupon.class);
            cuponDAO.deleteById(String.valueOf(id));
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

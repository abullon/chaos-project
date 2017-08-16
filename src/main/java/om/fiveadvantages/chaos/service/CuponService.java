package om.fiveadvantages.chaos.service;


import ch.qos.logback.classic.Logger;
import com.fiveadvantages.chaos.datalayer.dto.CuponDTO;
import com.fiveadvantages.chaos.datalayer.entity.Cupon;
import com.fiveadvantages.chaos.util.Util;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
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
    public List<CuponDTO> list() {
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

}

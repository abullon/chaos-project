package com.fiveadvantages.chaos.service;

/**
 * @author Foncho A.K.A. Papi polvora
 */

import ch.qos.logback.classic.Logger;
import com.fiveadvantages.chaos.datalayer.dto.CuponDTO;
import com.fiveadvantages.chaos.datalayer.entity.Cupon;
import com.fiveadvantages.chaos.service.generic.GenericCRUDService;
import io.advantageous.qbit.annotation.PathVariable;
import io.advantageous.qbit.annotation.RequestMapping;
import io.advantageous.qbit.annotation.RequestMethod;
import org.slf4j.LoggerFactory;

import java.util.List;


@RequestMapping("/cupones")
public class CuponService  {

    private Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    private GenericCRUDService<Cupon,CuponDTO,Integer> genericCRUDService;

    public CuponService() {
         genericCRUDService = new GenericCRUDService<Cupon, CuponDTO, Integer>(Cupon.class,CuponDTO.class);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<CuponDTO> listAll() {
        return genericCRUDService.listAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CuponDTO getById(@PathVariable("id") Integer id) {
        return genericCRUDService.getById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public CuponDTO create(final CuponDTO dto) {
        return genericCRUDService.create(dto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public CuponDTO updateById(@PathVariable("id") Integer id,final CuponDTO dto) {
        return genericCRUDService.updateById(id,dto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteById(@PathVariable("id") Integer id) {
        return genericCRUDService.deleteById(id);
    }

}

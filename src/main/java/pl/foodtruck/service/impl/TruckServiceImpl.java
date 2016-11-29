package pl.foodtruck.service.impl;

import pl.foodtruck.service.TruckService;
import pl.foodtruck.domain.Truck;
import pl.foodtruck.repository.TruckRepository;
import pl.foodtruck.service.dto.TruckDTO;
import pl.foodtruck.service.mapper.TruckMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Truck.
 */
@Service
@Transactional
public class TruckServiceImpl implements TruckService{

    private final Logger log = LoggerFactory.getLogger(TruckServiceImpl.class);
    
    @Inject
    private TruckRepository truckRepository;

    @Inject
    private TruckMapper truckMapper;

    /**
     * Save a truck.
     *
     * @param truckDTO the entity to save
     * @return the persisted entity
     */
    public TruckDTO save(TruckDTO truckDTO) {
        log.debug("Request to save Truck : {}", truckDTO);
        Truck truck = truckMapper.truckDTOToTruck(truckDTO);
        truck = truckRepository.save(truck);
        TruckDTO result = truckMapper.truckToTruckDTO(truck);
        return result;
    }

    /**
     *  Get all the trucks.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<TruckDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trucks");
        Page<Truck> result = truckRepository.findAll(pageable);
        return result.map(truck -> truckMapper.truckToTruckDTO(truck));
    }

    /**
     *  Get one truck by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TruckDTO findOne(Long id) {
        log.debug("Request to get Truck : {}", id);
        Truck truck = truckRepository.findOne(id);
        TruckDTO truckDTO = truckMapper.truckToTruckDTO(truck);
        return truckDTO;
    }

    /**
     *  Delete the  truck by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Truck : {}", id);
        truckRepository.delete(id);
    }
}

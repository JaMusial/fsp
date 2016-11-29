package pl.foodtruck.service.impl;

import pl.foodtruck.domain.Position;
import pl.foodtruck.domain.User;
import pl.foodtruck.repository.PositionRepository;
import pl.foodtruck.repository.UserRepository;
import pl.foodtruck.security.SecurityUtils;
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

    @Inject
    private PositionRepository positionRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * Save a truck.
     *
     * @param truckDTO the entity to save
     * @return the persisted entity
     */
    public TruckDTO save(TruckDTO truckDTO) {
        log.debug("Request to save Truck : {}", truckDTO);

        Position position;
        if( null != truckDTO.getPositionId() ) {
            position = positionRepository.getOne(truckDTO.getPositionId());
        } else {
            position = new Position();
        }

        position.setLat(truckDTO.getLat());
        position.setLng(truckDTO.getLng());
        positionRepository.save(position);

        Truck truck = truckMapper.truckDTOToTruck(truckDTO);
        truck.setPosition(position);

        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        truck.setUser(user);

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

    @Transactional(readOnly = true)
    public List<TruckDTO> findAll() {
        log.debug("Request to get all Trucks");
        List<TruckDTO> result = truckRepository.findAll().stream().map(truckMapper::truckToTruckDTO).collect(Collectors.toCollection(LinkedList::new));

        for(TruckDTO truck : result) {
            if( null != truck.getPositionId() ) {
                Position position = positionRepository.findOne(truck.getPositionId());
                truck.setLat(position.getLat());
                truck.setLng(position.getLng());
            }
        }
        return result;
    }


    @Transactional(readOnly = true)
    public Page<TruckDTO> findUsersTrucks(Pageable pageable) {
        log.debug("Request to get all Trucks");
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        Page<Truck> result = truckRepository.findByUser(user, pageable);
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

        if( truckDTO.getPositionId() != null ) {
            Position position = positionRepository.getOne(truckDTO.getPositionId());
            truckDTO.setLng(position.getLng());
            truckDTO.setLat(position.getLat());
        }
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

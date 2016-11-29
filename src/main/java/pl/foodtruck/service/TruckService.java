package pl.foodtruck.service;

import pl.foodtruck.service.dto.TruckDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Truck.
 */
public interface TruckService {

    /**
     * Save a truck.
     *
     * @param truckDTO the entity to save
     * @return the persisted entity
     */
    TruckDTO save(TruckDTO truckDTO);

    /**
     *  Get all the trucks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TruckDTO> findAll(Pageable pageable);

    List<TruckDTO> findAll();

    /**
     *  Get the "id" truck.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TruckDTO findOne(Long id);

    /**
     *  Delete the "id" truck.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    Page<TruckDTO> findUsersTrucks(Pageable pageable);
}

package pl.foodtruck.service.impl;

import pl.foodtruck.service.PositionService;
import pl.foodtruck.domain.Position;
import pl.foodtruck.repository.PositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Position.
 */
@Service
@Transactional
public class PositionServiceImpl implements PositionService{

    private final Logger log = LoggerFactory.getLogger(PositionServiceImpl.class);
    
    @Inject
    private PositionRepository positionRepository;

    /**
     * Save a position.
     *
     * @param position the entity to save
     * @return the persisted entity
     */
    public Position save(Position position) {
        log.debug("Request to save Position : {}", position);
        Position result = positionRepository.save(position);
        return result;
    }

    /**
     *  Get all the positions.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Position> findAll() {
        log.debug("Request to get all Positions");
        List<Position> result = positionRepository.findAll();

        return result;
    }

    /**
     *  Get one position by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Position findOne(Long id) {
        log.debug("Request to get Position : {}", id);
        Position position = positionRepository.findOne(id);
        return position;
    }

    /**
     *  Delete the  position by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Position : {}", id);
        positionRepository.delete(id);
    }
}

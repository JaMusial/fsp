package pl.foodtruck.repository;

import pl.foodtruck.domain.Position;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Position entity.
 */
@SuppressWarnings("unused")
public interface PositionRepository extends JpaRepository<Position,Long> {

}

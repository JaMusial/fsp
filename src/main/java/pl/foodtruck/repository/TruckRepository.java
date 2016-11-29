package pl.foodtruck.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.foodtruck.domain.Truck;

import org.springframework.data.jpa.repository.*;
import pl.foodtruck.domain.User;

import java.util.List;

/**
 * Spring Data JPA repository for the Truck entity.
 */
@SuppressWarnings("unused")
public interface TruckRepository extends JpaRepository<Truck,Long> {

    @Query("select truck from Truck truck where truck.user.login = ?#{principal.username}")
    List<Truck> findByUserIsCurrentUser();

    Page<Truck> findByUser(User user, Pageable pageable);
}

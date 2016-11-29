package pl.foodtruck.service.mapper;

import pl.foodtruck.domain.*;
import pl.foodtruck.service.dto.TruckDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Truck and its DTO TruckDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface TruckMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "position.id", target = "positionId")
    TruckDTO truckToTruckDTO(Truck truck);

    List<TruckDTO> trucksToTruckDTOs(List<Truck> trucks);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "positionId", target = "position")
    Truck truckDTOToTruck(TruckDTO truckDTO);

    List<Truck> truckDTOsToTrucks(List<TruckDTO> truckDTOs);

    default Position positionFromId(Long id) {
        if (id == null) {
            return null;
        }
        Position position = new Position();
        position.setId(id);
        return position;
    }
}

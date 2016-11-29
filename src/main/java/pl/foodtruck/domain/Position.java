package pl.foodtruck.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Position.
 */
@Entity
@Table(name = "position")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "lat", nullable = false)
    private Float lat;

    @NotNull
    @Column(name = "lng", nullable = false)
    private Float lng;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getLat() {
        return lat;
    }

    public Position lat(Float lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public Position lng(Float lng) {
        this.lng = lng;
        return this;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        if(position.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, position.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Position{" +
            "id=" + id +
            ", lat='" + lat + "'" +
            ", lng='" + lng + "'" +
            '}';
    }
}

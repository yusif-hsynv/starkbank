package az.orient.starkbank.repository.redis;

import az.orient.starkbank.model.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CitiesRepository extends JpaRepository<Cities, UUID> {
}

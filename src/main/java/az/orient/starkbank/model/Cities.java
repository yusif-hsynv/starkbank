package az.orient.starkbank.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@Data
@EqualsAndHashCode
@RedisHash("cities")
public class Cities implements Serializable {
    @Id
    private UUID id;

    private Integer plateCode;
    private String name;
}

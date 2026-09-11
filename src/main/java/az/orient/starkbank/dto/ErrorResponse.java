package az.orient.starkbank.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;  // Xətanın baş verdiyi vaxt
    private int status;               // HTTP Status Kodu (məs: 400, 404)
    private String error;             // Xətanın növü (məs: Bad Request)
    private String message;           // Bizim yazdığımız xüsusi mesaj
    private String path;              // Xətanın baş verdiyi URL endpointi
}

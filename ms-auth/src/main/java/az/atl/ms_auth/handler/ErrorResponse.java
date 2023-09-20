package az.atl.ms_auth.handler;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {
    Integer statusCode;
    String message;
    String path;
    LocalDateTime timestamp;
}

package az.atl.ms_auth.model.dto.message;


import az.atl.ms_auth.model.consts.Channel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageDto {
    String senderName;
    String receiverName;
    String messageBody;
    Boolean isMe;
    @Enumerated(EnumType.STRING)
    private Channel channel;
    LocalDateTime sendDateTime;
}

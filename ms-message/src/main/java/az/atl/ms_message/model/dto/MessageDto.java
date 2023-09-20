package az.atl.ms_message.model.dto;


import az.atl.ms_message.model.consts.Channel;
import com.fasterxml.jackson.annotation.JsonInclude;
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

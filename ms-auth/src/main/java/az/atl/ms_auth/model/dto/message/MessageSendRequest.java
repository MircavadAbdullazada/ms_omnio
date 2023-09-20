package az.atl.ms_auth.model.dto.message;


import az.atl.ms_auth.model.consts.Channel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageSendRequest {
    String receiverUserName;
    String messageBody;
    @Enumerated(EnumType.STRING)
    Channel channel;
}

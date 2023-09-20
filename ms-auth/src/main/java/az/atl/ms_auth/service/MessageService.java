package az.atl.ms_auth.service;

import az.atl.ms_auth.model.dto.message.AdminMessageDto;
import az.atl.ms_auth.model.dto.message.MessageDto;
import az.atl.ms_auth.model.dto.message.MessageSendRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface MessageService {

    MessageDto sendMessage(MessageSendRequest messageSendRequest, String authorizationHeader);

    List<MessageDto> getMyMessages(String authorizationHeader);

    List<MessageDto> getMyDialogue(String otherUserName,String authorizationHeader);

    //for Admin
    List<AdminMessageDto> getMessages( String senderName, String receiverName, String authorizationHeader);

    //for Admin
    ResponseEntity<String> deleteMessage( Long id, String senderName, String receiverName);

    ResponseEntity<String> deleteMessage( Long messageId,String authorizationHeader);

}

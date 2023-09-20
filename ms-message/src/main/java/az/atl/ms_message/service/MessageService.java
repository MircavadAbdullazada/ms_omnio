package az.atl.ms_message.service;


import az.atl.ms_message.model.dto.AdminMessageDto;
import az.atl.ms_message.model.dto.MessageDto;
import az.atl.ms_message.requests.MessageSendRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface MessageService {


  MessageDto sendMessage(MessageSendRequest messageSendRequest,UserDetails userDetails);
  List<MessageDto> getMyMessages(UserDetails userDetails);

  List<MessageDto> getMyDialogue(UserDetails userDetails, String otherUserName);

  List<AdminMessageDto> getMessages(String sender, String receiver);

  void deleteMessageById(Long id);

  void deleteMessageBySenderName(String senderName);

  void deleteMessageByReceiverName(String receiverName);




  ResponseEntity<String> deleteMessageByCurrentUser(Long messageId, UserDetails userDetails);


}

package az.atl.ms_auth.service.impl;

import az.atl.ms_auth.feign.MessageFeignClient;
import az.atl.ms_auth.model.dto.message.AdminMessageDto;
import az.atl.ms_auth.model.dto.message.MessageDto;
import az.atl.ms_auth.model.dto.message.MessageSendRequest;
import az.atl.ms_auth.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageFeignClient client;



    @Override
    public MessageDto sendMessage(MessageSendRequest messageSendRequest,String authorizationHeader) {
        return client.sendMessage(messageSendRequest, authorizationHeader);
    }

    @Override
    public List<MessageDto> getMyMessages(String authorizationHeader) {
        return client.getMyMessages(authorizationHeader);
    }

    @Override
    public List<MessageDto> getMyDialogue(String otherUserName,String authorizationHeader) {
        return client.getMyDialogue(otherUserName,authorizationHeader);
    }

    @Override
    public List<AdminMessageDto> getMessages(String senderName, String receiverName, String authorizationHeader) {
        return client.getMessages(senderName,receiverName,authorizationHeader);
    }


    //for Admin
    @Override
    public ResponseEntity<String> deleteMessage(Long id, String senderName, String receiverName) {
        return client.deleteMessage(id,senderName,receiverName);
    }

    @Override
    public ResponseEntity<String> deleteMessage(Long messageId, String authorizationHeader) {
        return client.deleteMessage(messageId, authorizationHeader);
    }

}


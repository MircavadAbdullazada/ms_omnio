package az.atl.ms_message.service.impl;


import az.atl.ms_message.dao.entity.MessageModel;
import az.atl.ms_message.dao.entity.UserEntity;
import az.atl.ms_message.dao.repo.MessageRepository;
import az.atl.ms_message.dao.repo.UserRepository;
import az.atl.ms_message.exception.MessageNotFoundException;
import az.atl.ms_message.exception.UserNameNotFoundException;
import az.atl.ms_message.mapper.MessageMapper;
import az.atl.ms_message.model.consts.ExceptionMessages;
import az.atl.ms_message.model.dto.AdminMessageDto;
import az.atl.ms_message.model.dto.MessageDto;
import az.atl.ms_message.requests.MessageSendRequest;
import az.atl.ms_message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;


    @Override
    public MessageDto sendMessage(MessageSendRequest messageSendRequest,UserDetails userDetails) {


        UserEntity sender = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new UserNameNotFoundException(userDetails.getUsername()));

        //findReceiver
        UserEntity receiver = userRepository.findByUserName(messageSendRequest.getReceiverUserName())
                .orElseThrow(() -> new UserNameNotFoundException(messageSendRequest.getReceiverUserName()));

        //sendMessage
        MessageModel sendMessage = new MessageModel();
        sendMessage.setSender(sender);
        sendMessage.setReceiver(receiver);

        sendMessage.setMessageBody(messageSendRequest.getMessageBody());
        sendMessage.setChannel(messageSendRequest.getChannel());
        sendMessage.setSendDateTime(LocalDateTime.now());
        messageRepository.save(sendMessage);

        MessageDto messageDto = MessageMapper.buildMessageDto(sendMessage);
        messageDto.setIsMe(true);
        messageDto.setSenderName(sender.getUsername());
        messageDto.setReceiverName(receiver.getUsername());


        return messageDto;
    }


    @Override
    public List<MessageDto> getMyMessages(UserDetails userDetails) {
        //Authenticated user
        UserEntity currentUser = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new UserNameNotFoundException(userDetails.getUsername()));

        // / Get  messages of the authenticated user
        List<MessageModel> messages = messageRepository.findBySenderOrReceiver(currentUser, currentUser);

        // Convert to DTOs and set 'isMe' field(and return)
        return messages.stream().map(message -> {
            MessageDto dto = MessageMapper.buildMessageDto(message);
            if (message.getSender().equals(currentUser)) {
                dto.setIsMe(true);
            } else if (message.getReceiver().equals(currentUser)) {
                dto.setIsMe(false);
            }
            return dto;
        }).sorted(Comparator.comparing(MessageDto::getSendDateTime).reversed()).toList();
    }

    @Override
    public List<MessageDto> getMyDialogue(UserDetails userDetails, String otherUserName) {
        //Authenticated user
        UserEntity currentUser = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new UserNameNotFoundException(userDetails.getUsername()));

        //Other User
        UserEntity otherUser = userRepository.findByUserName(otherUserName)
                .orElseThrow(() -> new UserNameNotFoundException(otherUserName));

        //Get Dialogue
        List<MessageModel> messages = messageRepository.
                findBySenderNameAndReceiverNameOrReceiverNameAndSenderName(currentUser.getUsername(), otherUser.getUsername());


        // Convert to DTOs and set 'isMe' field(and return)
        return messages.stream().map(message -> {
            MessageDto dto = MessageMapper.buildMessageDto(message);
            dto.setIsMe(message.getSender().equals(currentUser));
            return dto;
        }).sorted(Comparator.comparing(MessageDto::getSendDateTime).reversed()).toList();
    }


    //For Admin
    @Override
    public List<AdminMessageDto> getMessages(String sender, String receiver) {
        List<MessageModel> messages;
        if (sender != null && receiver != null) {
            messages = messageRepository.findBySenderNameAndReceiverNameOrReceiverNameAndSenderName(sender, receiver);
        } else if (sender != null) {
            messages = messageRepository.findBySenderName(sender);
        } else if (receiver != null) {
            messages = messageRepository.findByReceiverName(receiver);
        } else {
            messages = messageRepository.findAll();
        }

        return messages.stream()
                .map(MessageMapper::buildAdminMessageDto)
                .sorted(Comparator.comparing(AdminMessageDto::getMessageBody).reversed())
                .toList();
    }


    @Override
    public void deleteMessageById(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public void deleteMessageBySenderName(String senderName) {
        List<MessageModel> messages = messageRepository.findBySenderName(senderName);
        messageRepository.deleteAll(messages);
    }

    @Override
    public void deleteMessageByReceiverName(String receiverName) {
        List<MessageModel> messages = messageRepository.findByReceiverName(receiverName);
        messageRepository.deleteAll(messages);
    }

    @Override
    public ResponseEntity<String> deleteMessageByCurrentUser(Long messageId, UserDetails userDetails) {
        UserEntity currentUser = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new UserNameNotFoundException(userDetails.getUsername()));

        MessageModel message = messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException("id:"+ messageId));

        if(currentUser.getUsername().equals(message.getSender().getUsername())){
            messageRepository.deleteById(messageId);
            return ResponseEntity.ok("Message deleted");
        }
        return ResponseEntity.badRequest().body(ExceptionMessages.UNAUTHORIZED_EXCEPTION.getMessage());
    }




}

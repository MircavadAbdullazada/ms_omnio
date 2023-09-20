package az.atl.ms_message.mapper;

import az.atl.ms_message.dao.entity.MessageModel;
import az.atl.ms_message.model.dto.AdminMessageDto;
import az.atl.ms_message.model.dto.MessageDto;

import java.util.List;

public enum MessageMapper {
    MESSAGE_MAPPER;

    public static MessageModel buildMessageEntity(MessageDto messageDto){
        return MessageModel.builder()
                .messageBody(messageDto.getMessageBody())
                .channel(messageDto.getChannel())
                .sendDateTime(messageDto.getSendDateTime())
                .build();
    }

    public static MessageDto buildMessageDto(MessageModel messageModel){
        return MessageDto.builder()
                .senderName(messageModel.getSender().getUsername())
                .receiverName(messageModel.getReceiver().getUsername())
                .messageBody(messageModel.getMessageBody())
                .channel(messageModel.getChannel())
                .sendDateTime(messageModel.getSendDateTime())
                .build();
    }

    public static AdminMessageDto buildAdminMessageDto(MessageModel messageModel){
        return AdminMessageDto.builder()
                .id(messageModel.getId())
                .senderName(messageModel.getSender().getUsername())
                .receiverName(messageModel.getReceiver().getUsername())
                .messageBody(messageModel.getMessageBody())
                .channel(messageModel.getChannel())
                .sendDateTime(messageModel.getSendDateTime())
                .build();
    }

    public static List<MessageDto> buildMessageDtoList(List<MessageModel> messageModels){
        return messageModels.stream()
                .map(MessageMapper::buildMessageDto).
                toList();
    }

}

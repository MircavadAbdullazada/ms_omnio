package az.atl.ms_auth.controller;


import az.atl.ms_auth.model.dto.message.AdminMessageDto;
import az.atl.ms_auth.model.dto.message.MessageDto;
import az.atl.ms_auth.model.dto.message.MessageSendRequest;
import az.atl.ms_auth.service.impl.MessageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/message")
public class MessageController {

    private final MessageServiceImpl messageService;

    @PostMapping("/send")
    public MessageDto sendMessage(@RequestBody MessageSendRequest messageSendRequest,
                                  @RequestHeader("Authorization") String authorizationHeader) {
        return messageService.sendMessage(messageSendRequest, authorizationHeader);
    }

    @GetMapping("/myMessages")
    public List<MessageDto> getMyMessages(@RequestHeader("Authorization") String authorizationHeader) {
        return messageService.getMyMessages(authorizationHeader);
    }

    @GetMapping("/myDialogue")
    public List<MessageDto> getMyDialogue(@RequestHeader("Authorization") String authorizationHeader,
                                          @RequestParam String otherUserName) {
        return messageService.getMyDialogue(authorizationHeader, otherUserName);
    }

    //for Admin
    @GetMapping("/admin/get")
    public List<AdminMessageDto> getMessages(@RequestParam(required = false) String senderName,
                                             @RequestParam(required = false) String receiverName,
                                             @RequestHeader("Authorization") String authorizationHeader) {
        return messageService.getMessages(senderName, receiverName, authorizationHeader);
    }


    //for Admin
    @DeleteMapping("/admin/delete")
    public ResponseEntity<String> deleteMessage(@RequestParam(required = false) Long id,
                                                @RequestParam(required = false) String senderName,
                                                @RequestParam(required = false) String receiverName) {
        return messageService.deleteMessage(id, senderName, receiverName);
    }


    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long messageId,
                                                @RequestHeader("Authorization") String authorizationHeader) {

        return messageService.deleteMessage(messageId,authorizationHeader);
    }


}

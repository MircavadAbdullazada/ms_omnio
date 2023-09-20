package az.atl.ms_message.controller;


import az.atl.ms_message.model.consts.ExceptionMessages;
import az.atl.ms_message.model.dto.AdminMessageDto;
import az.atl.ms_message.model.dto.MessageDto;
import az.atl.ms_message.requests.MessageSendRequest;
import az.atl.ms_message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;


    @PostMapping("/send")
    public MessageDto sendMessage(@RequestBody MessageSendRequest messageSendRequest,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        return messageService.sendMessage(messageSendRequest, userDetails);
    }


    @GetMapping("/myMessages")
    public List<MessageDto> getMyMessages(@AuthenticationPrincipal UserDetails userDetails) {
        return messageService.getMyMessages(userDetails);
    }

    @GetMapping("/myDialogue")
    public List<MessageDto> getMyDialogue(@AuthenticationPrincipal UserDetails userDetails,
                                          @RequestParam String otherUserName) {
        return messageService.getMyDialogue(userDetails, otherUserName);
    }


    //for Admin
    @GetMapping("/admin/get")
    public List<AdminMessageDto> getMessages(@RequestParam(required = false) String senderName,
                                             @RequestParam(required = false) String receiverName,
                                             @AuthenticationPrincipal UserDetails userDetails) {
//         Check if the authenticated user is an admin
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            return messageService.getMessages(senderName, receiverName);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ExceptionMessages.UNAUTHORIZED_EXCEPTION.getMessage());
        }
    }


    //for Admin
    @DeleteMapping("/admin/delete")
    public ResponseEntity<String> deleteMessage(@RequestParam(required = false) Long id,
                                                @RequestParam(required = false) String senderName,
                                                @RequestParam(required = false) String receiverName) {
        if (id != null) {
            messageService.deleteMessageById(id);
        } else if (senderName != null) {
            messageService.deleteMessageBySenderName(senderName);
        } else if (receiverName != null) {
            messageService.deleteMessageByReceiverName(receiverName);
        } else {
            return ResponseEntity.badRequest().body("Please provide an ID, sender name, or receiver name.");
        }
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/delete/{messageId}")
    public void deleteMessage(@PathVariable Long messageId,
                              @AuthenticationPrincipal UserDetails userDetails) {

        messageService.deleteMessageByCurrentUser(messageId, userDetails);
    }


}




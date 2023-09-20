package az.atl.ms_auth.feign;


import az.atl.ms_auth.model.dto.message.AdminMessageDto;
import az.atl.ms_auth.model.dto.message.MessageDto;
import az.atl.ms_auth.model.dto.message.MessageSendRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(value = "Message", url = "http://localhost:8081/messages")
public interface MessageFeignClient {

    @PostMapping("/send")
    MessageDto sendMessage(@RequestBody MessageSendRequest messageSendRequest,
                           @RequestHeader("Authorization") String authorizationHeader);

    @GetMapping("/myMessages")
    List<MessageDto> getMyMessages(@RequestHeader("Authorization") String authorizationHeader);

    @GetMapping("/myDialogue")
    List<MessageDto> getMyDialogue(@RequestHeader("Authorization") String authorizationHeader,
                                   @RequestParam String otherUserName);

    //for Admin
    @GetMapping("/admin/get")
    List<AdminMessageDto> getMessages(@RequestParam(required = false) String senderName,
                                      @RequestParam(required = false) String receiverName,
                                      @RequestHeader("Authorization") String authorizationHeader);

    //for Admin
    @DeleteMapping("/admin/delete")
    ResponseEntity<String> deleteMessage(@RequestParam(required = false) Long id,
                                                @RequestParam(required = false) String senderName,
                                                @RequestParam(required = false) String receiverName);

    @DeleteMapping("/delete/{messageId}")
     ResponseEntity<String> deleteMessage(@PathVariable Long messageId,
                                          @RequestHeader("Authorization") String authorizationHeader);
}

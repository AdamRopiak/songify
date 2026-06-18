package com.songify.infrastructure.security.jwt;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @GetMapping("/message")
    public ResponseEntity<MessageDto> message(Authentication principal) {
        MessageDto messageDto = new MessageDto("Hi " + principal.getName());
        return ResponseEntity.ok(messageDto);
    }
}

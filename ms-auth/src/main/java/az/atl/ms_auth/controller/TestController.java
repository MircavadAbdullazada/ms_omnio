package az.atl.ms_auth.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/say-hello")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @GetMapping("/admin/hello")
    public ResponseEntity<String> sayHelloForAdmin(Principal principal) {
        if (hasRole(principal, "ADMIN")) {
            return ResponseEntity.ok("Hello from secured endpoint for ADMIN");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
    }

    @GetMapping("/user/hello")
    public ResponseEntity<String> sayHelloForUser(Principal principal) {
        if (hasRole(principal, "USER")) {
            return ResponseEntity.ok("Hello from secured endpoint for USER");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
    }

    @GetMapping("/agent/hello")
    public ResponseEntity<String> sayHelloForAgent(Principal principal) {
        if (hasRole(principal, "AGENT")) {
            return ResponseEntity.ok("Hello from secured endpoint for AGENT");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
    }
    @GetMapping("/supervisor/hello")
    public ResponseEntity<String> sayHelloForSupervisor(Principal principal) {
        if (hasRole(principal, "SUPERVISOR")) {
            return ResponseEntity.ok("Hello from secured endpoint for SUPERVISOR");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
    }

    private boolean hasRole(Principal principal, String role) {
        if (principal == null) {
            return false;
        }
        Authentication auth = (Authentication) principal;
        return auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role));


    }
}
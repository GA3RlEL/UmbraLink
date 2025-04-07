package com.umbra.umbralink.healthCheck;

import com.umbra.umbralink.auth.HealthCheckResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @GetMapping("/ping")
    public ResponseEntity<HealthCheckResponseDto> getHealth() throws InterruptedException {
//        Thread.sleep(2000);
        return new ResponseEntity<>(new HealthCheckResponseDto("Pong"), HttpStatus.OK);
    }
}

package swin.swe4006.c6g1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import swin.swe4006.c6g1.dto.HealthCheckDto;
import swin.swe4006.c6g1.dto.ResponseRestDto;

import java.net.InetAddress;
import java.net.UnknownHostException;

// Comment
@RestController
@RequestMapping("/health-check")
public class HealthCheckController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseRestDto index() throws UnknownHostException {
        // Get the local host information
        InetAddress localHost = InetAddress.getLocalHost();

        // Retrieve hostname and IP address
        String hostname = localHost.getHostName();
        String ipAddress = localHost.getHostAddress();
        HealthCheckDto res = HealthCheckDto.builder().apiVersion("v2").hostname(hostname).ipAddress(ipAddress).build();
        return ResponseRestDto.builder().data(res).message("Healthy!").build();
    }
}

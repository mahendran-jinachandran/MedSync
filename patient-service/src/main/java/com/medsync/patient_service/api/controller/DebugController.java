package com.medsync.patient_service.api.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/debug")
public class DebugController {

    @PostMapping("/echo")
    public String echo(@RequestBody String body) {
        System.out.println("### /debug/echo BODY: " + body);
        return "OK: " + body;
    }
}

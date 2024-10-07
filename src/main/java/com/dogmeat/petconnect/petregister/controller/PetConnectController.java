package com.dogmeat.petconnect.petregister.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dogmeat.petconnect.petregister.service.PetConnectService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PetConnectController {

    @Autowired
    private PetConnectService petConnectService;

    @PostMapping("/addOrphan")
    public int addOrphan(@RequestBody Map<String, Object> orphan) {
        log.info("addOrphan called {}", orphan);
        return petConnectService.addOrphan(orphan);
    }
}

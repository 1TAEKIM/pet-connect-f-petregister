package com.dogmeat.petconnect.petregister.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dogmeat.petconnect.petregister.mapper.PetConnectMapper;

@Service
public class PetConnectService {

    @Autowired
    private PetConnectMapper petConnectMapper;

    public int addOrphan(Map<String, Object> orphan) {
        return petConnectMapper.addOrphan(orphan);
    }
}

package com.dogmeat.petconnect.petregister.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PetConnectMapper {
    public int addOrphan(@Param("orphan") Map<String, Object> orphan);
}

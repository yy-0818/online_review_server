package com.scientific.audit.common.model.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scientific.audit.common.model.vo.FindByTypesVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class DirectionTest{
    @Resource
    private ObjectMapper objectMapper;
    @Test
    public void testDirectionToJson() {

        FindByTypesVo findByTypesVo = new FindByTypesVo();

        Direction direction = new Direction();
        direction.setId(1L);
        direction.setName("Test");
        findByTypesVo.getDirections().add(direction);


        try {
            String s = objectMapper.writeValueAsString(direction);
            log.info(s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
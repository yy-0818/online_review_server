package com.scientific.audit.mapper;

import com.scientific.audit.common.model.vo.CountVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@ActiveProfiles("dev")
public class PaperMapperTest{

    @Resource
    private PaperMapper paperMapper;

    @Test
    public void testCount() {
        Map<String, CountVo> stringLongMap = paperMapper.countByStateAndType();
        log.info(stringLongMap.toString());
    }
}
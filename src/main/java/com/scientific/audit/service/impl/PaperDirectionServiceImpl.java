package com.scientific.audit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scientific.audit.common.model.entity.PaperDirection;
import com.scientific.audit.common.model.vo.PaperVo;
import com.scientific.audit.mapper.PaperDirectionMapper;
import com.scientific.audit.service.PaperDirectionService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
@Service
public class PaperDirectionServiceImpl extends ServiceImpl<PaperDirectionMapper, PaperDirection>
        implements PaperDirectionService{


    @Override
    public void savaDirection(PaperVo paperVO) {
        PaperDirection paperDirection = new PaperDirection();
        String direction = paperVO.getDirectionId();
        List<String> directionList = Arrays.asList(direction.split(","));
        System.out.println(directionList);
        int a = directionList.size();
        paperDirection.setPaperId(paperVO.getId());
        for (int i = 0; i < a; i++) {
            long l = Long.parseLong(directionList.get(i));
            paperDirection.setDirectionId(l);
            paperDirection.setId(null);
            save(paperDirection);

        }
    }
}





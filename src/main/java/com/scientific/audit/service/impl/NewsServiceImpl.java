package com.scientific.audit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scientific.audit.common.model.entity.News;
import com.scientific.audit.mapper.NewsMapper;
import com.scientific.audit.service.NewsService;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News>
        implements NewsService{

}





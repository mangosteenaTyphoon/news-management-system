package com.qy.news.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.qy.news.constant.NewsConst;
import com.qy.news.entity.News;
import com.qy.news.entity.dto.NewsIdreqDTO;
import com.qy.news.mapper.NewsMapper;
import com.qy.news.service.NewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qy
 * @since 2023-03-14
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public boolean saveNews(News news) {
        if( StrUtil.isEmpty(news.getTitle()) || StrUtil.isEmpty(news.getContent())
            || StrUtil.isEmpty(news.getCover()) || StrUtil.isEmpty(news.getSummary())
            || StrUtil.isEmpty(news.getUserId()))      return false;
        news.setStatus(NewsConst.NEWS_NO_PUBLIC);
        news.setIsBanner(NewsConst.NEWS_NO_BANNER);
        news.setUserId(IdUtil.simpleUUID());
        news.setGoods(0);
        return newsMapper.insert(news) == 1;
    }

//    两种状态是可以抽取成一个方法的 但是懒的抽取了

    @Override
    public boolean publishNews(NewsIdreqDTO reqDTO) {
        if(StrUtil.isEmpty(reqDTO.getId())) return false;
        News news = new News();
        news.setId(reqDTO.getId());
        news.setStatus(NewsConst.NEWS_PUBLIC);
        return newsMapper.updateById(news) == 1;

    }

    @Override
    public boolean deleteNews(NewsIdreqDTO reqDTO) {
        if(StrUtil.isEmpty(reqDTO.getId())) return false;
        News news = new News();
        news.setId(reqDTO.getId());
        news.setStatus(NewsConst.NEWS_DEL);
        return newsMapper.updateById(news) == 1;
    }
}

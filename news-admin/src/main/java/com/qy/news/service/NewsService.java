package com.qy.news.service;

import com.qy.news.entity.News;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qy.news.entity.dto.NewsIdreqDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qy
 * @since 2023-03-14
 */
public interface NewsService extends IService<News> {

    boolean saveNews(News news);

    boolean publishNews(NewsIdreqDTO reqDTO);

    boolean deleteNews(NewsIdreqDTO reqDTO);
}

package com.qy.news.controller;


import com.qy.news.entity.News;
import com.qy.news.entity.dto.NewsIdreqDTO;
import com.qy.news.result.R;
import com.qy.news.service.NewsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qy
 * @since 2023-03-14
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    /*
    * @Author 山竹
    * @Description //TODO saveNews
    * @Date 11:08 2023/3/14
    * @Param : News reqDTO
    * @return R
    **/
    @ApiOperation("新建新闻")
    @PostMapping("saveNews")
    public R saveNews(@RequestBody News news){
        return newsService.saveNews(news)?R.ok():R.error();
    }

    /*
    * @Author 山竹
    * @Description //TODO 发布新闻状态
    * @Date 11:43 2023/3/14
    * @Param : NewsStatusDTO reqDTO
    * @return R
    **/
    @ApiOperation("发布新闻状态")
    @PostMapping("publishNews")
    public R publishNews(@RequestBody NewsIdreqDTO reqDTO) {
        return newsService.publishNews(reqDTO) ? R.ok(): R.error();
    }

    /*
    * @Author 山竹
    * @Description //TODO 删除新闻
    * @Date 11:51 2023/3/14
    * @Param : R
    * @return
    **/
    @ApiOperation("删除新闻")
    @DeleteMapping("deleteNews")
    public R deleteNews(@RequestBody NewsIdreqDTO reqDTO){
        return newsService.deleteNews(reqDTO) ? R.ok(): R.error();
    }





}


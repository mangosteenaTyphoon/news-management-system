package com.qy.news.constant;


import lombok.Data;

@Data
public class NewsConst {
    /*
    * 文章状态  0 未发布 1发布 -1 删除
    **/
    public static int NEWS_NO_PUBLIC =  0;
    public static int NEWS_PUBLIC =  1;
    public static int NEWS_DEL = -1;

    public static int NEWS_NO_BANNER = 0;
    public static int NEWS_BANNER = 1;
}

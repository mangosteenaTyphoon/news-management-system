package com.qy.news.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableOpenApi//开启swagger支持
public class SwaggerConfig {

    /**
     * Docket类是Swagger的配置类，要自定义修改 Swagger 的默认配置信息，我们需要覆盖该对象
     * @return
     */
    @Bean
    public Docket docket(){
        //1.以OAS_30标准构建Docket配置类
        return new Docket(DocumentationType.OAS_30)
                //2.配置Swagger接口文档基本信息apiInfo
                .apiInfo(apiInfo())
                //3.select方法开启配置扫描接口的Builder
                .select()
                //4.指定要扫描/维护接口文档的包（否则就全部扫描）
                .apis(RequestHandlerSelectors.basePackage("com.qy.news.controller"))
                //5.路径过滤：该Docket-UI展示时，只展示指定路径下的接口文档(any表示都展示)
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 配置 Swagger 接口文档的基本信息
     * @return
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                //1.接口文档标题
                .title("新闻管理系统接口文档")
                //2.接口文档描述内容
                .description("这里是新闻管理系统接口文档的具体接口描述")
                //3.项目文档迭代版本
                .version("9.0")
                //4.主要联系人信息（姓名name，个人主页url，邮箱email）
                .contact(new Contact("qy","www.1111.com","1111111@qq.com"))
                //5.相关许可证信息
                .license("The CSDN License")
                //6.相关许可证链接
                .licenseUrl("www.baidu.com")
                //7.返回构建的ApiInfo对象
                .build();
    }

}


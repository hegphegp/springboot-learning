package tech.ascs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration  //让Spring来加载该类配置
@EnableSwagger2 //启用Swagger2
public class SwaggerConfig {
    @Bean
    public Docket alipayApi() {
        return new Docket(DocumentationType.SWAGGER_2)
//            .securitySchemes(Arrays.asList(this.apiKey()))
            .groupName("支付宝API接口文档")
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("tech.ascs.controller.alipay"))
            .paths(PathSelectors.any())
            .build();
    }

    @Bean
    public Docket weixinpayApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("微信API接口文档")
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("tech.ascs.controller.weixinpay"))
            .paths(PathSelectors.any())
            .build();
    }

    @Bean
    public Docket unionpayApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("银联API接口文档")
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.itstyle.modules.unionpay"))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("支付系统")
            .description("微信、支付宝、银联支付服务")
            .license("licenses")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .termsOfServiceUrl("")
            //.termsOfServiceUrl("http://www.baidu.com") //URL外链跳转
            //只能有一个Contact，多个Contact时，后面的Contact覆盖掉前面的Contact
            //.contact(new Contact("科帮网1", "http://www.baidu1.com", "345849402@qq.com"))
            .contact(new Contact("科帮网2", "http://www.baidu2.com", "345849402@qq.com"))
            .version("1.0")
            .build();
    }

    ApiKey apiKey() {
        return new ApiKey("sessionId", "sessionId", "header");
    }
}

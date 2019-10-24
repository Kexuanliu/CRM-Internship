/**
 * @author ZXL
 * @date 2019/6/5 11:06
 */
package com.xuebei.crm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {


    @Value("${weixinPatch}")
    private String weixinPatch;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/MP_verify_McoS62zZjgwwsROd.txt").addResourceLocations(weixinPatch);
    }
}


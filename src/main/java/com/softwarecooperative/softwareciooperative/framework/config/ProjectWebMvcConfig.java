package com.softwarecooperative.softwareciooperative.framework.config;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/3/4-20:01:35
 */
// 文档地址：https://gitee.com/wenshao/fastjson2/blob/main/docs/features_cn.md
@Configuration
public class ProjectWebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //定义一个convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter=new FastJsonHttpMessageConverter();
        // 添加fastjson的配置信息，比如是否要格式化返回的json数据
        FastJsonConfig fastJsonConfig = getFastJsonConfig();
        //在convert中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);
        //设置支持的媒体类型
        fastConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        //设置默认字符集
        fastConverter.setDefaultCharset(StandardCharsets.UTF_8);
        //将convert添加到converters
        converters.add(0, fastConverter);

        //解决返回字符串带双引号问题
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        stringHttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_PLAIN));
        stringHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
        converters.add(0, stringHttpMessageConverter);

        super.addDefaultHttpMessageConverters(converters);
    }

    private static FastJsonConfig getFastJsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setWriterFeatures(
                //是否输出值为null的字段,默认为false
                JSONWriter.Feature.WriteMapNullValue,
                //将Collection类型字段的字段空值输出为[]
                JSONWriter.Feature.WriteNullListAsEmpty,
                //将字符串类型字段的空值输出为空字符串
                JSONWriter.Feature.WriteNullStringAsEmpty,
                // 格式化输出
                JSONWriter.Feature.PrettyFormat,
                // 将Number类型字段的空值序列化输出为0
                JSONWriter.Feature.WriteNullNumberAsZero
                // 视情况而定
                // JSONWriter.Feature.WriteLongAsString
        );
        return fastJsonConfig;
    }
}

package com.softwarecooperative.softwareciooperative.framework.config;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;
import com.softwarecooperative.softwareciooperative.framework.interceptor.IntegratingInterceptor;
import com.softwarecooperative.softwareciooperative.framework.interceptor.JWTInterceptor;
import com.softwarecooperative.softwareciooperative.framework.interceptor.TeacherRoleInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * 大坑，WebMvcConfigurationSupport只能被一个类继承，多的无效
 */
@Slf4j
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    /**
     * JWT拦截器配置
     * @return
     */
    @Bean
    public JWTInterceptor jwtInterceptor(){
        return new JWTInterceptor();
    }

    @Bean
    public TeacherRoleInterceptor teacherRoleInterceptor() {
        return new TeacherRoleInterceptor();
    }

    @Bean
    public IntegratingInterceptor integratingInterceptor() {
        return new IntegratingInterceptor();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("注册jwt拦截器");
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/teacher/**")
                .addPathPatterns("/student/**")
                .addPathPatterns("/common/**")
                .order(1);

        log.info("注册教师权限权限拦截器");
        registry.addInterceptor(teacherRoleInterceptor())
                .addPathPatterns("/teacher/**")
                .order(2);

        log.info("注册组队权限拦截器");
        registry.addInterceptor(integratingInterceptor())
                .addPathPatterns("/student/group/**")
                .order(3);

        super.addInterceptors(registry);
    }

    /**
     * 消息转换器
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("准备消息转换...");
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

    /**
     * 设置静态资源映射
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("准备资源映射...");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
    }
}

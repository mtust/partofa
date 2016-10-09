package com.partofa.controller.handler;

import com.partofa.domain.Role;
import com.partofa.domain.User;
import com.partofa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;

/**
 * Created by tust on 10.09.2016.
 */
@Controller
@EnableWebMvc
@Slf4j
public class WebController extends WebMvcConfigurerAdapter {


    @Autowired
    UserService userService;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("index");
    }
//
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("resources");

        registry.addResourceHandler("css/**").addResourceLocations(
                "classpath:/static/css/");
        registry.addResourceHandler("js/**").addResourceLocations(
                "classpath:/static/js/");
        registry.addResourceHandler("file:///C:/Users/tust/Downloads/приклад.xlsx");
//        registry.addResourceHandler("/static/**").addResourceLocations("/static/");


    }


    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(500000000);
        return multipartResolver;
    }


    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String getHomePage() {
        User user = null;
        try {
            user = userService.getLoginUser();
        } catch (NullPointerException e){
            log.info("anonymus user");
        }
        if (user != null) {
   //         log.info(user.toString());
            if (user.getRole().equals(Role.ROLE_USER)) {
                return "home";
            } else if (user.getRole().equals(Role.ROLE_ADMIN)) {
                return "admin";
            }
        }
        return "index";

    }

    @RequestMapping(value = "private/page/home", method = RequestMethod.GET)
    public String getPrivateHomePage() {
        return "home";
    }

    @RequestMapping(value = "admin/page/users", method = RequestMethod.GET)
    public String getPrivateAdminPage() {
        User user = userService.getLoginUser();
        if(user != null && user.getRole().equals(Role.ROLE_ADMIN)){
            return "admin";
        } else {
            return getHomePage();
        }

    }

    @RequestMapping(value = {"private/page/private/me", "admin/page/private/me", "private/me" }, method = RequestMethod.GET)
    public String myPage(){
        return "personal";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String logout(@RequestParam("logout") String logout) {
        log.info("login after logout");
        return "index";
    }

    @RequestMapping(value = {"/public/user/index", "/index"}, method = RequestMethod.GET)
    public String getIndexPage() {
        return this.getHomePage();
    }


    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("templates/");
//        resolver.setSuffix(".html");
        return resolver;
    }

    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}

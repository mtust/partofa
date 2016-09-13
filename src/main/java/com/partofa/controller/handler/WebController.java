package com.partofa.controller.handler;

import com.partofa.domain.Role;
import com.partofa.domain.User;
import com.partofa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

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
        registry.addViewController("/results").setViewName("results");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/form").setViewName("form");
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

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String logout(@RequestParam("logout") String logout) {
        return "index";
    }

    @RequestMapping(value = {"/partofa-0.1/public/user/index", "/partofa-0.1/index"}, method = RequestMethod.GET)
    public String getIndexPage() {
        return this.getHomePage();
    }


    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("templates/");
        //resolver.setSuffix(".html");
        return resolver;
    }

    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}

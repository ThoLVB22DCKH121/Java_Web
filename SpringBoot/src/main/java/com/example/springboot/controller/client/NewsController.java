package com.example.springboot.controller.client;

import com.example.springboot.repository.entity.NewsEntity;
import com.example.springboot.service.Impl.UserDetailsImpl;
import com.example.springboot.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/news")
    public ModelAndView news(){
        ModelAndView mav = new ModelAndView("client/news/news-list");
        String username=((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        mav.addObject("username",username);
        mav.addObject("newsEntity",new NewsEntity());
        mav.addObject("newsList",newsService.getAllNews());
        mav.addObject("currentPage", "news");
        return mav;
    }

    @GetMapping("/news/{idNews}")
    public ModelAndView newsDetail(@PathVariable("idNews") Long idNews){
        ModelAndView mav = new ModelAndView("client/news/news-detail");
        mav.addObject("newsEntity",newsService.getNewsById(idNews));
        return mav;
    }
}

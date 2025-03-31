package com.example.springboot.controller.admin;


import com.example.springboot.repository.entity.NewsDetailEntity;
import com.example.springboot.repository.entity.NewsEntity;
import com.example.springboot.service.NewsDetailService;
import com.example.springboot.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/admin/news")
public class AdNewsController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private NewsDetailService newsDetailService;

    @GetMapping()
    public ModelAndView showAdminNewsPage() {
        ModelAndView mav = new ModelAndView("admin/news");
        mav.addObject("newsEntity", new NewsEntity());
        mav.addObject("newsList",newsService.getAllNews());
        mav.addObject("newsDetailList",newsDetailService.getAllNewsDetail());
        mav.addObject("newsDetailEntity",new NewsDetailEntity());
        mav.addObject("success",false);
        mav.addObject("error",false);
        return mav;
    }

    @PostMapping()
    public ModelAndView saveNews(@ModelAttribute NewsEntity newsEntity) {
        ModelAndView mav = new ModelAndView("redirect:/admin/news");
        newsService.createNews(newsEntity);
        mav.addObject("success", "Thêm tin tức thành công!");
        return mav;
    }

    @DeleteMapping("/{id}")
    public ModelAndView deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return new ModelAndView("redirect:/admin/news");
    }

    @PutMapping("/{id}")
    public ModelAndView updateNews(@PathVariable Long id, @ModelAttribute NewsEntity newsEntity) {
        newsService.updateNews(id, newsEntity);
        return new ModelAndView("redirect:/admin/news");
    }

    @PostMapping("/{idNews}")
    public ModelAndView addNewsDetailForNews(@ModelAttribute NewsDetailEntity newsDetailEntity, @PathVariable("idNews") Long idNews) {
        ModelAndView mav = new ModelAndView("redirect:/admin/news");
        newsDetailService.createNewsDetailForNews(newsDetailEntity,idNews);
        return mav;
    }

    @DeleteMapping("/newsdetail/{idNewsDetail}")
    public ModelAndView deleteNewsDetail(@PathVariable("idNewsDetail") Long idNewsDetail) {
        ModelAndView mav = new ModelAndView("redirect:/admin/news");
        newsDetailService.deleteNewsDetail(idNewsDetail);
        return mav;
    }

    @PutMapping("/newsdetail/{idNewsDetail}")
    public ModelAndView updateNewsDetail(@ModelAttribute NewsDetailEntity newsDetailEntity, @PathVariable("idNewsDetail") Long idNewsDetail) {
        ModelAndView mav = new ModelAndView("redirect:/admin/news");
        newsDetailService.updateNewsDetail(newsDetailEntity,idNewsDetail);
        return mav;
    }
}

package com.example.springboot.controller.admin;

import com.example.springboot.repository.entity.HandBookDetailEntity;
import com.example.springboot.repository.entity.HandBookEntity;
import com.example.springboot.service.HandBookDetailService;
import com.example.springboot.service.HandBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller()
@RequestMapping("/admin/handbook")
public class AdHandBookController {
    @Autowired
    private HandBookService handBookService;
    @Autowired
    private HandBookDetailService handBookDetailService;

    @GetMapping()
    public ModelAndView showAdminHandBookPage(){
        ModelAndView mav = new ModelAndView("admin/handbook");
        mav.addObject("handBookList", handBookService.getAllHandBooks());
        mav.addObject("handBookDetailList", handBookDetailService.getAllHandBookDetails());
        mav.addObject("handBookEntity",new HandBookEntity());
        mav.addObject("handBookDetailEntity",new HandBookDetailEntity());
        mav.addObject("success",false);
        mav.addObject("error",false);
        return mav;
    }

    @PostMapping()
    public ModelAndView saveHandBook(@ModelAttribute HandBookEntity handBookEntity) {
        ModelAndView mav = new ModelAndView("redirect:/admin/handbook");
        if (handBookEntity.getTitle().equals("") || handBookEntity.getCategory().equals("")) {
            mav.addObject("error", "Lỗi thêm khi thêm cẩm nang");
            return mav;
        }
        handBookService.createHandBook(handBookEntity);
        mav.addObject("success", "Thêm cẩm nang thành công!");
        return mav;
    }

    @PostMapping("/{idHandBook}")
    public ModelAndView addHandBookDetailForHandBook(@ModelAttribute HandBookDetailEntity handBookDetailEntity,@PathVariable("idHandBook") Long idHandBook) {
        ModelAndView mav = new ModelAndView("redirect:/admin/handbook");
        if(handBookDetailEntity.getTitle().equals("") || handBookDetailEntity.getContent().equals("")) {
            mav.addObject("error", "Lỗi thêm khi thêm mục");
            return mav;
        }
        handBookDetailService.createHandBookDetailForHandBook(idHandBook,handBookDetailEntity);
        mav.addObject("success", "Thêm mục thành công!");
        return mav;
    }

    @DeleteMapping("/handbookdetail/{idHandBookDetail}")
    public ModelAndView deleteHandBookDetail(@PathVariable("idHandBookDetail") Long idHandBookDetail) {
        ModelAndView mav = new ModelAndView("redirect:/admin/handbook");
        handBookDetailService.deleteHandBookDetail(idHandBookDetail);
        return mav;
    }

    @PutMapping("/{idHandBook}")
    public ModelAndView updateHandBook(@ModelAttribute HandBookEntity handBookEntity,@PathVariable("idHandBook") Long idHandBook) {
        ModelAndView mav = new ModelAndView("redirect:/admin/handbook");
        handBookService.updateHandBook(handBookEntity,idHandBook);
        return mav;
    }

    @PutMapping("/handbookdetail/{idHandBookDetail}")
    public ModelAndView updateHandBookDetail(@ModelAttribute HandBookDetailEntity handBookDetailEntity,@PathVariable("idHandBookDetail") Long idHandBookDetail) {
        ModelAndView mav = new ModelAndView("redirect:/admin/handbook");
        handBookDetailService.updateHandBookDetail(idHandBookDetail,handBookDetailEntity);
        return mav;
    }
}

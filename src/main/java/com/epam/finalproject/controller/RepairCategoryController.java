package com.epam.finalproject.controller;

import com.epam.finalproject.currency.context.CurrencyUnitContextHolder;
import com.epam.finalproject.dto.RepairCategoryDTO;
import com.epam.finalproject.framework.web.annotation.Controller;
import com.epam.finalproject.framework.web.annotation.GetMapping;
import com.epam.finalproject.framework.web.annotation.PathVariable;
import com.epam.finalproject.framework.web.annotation.RequestMapping;
import com.epam.finalproject.model.entity.AppCurrency;
import com.epam.finalproject.service.AppCurrencyService;
import com.epam.finalproject.service.RepairCategoryService;
import com.epam.finalproject.service.RepairWorkService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;

import java.util.List;

@Controller
@RequestMapping("/category")
public class RepairCategoryController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RepairCategoryController.class);
    RepairCategoryService repairCategoryService;

    RepairWorkService repairWorkService;

    AppCurrencyService appCurrencyService;

    public RepairCategoryController(RepairCategoryService repairCategoryService, RepairWorkService repairWorkService,
            AppCurrencyService appCurrencyService) {
        this.repairCategoryService = repairCategoryService;
        this.repairWorkService = repairWorkService;
        this.appCurrencyService = appCurrencyService;
    }

    @GetMapping("")
    String allCategoryPage(HttpServletRequest request) {
        List<RepairCategoryDTO> categories = repairCategoryService.findAll();
        AppCurrency currency = appCurrencyService.findByCode(CurrencyUnitContextHolder.getCurrencyUnit().getCurrencyCode());
        request.setAttribute("categories",categories);
        request.setAttribute("currency",currency);
        return "categories";
    }

    @GetMapping("/{keyName}")
    String allCategoryPage(HttpServletRequest request,@PathVariable("keyName") String keyName) {
        RepairCategoryDTO category = repairCategoryService.findByKeyName(keyName);
        AppCurrency currency = appCurrencyService.findByCode(CurrencyUnitContextHolder.getCurrencyUnit().getCurrencyCode());
        request.setAttribute("category",category);
        request.setAttribute("currency",currency);
        return "category";
    }


}

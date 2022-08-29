package com.epam.finalproject.controller;

import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.dto.ReceiptResponseDTO;
import com.epam.finalproject.dto.ReceiptStatusFlowDTO;
import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.PageRequest;
import com.epam.finalproject.framework.security.UserDetails;
import com.epam.finalproject.framework.security.annotation.PreAuthorize;
import com.epam.finalproject.framework.web.annotation.*;
import com.epam.finalproject.request.search.ReceiptWithMasterSearchRequest;
import com.epam.finalproject.service.ReceiptResponseService;
import com.epam.finalproject.service.ReceiptStatusFlowService;
import com.epam.finalproject.service.SearchService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/master")
@PreAuthorize("hasRole('MASTER') || hasRole('ADMIN')")
public class MasterController {

    public static final String ACTIVE = "active";

    public static final String MASTER_VIEW = "cabinet";
    public static final String TYPE = "type";
    public static final String MASTER = "master";
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(MasterController.class);

    SearchService searchService;

    ReceiptResponseService receiptResponseService;

    ReceiptStatusFlowService receiptStatusFlowService;

    public MasterController(SearchService searchService, ReceiptResponseService receiptResponseService,
            ReceiptStatusFlowService receiptStatusFlowService) {
        this.searchService = searchService;
        this.receiptResponseService = receiptResponseService;
        this.receiptStatusFlowService = receiptStatusFlowService;
    }


    @GetMapping("/orders")
    String ordersPage(HttpServletRequest request, UserDetails userDetails, @RequestObject @Valid ReceiptWithMasterSearchRequest searchRequest) {
        Page<ReceiptDTO> receipts = searchService.findBySearch(searchRequest, userDetails.getUsername());
        List<ReceiptStatusFlowDTO> flows = receiptStatusFlowService.listAllAvailableForUser(userDetails.getUsername());
        request.setAttribute("flows",flows);
        request.setAttribute("search", searchRequest);
        request.setAttribute("receipts", receipts);
        request.setAttribute(ACTIVE, "orders");
        request.setAttribute(TYPE, MASTER);
        return MASTER_VIEW;
    }

    @GetMapping("/responses")
    String responsesPage(HttpServletRequest request, UserDetails userDetails, @RequestParam(value = "page",required = false) Integer page) {
        int actualPage = Optional.ofNullable(page).orElse(0);
        Page<ReceiptResponseDTO> responses = receiptResponseService.findByMasterUsername(userDetails.getUsername(), PageRequest.of(actualPage, 5));
        request.setAttribute("responses", responses);
        request.setAttribute(ACTIVE, "responses");
        request.setAttribute(TYPE, MASTER);
        return MASTER_VIEW;
    }
}

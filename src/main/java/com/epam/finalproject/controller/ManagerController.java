package com.epam.finalproject.controller;

import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.dto.ReceiptResponseDTO;
import com.epam.finalproject.dto.ReceiptStatusFlowDTO;
import com.epam.finalproject.dto.UserDTO;
import com.epam.finalproject.framework.data.Page;
import com.epam.finalproject.framework.data.PageRequest;
import com.epam.finalproject.framework.security.UserDetails;
import com.epam.finalproject.framework.security.annotation.PreAuthorize;
import com.epam.finalproject.framework.web.annotation.*;
import com.epam.finalproject.request.search.MasterSearchRequest;
import com.epam.finalproject.request.search.ReceiptSearchRequest;
import com.epam.finalproject.request.search.UserSearchRequest;
import com.epam.finalproject.service.ReceiptResponseService;
import com.epam.finalproject.service.ReceiptStatusFlowService;
import com.epam.finalproject.service.SearchService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/manager")
@PreAuthorize("hasRole('MANAGER')")
public class ManagerController {

    public static final String ACTIVE = "active";

    public static final String MANAGER_VIEW = "cabinet";

    public static final String TYPE = "type";
    public static final String MANAGER = "manager";
    public static final String SEARCH = "search";
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ManagerController.class);

    SearchService searchService;
    ReceiptResponseService receiptResponseService;
    ReceiptStatusFlowService receiptStatusFlowService;

    public ManagerController(SearchService searchService, ReceiptResponseService receiptResponseService,
            ReceiptStatusFlowService receiptStatusFlowService) {
        this.searchService = searchService;
        this.receiptResponseService = receiptResponseService;
        this.receiptStatusFlowService = receiptStatusFlowService;
    }


    @GetMapping("/orders")
    String ordersPage(HttpServletRequest request, UserDetails userDetails,@RequestObject @Valid ReceiptSearchRequest receiptSearchRequest) {
        Page<ReceiptDTO> receipts = searchService.findBySearch(receiptSearchRequest);
        List<ReceiptStatusFlowDTO> flows = receiptStatusFlowService.listAllAvailableForUser(userDetails.getUsername());
        request.setAttribute("flows",flows);
        request.setAttribute(SEARCH,receiptSearchRequest);
        request.setAttribute("receipts", receipts);
        request.setAttribute(ACTIVE, "orders");
        request.setAttribute(TYPE, MANAGER);
        return MANAGER_VIEW;
    }

    @GetMapping("/users")
    String usersPage(HttpServletRequest request,@RequestObject @Valid UserSearchRequest userSearchRequest) {
        Page<UserDTO> users = searchService.findBySearch(userSearchRequest);
        request.setAttribute(SEARCH,userSearchRequest);
        request.setAttribute("users", users);
        request.setAttribute(ACTIVE, "users");
        request.setAttribute(TYPE, MANAGER);
        return MANAGER_VIEW;
    }

    @GetMapping("/masters")
    String mastersPage(HttpServletRequest request,@RequestObject @Valid MasterSearchRequest masterSearchRequest) {
        Page<UserDTO> masters = searchService.findBySearch(masterSearchRequest);
       request.setAttribute(SEARCH,masterSearchRequest);
       request.setAttribute("masters", masters);
       request.setAttribute(ACTIVE, "masters");
       request.setAttribute(TYPE, MANAGER);
        return MANAGER_VIEW;
    }
    @GetMapping("/responses")
    String responsesPage(HttpServletRequest request,@RequestParam(required = false) Integer page) {
        int actualPage = Optional.ofNullable(page).orElse(0);
        Page<ReceiptResponseDTO> responses = receiptResponseService.findAll(PageRequest.of(actualPage,5));
        request.setAttribute("responses", responses);
        request.setAttribute(ACTIVE, "responses");
        request.setAttribute(TYPE, MANAGER);
        return MANAGER_VIEW;
    }
}

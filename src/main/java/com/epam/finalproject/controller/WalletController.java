package com.epam.finalproject.controller;

import com.epam.finalproject.dto.WalletDTO;
import com.epam.finalproject.framework.security.UserDetails;
import com.epam.finalproject.framework.security.annotation.PreAuthorize;
import com.epam.finalproject.framework.web.annotation.*;
import com.epam.finalproject.request.AddMoneyRequest;
import com.epam.finalproject.request.CreateWalletRequest;
import com.epam.finalproject.service.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(WalletController.class);
    WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/addMoney")
    @PreAuthorize("hasRole('MANAGER')")
    String addMoney(HttpServletRequest request, @RequestObject @Valid AddMoneyRequest moneyRequest,
            @RequestParam("redirectUrl") String redirectUrl) {
        WalletDTO wallet = walletService.addMoney(moneyRequest);
        log.trace("Wallet updated {}", wallet);
        return "redirect:" + redirectUrl;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER')")
    String createWallet(HttpServletRequest request, @RequestObject @Valid CreateWalletRequest createWalletRequest,
            @RequestParam("redirectUrl") String redirectUrl,
            UserDetails userDetails) {
        WalletDTO wallet = walletService.create(createWalletRequest,userDetails.getUsername());
        log.trace("Wallet created {}", wallet);
        return "redirect:" + redirectUrl;
    }

}

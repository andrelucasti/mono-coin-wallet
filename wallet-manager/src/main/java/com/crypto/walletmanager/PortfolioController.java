package com.crypto.walletmanager;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    @GetMapping
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("WalletManager Module");
    }
}

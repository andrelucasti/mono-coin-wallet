package com.crypto.wallettrade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @GetMapping
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("WalletTrade Module");
    }
}

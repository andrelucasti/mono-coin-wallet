package com.crypto.walletmanager.portfolio;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.UUID;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    private final CreatePortfolio createPortfolio;

    public PortfolioController(CreatePortfolio createPortfolio) {
        this.createPortfolio = createPortfolio;
    }

    @GetMapping
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("WalletManager Module");
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<PortfolioResponse> create(@RequestHeader UUID userId,
                                       @RequestBody PortfolioRequest portfolioRequest){

        var portfolio = new Portfolio(portfolioRequest.name(), userId);
        createPortfolio.execute(portfolio);

        return ResponseEntity.status(HttpStatus.CREATED).body(new PortfolioResponse(portfolio.name(), userId));
    }
}

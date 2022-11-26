package com.crypto.walletmanager.business.portfolio;

import org.springframework.stereotype.Service;

@Service
public class CreatePortfolio {
    private final PortfolioRepository portfolioRepository;
    private final PortfolioIntegration portfolioIntegration;

    public CreatePortfolio(PortfolioRepository portfolioRepository,
                           PortfolioIntegration portfolioIntegration) {
        this.portfolioRepository = portfolioRepository;
        this.portfolioIntegration = portfolioIntegration;
    }

    public void execute(Portfolio portfolio){
        portfolioRepository.save(portfolio);

        //TODO must be async and when transaction to be committed
        var newPortfolio = portfolioRepository
                .findByUserIdAndName(portfolio.userId(), portfolio.name());
        portfolioIntegration.send(newPortfolio);
    }
}

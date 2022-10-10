package com.crypto.walletmanager.portfolio;

import org.springframework.stereotype.Service;

@Service
public class CreatePortfolio {
    private final PortfolioDAO portfolioDAO;

    public CreatePortfolio(PortfolioDAO portfolioDAO) {
        this.portfolioDAO = portfolioDAO;
    }

    public Portfolio execute(Portfolio portfolio){
        return portfolioDAO.save(portfolio);
    }
}

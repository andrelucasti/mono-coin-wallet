package com.crypto.walletmanager.portfolio;

public class CreatePortfolio {
    private final PortfolioDAO portfolioDAO;

    public CreatePortfolio(PortfolioDAO portfolioDAO) {
        this.portfolioDAO = portfolioDAO;
    }

    public void execute(Portfolio portfolio){
        portfolioDAO.save(portfolio);
    }
}

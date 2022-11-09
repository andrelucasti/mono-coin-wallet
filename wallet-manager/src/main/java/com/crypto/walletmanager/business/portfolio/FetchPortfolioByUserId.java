package com.crypto.walletmanager.business.portfolio;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FetchPortfolioByUserId {
    private final PortfolioDAO portfolioDAO;

    public FetchPortfolioByUserId(PortfolioDAO portfolioDAO) {
        this.portfolioDAO = portfolioDAO;
    }

    public List<Portfolio> execute(UUID userId){
        return this.portfolioDAO.findBy(userId);
    }
}
package com.crypto.walletmanager.business.portfolio;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FetchPortfolioByUserId {
    private final PortfolioRepository portfolioRepository;

    public FetchPortfolioByUserId(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public List<Portfolio> execute(UUID userId){
        return this.portfolioRepository.findBy(userId);
    }
}
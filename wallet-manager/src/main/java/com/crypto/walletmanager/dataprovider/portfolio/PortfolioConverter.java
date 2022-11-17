package com.crypto.walletmanager.dataprovider.portfolio;

import com.crypto.walletmanager.business.portfolio.Portfolio;
import org.springframework.stereotype.Component;

@Component
public class PortfolioConverter {

    public Portfolio fromModelToEntity(PortfolioEntity portfolioEntity){
        return new Portfolio(portfolioEntity.getName(), portfolioEntity.getUserId(), portfolioEntity.getId());
    }

    public PortfolioEntity fromEntityToModel(Portfolio portfolio){
        return new PortfolioEntity(portfolio.name(), portfolio.userId());
    }
}

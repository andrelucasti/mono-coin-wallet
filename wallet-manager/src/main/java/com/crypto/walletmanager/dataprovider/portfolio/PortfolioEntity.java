package com.crypto.walletmanager.dataprovider.portfolio;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Entity
@Table(name = "PORTFOLIO")
public class PortfolioEntity {

    @Id
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "USER_ID")
    private UUID userId;

    public PortfolioEntity(){}

    public PortfolioEntity(String name, UUID userId){
        this.id = UUID.randomUUID();
        this.name = name;
        this.userId = userId;
    }
}

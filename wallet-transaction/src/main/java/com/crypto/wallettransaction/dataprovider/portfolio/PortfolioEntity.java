package com.crypto.wallettransaction.dataprovider.portfolio;

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
    @Column(name = "ID")
    private UUID id;

    @Column(name = "NAME")
    private String name;


    public PortfolioEntity(){}

    public PortfolioEntity(UUID id, String name){
        this.id = id;
        this.name = name;
    }
}

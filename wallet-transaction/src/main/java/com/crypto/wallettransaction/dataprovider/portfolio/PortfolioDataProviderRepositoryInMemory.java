package com.crypto.wallettransaction.dataprovider.portfolio;

import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@VisibleForTesting
public class PortfolioDataProviderRepositoryInMemory implements PortfolioDataProviderRepository{
    private final Map<UUID, PortfolioEntity> dbMemory = new HashMap<>();

    @Override
    public void save(PortfolioEntity entity) {
        dbMemory.put(entity.getId(), entity);
    }

    @Override
    public List<PortfolioEntity> findAll() {
        return dbMemory.values().stream().toList();
    }

    @Override
    public Optional<PortfolioEntity> findById(UUID id) {
        return Optional.ofNullable(dbMemory.get(id));
    }
}

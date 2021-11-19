package com.scoperetail.fusion.adapter.dedupe.cassandra.repository.custom;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(
        value = "fusion.dedupe.dbType",
        havingValue = "Cassandra",
        matchIfMissing = false)
public interface DedupeKeyRepositoryCustom {

  Boolean insertIfNotExist(String logKey);
}

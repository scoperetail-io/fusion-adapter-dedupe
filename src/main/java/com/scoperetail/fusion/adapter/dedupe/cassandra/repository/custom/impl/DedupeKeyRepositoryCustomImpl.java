package com.scoperetail.fusion.adapter.dedupe.cassandra.repository.custom.impl;

import com.scoperetail.fusion.adapter.dedupe.cassandra.entity.DedupeKeyEntity;
import com.scoperetail.fusion.adapter.dedupe.cassandra.repository.custom.DedupeKeyRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.EntityWriteResult;
import org.springframework.data.cassandra.core.InsertOptions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
@ConditionalOnProperty(
        value = "fusion.dedupe.dbType",
        havingValue = "Cassandra",
        matchIfMissing = false)
public class DedupeKeyRepositoryCustomImpl implements DedupeKeyRepositoryCustom {

  @Autowired private CassandraTemplate template;

  @Value("${fusion.dedupe.ttl:0}")
  private Integer ttl;

  @Override
  public Boolean insertIfNotExist(String logKey) {
    final InsertOptions insertOptions =
        InsertOptions.builder().ifNotExists(true).ttl(this.ttl).build();
    final DedupeKeyEntity entity =
        DedupeKeyEntity.builder().log_Key(logKey).create_Ts(LocalDateTime.now()).build();
    final EntityWriteResult<DedupeKeyEntity> record = template.insert(entity, insertOptions);
    return record.wasApplied();
  }
}

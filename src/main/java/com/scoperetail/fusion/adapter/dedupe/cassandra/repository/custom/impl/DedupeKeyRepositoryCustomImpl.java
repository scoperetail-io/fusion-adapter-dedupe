package com.scoperetail.fusion.adapter.dedupe.cassandra.repository.custom.impl;

/*-
 * *****
 * fusion-adapter-dedupe
 * -----
 * Copyright (C) 2018 - 2021 Scope Retail Systems Inc.
 * -----
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * =====
 */

import com.scoperetail.fusion.adapter.dedupe.cassandra.entity.DedupeKeyEntity;
import com.scoperetail.fusion.adapter.dedupe.cassandra.repository.custom.DedupeKeyRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.EntityWriteResult;
import org.springframework.data.cassandra.core.InsertOptions;

import java.time.LocalDateTime;

public class DedupeKeyRepositoryCustomImpl implements DedupeKeyRepositoryCustom {

  @Autowired private CassandraTemplate template;

  @Value("${db.cassandra.ttl:0}")
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

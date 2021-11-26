package com.scoperetail.fusion.adapter.dedupe.cassandra.config;

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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@ConditionalOnProperty(
    value = "fusion.dedupe.dbType",
    havingValue = "Cassandra",
    matchIfMissing = false)
@EnableCassandraRepositories(
    basePackages = "com.scoperetail.fusion.adapter.dedupe.cassandra.repository")
public class FusionCassandraDedupeConfig extends AbstractCassandraConfiguration {

  @Value("${spring.data.cassandra.keyspace-name}")
  private String keySpace;

  @Value("${spring.data.cassandra.contact-points}")
  private String contactPoints;

  @Value("${spring.data.cassandra.local-datacenter}")
  private String localDatacenter;

  @Value("${spring.data.cassandra.port}")
  private int port;

  @Value("${spring.data.cassandra.username}")
  private String username;

  @Value("${spring.data.cassandra.password}")
  private String password;

  @Override
  protected String getKeyspaceName() {
    return keySpace;
  }

  @Bean
  @Override
  public CqlSessionFactoryBean cassandraSession() {
    CqlSessionFactoryBean cassandraSession = new CqlSessionFactoryBean();
    cassandraSession.setContactPoints(contactPoints);
    cassandraSession.setKeyspaceName(this.getKeyspaceName());
    cassandraSession.setLocalDatacenter(localDatacenter);
    cassandraSession.setPort(port);
    cassandraSession.setUsername(username);
    cassandraSession.setPassword(password);
    return cassandraSession;
  }
}

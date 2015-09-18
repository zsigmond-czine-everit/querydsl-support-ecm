/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.everit.persistence.querydsl.support.ri.osgi.ecm.internal;

import javax.sql.DataSource;

import org.everit.osgi.ecm.annotation.Activate;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.ConfigurationPolicy;
import org.everit.osgi.ecm.annotation.Service;
import org.everit.osgi.ecm.annotation.ServiceRef;
import org.everit.osgi.ecm.annotation.attribute.StringAttribute;
import org.everit.osgi.ecm.annotation.attribute.StringAttributes;
import org.everit.osgi.ecm.extender.ECMExtenderConstants;
import org.everit.persistence.querydsl.support.QuerydslCallable;
import org.everit.persistence.querydsl.support.QuerydslSupport;
import org.everit.persistence.querydsl.support.ri.QuerydslSupportImpl;
import org.everit.persistence.querydsl.support.ri.osgi.ecm.QuerydslSupportConstants;
import org.osgi.framework.Constants;

import com.mysema.query.sql.Configuration;

import aQute.bnd.annotation.headers.ProvideCapability;

/**
 * ECM component for {@link QuerydslSupport} interface based on {@link QuerydslSupportImpl}.
 */
@Component(componentId = QuerydslSupportConstants.SERVICE_FACTORYPID_QUERYDSL_SUPPORT,
    configurationPolicy = ConfigurationPolicy.FACTORY)
@ProvideCapability(ns = ECMExtenderConstants.CAPABILITY_NS_COMPONENT,
    value = ECMExtenderConstants.CAPABILITY_ATTR_CLASS + "=${@class}")
@StringAttributes({
    @StringAttribute(attributeId = Constants.SERVICE_DESCRIPTION,
        defaultValue = QuerydslSupportConstants.DEFAULT_SERVICE_DESCRIPTION) })
@Service
public class QuerydslSupportComponent implements QuerydslSupport {

  /**
   * Querydsl configuration.
   */
  private Configuration configuration;

  /**
   * DataSource for database connections.
   */
  private DataSource dataSource;

  private QuerydslSupport querydslSupport;

  @Activate
  public void activate() {
    querydslSupport = new QuerydslSupportImpl(configuration, dataSource);
  }

  @Override
  public <R> R execute(final QuerydslCallable<R> callable) {
    return querydslSupport.execute(callable);
  }

  @ServiceRef(attributeId = QuerydslSupportConstants.ATTR_CONFIGURATION_TARGET,
      defaultValue = "")
  public void setConfiguration(final Configuration configuration) {
    this.configuration = configuration;
  }

  @ServiceRef(attributeId = QuerydslSupportConstants.ATTR_DATASOURCE_TARGET,
      defaultValue = "")
  public void setDataSource(final DataSource dataSource) {
    this.dataSource = dataSource;
  }
}

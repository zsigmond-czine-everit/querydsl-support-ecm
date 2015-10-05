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
package org.everit.persistence.querydsl.support.ri.ecm.internal;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.sql.DataSource;

import org.everit.osgi.ecm.annotation.Activate;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.ConfigurationPolicy;
import org.everit.osgi.ecm.annotation.Deactivate;
import org.everit.osgi.ecm.annotation.ManualService;
import org.everit.osgi.ecm.annotation.ServiceRef;
import org.everit.osgi.ecm.annotation.attribute.StringAttribute;
import org.everit.osgi.ecm.annotation.attribute.StringAttributes;
import org.everit.osgi.ecm.component.ComponentContext;
import org.everit.osgi.ecm.extender.ECMExtenderConstants;
import org.everit.persistence.querydsl.support.QuerydslSupport;
import org.everit.persistence.querydsl.support.ri.QuerydslSupportImpl;
import org.everit.persistence.querydsl.support.ri.ecm.QuerydslSupportConstants;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;

import com.mysema.query.sql.Configuration;

import aQute.bnd.annotation.headers.ProvideCapability;

/**
 * ECM component for {@link QuerydslSupport} interface based on {@link QuerydslSupportImpl}.
 */
@Component(componentId = QuerydslSupportConstants.SERVICE_FACTORYPID_QUERYDSL_SUPPORT,
    configurationPolicy = ConfigurationPolicy.FACTORY,
    label = "Everit Querydsl Support",
    description = "Component that registers a QuerydslSupport OSGi service.")
@ProvideCapability(ns = ECMExtenderConstants.CAPABILITY_NS_COMPONENT,
    value = ECMExtenderConstants.CAPABILITY_ATTR_CLASS + "=${@class}")
@StringAttributes({
    @StringAttribute(attributeId = Constants.SERVICE_DESCRIPTION,
        defaultValue = QuerydslSupportConstants.DEFAULT_SERVICE_DESCRIPTION,
        priority = QuerydslSupportComponent.P00_SERVICE_DESCRIPTION, label = "Service Description",
        description = "The description of this component configuration. It is used to easily "
            + "identify the service registered by this component.") })
@ManualService(QuerydslSupport.class)
public class QuerydslSupportComponent {

  public static final int P00_SERVICE_DESCRIPTION = 0;

  public static final int P01_DATA_SOURCE = 1;

  public static final int P02_CONFIGURATION = 2;

  /**
   * Querydsl configuration.
   */
  private Configuration configuration;

  /**
   * DataSource for database connections.
   */
  private DataSource dataSource;

  private ServiceRegistration<QuerydslSupport> serviceRegistration;

  /**
   * Component activator method.
   */
  @Activate
  public void activate(final ComponentContext<QuerydslSupportComponent> componentContext) {
    QuerydslSupport querydslSupport = new QuerydslSupportImpl(configuration, dataSource);

    Dictionary<String, Object> serviceProperties =
        new Hashtable<String, Object>(componentContext.getProperties());
    serviceRegistration =
        componentContext.registerService(QuerydslSupport.class, querydslSupport, serviceProperties);
  }

  /**
   * Component deactivate method.
   */
  @Deactivate
  public void deactivate() {
    if (serviceRegistration != null) {
      serviceRegistration.unregister();
    }
  }

  @ServiceRef(attributeId = QuerydslSupportConstants.ATTR_CONFIGURATION_TARGET,
      defaultValue = "", attributePriority = P02_CONFIGURATION,
      label = "Configuration OSGi filter",
      description = "OSGi filter for Querydsl Configuration service instance.")
  public void setConfiguration(final Configuration configuration) {
    this.configuration = configuration;
  }

  @ServiceRef(attributeId = QuerydslSupportConstants.ATTR_DATA_SOURCE_TARGET,
      defaultValue = "", attributePriority = P01_DATA_SOURCE,
      label = "DataSource OSGi filter",
      description = "OSGi filter for javax.sql.DataSource OSGi service")
  public void setDataSource(final DataSource dataSource) {
    this.dataSource = dataSource;
  }
}

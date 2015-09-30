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
package org.everit.persistence.querydsl.support.ri.ecm;

/**
 * Constants that make it possible to configure the Support component programmatically.
 */
public final class QuerydslSupportConstants {

  /**
   * OSGi service filter for the Querydsl configuration service.
   */
  public static final String ATTR_CONFIGURATION_TARGET = "configuration.target";

  /**
   * OGSi service filter for the DataSource.
   */
  public static final String ATTR_DATA_SOURCE_TARGET = "dataSource.target";

  public static final String DEFAULT_SERVICE_DESCRIPTION = "Querydsl Support Component";

  /**
   * The name of the component.
   */
  public static final String SERVICE_FACTORYPID_QUERYDSL_SUPPORT =
      "org.everit.persistence.querydsl.support.QuerydslSupport";

  private QuerydslSupportConstants() {
  }
}

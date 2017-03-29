/*
 * Copyright (c) 2010 - 2015 Norwegian Agency for Pupblic Government and eGovernment (Difi)
 *
 * This file is part of Oxalis.
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission
 * - subsequent versions of the EUPL (the "Licence"); You may not use this work except in compliance with the Licence.
 *
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl5
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the Licence
 *  is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the Licence for the specific language governing permissions and limitations under the Licence.
 *
 */

package no.balder.heimdall.persistence;

import org.apache.commons.dbcp2.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.sql.DataSource;

/**
 * Thread safe and singleton. I.e. will always return the same DataSource.
 *
 * @author steinar
 *         Date: Dec. 27, 2016
 *         Time: 11.20
 */
public class MysqlDataSourceFactory implements DbcpDataSourceFactory {

    public static final Logger log = LoggerFactory.getLogger(MysqlDataSourceFactory.class);

    private volatile DataSource dataSource;

    @Inject
    public MysqlDataSourceFactory() {
    }

    @Override
    public DataSource getDataSource() {
        DataSource result = dataSource;
        if (result == null) {
            synchronized (this) {
                result = dataSource;
                if (result == null) {
                    dataSource = result = configureAndCreateDataSource();
                }
            }
        }
        return dataSource;
    }


    /**
     * Creates a DataSource with connection pooling as provided by Apache DBCP
     *
     * @return a DataSource
     */
    DataSource configureAndCreateDataSource() {

        log.debug("Configuring DataSource wrapped in a Database Connection Pool, using custom loader");
        BasicDataSource ds = new BasicDataSource();

        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUsername("heimdall");
        ds.setPassword("apabase");
        ds.setUrl("jdbc:mysql://localhost/heimdall");
        ds.setInitialSize(5);

        return ds;
    }
}

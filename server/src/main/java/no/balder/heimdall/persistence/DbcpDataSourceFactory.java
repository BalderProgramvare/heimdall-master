package no.balder.heimdall.persistence;

import javax.sql.DataSource;

/**
 * @author steinar
 *         Date: 27.12.2016
 *         Time: 11.20
 */
public interface DbcpDataSourceFactory {
    DataSource getDataSource();
}

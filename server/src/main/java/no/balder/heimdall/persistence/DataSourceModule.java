package no.balder.heimdall.persistence;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import javax.sql.DataSource;


/**
 * @author steinar
 *         Date: 27.12.2016
 *         Time: 12.23
 */
public class DataSourceModule extends AbstractModule{

    @Override
    protected void configure() {
        bind(DbcpDataSourceFactory.class).to(MysqlDataSourceFactory.class).in(Singleton.class);
    }

    @Provides
    DataSource providesDataSource(DbcpDataSourceFactory factory) {
        return factory.getDataSource();
    }
}

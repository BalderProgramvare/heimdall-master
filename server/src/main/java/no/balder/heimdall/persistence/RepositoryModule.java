package no.balder.heimdall.persistence;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;

/**
 * @author steinar
 *         Date: 27.12.2016
 *         Time: 20.52
 */
public class RepositoryModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(CheckInRepository.class);
    }

    @Provides
    QueryRunner proivideQueryRunner(DataSource dataSource) {
        return new QueryRunner(dataSource);
    }
}

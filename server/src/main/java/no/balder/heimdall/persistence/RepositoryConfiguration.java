package no.balder.heimdall.persistence;

import java.net.URI;
import java.nio.file.Path;

/**
 * @author steinar
 *         Date: 27.10.2016
 *         Time: 13.07
 */
public interface RepositoryConfiguration {

    URI getJdbcConnectionUri();

    String getJdbcDriverClassName();

    String getJdbcUsername();

    String getJdbcPassword();

    String getValidationQuery();
}

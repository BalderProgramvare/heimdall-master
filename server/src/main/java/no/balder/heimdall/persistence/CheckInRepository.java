package no.balder.heimdall.persistence;

import com.google.inject.Inject;
import no.balder.heimdall.CheckIn;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * @author steinar
 *         Date: 27.12.2016
 *         Time: 20.27
 */
public class CheckInRepository {

    private final QueryRunner queryRunner;
    private final BeanHandler<CheckIn> beanHandler = new BeanHandler<>(CheckIn.class, new BasicRowProcessor(new CheckInBeanProcessor()));

    @Inject
    public CheckInRepository(QueryRunner queryRunner) {
        this.queryRunner = queryRunner;
    }


    public Long insert(CheckIn checkIn) throws IllegalStateException {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(checkIn.getTimeStamp());
        String timeZone = zonedDateTime.getZone().getId();

        try {
            Long generatedId = queryRunner.insert("INSERT INTO checkin (uuid, lat, lon, tstamp, tzone) values(?,?,?,?,?)",
                    new ScalarHandler<Long>(),
                    checkIn.getUuid(),
                    checkIn.getLatitude(),
                    checkIn.getLongitude(),
                    Date.from(zonedDateTime.toInstant()),
                    timeZone);
            return generatedId;
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to insert entry into database. " + e.getMessage(), e);
        }
    }

    public Optional<CheckIn> findByUuid(String uuid) {
        String sql = "select * from checkin where uuid=?";
        CheckIn checkIn = null;
        try {
            checkIn = queryRunner.query(sql, beanHandler, uuid);
            return Optional.ofNullable(checkIn);
        } catch (SQLException e) {
            throw new IllegalStateException("Error during exeution of '" + sql + "', cause:" + e.getMessage(), e);
        }
    }


}

package no.balder.heimdall.persistence;

import com.google.inject.Inject;
import no.balder.heimdall.CheckIn;
import no.balder.heimdall.ObjectMother;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.testng.Assert.*;

/**
 * @author steinar
 *         Date: 27.12.2016
 *         Time: 12.23
 */
@Guice(modules = DataSourceModule.class)
public class MysqlDataSourceFactoryTest {

    @Inject
    DataSource dataSource;

    @Inject
    DataSource ds2;

    @Test
    public void testGetDataSource() throws Exception {
        assertNotNull(dataSource);
        assertEquals(dataSource,ds2,"DataSource should be singleton");
    }

    @Test
    public void testInsertSampleRow() throws SQLException {

        CheckIn checkIn = ObjectMother.sampleCheckIn();
        QueryRunner qr = new QueryRunner(dataSource);
        BeanHandler<CheckIn> beanHandler = new BeanHandler<>(CheckIn.class);
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now.getZone().toString());
        Long generatedId = qr.insert("INSERT INTO checkIn (uuid, lat, lon, tstamp, tzone) values(?,?,?,?,?)",
                new ScalarHandler<Long>(),
                checkIn.getUuid(), checkIn.getLatitude(), checkIn.getLongitude(),
                Date.from(now.toInstant()),
                now.getZone().getId());

        CheckIn ch2 = qr.query("select * from checkIn where id=?", beanHandler, generatedId);
        assertEquals(ch2.getUuid(), checkIn.getUuid());
    }
}
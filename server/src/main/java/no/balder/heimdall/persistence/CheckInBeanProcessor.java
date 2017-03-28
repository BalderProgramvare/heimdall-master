package no.balder.heimdall.persistence;

import no.balder.heimdall.CheckIn;
import org.apache.commons.dbutils.BeanProcessor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author steinar
 *         Date: 27.12.2016
 *         Time: 21.11
 */
public class CheckInBeanProcessor extends BeanProcessor {

    @Override
    public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {

        CheckIn checkIn = new CheckIn();
        checkIn.setUuid(rs.getString("uuid"));
        checkIn.setLatitude(rs.getString("lat"));
        checkIn.setLongitude(rs.getString("lon"));

        Timestamp ts = rs.getTimestamp("tstamp");
        String zoneId = rs.getString("tzone");
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(ts.toInstant(), ZoneId.of(zoneId));
        checkIn.setTimeStamp( zonedDateTime.toString());

        return (T) checkIn;
    }
}

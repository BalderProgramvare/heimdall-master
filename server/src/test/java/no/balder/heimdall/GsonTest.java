package no.balder.heimdall;

import com.google.gson.Gson;
import org.testng.annotations.Test;

import java.time.*;
import java.util.Date;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author steinar
 *         Date: 26.12.2016
 *         Time: 15.12
 */
public class GsonTest {

    @Test
    public void testGson() throws Exception {

        Gson gson = new Gson();
        ZonedDateTime.now().toString();
        CheckIn checkIn = new CheckIn(UUID.randomUUID().toString(), "61.148587", "10.654484", ZonedDateTime.now().toString());

        String s = gson.toJson(checkIn);

        System.out.println(s);
        CheckIn checkIn1 = gson.fromJson(s, CheckIn.class);

        assertEquals(checkIn, checkIn1);

    }

    @Test
    public void testFromStringToZonedDateTime() throws Exception {
        String dt = "2016-12-27 21:16:29";
        String zone = "Europe/Oslo";

        Date date = new Date();

        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        System.out.println(offsetDateTime);

        OffsetDateTime dt2 = OffsetDateTime.parse("2016-12-28T09:24:18.778+00:00");
        System.out.println(dt2.toString());
        ZoneOffset zoneOffset = ZoneOffset.of("+01:00");
        ZonedDateTime dt3 = dt2.atZoneSameInstant(zoneOffset);
        System.out.println(dt3);


    }

}

package no.balder.heimdall;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author steinar
 *         Date: 27.12.2016
 *         Time: 18.31
 */
public class ObjectMother {


    public static CheckIn sampleCheckIn() {

        CheckIn checkIn = new CheckIn(UUID.randomUUID().toString(),
                "61.148576",
                "10.654695",
                ZonedDateTime.now().toString());
        return checkIn;
    }
}

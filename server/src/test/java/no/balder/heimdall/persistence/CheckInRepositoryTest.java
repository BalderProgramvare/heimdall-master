package no.balder.heimdall.persistence;

import com.google.inject.Inject;
import no.balder.heimdall.CheckIn;
import no.balder.heimdall.ObjectMother;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.*;

/**
 * @author steinar
 *         Date: 27.12.2016
 *         Time: 20.48
 */
@Guice(modules = {DataSourceModule.class, RepositoryModule.class})
public class CheckInRepositoryTest {


    @Inject
    CheckInRepository checkInRepository;

    @Test
    public void testInsert() throws Exception {
        CheckIn checkIn = ObjectMother.sampleCheckIn();
        Long insert = checkInRepository.insert(checkIn);

        Optional<CheckIn> result = checkInRepository.findByUuid(checkIn.getUuid());

        assertTrue(result.isPresent(), "No entries found in the database");
        CheckIn byUuid = result.get();

        assertEquals(byUuid.getUuid(), checkIn.getUuid());
        assertEquals(byUuid.getLatitude(), checkIn.getLatitude(), "Invalid Latitude");
        assertEquals(byUuid.getLongitude(), checkIn.getLongitude(), "Invalid Longitude");

        System.out.println(checkIn.getTimeStamp());
        System.out.println(byUuid.getTimeStamp().toString());
    }


    @Test
    public void testFindByUuid() throws Exception {

    }

}
package no.balder.heimdall;

import com.google.inject.Inject;
import no.balder.heimdall.persistence.CheckInRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author steinar
 *         Date: 27.12.2016
 *         Time: 20.26
 */
public class CheckInUseCase {

    public static final Logger log = LoggerFactory.getLogger(CheckInUseCase.class);
    private final CheckInRepository checkInRepository;

    @Inject
    public CheckInUseCase(CheckInRepository checkInRepository) {
        this.checkInRepository = checkInRepository;
    }

    public Long checkIn(CheckIn checkIn) {
        try {
            Long inserted = checkInRepository.insert(checkIn);
            return inserted;
        } catch (Exception e) {
            log.error("Inserting checkIn entry failed: " + e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }
}

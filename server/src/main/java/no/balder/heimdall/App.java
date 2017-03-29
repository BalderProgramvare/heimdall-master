package no.balder.heimdall;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import no.balder.heimdall.persistence.DataSourceModule;
import no.balder.heimdall.persistence.RepositoryModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

import static spark.Spark.*;

/**
 * Hello world!
 */
public class App {

    public static final Logger log = LoggerFactory.getLogger(App.class);

    static Gson gson = new Gson();

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new DataSourceModule(),
                new RepositoryModule());

        CheckInUseCase checkInUseCase = injector.getInstance(CheckInUseCase.class);
        RaiseAlarmUseCase raiseAlarmUseCase = injector.getInstance(RaiseAlarmUseCase.class);

        get("/hello", (req, res) -> {
            res.type("application/json");
            return "{ \"msg\" : \"Hello World\" }";
        });

        post("/checkin", (req, res) -> {
            int contentLength = req.contentLength();
            String body = req.body();
            log.debug("Received POST with body: " + body);                                                                                              
            CheckIn checkIn = gson.fromJson(body, CheckIn.class);

            // Never mind the timestamp supplied by the caller, we always set it on the server
            checkIn.setTimeStamp(ZonedDateTime.now().toString());
            
            checkInUseCase.checkIn(checkIn);
            res.type("application/json");

            return body;
        });

        // Raise the alarm first time
        post("/alarm", (req, res) -> {
            String body = req.body();
            final AlarmData alarmData = gson.fromJson(body, AlarmData.class);
            log.debug("Raising alarm {} ", alarmData);
            raiseAlarmUseCase.raiseAlarm(alarmData);

            res.type("application/json");
            return body;
        });

        // Updates /augments the alarm raised previously
        // alarm/{uuid}
        put("/alarm/:uuid", (req, res) -> {

            // Retrieves the UUID of the alarm incident
            final String uuid = req.params(":uuid");

            log.debug("Augmenting alarm {} ", uuid);
            // Updates the database


            res.type("application/json");
            return req.body();
        });

        // Deactives the alarm
        delete("/alarm/:uuid", (req, res) -> {

            // Retrieves the UUID of the alarm incident
            final String uuid = req.params(":uuid");

            log.debug("Deleting raised alarm {}", uuid);

            // Deactivates the alarm
            

            res.type("application/json");
            return req.body();
        });

        notFound((req,res) -> {
            log.warn("Resource " + req.uri() + " not found for method " + req.requestMethod());
            return (String.format("Not found %s for method %s", req.uri(), req.requestMethod()));
        });


        exception(JsonSyntaxException.class, (e, req, res) -> {
            log.info("Invalid request: " + e.getMessage(),e);
            log.error("Bad request, body: " + req.body());

            res.status(400);
            res.body("Bad JSON data: " + e.getMessage());
        });

        // Experimental, let's see if it works as expected
        exception(Exception.class, (e, req,res) -> {
            log.error("Error during processing: " + e.getMessage(), e);
            res.status(400);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintWriter pw = new PrintWriter(baos);
            e.printStackTrace(pw );
            res.body(baos.toString());
        });
    }

}

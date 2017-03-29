package no.balder.heimdall;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static spark.Spark.stop;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * Unit test for simple App.
 */

@Test(groups = {"rest"})
public class AppTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(AppTest.class);

    @BeforeClass
    public void setUp() throws Exception {
        // Starts the app server
        App.main(new String[]{});

        RestAssured.port = 4567;
        RestAssured.basePath = "/";
        RestAssured.baseURI = "http://localhost:4567/";
    }

    @AfterClass
    public void tearDown() throws Exception {

        // Stops the server
        stop();
    }

    @Test
    public void testRunApp() throws Exception {

        // Invokes the /hello method and verifies the contents
        given().when().get("/hello").then().statusCode(200).body("msg", equalTo("Hello World"));
    }

    @Test
    public void testCheckIn() throws Exception {
        Map<String, String> checkIn = new HashMap<>();
        checkIn.put("uuid", UUID.randomUUID().toString());
        checkIn.put("latitude", "60.015347");
        checkIn.put("longitude", "10.819413");

        given().contentType("application/json")
                .body(checkIn).when().post("/checkin")
                .then()
                .statusCode(200)
                .assertThat().contentType(ContentType.JSON)
                .body("latitude", Matchers.equalTo("60.015347"));
    }

    @Test
    public void testRaiseAlarm() throws Exception {
        Map<String, String> raisedAlarm = new HashMap<>();
        final String uuid = UUID.randomUUID().toString();
        raisedAlarm.put("uuid", uuid);
        raisedAlarm.put("latitude", "60.115347");
        raisedAlarm.put("longitude", "10.819413");

        given().contentType(ContentType.JSON)
                .body(raisedAlarm)
                .when().post("/alarm")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("latitude", equalTo("60.115347"))
                .body("uuid", equalTo(uuid));
    }

    @Test
    public void testAugmentAlarm() throws Exception {
        Map<String, String> augmentedAlarmData = new HashMap<>();
        final String uuid = UUID.randomUUID().toString();
        augmentedAlarmData.put("info", "False alarm");

        given().contentType(ContentType.JSON)
                .body(augmentedAlarmData)
                .when().put("/alarm/" + uuid)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("info", equalTo("False alarm"));
    }

    @Test
    public void testDeleteAlarm() throws Exception {

        Map<String, String> raisedAlarm = new HashMap<>();
        final String uuid = UUID.randomUUID().toString();
        raisedAlarm.put("uuid", uuid);
        raisedAlarm.put("latitude", "60.115347");
        raisedAlarm.put("longitude", "10.819413");

        // Raises the alarm
        given().contentType(ContentType.JSON)
                .body(raisedAlarm)
                .when().post("/alarm")
                .then().statusCode(200);

        // Deactivates the alarm
        given().contentType(ContentType.JSON)
                .body(raisedAlarm)
                .when()
                .delete("/alarm/" + uuid)
                .then()
                .statusCode(200);

    }
}

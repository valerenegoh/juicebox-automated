package org.owasp.performance.simulations;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.util.*;
import java.util.stream.Stream;

public class RegistrationSimulation extends Simulation {
    private HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:3000")
            .acceptHeader("application/json");

    private Iterator feeder = Stream.generate(() ->
                    Map.of("email", String.format("%s@foo.com", UUID.randomUUID())))
            .iterator();

    private ScenarioBuilder registrationScenario = scenario("User Registration")
            .feed(feeder)
            .exec(http("Register User")
                    .post("/api/Users")
                    .body(ElFileBody("registration_request.json")).asJson()
                    .check(status().is(201))

            ).pause(5);

    {
        setUp(registrationScenario.injectOpen(rampUsers(10).during(10))
        ).protocols(httpProtocol);
    }
}

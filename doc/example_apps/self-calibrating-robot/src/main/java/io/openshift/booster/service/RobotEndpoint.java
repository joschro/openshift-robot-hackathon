/*
 * Copyright 2016-2017 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.openshift.booster.service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.lang.Math;

//This class provides a RESTful API with a /run method that can be triggered through the main app webpage

@Path("/my_robot")
@Component
public class RobotEndpoint {

    // private static final Logger log = LoggerFactory.getLogger(Application.class);

    // This contains the uri of the Robot APi that this application is invoking. The
    // value is defined in application.properties.
    // Set it to your specific API
    @Value("${hub.controller.uri}")
    private String hubControllerEndpoint;

    private RestTemplate restTemplate = new RestTemplate();

    private String apiKey = "ba62d98fb5aefe1bd6f3ec712624e148";
    private int rotationOffset = 10;
    private int distanceOffset = 8;

    // This method checks if the HubController can be reached
    @GET
    public Object ping() {

        System.out.println("Ping method invoked");
        String response = restTemplate.getForObject(hubControllerEndpoint, String.class);
        return response;
    }

    // This method should execute the program steps for the robot. It can be invoked
    // by the main application website
    @POST
    @Path("/run")
    public Object run() {

        // System.out.println("Hallo Welt");

        String response = "Hello World";
        String apiKey = "ba62d98fb5aefe1bd6f3ec712624e148";

        // Example GET invokation of the Robot API
        response = restTemplate.getForObject(hubControllerEndpoint + "/power?user_key=" + apiKey, String.class);

        // Example POST invokation of the Robot API
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
        paramMap.add("user_key", apiKey);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(paramMap, new LinkedMultiValueMap<String, String>());

        // response = restTemplate.postForObject(hubControllerEndpoint + "/forward/20", request, String.class);
        // response = restTemplate.postForObject(hubControllerEndpoint + "/left/91", request, String.class);
        // response = restTemplate.postForObject(hubControllerEndpoint + "/forward/20", request, String.class);
        // response = restTemplate.postForObject(hubControllerEndpoint + "/left/91", request, String.class);
        // response = restTemplate.postForObject(hubControllerEndpoint + "/forward/20", request, String.class);
        // response = restTemplate.postForObject(hubControllerEndpoint + "/left/91", request, String.class);
        // response = restTemplate.postForObject(hubControllerEndpoint + "/forward/20", request, String.class);
        // response = restTemplate.postForObject(hubControllerEndpoint + "/left/91", request, String.class);

        while(true){
            int distanceCm = distanceInCm();

            System.out.println("Distance in mm: " + distanceCm);
            if (distanceCm > distanceOffset && distanceCm <= 200) {
                response = forward(distanceCm - distanceOffset, request);
                calibrationDrive(request);
            } else if (distanceCm <= distanceOffset ) {
                calcOffset(distanceInMm(), request);
                System.out.println("Rotation: " + 45+(rotationOffset*2));
                response = restTemplate.postForObject(hubControllerEndpoint + "/left/"+(45+(rotationOffset*2)), request, String.class);
                calibrationDrive(request);
            } else {
                restTemplate.postForObject(hubControllerEndpoint + "/forward/100", request, String.class);
                break;
            }
        }

        return response;
    }

    private void calibrationDrive(HttpEntity<MultiValueMap<String, String>> request) {
        int leftSide = getLength(135, request); // a
        int rightSide = getLength(45, request); // b

        double c = Math.sqrt(Math.pow(leftSide, 2) + Math.pow(rightSide, 2));
        System.out.println("c: " + c);
        System.out.println("left: " + leftSide + " right: " + rightSide);
        if (leftSide > rightSide) {
            double korrekturWinkel = 45 - Math.toDegrees(Math.asin(rightSide/c));
            System.out.println("Winkel: " + korrekturWinkel);
            restTemplate.postForObject(hubControllerEndpoint + "/right/"+(new Double(korrekturWinkel).intValue()), request, String.class);
        } else if (leftSide < rightSide) {
            double korrekturWinkel = 45 - Math.toDegrees(Math.asin(leftSide/c));
            System.out.println("Winkel: " + korrekturWinkel);
            restTemplate.postForObject(hubControllerEndpoint + "/left/"+(new Double(korrekturWinkel).intValue()), request, String.class);
        }
    }

    private int getLength(int degrees, HttpEntity<MultiValueMap<String, String>> request) {
        restTemplate.postForObject(hubControllerEndpoint + "/servo/" + degrees, request, String.class);
        int result = distanceInMm();
        restTemplate.postForObject(hubControllerEndpoint + "/servo/90", request, String.class);
        return result;
    }

    private String forward(Integer distanceInCm, HttpEntity<MultiValueMap<String, String>> request) {
        System.out.println("---> " + distanceInCm);
        return restTemplate.postForObject(hubControllerEndpoint + "/forward/"+ distanceInCm, request, String.class);
    }

    private int distanceInCm() {
        String result = restTemplate.getForObject(hubControllerEndpoint + "/distance?user_key=" + apiKey, String.class); // in mm!!
        Integer distance = Integer.parseInt(result);
        return (int) (distance /10);
    }

    private int distanceInMm() {
        String result = restTemplate.getForObject(hubControllerEndpoint + "/distance?user_key=" + apiKey, String.class); // in mm!!
        Integer distance = Integer.parseInt(result);
        return (int) (distance);
    }
    private void calcOffset(int oldDistanceInMm,  HttpEntity<MultiValueMap<String, String>> request) {
        restTemplate.postForObject(hubControllerEndpoint + "/left/"+(45), request, String.class);
        int newDistance = distanceInMm();
        double oldDistwithOffset = (double) oldDistanceInMm + (double) 120;
        double newDistwithOffset = (double) newDistance + (double) 120;
        double diffOffset = oldDistwithOffset/newDistwithOffset;

        double angel = Math.toDegrees(Math.acos(diffOffset));

        System.out.println("old: " + oldDistwithOffset + " new: " + newDistwithOffset + " diff: " + diffOffset + " angel: " + angel);
        // System.out.println("old: " + oldDistanceInMm + " new: " + newDistance + " angel: " + angel);
        System.out.println((int) (45 - angel));
        if (newDistance > (2 * oldDistanceInMm)) {
            return;
        }
        rotationOffset = (int) (45 - angel);
    }
}

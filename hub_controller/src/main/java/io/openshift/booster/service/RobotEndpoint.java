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
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Path("/robot")
@Component
public class RobotEndpoint {
    
    //private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Value("${edge.controller.uri}")
    private String edgeControllerEndpoint;


    //private final static String ROBOT_ENDPOINT = "http://localhost:8089";
    private RestTemplate restTemplate = new RestTemplate();
    
    @GET   
    public Object ping() {        
        
        String response = restTemplate.getForObject(edgeControllerEndpoint, String.class);        
        return response;
    }

    @POST
    @Path("/forward/<length_in_cm>")
    public Object forward(@PathParam("length_in_cm>") Integer lengthInCm ) {        
        
        HttpEntity<String> request = new HttpEntity<>(new String(""));
        String response = restTemplate.postForObject(edgeControllerEndpoint +"/forward/" + lengthInCm, request, String.class);        
        return response;
    }

    @POST
    @Path("/backward/<length_in_cm>")
    public Object backward(@PathParam("length_in_cm") Integer lengthInCm ) {        
        
        HttpEntity<String> request = new HttpEntity<>(new String(""));
        String response = restTemplate.postForObject(edgeControllerEndpoint +"/backward/" + lengthInCm, request,  String.class);        
        return response;
    }

    @POST
    @Path("/left/<degrees>")
    public Object left(@PathParam("degrees") Integer degrees ) {        
        
        HttpEntity<String> request = new HttpEntity<>(new String(""));
        String response = restTemplate.postForObject(edgeControllerEndpoint +"/left/" + degrees, request,  String.class);        
        return response;
    }

    @POST
    @Path("/right/<degrees>")
    public Object right(@PathParam("degrees") Integer degrees ) {        
        
        HttpEntity<String> request = new HttpEntity<>(new String(""));
        String response = restTemplate.postForObject(edgeControllerEndpoint +"/degrees/" + degrees, request,  String.class);        
        return response;
    }

    @POST
    @Path("/reset")
    public Object reset() {        
        
        HttpEntity<String> request = new HttpEntity<>(new String(""));
        String response = restTemplate.postForObject(edgeControllerEndpoint +"/reset", request, String.class);        
        return response;
    }

    @GET
    @Path("/power")   
    public Object power() {        
        
        String response = restTemplate.getForObject(edgeControllerEndpoint+"/power", String.class);        
        return response;
    }

    @GET
    @Path("/distance")   
    public Object distance() {        
        
        String response = restTemplate.getForObject(edgeControllerEndpoint + "/distance", String.class);        
        return response;
    }

    

    /*@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}*/
}
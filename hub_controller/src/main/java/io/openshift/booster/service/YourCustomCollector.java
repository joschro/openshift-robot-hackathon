package io.openshift.booster.service;

import java.util.ArrayList;


import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import io.prometheus.client.Collector;
import io.prometheus.client.GaugeMetricFamily;

public class YourCustomCollector extends Collector {

    @Value("${edge.controller.uri}")
    private String edgeControllerEndpoint;


    //private final static String ROBOT_ENDPOINT = "http://localhost:8089";
    private RestTemplate restTemplate = new RestTemplate();

    public List<MetricFamilySamples> collect() {
      List<MetricFamilySamples> mfs = new ArrayList<MetricFamilySamples>();
      // With no labels.
      mfs.add(new GaugeMetricFamily("my_gauge", "help", Integer.valueOf(restTemplate.getForObject(edgeControllerEndpoint+"/power", String.class))));
      
      return mfs;
    }
  }
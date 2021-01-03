package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.restmethods;

import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.datasource.Dvd;

import org.springframework.web.client.RestTemplate;

import java.util.List;

public class PostListOfDvdsTaskParams {
    private RestTemplate restTemplate;
    private String url;
    private List<Dvd> dvds;

    public PostListOfDvdsTaskParams(RestTemplate restTemplate, String url, List<Dvd> dvds) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.dvds = dvds;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public String getUrl() {
        return url;
    }

    public List<Dvd> getDvds() {
        return dvds;
    }
}

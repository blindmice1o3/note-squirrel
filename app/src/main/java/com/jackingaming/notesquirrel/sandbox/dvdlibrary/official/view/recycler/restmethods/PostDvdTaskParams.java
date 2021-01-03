package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.restmethods;

import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.datasource.Dvd;

import org.springframework.web.client.RestTemplate;

public class PostDvdTaskParams {
    private RestTemplate restTemplate;
    private String url;
    private Dvd dvd;

    public PostDvdTaskParams(RestTemplate restTemplate, String url, Dvd dvd) {
        this.restTemplate = restTemplate;
        this.url = url;
        this.dvd = dvd;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public String getUrl() {
        return url;
    }

    public Dvd getDvd() {
        return dvd;
    }
}

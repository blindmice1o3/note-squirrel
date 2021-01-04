package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.restmethods;

import org.springframework.web.client.RestTemplate;

public class GetDvdTaskParams {
    private RestTemplate restTemplate;
    private String url;

    public GetDvdTaskParams(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public String getUrl() {
        return url;
    }
}

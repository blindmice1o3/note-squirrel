package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.restmethods;

import android.os.AsyncTask;

import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.datasource.Dvd;

import org.springframework.web.client.RestTemplate;

public class PostDvdTask extends AsyncTask<PostDvdTaskParams, Void, Void> {
    @Override
    protected Void doInBackground(PostDvdTaskParams... params) {
        RestTemplate restTemplate = params[0].getRestTemplate();
        String url = params[0].getUrl();
        Dvd newDvd = params[0].getDvd();

        restTemplate.postForObject(url, newDvd, Dvd.class);
        return null;
    }
}


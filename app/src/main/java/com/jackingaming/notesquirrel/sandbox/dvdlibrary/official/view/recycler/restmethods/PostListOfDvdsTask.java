package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.restmethods;

import android.os.AsyncTask;

import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.datasource.Dvd;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class PostListOfDvdsTask extends AsyncTask<PostListOfDvdsTaskParams, Void, Void> {
    @Override
    protected Void doInBackground(PostListOfDvdsTaskParams... params) {
        RestTemplate restTemplate = params[0].getRestTemplate();
        String url = params[0].getUrl();
        List<Dvd> dvds = params[0].getDvds();

        restTemplate.postForObject(url, dvds, ResponseEntity.class);
        return null;
    }
}

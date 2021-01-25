package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler.restmethods;

import android.os.AsyncTask;

import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.datasource.Dvd;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class GetDvdTask extends AsyncTask<GetDvdTaskParams, Void, List<Dvd>> {
    @Override
    protected List<Dvd> doInBackground(GetDvdTaskParams... params) {
        List<Dvd> dvdsUpdated = null;

        RestTemplate restTemplate = params[0].getRestTemplate();
        String url = params[0].getUrl();

        try {
            ResponseEntity<List<Dvd>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Dvd>>() {
                    });
            dvdsUpdated = response.getBody();
        } catch (RestClientException ex) {
            ex.printStackTrace();
        }

        return dvdsUpdated;
    }
}

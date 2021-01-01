package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.view.recycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.datasource.Dvd;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class ViewCartActivity extends AppCompatActivity
        implements AdapterRecyclerView.ItemClickListener {

    private List<Dvd> cart;

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        Intent intent = getIntent();
        Bundle cartBundle = intent.getBundleExtra(RecyclerViewActivity.BUNDLE_KEY);
        cart = (ArrayList<Dvd>) cartBundle.getSerializable(RecyclerViewActivity.CART_KEY);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_view_cart);
        recyclerView.setHasFixedSize(true);
        AdapterRecyclerView adapter = new AdapterRecyclerView(cart);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        Button checkOutButton = (Button) findViewById(R.id.button_check_out);
    }

    @Override
    public void onItemClick(View view, int position) {
        //TODO: show Dialog to confirm removing selected item from cart
        Toast.makeText(this, "Clicked " + cart.get(position), Toast.LENGTH_SHORT).show();
    }


    public void onButtonCheckOutClick(View view) {
        Toast.makeText(this, "Check out button clicked", Toast.LENGTH_SHORT).show();

        String path = "/dvds/checkout";
        String url = RecyclerViewActivity.IP_ADDRESS + path;
        PostTaskParams params = new PostTaskParams(url, cart);

        PostTask taskPost = new PostTask();
        taskPost.execute(params);
    }

    private class PostTask extends AsyncTask<PostTaskParams, Void, Void> {
        @Override
        protected Void doInBackground(PostTaskParams... postTaskParams) {
            String url = postTaskParams[0].url;
            List<Dvd> cart = postTaskParams[0].cart;

            restTemplate.postForObject(url, cart, ResponseEntity.class);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finish();
        }
    }

    private class PostTaskParams {
        String url;
        List<Dvd> cart;
        public PostTaskParams(String url, List<Dvd> cart) {
            this.url = url;
            this.cart = cart;
        }
    }
}

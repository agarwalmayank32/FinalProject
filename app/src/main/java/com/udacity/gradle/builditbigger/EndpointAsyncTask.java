package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.powerpack.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;


class EndpointAsyncTask extends AsyncTask<MainActivityFragment, Void, String> {
    private static MyApi myApiService = null;
    private Context context;

    private MainActivityFragment mainActivityFragment;

    @Override
    protected String doInBackground(MainActivityFragment... params) {
        if(myApiService == null) {// Only do this once
                    MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new
                    AndroidJsonFactory(), null)
                    .setRootUrl("https://joketelling-143821.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }

        mainActivityFragment = params[0];
        context = mainActivityFragment.getActivity();


        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        /*// Create Intent to launch JokeFactory Activity
        Intent intent = new Intent(context, DisplayJokeActivity.class);
        // Put the string in the envelope
        intent.putExtra(DisplayJokeActivity.JOKE_KEY,result);
        context.startActivity(intent);
*/
        mainActivityFragment.loadedJoke = result;
        mainActivityFragment.launchDisplayJokeActivity();

        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
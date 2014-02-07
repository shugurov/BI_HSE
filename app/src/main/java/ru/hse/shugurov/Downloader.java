package ru.hse.shugurov;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

/**
 * Created by Иван on 29.12.13.
 */
public class Downloader extends AsyncTask<String, Void, String[]>
{
    CallBack callBack;


    public Downloader(CallBack callBack)
    {
        this.callBack = callBack;
    }

    @Override
    protected String[] doInBackground(String... params)
    {
        for (int i = 0; i < params.length; i++)
        {
            if (isCancelled())
            {
                params[i] = null;
            } else
            {

                InputStream inputStream = openInputStream(params[i]);
                if (inputStream == null)
                {
                    return null;
                } else
                {
                    params[i] = readInputStream(inputStream);
                    try
                    {
                        inputStream.close();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        return params;
    }

    @Override
    protected void onPostExecute(String[] strings)
    {
        super.onPostExecute(strings);
        if (callBack != null)
        {
            String[] result = null;
            try
            {
                result = get();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            } catch (ExecutionException e)
            {
                e.printStackTrace();
            }
            callBack.call(result);
        }
    }

    private InputStream openInputStream(String urlString) //TODO wtf? как и что тут происходит?(
    {
        InputStream inputStream = null;
        try
        {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            if (!(connection instanceof HttpURLConnection))
            {
                return null;
            }
            connection.connect();
            int response = ((HttpURLConnection) connection).getResponseCode();
            if (response == HttpURLConnection.HTTP_OK)
            {
                inputStream = connection.getInputStream();
            }

        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return inputStream;
    }

    private String readInputStream(InputStream inputStream)
    {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        final int BUFFER_SIZE = 2000;
        char[] buffer = new char[BUFFER_SIZE];
        int charsRead;
        try
        {
            while ((charsRead = reader.read(buffer)) > 0)
            {
                builder.append(String.copyValueOf(buffer, 0, charsRead));
                buffer = new char[BUFFER_SIZE];
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return builder.toString();
    }
}

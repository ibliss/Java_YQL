package org.blissard.javayql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Query implements Runnable {

    // Static function for lightweight use
    // TODO: There might be some optimizations in managing connection
    public static JsonObject query(QueryConfiguration conf, String query) {
        URL url = constructURL(conf, query);
        
        // Use HttpURLConnection, 'should' maintain connection and share connection with other queries
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // No output - all in uri
            conn.setRequestMethod("GET");
            // 15 seconds timeout. TODO: more robust, or put in conf
            conn.setReadTimeout(15*1000);
            
            // Get result
            conn.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return (new JsonParser()).parse(sb.toString()).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Can instantiate Query for managed use
    // TODO: Query building
    
    protected QueryConfiguration conf;
    protected String query;
    protected JsonObject result;
    
    public Query() {
        this(new QueryConfiguration());
    }
    
    public Query(QueryConfiguration conf) {
        this.conf = conf;
        this.query = null;
        this.result = null;
    }
    
    // Assign result from query & conf
    public void run() {
        setResult(query(getConf(), getQuery()));
    }
    
    public QueryConfiguration getConf() {
        return conf;
    }

    public void setConf(QueryConfiguration conf) {
        this.conf = conf;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public JsonObject getResult() {
        return result;
    }

    
    protected void setResult(JsonObject result) {
        this.result = result;
    }
    
    protected URL constructURL() {
        return constructURL(getConf(), getQuery());
    }
    
    protected static URL constructURL(QueryConfiguration conf, String query) {
        assert(query != null);
        
        URL url = null;
        try {
            StringBuilder urlString = new StringBuilder(conf.getBaseURL());
            urlString.append("?").append(conf.getQueryVar()).append("=").append(URLEncoder.encode(query, "UTF-8"));
            urlString.append("&").append(conf.getFormatVar()).append("=").append(conf.getFormat());
            if(conf.hasEnv()) {
                urlString.append("&").append(conf.getEnvVar()).append("=").append(conf.getEnv());
            }
            url = new URL(urlString.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

}

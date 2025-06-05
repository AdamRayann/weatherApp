package org.spring.util;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpUtil {
    private static final HttpClient client = HttpClient.newHttpClient();

    public static String get(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch data. HTTP Status: " + response.statusCode());
        }

        if (response.statusCode() == 429) {
            System.err.println("Too many requests! Status 429");
            Thread.sleep(5000); // backoff for 5 seconds
            return get(url);    // retry once
        }


        return response.body();
    }
}


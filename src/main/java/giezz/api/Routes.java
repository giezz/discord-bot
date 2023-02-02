package giezz.api;

import com.google.gson.Gson;
import giezz.pojos.profile.Profile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Routes {

    public static Profile getInfo(String profileName) throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://shikimori.one/api/users/" + profileName + "/info"))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
        Gson gson = new Gson();
        return gson.fromJson(response.body(), Profile.class);
    }
}

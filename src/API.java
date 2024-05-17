import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public record API(String origem, String destino, double valor) {
    public double conversor(){
        String api_key = "7bee4b9e729ca9d05c7a06c8";
        String url_str = "https://v6.exchangerate-api.com/v6/"+ api_key +"/pair/"+ origem +"/"+ destino +"/"+valor;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url_str))
                .build();

        HttpResponse<String> response = null;
        try{
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e){
            throw new RuntimeException(e);
        }

        JsonParser jp = new JsonParser();
        JsonObject jsonb = jp.parse(response.body()).getAsJsonObject();

        return jsonb.get("conversion_result").getAsDouble();
    }
}

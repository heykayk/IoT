package com.example.demoiot.service.serviceIml;

import com.example.demoiot.dto.DataDto;
import com.example.demoiot.dto.WeatherDto;
import com.example.demoiot.model.Data;
import com.example.demoiot.repository.DataRepository;
import com.example.demoiot.service.DataService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class DataServiceIml implements DataService {
    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void saveData(Data data){this.dataRepository.save(data);}

    @Override
    public List<DataDto> getTenLastestData(){
        long cnt = this.dataRepository.count();
        Query query = this.entityManager.createQuery("select new com.example.demoiot.dto.DataDto(d) from Data d").setFirstResult(cnt > 10 ? (int)cnt - 10 : (int)cnt).setMaxResults(10);
        return query.getResultList();
    }

    @Override
    public WeatherDto getWeather() {
        try {
            double lat = 44.34;
            double lon = 10.99;
            String appid = "84e69d56ded7404675b73883e6850fdc";

            // Tạo URL từ đường dẫn sử dụng URI và String.format
            URI uri = URI.create(String.format("http://api.openweathermap.org/data/2.5/forecast?lat=%f&lon=%f&appid=%s", lat, lon, appid));

            // Tạo đối tượng HttpClient
            HttpClient httpClient = HttpClient.newHttpClient();

            // Tạo đối tượng HttpRequest
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            // Thực hiện yêu cầu HTTP và nhận phản hồi
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // Xử lý dữ liệu phản hồi
            String responseBody = httpResponse.body();
            JsonObject jsonObject = new Gson().fromJson(responseBody, JsonObject.class);

            JsonArray jsonArray = jsonObject.getAsJsonArray("list");
            for (JsonElement e : jsonArray) {
                if (e.isJsonObject()) {
                    JsonObject tmp = e.getAsJsonObject();

                }
            }
        } catch (Exception e) {
            // Xử lý lỗi
            e.printStackTrace();
        }
        return null;
    }
}

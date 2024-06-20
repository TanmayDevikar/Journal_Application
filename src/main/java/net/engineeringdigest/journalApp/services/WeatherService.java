package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    //Mastering external API integration


    @Value("${weather.api.key}")
    private  String apiKey;

    //private static final String API = "http://api.weatherapi.com/v1/current.json?key=API_KEY&q=CITY&aqi=no";   // We cannot expose our api in the code. Thats why putting it into db.

    @Autowired
    //RestTemplate is a class in Spring Boot, which process the HTTP requests for us and give us the response.
    private RestTemplate restTemplate;
    //It will throw the error that the implementation of restTemplate if nowhere.
    //So we need to create a new instance of it in our main class i.e., JournalApplication.java

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city) {
//        String finalAPI = API.replace("CITY", city).replace("API_KEY", apiKey);
        String finalAPI = appCache.appCache.get(Placeholders.KEY).replace(Placeholders.CITY, city).replace(Placeholders.API_KEY, apiKey);
        //So here, all the constants, i.e., <city>, <apiKey> and "weather_api". We have defines all these constants in a interface named 'Placeholders', which is the best practise.

        //We need to create a POJO class so that we can store the upcoming JSON from the above api key.
        //This is called Deserialization. The process of converting JSON response into the corresponding Java Object.
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }
}

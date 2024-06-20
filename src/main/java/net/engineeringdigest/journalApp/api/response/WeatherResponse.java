package net.engineeringdigest.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse {

       //The JSON we were getting from the weather api, we have converted that into a POJO class, using google json to pojo.
       //We are just taking the fields that we need.
       //This is called Deserialization. The process of converting JSON response into the corresponding Java Object.



       //public Condition condition;
       //public Location location;
       public Current current;

//       public class Condition{
//           private String text;
//           private String icon;
//           private int code;
//       }

    @Getter
    @Setter
    public class Current{
//           private int last_updated_epoch;
//           private String last_updated;
//           private double temp_c;
//           private double temp_f;
//           private int is_day;
//           private Condition condition;
//           private double wind_mph;
//           private double wind_kph;
//           private int wind_degree;
//           private String wind_dir;
//           private int pressure_mb;
//           private double pressure_in;
//           private double precip_mm;
//           private double precip_in;
//           private int humidity;
//           private int cloud;

           //private double feelslike_c;
           //Here, for the name, we are using snake case i.e., feelslike_c. But the recommended style for java is camel case.
           //But if we use a camel case and change it, then the upcoming json value will not get store here. That's why we will use @JsonProperty.
           //Using @JsonProperty, we can give the original name of JSON into it, and for the Java variable we can use camel case. This is the best practice.

             @JsonProperty("feelslike_c")
             private double feelsLikeC;

//           private double feelslike_f;
//           private double windchill_c;
//           private double windchill_f;
//           private double heatindex_c;
//           private double heatindex_f;
//           private double dewpoint_c;
//           private double dewpoint_f;
//           private int vis_km;
//           private int vis_miles;
//           private int uv;
//           private double gust_mph;
//           private double gust_kph;
       }

//       public class Location{
//           private String name;
//           private String region;
//           private String country;
//           private double lat;
//           private double lon;
//           private String tz_id;
//           private int localtime_epoch;
//           private String localtime;
//       }
    }

package soungegroup.soungeapi.util;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import soungegroup.soungeapi.enums.State;

import java.util.HashMap;
import java.util.Map;

@Component
public class LocationUtil {
    @Value("${open-cage.api-key}")
    private String key;

    public Map<String, Double> coordinates(String city, State state) {
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder(key);
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(String.format("%s, %s", city, state.getFullName()));
        request.setRestrictToCountryCode("br"); // restrict results to a specific country

        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng result = response.getFirstPosition(); // get the coordinate pair of the first result

        HashMap<String, Double> coordinates = new HashMap<>();
        coordinates.put("lat", result.getLat());
        coordinates.put("lon", result.getLng());

        return coordinates;
    }

    public double distance(double lat1,
                           double lon1,
                           double lat2,
                           double lon2) {
        final double R = 6371.0008; // Radius of the earth
        double latDistance = toRad(lat2-lat1);
        double lonDistance = toRad(lon2-lon1);
        double a = Math.sin(latDistance / 2) *
                Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) *
                Math.cos(toRad(lat2)) *
                Math.sin(lonDistance / 2) *
                Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    private static double toRad(double value) {
        return value * Math.PI / 180;
    }
}

package soungegroup.soungeapi.util;

import org.springframework.stereotype.Component;

@Component
public class LocationUtil {
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

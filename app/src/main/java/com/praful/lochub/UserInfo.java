package com.praful.lochub;

/**
 * Created by prafu on 08-12-2016.
 */

class UserInfo {

    public String Place;
    public String latitude,longitude;


    public UserInfo(String Place, String latitude, String longitude) {
        this.Place = Place;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public String getPlace() {
        return Place;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public UserInfo() {


    }


}

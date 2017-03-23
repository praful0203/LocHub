package com.praful.lochub;

/**
 * Created by prafu on 08-12-2016.
 */

class UserInfo {

    public String place;
    public String latitude,longitude;


    public UserInfo(String place, String latitude, String longitude) {
        this.place = place;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public String getPlace() {
        return place;
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

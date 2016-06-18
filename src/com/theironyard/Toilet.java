package com.theironyard;

/**
 * Created by jeffryporter on 6/16/16.
 */
public class Toilet
{
    Integer id;
    String facility;
    double lat;
    double lon;
    int access;
    int capacity;
    int cleanliness;
    String address;

    public Toilet()
    {
    }

    public Toilet(Integer id, String facility, double lat, double lon, int access, int capacity, int cleanliness, String address)
    {
        this.id = id;
        this.facility = facility;
        this.lat = lat;
        this.lon = lon;
        this.access = access;
        this.capacity = capacity;
        this.cleanliness = cleanliness;
        this.address = address;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getFacility()
    {
        return facility;
    }

    public void setFacility(String facility)
    {
        this.facility = facility;
    }

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public double getLon()
    {
        return lon;
    }

    public void setLon(double lon)
    {
        this.lon = lon;
    }

    public int getAccess()
    {
        return access;
    }

    public void setAccess(int access)
    {
        this.access = access;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    public int getCleanliness()
    {
        return cleanliness;
    }

    public void setCleanliness(int cleanliness)
    {
        this.cleanliness = cleanliness;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
}

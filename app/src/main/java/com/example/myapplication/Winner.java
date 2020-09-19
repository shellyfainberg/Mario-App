package com.example.myapplication;

import java.util.Date;

public class Winner {

    private double lat = 0;
    private double lon = 0;
    private String timestamp = "";
    private int numOfMoves = 99;
    private String name = "";
    private int player_number = 0;

    public Winner() {
        this.timestamp = java.text.DateFormat.getDateTimeInstance().format(new Date());
    }

    public Winner(String name, int player_number) {
        this.name = name;
        this.player_number = player_number;
        this.timestamp = java.text.DateFormat.getDateTimeInstance().format(new Date());
    }

    public Winner(double lat, double lon, String timestamp, int numOfMoves, String name, int player_number) {
        this.lat = lat;
        this.lon = lon;
        this.timestamp = timestamp;
        this.numOfMoves = numOfMoves;
        this.name = name;
        this.player_number = player_number;
    }

    public double getLat() {
        return lat;
    }

    public Winner setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public Winner setLon(double lon) {
        this.lon = lon;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Winner setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public int getNumOfMoves() {
        return numOfMoves;
    }

    public Winner setNumOfMoves(int numOfMoves) {
        this.numOfMoves = numOfMoves;
        return this;
    }

    public String getName() {
        return name;
    }

    public Winner setName(String name) {
        this.name = name;
        return this;
    }

    public int getPlayer_number() {
        return player_number;
    }

    public Winner setPlayer_number(int player_number) {
        this.player_number = player_number;
        return this;
    }


}

package ch.gabrielegli.flugverwaltung.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

/**
 * Die Flughafenklasse welche die einzelnen Flüge Verwaltet.
 *
 * @author Gabriel Egli
 * @version 1.1
 * @since 24-05-22
 */
public class Airport {
    private String airportUUID;
    private String location;
    @JsonIgnore
    private ArrayList<Flight> flightList;

    /**
     * Getter
     *
     * @return flughafenUUID
     */
    public String getAirportUUID() {
        return airportUUID;
    }

    /**
     * Setter
     *
     * @param airportUUID
     */
    public void setAirportUUID(String airportUUID) {
        this.airportUUID = airportUUID;
    }

    /**
     * Getter
     *
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter
     *
     * @return flightList
     */
    public ArrayList<Flight> getFlightList() {
        return flightList;
    }

    /**
     * Setter
     *
     * @param flightList
     */
    public void setFlightList(ArrayList<Flight> flightList) {
        this.flightList = flightList;
    }
}
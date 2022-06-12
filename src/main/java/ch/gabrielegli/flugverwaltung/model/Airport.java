package ch.gabrielegli.flugverwaltung.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.util.ArrayList;

/**
 * Die Flughafenklasse welche die einzelnen Flüge Verwaltet.
 *
 * @author Gabriel Egli
 * @version 1.1
 * @since 24-05-22
 */
public class Airport {
    @FormParam("airportUUID")
    @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String airportUUID;

    @FormParam("location")
    @NotEmpty
    @Size(min = 6, max = 25)
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
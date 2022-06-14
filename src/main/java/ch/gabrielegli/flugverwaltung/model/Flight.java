
package ch.gabrielegli.flugverwaltung.model;

import ch.gabrielegli.flugverwaltung.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

/**
 * the flightclass that creates a connection between the airplane- and airportclass.
 *
 * @author Gabriel Egli
 * @version 1.1
 * @since 24-05-22
 */
public class Flight {
    @FormParam("flightUUID")
    @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String flightUUID;

    @FormParam("arrivalTime")
    @NotEmpty
    @Size(min = 5, max = 5)
    private String arrivalTime;

    @FormParam("departureTime")
    @NotEmpty
    @Size(min = 5, max = 5)
    private String departureTime;

    @JsonIgnore
    private Airplane airplane;
    @JsonIgnore
    private Airport departure;

    @JsonIgnore
    private Airport destination;

    /**
     * gets the airportUUID from the Airport-object
     *
     * @return airportUUID
     */
    @JsonIgnore
    public String getDepartureUUID() {
        if (getDeparture() == null) return null;
        return getDeparture().getAirportUUID();
    }

    /**
     * creates a Airport-object without the flightList
     *
     * @param airportUUID the key
     */
    @JsonIgnore
    public void setDepartureUUID(String airportUUID) {
        setDeparture(new Airport());
        Airport airport = DataHandler.readAirportByUUID(airportUUID);
        getDeparture().setAirportUUID(airport.getAirportUUID());
        getDeparture().setLocation(airport.getLocation());
    }

    /**
     * gets the airportUUID from the Airport-object
     *
     * @return airportUUID
     */
    @JsonIgnore
    public String getDestinationUUID() {
        if (getDestination() == null) return null;
        return getDestination().getAirportUUID();
    }

    /**
     * creates a Airport-object without the flightList
     *
     * @param airportUUID the key
     */
    @JsonIgnore
    public void setDestinationUUID(String airportUUID) {
        setDestination(new Airport());
        Airport airport = DataHandler.readAirportByUUID(airportUUID);
        getDestination().setAirportUUID(airport.getAirportUUID());
        getDestination().setLocation(airport.getLocation());
    }

    /**
     * gets the airplaneUUID from the Airplane-object
     *
     * @return airplaneUUID
     */
    @JsonIgnore
    public String getAirplaneUUID() {
        if (getAirplane() == null) return null;
        return getAirplane().getAirplaneUUID();
    }

    /**
     * creates a Airplane-object
     *
     * @param airplaneUUID the key
     */
    @JsonIgnore
    public void setAirplaneUUID(String airplaneUUID) {
        setAirplane(new Airplane());
        Airplane airplane = DataHandler.readAirplaneByUUID(airplaneUUID);
        getAirplane().setAirplaneUUID(airplane.getAirplaneUUID());
        getAirplane().setAirline(airplane.getAirline());
        getAirplane().setFlightNumber(airplane.getFlightNumber());
        getAirplane().setModelName(airplane.getModelName());
    }

    /**
     * Getter
     *
     * @return flightUUID
     */
    public String getFlightUUID() {
        return flightUUID;
    }

    /**
     * Setter
     *
     * @param flightUUID
     */
    public void setFlightUUID(String flightUUID) {
        this.flightUUID = flightUUID;
    }

    /**
     * Getter
     *
     * @return arrivalTime
     */
    public String getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Setter
     *
     * @param arrivalTime
     */
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Getter
     *
     * @return departureTime
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * Setter
     *
     * @param departureTime
     */
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Getter
     *
     * @return airplane
     */
    public Airplane getAirplane() {
        return airplane;
    }

    /**
     * Setter
     *
     * @param airplane
     */
    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    /**
     * Getter
     *
     * @return departure
     */
    public Airport getDeparture() {
        return departure;
    }

    /**
     * Setter
     *
     * @param departure
     */
    public void setDeparture(Airport departure) {
        this.departure = departure;
    }

    /**
     * Getter
     *
     * @return destination
     */
    public Airport getDestination() {
        return destination;
    }

    /**
     * Setter
     *
     * @param destination
     */
    public void setDestination(Airport destination) {
        this.destination = destination;
    }
}
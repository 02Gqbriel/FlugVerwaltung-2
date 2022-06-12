
package ch.gabrielegli.flugverwaltung.model;

/**
 * Die Flight-Klasse welche die Verbindung zwischen dem Airport und dem Airplane herstellt.
 *
 * @author Gabriel Egli
 * @version 1.1
 * @since 24-05-22
 */
public class Flight {
    private String flightUUID;
    private String arrivalTime;
    private String departureTime;

    private Airplane airplane;
    private Airport departure, destination;

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
    public void setFlugUUID(String flightUUID) {
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
package ch.gabrielegli.flugverwaltung.model;

/**
 * Die Flugzeuge die von Airport zu Airport fliegen.
 *
 * @author Gabriel Egli
 * @version 1.1
 * @since 24-05-22
 */
public class Airplane {
    private String airplaneUUID;
    private String airline;
    private String modelName;
    private String flightNumber;

    /**
     * Getter
     *
     * @return airplaneUUID
     */
    public String getAirplaneUUID() {
        return airplaneUUID;
    }

    /**
     * Setter
     *
     * @param airplaneUUID
     */
    public void setAirplaneUUID(String airplaneUUID) {
        this.airplaneUUID = airplaneUUID;
    }

    /**
     * Getter
     *
     * @return airline
     */
    public String getAirline() {
        return airline;
    }

    /**
     * Setter
     *
     * @param airline
     */
    public void setAirline(String airline) {
        this.airline = airline;
    }

    /**
     * Getter
     *
     * @return modelName
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * Setter
     *
     * @param modelName
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    /**
     * Getter
     *
     * @return flightNumber
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Setter
     *
     * @param flightNumber
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
}
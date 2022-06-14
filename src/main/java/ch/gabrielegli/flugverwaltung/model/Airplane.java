package ch.gabrielegli.flugverwaltung.model;

import ch.gabrielegli.flugverwaltung.util.IsFlightNumber;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

/**
 * the airplaneclass that flies between airports
 *
 * @author Gabriel Egli
 * @version 1.1
 * @since 24-05-22
 */
public class Airplane {

    @FormParam("airplaneUUID")
    @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String airplaneUUID;

    @FormParam("airline")
    @NotEmpty
    @Size(min = 6, max = 18)
    private String airline;

    @FormParam("modelName")
    @NotEmpty
    @Size(min = 7, max = 12)
    private String modelName;

    @FormParam("flightNumber")
    @IsFlightNumber
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
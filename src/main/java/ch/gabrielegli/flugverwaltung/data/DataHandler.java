package ch.gabrielegli.flugverwaltung.data;

import ch.gabrielegli.flugverwaltung.model.Airplane;
import ch.gabrielegli.flugverwaltung.model.Airport;
import ch.gabrielegli.flugverwaltung.model.Flight;
import ch.gabrielegli.flugverwaltung.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 */
public class DataHandler {
    private static DataHandler instance = null;
    private List<Flight> flightList;
    private List<Airplane> airplaneList;
    private List<Airport> airportList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
        setAirplaneList(new ArrayList<>());
        readAirplaneJSON();
        setFlightList(new ArrayList<>());
        readFlightJSON();
        setAirportList(new ArrayList<>());
        readAirportJSON();
    }


    /**
     * gets the only instance of this class
     *
     * @return instance
     */
    public static DataHandler getInstance() {
        if (instance == null)
            instance = new DataHandler();
        return instance;
    }


    /**
     * reads all flights
     *
     * @return list of flights
     */
    public List<Flight> readAllFlights() {
        return getFlightList();
    }

    /**
     * reads a flights by its uuid
     *
     * @param flightUUID flightUUID
     * @return the Flight (null=not found)
     */
    public Flight readFlightByUUID(String flightUUID) {
        Flight flight = null;
        for (Flight entry : getFlightList()) {
            if (entry.getFlightUUID().equals(flightUUID)) {
                flight = entry;
            }
        }
        return flight;
    }

    /**
     * reads all airplanes
     *
     * @return list of airplanes
     */
    public List<Airplane> readAllAirplanes() {
        return getAirplaneList();
    }

    /**
     * reads a airplane by its uuid
     *
     * @param airplaneUUID airplaneUUID
     * @return the Airplane (null=not found)
     */
    public Airplane readAirplaneByUUID(String airplaneUUID) {
        Airplane airplane = null;
        for (Airplane entry : getAirplaneList()) {
            if (entry.getAirplaneUUID().equals(airplaneUUID)) {
                airplane = entry;
            }
        }
        return airplane;
    }

    /**
     * reads all Airport
     *
     * @return list of flughafen
     */
    public List<Airport> readAllAirport() {
        return getAirportList();
    }

    /**
     * reads a airport by its uuid
     *
     * @param airportUUID airportUUID
     * @return the Airport (null=not found)
     */
    public Airport readAirportByUUID(String airportUUID) {
        Airport airport = null;
        for (Airport entry : getAirportList()) {
            if (entry.getAirportUUID().equals(airportUUID)) {
                airport = entry;
            }
        }
        return airport;
    }

    /**
     * reads the flights from the JSON-file
     */
    private void readFlightJSON() {
        try {
            String path = Config.getProperty("flightJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Flight[] flights = objectMapper.readValue(jsonData, Flight[].class);
            for (Flight flight : flights) {
                getFlightList().add(flight);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the flugzeugs from the JSON-file
     */
    private void readAirplaneJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("airplaneJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Airplane[] airplanes = objectMapper.readValue(jsonData, Airplane[].class);
            for (Airplane airplane : airplanes) {
                getAirplaneList().add(airplane);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * reads the flughafen from the JSON-file
     */
    private void readAirportJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("airportJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Airport[] airports = objectMapper.readValue(jsonData, Airport[].class);
            for (Airport airport : airports) {
                getAirportList().add(airport);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * gets flightList
     *
     * @return value of flightList
     */
    private List<Flight> getFlightList() {
        return flightList;
    }

    /**
     * sets flightList
     *
     * @param flightList the value to set
     */
    private void setFlightList(List<Flight> flightList) {
        this.flightList = flightList;
    }

    /**
     * gets airplaneList
     *
     * @return value of airplaneList
     */
    private List<Airplane> getAirplaneList() {
        return airplaneList;
    }

    /**
     * sets airplaneList
     *
     * @param airplaneList the value to set
     */
    private void setAirplaneList(List<Airplane> airplaneList) {
        this.airplaneList = airplaneList;
    }

    /**
     * gets airplaneList
     *
     * @return value of airportList
     */
    private List<Airport> getAirportList() {
        return airportList;
    }

    /**
     * sets airportList
     *
     * @param airportList the value to set
     */
    private void setAirportList(List<Airport> airportList) {
        this.airportList = airportList;
    }
}
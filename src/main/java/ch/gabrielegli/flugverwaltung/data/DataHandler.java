package ch.gabrielegli.flugverwaltung.data;

import ch.gabrielegli.flugverwaltung.model.Airplane;
import ch.gabrielegli.flugverwaltung.model.Airport;
import ch.gabrielegli.flugverwaltung.model.Flight;
import ch.gabrielegli.flugverwaltung.service.Config;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 */
public class DataHandler {
    private static List<Flight> flightList = null;
    private static List<Airplane> airplaneList = null;
    private static List<Airport> airportList = null;

    /**
     * reads all flights
     *
     * @return list of flights
     */
    public static List<Flight> readAllFlights() {
        return getFlightList();
    }

    /**
     * reads a flights by its uuid
     *
     * @param flightUUID flightUUID
     * @return the Flight (null=not found)
     */
    public static Flight readFlightByUUID(String flightUUID) {
        Flight flight = null;
        for (Flight entry : getFlightList()) {
            if (entry.getFlightUUID().equals(flightUUID)) {
                flight = entry;
            }
        }
        return flight;
    }

    /**
     * inserts a new flight into the flightList
     *
     * @param flight the flug to be saved
     */
    public static void insertFlight(Flight flight) {
        getFlightList().add(flight);
        writeFlightJSON();
    }

    /**
     * updates the flightList
     */
    public static void updateFlight() {
        writeFlightJSON();
    }

    /**
     * deletes a flight identified by the flightUUID
     *
     * @param flightUUID the key
     * @return success=true/false
     */
    public static boolean deleteFlight(String flightUUID) {
        Flight flug = readFlightByUUID(flightUUID);
        if (flug != null) {
            getFlightList().remove(flug);
            writeFlightJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * reads all airplanes
     *
     * @return list of airplanes
     */
    public static List<Airplane> readAllAirplanes() {
        return getAirplaneList();
    }

    /**
     * reads a airplane by its uuid
     *
     * @param airplaneUUID airplaneUUID
     * @return the Airplane (null=not found)
     */
    public static Airplane readAirplaneByUUID(String airplaneUUID) {
        Airplane airplane = null;
        for (Airplane entry : getAirplaneList()) {
            if (entry.getAirplaneUUID().equals(airplaneUUID)) {
                airplane = entry;
            }
        }
        return airplane;
    }

    /**
     * inserts a new airplane into the airplaneList
     *
     * @param airplane the airplane to be saved
     */
    public static void insertAirplane(Airplane airplane) {
        getAirplaneList().add(airplane);
        writeAirplaneJSON();
    }

    /**
     * updates the airplaneList
     */
    public static void updateAirplane() {
        writeAirplaneJSON();
    }

    /**
     * deletes a airplane identified by the airplaneUUID
     *
     * @param airplaneUUID the key
     * @return success=true/false
     */
    public static boolean deleteAirplane(String airplaneUUID) {
        Airplane airplane = readAirplaneByUUID(airplaneUUID);
        if (airplane != null) {
            getAirplaneList().remove(airplane);
            writeAirplaneJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * reads all Airport
     *
     * @return list of flughafen
     */
    public static List<Airport> readAllAirport() {
        return getAirportList();
    }

    /**
     * reads a airport by its uuid
     *
     * @param airportUUID airportUUID
     * @return the Airport (null=not found)
     */
    public static Airport readAirportByUUID(String airportUUID) {
        Airport airport = null;
        for (Airport entry : getAirportList()) {
            if (entry.getAirportUUID().equals(airportUUID)) {
                airport = entry;
            }
        }
        return airport;
    }

    /**
     * inserts a new airport into the airportList
     *
     * @param airport the airport to be saved
     */
    public static void insertAirport(Airport airport) {
        getAirportList().add(airport);
        writeAirportJSON();
    }

    /**
     * updates the airportList
     */
    public static void updateAirport() {
        writeAirportJSON();
    }

    /**
     * deletes a airport identified by the airportUUID
     *
     * @param airportUUID the key
     * @return success=true/false
     */
    public static boolean deleteAirport(String airportUUID) {
        Airport airport = readAirportByUUID(airportUUID);
        if (airport != null) {
            getAirportList().remove(airport);
            writeAirportJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * reads the flights from the JSON-file
     */
    private static void readFlightJSON() {
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
     * writes the flightList to the JSON-file
     */
    private static void writeFlightJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String flugPath = Config.getProperty("flightJSON");
        try {
            fileOutputStream = new FileOutputStream(flugPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getFlightList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the flugzeugs from the JSON-file
     */
    private static void readAirplaneJSON() {
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
     * writes the airplaneList to the JSON-file
     */
    private static void writeAirplaneJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String flugzeugPath = Config.getProperty("flugzeugJSON");
        try {
            fileOutputStream = new FileOutputStream(flugzeugPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getAirplaneList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * reads the flughafen from the JSON-file
     */
    private static void readAirportJSON() {
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
     * writes the airportList to the JSON-file
     */
    private static void writeAirportJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String flughafenPath = Config.getProperty("flughafenJSON");
        try {
            fileOutputStream = new FileOutputStream(flughafenPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getAirportList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * gets flightList
     *
     * @return value of flightList
     */
    private static List<Flight> getFlightList() {

        if (flightList == null) {
            setFlightList(new ArrayList<>());
            readFlightJSON();
        }

        return flightList;
    }

    /**
     * sets flightList
     *
     * @param flightList the value to set
     */
    private static void setFlightList(List<Flight> flightList) {
        DataHandler.flightList = flightList;
    }

    /**
     * gets airplaneList
     *
     * @return value of airplaneList
     */
    private static List<Airplane> getAirplaneList() {

        if (airplaneList == null) {
            setAirplaneList(new ArrayList<>());
            readAirplaneJSON();
        }

        return airplaneList;
    }

    /**
     * sets airplaneList
     *
     * @param airplaneList the value to set
     */
    private static void setAirplaneList(List<Airplane> airplaneList) {
        DataHandler.airplaneList = airplaneList;
    }

    /**
     * gets airplaneList
     *
     * @return value of airportList
     */
    private static List<Airport> getAirportList() {

        if (airportList == null) {
            setAirportList(new ArrayList<>());
            readAirportJSON();
        }

        return airportList;
    }

    /**
     * sets airportList
     *
     * @param airportList the value to set
     */
    private static void setAirportList(List<Airport> airportList) {
        DataHandler.airportList = airportList;
    }
}
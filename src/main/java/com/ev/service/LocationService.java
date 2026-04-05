package com.ev.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for loading and managing locations data
 */
@Service
public class LocationService {
    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);
    private List<String> locationsList;
    private Map<String, Integer> locationEncoding;

    public LocationService() {
        loadLocations();
    }

    private void loadLocations() {
        try {
            Set<String> locationSet = new HashSet<>();
            
            // Load counties from CSV
            try (FileReader reader = new FileReader("Electric_Vehicle_Population_Size_History_By_County_.csv");
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
                for (CSVRecord record : csvParser) {
                    String county = record.get("County");
                    if (county != null && !county.trim().isEmpty()) {
                        locationSet.add(county.trim());
                    }
                }
            }

            // Add Indian locations
            List<String> indianLocations = getIndianLocations();
            locationSet.addAll(indianLocations);

            // Add international locations
            List<String> internationalLocations = getInternationalLocations();
            locationSet.addAll(internationalLocations);

            // Sort and create encoding
            locationsList = locationSet.stream()
                    .sorted()
                    .collect(Collectors.toList());

            locationEncoding = new HashMap<>();
            for (int i = 0; i < locationsList.size(); i++) {
                locationEncoding.put(locationsList.get(i), i);
            }

            logger.info("Loaded {} locations", locationsList.size());

        } catch (IOException e) {
            logger.warn("Could not load CSV file, using default locations", e);
            loadDefaultLocations();
        }
    }

    private void loadDefaultLocations() {
        locationsList = Arrays.asList(
                "India - Delhi", "India - Mumbai", "India - Bangalore",
                "King", "Snohomish", "Pierce", "Spokane", "Clark",
                "Thurston", "Kitsap", "Whatcom", "Benton", "Yakima"
        );
        locationEncoding = new HashMap<>();
        for (int i = 0; i < locationsList.size(); i++) {
            locationEncoding.put(locationsList.get(i), i);
        }
    }

    private List<String> getIndianLocations() {
        return Arrays.asList(
                "India - Delhi", "India - Mumbai", "India - Bangalore", "India - Hyderabad",
                "India - Chennai", "India - Kolkata", "India - Pune", "India - Ahmedabad",
                "India - Surat", "India - Jaipur", "India - Lucknow", "India - Kanpur",
                "India - Nagpur", "India - Indore", "India - Thane", "India - Bhopal",
                "India - Visakhapatnam", "India - Pimpri-Chinchwad", "India - Patna",
                "India - Vadodara", "India - Ghaziabad", "India - Ludhiana", "India - Agra",
                "India - Nashik", "India - Faridabad", "India - Meerut", "India - Rajkot",
                "India - Kalyan-Dombivali", "India - Vasai-Virar", "India - Varanasi",
                "India - Srinagar", "India - Aurangabad", "India - Dhanbad", "India - Amritsar",
                "India - Navi Mumbai", "India - Allahabad", "India - Ranchi", "India - Howrah",
                "India - Coimbatore", "India - Jabalpur", "India - Gwalior", "India - Vijayawada",
                "India - Jodhpur", "India - Madurai", "India - Raipur", "India - Kota",
                "India - Chandigarh", "India - Guwahati", "India - Solapur", "India - Hubli-Dharwad",
                "India - Mysore", "India - Tiruchirappalli", "India - Bareilly", "India - Aligarh",
                "India - Tiruppur", "India - Moradabad", "India - Jalandhar", "India - Bhubaneswar",
                "India - Salem", "India - Warangal", "India - Mira-Bhayandar", "India - Thiruvananthapuram",
                "India - Guntur", "India - Bhiwandi", "India - Saharanpur", "India - Gorakhpur",
                "India - Bikaner", "India - Amravati", "India - Noida", "India - Jamshedpur",
                "India - Bhilai", "India - Cuttack", "India - Firozabad", "India - Kochi",
                "India - Nellore", "India - Bhavnagar", "India - Dehradun", "India - Durgapur",
                "India - Asansol", "India - Rourkela", "India - Nanded", "India - Kolhapur",
                "India - Ajmer", "India - Akola", "India - Gulbarga", "India - Jamnagar",
                "India - Ujjain", "India - Loni", "India - Siliguri", "India - Jhansi",
                "India - Ulhasnagar", "India - Jammu", "India - Sangli-Miraj & Kupwad",
                "India - Mangalore", "India - Erode", "India - Belgaum", "India - Ambattur",
                "India - Tirunelveli", "India - Malegaon", "India - Gaya", "India - Jalgaon",
                "India - Udaipur", "India - Maheshtala"
        );
    }

    private List<String> getInternationalLocations() {
        return Arrays.asList(
                "China - Beijing", "China - Shanghai", "China - Shenzhen", "China - Guangzhou",
                "UK - London", "UK - Manchester", "UK - Birmingham", "UK - Edinburgh",
                "Germany - Berlin", "Germany - Munich", "Germany - Hamburg", "Germany - Frankfurt",
                "France - Paris", "France - Lyon", "France - Marseille",
                "Japan - Tokyo", "Japan - Osaka", "Japan - Kyoto",
                "Canada - Toronto", "Canada - Vancouver", "Canada - Montreal",
                "Australia - Sydney", "Australia - Melbourne", "Australia - Brisbane",
                "Norway - Oslo", "Norway - Bergen",
                "Netherlands - Amsterdam", "Netherlands - Rotterdam",
                "Sweden - Stockholm", "Sweden - Gothenburg"
        );
    }

    public List<String> getAllLocations() {
        return locationsList;
    }

    public int getLocationEncoding(String location) {
        return locationEncoding.getOrDefault(location, 0);
    }

    public int getTotalLocations() {
        return locationsList.size();
    }
}

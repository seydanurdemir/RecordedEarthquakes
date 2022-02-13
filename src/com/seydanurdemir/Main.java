package com.seydanurdemir;

import com.seydanurdemir.model.Feature;
import com.seydanurdemir.model.Root;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

/**
 * @version 1.0.0
 * @author SeydaNur DEMIR
 * @since February 2022
 * @see "https://github.com/seydanurdemir"
 */
public class Main {
    /**
     * Main class.
     * @param args The command line arguments.
     * @throws IOException when can not read or write file.
     * @throws InterruptedException when connection is interrupted.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // Scan user input from the terminal
        Scanner scanner = new Scanner(System.in);
        System.out.print("Start time (yyyy/MM/dd): ");
        String startTimeStr = scanner.next();
        System.out.print("End time (yyyy/MM/dd): ");
        String endTimeStr = scanner.next();

        // Format the date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd' 'HH:mm:ss:S");
        String[] arrOft1 = startTimeStr.split("/");
        LocalDate t1 = LocalDate.of(Integer.parseInt(arrOft1[0]), Integer.parseInt(arrOft1[1]), Integer.parseInt(arrOft1[2]));
        String[] arrOft2 = endTimeStr.split("/");
        LocalDate t2 = LocalDate.of(Integer.parseInt(arrOft2[0]), Integer.parseInt(arrOft2[1]), Integer.parseInt(arrOft2[2]));

        // Calculate the difference between start and end days
        Period diff = Period.between(t1, t2);

        // Create new http client
        HttpClient client = HttpClient.newHttpClient();

        // Get request from the given api
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=%s&endtime=%s", startTimeStr, endTimeStr)))
                .header("Content-Type", "application/json")
                .build();

        // Get response
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        // Get json file and parse using Gson
        Root obj = new Gson().fromJson(response.body(), Root.class);

        if ((long) obj.features.size() == 0) {
            // If there were no earthquakes happened in given {x} days.
            System.out.printf("No Earthquakes were recorded past %d days.%n", diff.getDays());
        } else {
            // Otherwise, list country, place, magnitude, date and time
            // of the earthquakes that happened in given {x} days.
            System.out.printf("%n%d Earthquakes were recorded past %d days.%n %n", (long) obj.features.size(), diff.getDays());
            for (Feature feature: obj.features) {
                Timestamp timestamp = new Timestamp(feature.properties.time);
                System.out.printf("%s, %.2f, %s %n", feature.properties.place, feature.properties.mag, simpleDateFormat.format(timestamp.getTime()));
            }
        }
    }
}
package com.seydanurdemir;

import java.io.IOException;
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

    }
}
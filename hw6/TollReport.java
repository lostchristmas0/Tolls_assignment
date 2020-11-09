/*
 * file: TollReport.java
 * This file represents the TollReport class holding the main method
 * to print all required output (refer to the "Toll Report" design)
 */
package hw6;

import java.util.Scanner;

/**
 * The TollReport class have the main method takes the input text file
 * and print all required output
 * @author Chenghui Zhu
 * @author Dongyu Wu
 */
public class TollReport {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Toll tollData = new Toll(args[0]);
        System.out.println("\n" + tollData.completedTrips() +
                " completed trips\n");
        System.out.println("On-Road Report\n==============\n" +
                tollData.onRoadReport());
        System.out.println("\nSPEEDER REPORT\n==============\n" +
                tollData.speedReport());
        System.out.println("BILLING INFORMATION\n===================\n" +
                tollData.billingReport() + "\n");
        while (true) {
            System.out.println("'b <string>' to see bill for license tag\n" +
                    "'e <number>' to see activity at exit\n'q' to quit");
            String[] option = sc.nextLine().split(" ");
            if (option.length == 2) {
                if (option[0].toLowerCase().equals("b")) {
                    System.out.println(tollData.singleVehicle(option[1])+"\n");
                } else if (option[0].toLowerCase().equals("e")) {
                    System.out.println("\nEXIT " + option[1] +
                            " REPORT\n==============");
                    System.out.println(tollData.singleExit
                            (Integer.parseInt(option[1])));
                }
            } else if (option[0].equals("q") && option.length == 1) {
                break;
            } else {
                System.out.println("Illegal command. Try again");
            }
        }
        sc.close();
    }
}

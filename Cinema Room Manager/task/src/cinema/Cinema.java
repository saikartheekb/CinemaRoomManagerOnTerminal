package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {
    static int numOfTicketsPurchased = 0;
    static int totalNumOfTickets;
    static int currentIncome = 0;
    static int totalIncome;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        int rows = scan.nextInt();

        System.out.println("Enter the number of seats in each row:");
        int seats = scan.nextInt();
        totalNumOfTickets = rows * seats;
        totalIncome = calcTotalIncome(rows, seats);
        System.out.println();

        int[][] seatArrangement = new int[rows][seats];

        boolean exit = true;
        while(exit){
            printMenu();
            int option = scan.nextInt();
            switch (option) {
                case 1 -> printSeats(seatArrangement);
                case 2 -> buyTicket(seatArrangement);
                case 3 -> showStats();
                case 0 -> exit = false;
            }
            System.out.println();
        }
    }

    public static void printSeats(int[][] cinema){
        System.out.println("Cinema:");

        for(int i = 0; i <= cinema[0].length; i++) System.out.print((i != 0 ? i : " ")+" ");
        System.out.println();

        for(int i = 0; i < cinema.length; i++){
            System.out.print((i+1) + " ");
            for(int j = 0; j < cinema[0].length; j++){
                System.out.print(cinema[i][j]==1 ? "B " : "S ");
            }
            System.out.println();
        }
    }

    public static void printMenu() {
        System.out.println("""
                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit
                """);
    }

    public static void buyTicket(int[][] cinema){
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter a row number:");
        int row = scan.nextInt();

        System.out.println("Enter a seat number in that row:");
        int seat = scan.nextInt();

        try{
            if (cinema[row-1][seat-1] == 1) {
                System.out.println("That ticket has already been purchased!");
                buyTicket(cinema);
                return;
            }
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Wrong input!");
            buyTicket(cinema);
            return;
        }


        cinema[row-1][seat-1] = 1;
        numOfTicketsPurchased++;

        System.out.print("Ticket price: $");
        int price = 0;
        if(cinema.length * cinema[0].length <= 60) {
            price = 10;
        } else {
            int frontRows = cinema.length / 2;
            price = row > frontRows ? 8 : 10;
        }
        currentIncome += price;
        System.out.print(price);
        System.out.println();
    }

    public static void showStats(){
        System.out.printf("Number of purchased tickets: %d\n", numOfTicketsPurchased);
        System.out.printf("Percentage: %.2f",((double) numOfTicketsPurchased/totalNumOfTickets)*100);
        System.out.println("%");
        System.out.printf("Current income: $%d\n", currentIncome);
        System.out.printf("Total income: $%d\n", totalIncome);
    }

    public static int calcTotalIncome(int rows, int seats){
        if(rows * seats > 60) {
            int frontRows = rows / 2;
            return frontRows * seats * 10 + (rows - frontRows) * seats * 8;
        } else {
            return rows * seats * 10;
        }
    }
}
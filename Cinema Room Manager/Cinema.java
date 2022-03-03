package cinema;

import java.util.Scanner;

public class Cinema {
    private static final Scanner sc = new Scanner(System.in);
    private static int rows;
    private static int seats;
    private static int purchased = 0;
    private static int income = 0;
    private static int totalincome = 0;
    private static double percentage = 0;
    private static CinemaHall hall;

    public static void main(String[] args) {
        beginnerPrinter();
        mainMenu();
    }

    public static class CinemaHall {
        private static final int FRONT_HALF_PRICE = 10;
        private static final int BACK_HALF_PRICE = 8;
        private final Seat[][] cinemaHall;

        CinemaHall(int rows, int seats) {
            this.cinemaHall = new Seat[rows + 1][seats + 1];
            for (int i = 0; i < cinemaHall.length; i++) {
                for (int j = 0; j < cinemaHall[i].length; j++) {
                    if (rows * seats <= 60) {
                        cinemaHall[i][j] = new Seat(FRONT_HALF_PRICE);
                        totalincome += i == 0 || j ==0 ? 0 : FRONT_HALF_PRICE;
                    } else if (i <= rows / 2) {
                        cinemaHall[i][j] = new Seat(FRONT_HALF_PRICE);
                        totalincome += i == 0 || j ==0 ? 0 : FRONT_HALF_PRICE;
                    } else {
                        cinemaHall[i][j] = new Seat(BACK_HALF_PRICE);
                        totalincome += i == 0 || j ==0 ? 0 : BACK_HALF_PRICE;
                    }


                    /*cinemaHall[i][j] = rows * seats <= 60 ? new Seat(FRONT_HALF_PRICE)
                            : i <= rows / 2 ? new Seat(FRONT_HALF_PRICE)
                            : new Seat(BACK_HALF_PRICE);*/
                }
            }
        }

        public void printSeats() {
            System.out.println("\nCinema:");
            for (int i = 0; i < cinemaHall.length; i++) {
                for (int j = 0; j < cinemaHall[i].length; j++) {
                    if (i == 0 && j == 0) {
                        System.out.print("  ");
                    } else {
                        System.out.print(j == 0 ? i + " "
                                : i == 0 ? j + " "
                                : cinemaHall[i][j].getSeatStatus());
                    }
                }
                System.out.println();
            }
            System.out.println();
        }

        public void setSeatBooked(int row, int seat) {
            if (cinemaHall[row][seat].isAvailable) {
                cinemaHall[row][seat].setBooked();
                income += cinemaHall[row][seat].getPrice();
                System.out.println("Ticket price: $" + cinemaHall[row][seat].getPrice()+"\n");
            } else {
                System.out.println("That ticket has already been purchased!");
                System.out.println();
                seatBooking();
            }
        }

        public void printPrice(int row, int seat) {
            System.out.println("Ticket price: $" + cinemaHall[row][seat].getPrice()+"\n");
        }
    }

    public static class Seat {
        private boolean isAvailable;
        private final int price;

        Seat(int price) {
            this.isAvailable = true;
            this.price = price;
        }

        public void setBooked() {
            isAvailable = false;
            purchased++;
            percentage = (double) purchased / (rows * seats) * 100;
        }

        public int getPrice() {
            return price;
        }

        public String getSeatStatus() {
            return isAvailable ? "S " : "B ";
        }
    }

    public static void mainMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        int action = sc.nextInt();
        switch (action) {
            case 1:
                hall.printSeats();
                mainMenu();
                break;
            case 2:
                seatBooking();
                break;
            case 3:
                statistics();
                break;
            case 0:
                break;
        }
    }

    public static void beginnerPrinter() {
        System.out.println("Enter the number of rows:");
        rows = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seats = sc.nextInt();
        hall = new CinemaHall(rows, seats);
    }

    public static void seatBooking() {
        System.out.println("\nEnter a row number:");
        int row = sc.nextInt();
        System.out.println("Enter a seat number in that row:");
        int seat = sc.nextInt();
        if (row < 0 || row > rows || seat < 0 || seat > seats) {
            System.out.println("Wrong input!");
            System.out.println("");
            seatBooking();
        }
        hall.setSeatBooked(row, seat);
        mainMenu();
    }

    public static void statistics() {
        System.out.println("Number of purchased tickets: " + purchased);
        System.out.println("Percentage: " + String.format("%.2f", percentage) + "%");
        System.out.println("Current income: $" + income);
        System.out.println("Total income: $" + totalincome);
        System.out.println();
        mainMenu();
    }
}

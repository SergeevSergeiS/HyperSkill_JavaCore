package machine;

import java.util.Scanner;

enum CoffeeState {
    ACTION,
    BUY,
    FILL,
    TAKE,
    REMAINING,
    EXIT,
    ESPRESS0,
    LATTE,
    CAPPUCCINO,
    ADD_WATER,
    ADD_MILK,
    ADD_BEANS,
    ADD_CUP
}

public class CoffeeMachine {
    static int waterAmount = 400;
    static int milkAmount = 540;
    static int beansAmount = 120;
    static int cupsAmount = 9;
    static int currentPrice = 550;
    static CoffeeState coffeeState;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        while (coffeeState != CoffeeState.EXIT) {
            runMachine(sc.nextLine());
        }
    }

    public static void runMachine (String state) {
        switch (state) {
            case "buy":
                coffeeState = CoffeeState.BUY;
                break;
            case "fill":
                coffeeState = CoffeeState.FILL;
                break;
            case "take":
                coffeeState = CoffeeState.TAKE;
                break;
            case "remaining":
                coffeeState = CoffeeState.REMAINING;
                break;
            case "exit":
                coffeeState = CoffeeState.EXIT;
                break;
            case "1":
                coffeeState = CoffeeState.ESPRESS0;
                break;
            case "2":
                coffeeState = CoffeeState.LATTE;
                break;
            case "3":
                coffeeState = CoffeeState.CAPPUCCINO;
                break;
            case "back":
                coffeeState = CoffeeState.ACTION;
                break;

        }
        switch (coffeeState) {
            case BUY:
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                break;
            case FILL:
                coffeeState = CoffeeState.ADD_WATER;
                break;
            case ADD_WATER:
                System.out.println("Write how many ml of water do you want to add:");
                waterAmount += Integer.parseInt(state);
                coffeeState = CoffeeState.ADD_MILK;
                break;
            case ADD_MILK:
                System.out.println("Write how many ml of milk do you want to add:");
                milkAmount += Integer.parseInt(state);
                coffeeState = CoffeeState.ADD_BEANS;
                break;
            case ADD_BEANS:
                System.out.println("Write how many grams of coffee beans do you want to add:");
                beansAmount += Integer.parseInt(state);
                coffeeState = CoffeeState.ADD_CUP;
                break;
            case ADD_CUP:
                System.out.println("Write how many disposable cups of coffee do you want to add:");
                cupsAmount += Integer.parseInt(state);
                coffeeState = CoffeeState.ACTION;
                break;
            case TAKE:
                System.out.println("I gave you $" + currentPrice);
                currentPrice -= currentPrice;
                coffeeState = CoffeeState.ACTION;
                break;
            case REMAINING:
                System.out.println("The coffee machine has:");
                System.out.println(waterAmount + " of water");
                System.out.println(milkAmount + " of milk");
                System.out.println(beansAmount + " of coffee beans");
                System.out.println(cupsAmount + " of disposable cups");
                System.out.println(currentPrice + " of money");
                coffeeState = CoffeeState.ACTION;
                break;
            case EXIT:
                break;
            case ESPRESS0:
                if (waterAmount - 250 < 0) {
                    System.out.println("Sorry, not enough water!");
                } else if (beansAmount - 16 < 0) {
                    System.out.println("Sorry, not enough beans!");
                } else if (cupsAmount - 1 < 0) {
                    System.out.println("Sorry, not enough cups!");
                } else if (milkAmount - 0 < 0){
                    System.out.println("Sorry, not enough milk!");
                } else {
                    waterAmount -= 250;
                    beansAmount -= 16;
                    cupsAmount -= 1;
                    milkAmount -= 0;
                    currentPrice += 4;
                    System.out.println("I have enough resources, making you a coffee!");
                }
                coffeeState = CoffeeState.ACTION;
                break;
            case LATTE:
                if (waterAmount - 350 < 0) {
                    System.out.println("Sorry, not enough water!");
                } else if (beansAmount - 20 < 0) {
                    System.out.println("Sorry, not enough beans!");
                } else if (cupsAmount - 1 < 0) {
                    System.out.println("Sorry, not enough cups!");
                } else if (milkAmount - 75 < 0){
                    System.out.println("Sorry, not enough milk!");
                } else {
                    waterAmount -= 350;
                    beansAmount -= 20;
                    cupsAmount -= 1;
                    milkAmount -= 75;
                    currentPrice += 7;
                    System.out.println("I have enough resources, making you a coffee!");
                }
                coffeeState = CoffeeState.ACTION;
                break;
            case CAPPUCCINO:
                if (waterAmount - 200 < 0) {
                    System.out.println("Sorry, not enough water!");
                } else if (beansAmount - 12 < 0) {
                    System.out.println("Sorry, not enough beans!");
                } else if (cupsAmount - 1 < 0) {
                    System.out.println("Sorry, not enough cups!");
                } else if (milkAmount - 100 < 0){
                    System.out.println("Sorry, not enough milk!");
                } else {
                    waterAmount -= 200;
                    beansAmount -= 12;
                    cupsAmount -= 1;
                    milkAmount -= 100;
                    currentPrice += 6;
                    System.out.println("I have enough resources, making you a coffee!");
                }
                coffeeState = CoffeeState.ACTION;
                break;
        }
        if (coffeeState == CoffeeState.ACTION) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
        }
    }
}

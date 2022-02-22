package machine;

public class CoffeeMachine {
    public static void main(String[] args) {
        startMachine();
        grindBeans();
        boilWater();
        mixWaterAndBeans();
        pourCoffee();
        pourMilk();
        serveCoffee();
    }

    private static void startMachine() {
        System.out.println("Starting to make a coffee");
    }
    private static void grindBeans() {
        System.out.println("Grinding coffee beans");
    }

    private static void boilWater() {
        System.out.println("Boiling water");
    }

    private static void mixWaterAndBeans() {
        System.out.println("Mixing boiled water with crushed coffee beans");
    }

    private static void pourCoffee() {
        System.out.println("Pouring coffee into the cup");
    }

    private static void pourMilk() {
        System.out.println("Pouring some milk into the cup");
    }

    private static void serveCoffee() {
        System.out.println("Coffee is ready!");
    }
}

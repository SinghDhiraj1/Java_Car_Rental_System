import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
class Car{

    // car info
    private String carID;
    private String carBrand;
    private String carModel;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String carBrand, String carModel, double basePricePerDay){
        this.carID = carId;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarID(){
        return carID;
    }

    public String getCarBrand(){
        return carBrand;
    }

    public String getCarModel(){
        return carModel;
    }

    public double getBasePricePerDay(){
        return basePricePerDay;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public double calculatePrice(int rentalDays){
        return basePricePerDay * rentalDays;
    }

    public void rent(){
        isAvailable = false;
    }

    public void returnCar(){
        isAvailable = true;
    }
}

class Customer{
    private String customerId;
    private String name;

    public Customer(String customerId, String name){
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName(){
        return name;
    }

}

class Rental{
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days){
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalSystem{
    //declaring empty arraylist to store data of cars, customer and rental
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car){
        cars.add(car);
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days){
        if(car.isAvailable()){
            rentals.add(new Rental(car, customer, days));
            car.rent();
        } else {
            System.out.println("Sorry, specified Car is not available. Search for similar cars");
        }
    }

    public void returnCar(Car car){
        Rental RentalToReturn = null;
        for(Rental rental:rentals){
            if(rental.getCar() == car){
                RentalToReturn = rental;
                break;
            }
        }
        if(RentalToReturn != null){
            rentals.remove(RentalToReturn);
            car.returnCar();
        } else {
            System.out.println("Car was not rented");
        }
    }

    public void menu(){
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("======Welcome to Car Rental System======");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); //Customer choice

            if(choice == 1){
                System.out.println("======Rent a Car======");
                System.out.print("Enter your name: ");
                String customerName = sc.nextLine();
                System.out.println("Enter your contact number : ");

                for(Car car:cars){
                    if(car.isAvailable()){
                        System.out.println(car.getCarID()+" - "+car.getCarBrand()+" "+car.getCarModel());
                    }
                }

                System.out.print("Enter the car ID you want to rent: ");
                String carID = sc.nextLine();

                System.out.print("Enter total no. of days to rent the car: ");
                int days = sc.nextInt();
                sc.nextLine();

                Customer newCustomer = new Customer("CUS"+customers.size()+1, customerName);

                Car selectedCar = null;
                for(Car car:cars){
                    if(carID.equals(car.getCarID()) && car.isAvailable()){
                        selectedCar = car;
                        break;
                    }
                }

                if(selectedCar != null){
                    double totalPrice = selectedCar.calculatePrice(days);
                    System.out.println("====Rental Information====");
                    System.out.println("Customer ID : "+newCustomer.getCustomerId());
                    System.out.println("Customer Name : "+newCustomer.getName());
                    System.out.println("Car information : "+selectedCar.getCarBrand()+" "+selectedCar.getCarModel());
                    System.out.println("Rented Days : "+days);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.println("Please confirm (Y/N) : ");
                    String option = sc.nextLine();

                    if(option.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar,newCustomer,days);
                        System.out.println("Car Rented Successfully");
                    } else {
                        System.out.println("Rental Cancelled");
                    }
                } else {
                    System.out.println("\nInvalid car selected or car not available for rent");
                }
            } else if (choice==2) {
                System.out.println("\n====Return a Car====\n");
                System.out.println("Enter carId you have to return :");
                String carId = sc.nextLine();

                Car carToReturn = null;
                for(Car car:cars){
                    if(carId.equals(car.getCarID())){
                        carToReturn = car;
                        break;
                    }
                }
                if(carToReturn != null){
                    Customer customer = null;
                    for(Rental rental:rentals){
                        if(rental.getCar().equals(carToReturn)){
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if(customer != null){
                        returnCar(carToReturn);
                        System.out.println("Car returned Successfully by "+customer.getName());
                    }
                    else{
                        System.out.println("Car was not rented or rental information is missing");
                    }
                }
                else{
                    System.out.println("Invalid car ID or car is not rented");
                }
            }
            else if(choice == 3){
                break;
            }
            else{
                System.out.println("Invalid choice. Please enter a valid option");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("C001","Mahindra","Thar",70.0);
        Car car2 = new Car("C002","Tata","Safari",100.0);
        Car car3 = new Car("C003","Toyota","Fortuner",150.0);
        Car car4 = new Car("C004", "Hyundai","Creta",90.0);
        Car car5 = new Car("C005","Honda","City",90.0);

        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.addCar(car4);
        rentalSystem.addCar(car5);

        rentalSystem.menu();
    }
}
// Abstract base class for Vehicle
public abstract class Vehicle {
    // Encapsulated fields to protect data
    private String vehicleId;
    private String model;
    private double baseRentalRate;
    private boolean isAvailable;

    // Constructor to initialize vehicle details with validation
    public Vehicle(String vehicleId, String model, double baseRentalRate) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.baseRentalRate = baseRentalRate;
        this.isAvailable = true; // Default to available
    }

    // Getters for accessing private fields
    public String getVehicleId() {
        return vehicleId;
    }

    public String getModel() {
        return model;
    }

    public double getBaseRentalRate() {
        return baseRentalRate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    // Setter to update availability status
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    // Abstract methods to enforce implementation in subclasses
    public abstract double calculateRentalCost(int days);

    public abstract boolean isAvailableForRental();

    // Overriding toString for meaningful object representation
    @Override
    public String toString() {
        return "Vehicle ID: " + vehicleId + ", Model: " + model + ", Base Rental Rate: " + baseRentalRate + ", Available: " + isAvailable;
    }
}

// Rentable interface defining common rental operations
interface Rentable {
    void rent(Customer customer, int days);

    void returnVehicle();
}

// Car class extending Vehicle and implementing Rentable
class Car extends Vehicle implements Rentable {
    private boolean hasGPS; // Additional feature specific to Car

    // Constructor initializing Car-specific attributes
    public Car(String vehicleId, String model, double baseRentalRate, boolean hasGPS) {
        super(vehicleId, model, baseRentalRate);
        this.hasGPS = hasGPS;
    }

    // Getter for GPS feature
    public boolean hasGPS() {
        return hasGPS;
    }

    // Overriding rental cost calculation for Car
    @Override
    public double calculateRentalCost(int days) {
        double cost = getBaseRentalRate() * days;
        if (hasGPS) {
            cost += 5 * days; // Additional GPS fee per day
        }
        return cost;
    }

    // Checking availability for rental
    @Override
    public boolean isAvailableForRental() {
        return isAvailable();
    }

    // Renting the car to a customer
    @Override
    public void rent(Customer customer, int days) {
        if (isAvailableForRental()) {
            setAvailable(false); // Mark car as unavailable
            System.out.println("Car rented to " + customer.getName() + " for " + days + " days.");
        } else {
            System.out.println("Car is not available for rental.");
        }
    }

    // Returning the car and marking it as available
    @Override
    public void returnVehicle() {
        setAvailable(true);
        System.out.println("Car returned.");
    }
}

// Motorcycle class extending Vehicle and implementing Rentable
class Motorcycle extends Vehicle implements Rentable {
    private boolean hasSidecar; // Additional feature specific to Motorcycle

    // Constructor initializing Motorcycle-specific attributes
    public Motorcycle(String vehicleId, String model, double baseRentalRate, boolean hasSidecar) {
        super(vehicleId, model, baseRentalRate);
        this.hasSidecar = hasSidecar;
    }

    // Getter for sidecar feature
    public boolean hasSidecar() {
        return hasSidecar;
    }

    // Overriding rental cost calculation for Motorcycle
    @Override
    public double calculateRentalCost(int days) {
        double cost = getBaseRentalRate() * days;
        if (hasSidecar) {
            cost += 10 * days; // Additional sidecar fee per day
        }
        return cost;
    }

    // Checking availability for rental
    @Override
    public boolean isAvailableForRental() {
        return isAvailable();
    }

    // Renting the motorcycle to a customer
    @Override
    public void rent(Customer customer, int days) {
        if (isAvailableForRental()) {
            setAvailable(false); // Mark motorcycle as unavailable
            System.out.println("Motorcycle rented to " + customer.getName() + " for " + days + " days.");
        } else {
            System.out.println("Motorcycle is not available for rental.");
        }
    }

    // Returning the motorcycle and marking it as available
    @Override
    public void returnVehicle() {
        setAvailable(true);
        System.out.println("Motorcycle returned.");
    }
}

// Truck class extending Vehicle and implementing Rentable
class Truck extends Vehicle implements Rentable {
    private double maxLoadCapacity; // Additional feature specific to Truck

    // Constructor initializing Truck-specific attributes
    public Truck(String vehicleId, String model, double baseRentalRate, double maxLoadCapacity) {
        super(vehicleId, model, baseRentalRate);
        this.maxLoadCapacity = maxLoadCapacity;
    }

    // Getter for load capacity
    public double getMaxLoadCapacity() {
        return maxLoadCapacity;
    }

    // Overriding rental cost calculation for Truck
    @Override
    public double calculateRentalCost(int days) {
        double cost = getBaseRentalRate() * days;
        // Additional fee based on load capacity
        cost += (maxLoadCapacity > 1000 ? 50 : 20) * days;
        return cost;
    }

    // Checking availability for rental
    @Override
    public boolean isAvailableForRental() {
        return isAvailable();
    }

    // Renting the truck to a customer
    @Override
    public void rent(Customer customer, int days) {
        if (isAvailableForRental()) {
            setAvailable(false); // Mark truck as unavailable
            System.out.println("Truck rented to " + customer.getName() + " for " + days + " days.");
        } else {
            System.out.println("Truck is not available for rental.");
        }
    }

    // Returning the truck and marking it as available
    @Override
    public void returnVehicle() {
        setAvailable(true);
        System.out.println("Truck returned.");
    }
}

// Customer class to manage customer-specific details
class Customer {
    private String customerId;
    private String name;
    private String rentalHistory; // Stores rental history as a string

    // Constructor initializing customer details
    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
        this.rentalHistory = "";
    }

    // Getters for customer details
    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    // Adding rental history entry
    public void addRentalHistory(String rental) {
        this.rentalHistory += rental + "\n";
    }

    // Getter for rental history
    public String getRentalHistory() {
        return rentalHistory;
    }
}

// RentalAgency class to manage the vehicle rental process
class RentalAgency {
    private List<Vehicle> vehicleFleet; // Composition: list of vehicles in the fleet

    // Constructor initializing the vehicle fleet
    public RentalAgency() {
        this.vehicleFleet = new ArrayList<>();
    }

    // Adding a vehicle to the fleet
    public void addVehicle(Vehicle vehicle) {
        vehicleFleet.add(vehicle);
    }

    // Finding an available vehicle by model
    public Vehicle findAvailableVehicle(String model) {
        for (Vehicle vehicle : vehicleFleet) {
            if (vehicle.getModel().equalsIgnoreCase(model) && vehicle.isAvailableForRental()) {
                return vehicle;
            }
        }
        return null; // Return null if no vehicle is available
    }

    // Processing a rental for a customer
    public void processRental(Customer customer, String model, int days) {
        Vehicle vehicle = findAvailableVehicle(model);
        if (vehicle != null) {
            ((Rentable) vehicle).rent(customer, days); // Polymorphic call
            customer.addRentalHistory("Rented " + model + " for " + days + " days.");
        } else {
            System.out.println("No available vehicle of model: " + model);
        }
    }

    // Returning a vehicle
    public void returnVehicle(Vehicle vehicle) {
        ((Rentable) vehicle).returnVehicle(); // Polymorphic call
    }
}

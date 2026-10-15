public class ConstructorsExamples {

    public static void main(String[] args) {
        // Run examples of constructor overloading and initialization.
        demonstrateCircles();
        demonstrateEmployees();
        demonstrateInitializationOrder();
        demonstrateTemperatures();
    }

    private static void demonstrateCircles() {
        System.out.println("CIRCLES (DEFAULT & PARAMETERIZED CONSTRUCTORS)");
        Circle defaultCircle = new Circle();
        Circle customCircle = new Circle(2.5);

        System.out.println("  Default circle radius: " + defaultCircle.getRadius());
        System.out.println("  Custom circle radius:  " + customCircle.getRadius());
        System.out.println("  Total circles created: " + Circle.getCircleCount());
    }

    private static void demonstrateEmployees() {
        System.out.println("\nEMPLOYEES (CONSTRUCTOR OVERLOADING & COPY CONSTRUCTOR)");
        Employee first = new Employee();
        Employee second = new Employee("Amina");
        Employee third = new Employee("Brian", 52000.0);
        Employee copied = new Employee(third);

        System.out.println("  " + first);
        System.out.println("  " + second);
        System.out.println("  " + third);
        System.out.println("  " + copied + " (cloned from Brian with new unique ID)");
    }

    private static void demonstrateInitializationOrder() {
        System.out.println("\nINITIALIZATION ORDER (STATIC vs INSTANCE vs CONSTRUCTOR)");
        System.out.println("Creating first instance of InitializationOrder:");
        new InitializationOrder();
        System.out.println("Creating second instance of InitializationOrder:");
        new InitializationOrder();
    }

    private static void demonstrateTemperatures() {
        System.out.println("\nTEMPERATURE (STATIC FACTORY METHODS)");
        Temperature fromCelsius = Temperature.fromCelsius(25.0);
        Temperature fromFahrenheit = Temperature.fromFahrenheit(77.0);

        System.out.printf("  25.0 C converted to Fahrenheit: %.1f F%n", fromCelsius.toFahrenheit());
        System.out.printf("  77.0 F converted to Celsius:    %.1f C%n", fromFahrenheit.toCelsius());
    }

    public static class Circle {
        private static int circleCount;
        private final double radius;

        public Circle() {
            // Reuse the parameterized constructor's initialization.
            this(1.0);
        }

        public Circle(double radius) {
            this.radius = radius;
            circleCount++;
        }

        public double getRadius() {
            return radius;
        }

        public static int getCircleCount() {
            return circleCount;
        }
    }

    public static class Employee {
        private static int nextEmployeeId = 1;

        private final int employeeId;
        private final String name;
        private final double salary;

        public Employee() {
            this("Unassigned", 0.0);
        }

        public Employee(String name) {
            this(name, 0.0);
        }

        public Employee(String name, double salary) {
            this.employeeId = nextEmployeeId++;
            this.name = name;
            this.salary = salary;
        }

        public Employee(Employee other) {
            // Copy the details while assigning a new employee ID.
            this(other.name, other.salary);
        }

        public int getEmployeeId() {
            return employeeId;
        }

        public String getName() {
            return name;
        }

        public double getSalary() {
            return salary;
        }

        @Override
        public String toString() {
            return "Employee{id=" + employeeId
                    + ", name='" + name + '\''
                    + ", salary=$" + String.format("%.2f", salary) + '}';
        }
    }

    public static class InitializationOrder {
        // Runs once when the class is first loaded.
        static {
            System.out.println("  [1] Static initializer block (runs once when class is loaded)");
        }

        // Runs before the constructor for every new instance.
        {
            System.out.println("  [2] Instance initializer block (runs before constructor on each instance)");
        }

        public InitializationOrder() {
            System.out.println("  [3] Constructor body executed");
        }
    }

    public static class Temperature {
        private final double celsius;

        private Temperature(double celsius) {
            this.celsius = celsius;
        }

        public static Temperature fromCelsius(double celsius) {
            return new Temperature(celsius);
        }

        public static Temperature fromFahrenheit(double fahrenheit) {
            return new Temperature((fahrenheit - 32.0) * 5.0 / 9.0);
        }

        public double toCelsius() {
            return celsius;
        }

        public double toFahrenheit() {
            return celsius * 9.0 / 5.0 + 32.0;
        }
    }
}

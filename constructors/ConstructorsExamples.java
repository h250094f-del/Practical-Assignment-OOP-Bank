public class ConstructorsExamples {

    public static void main(String[] args) {
        demonstrateCircles();
        demonstrateEmployees();
        demonstrateInitializationOrder();
        demonstrateTemperatures();
    }

    private static void demonstrateCircles() {
        System.out.println("CIRCLES");
        Circle defaultCircle = new Circle();
        Circle customCircle = new Circle(2.5);

        System.out.println("Default radius: " + defaultCircle.getRadius());
        System.out.println("Custom radius: " + customCircle.getRadius());
        System.out.println("Circles created: " + Circle.getCircleCount());
    }

    private static void demonstrateEmployees() {
        System.out.println("\nEMPLOYEES");
        Employee first = new Employee();
        Employee second = new Employee("Amina");
        Employee third = new Employee("Brian", 52000.0);
        Employee copied = new Employee(third);

        System.out.println(first);
        System.out.println(second);
        System.out.println(third);
        System.out.println(copied);
    }

    private static void demonstrateInitializationOrder() {
        System.out.println("\nINITIALIZATION ORDER");
        new InitializationOrder();
        new InitializationOrder();
    }

    private static void demonstrateTemperatures() {
        System.out.println("\nTEMPERATURE FACTORIES");
        Temperature fromCelsius = Temperature.fromCelsius(25.0);
        Temperature fromFahrenheit = Temperature.fromFahrenheit(77.0);

        System.out.printf("25.0 C = %.1f F%n", fromCelsius.toFahrenheit());
        System.out.printf("77.0 F = %.1f C%n", fromFahrenheit.toCelsius());
    }

    private static class Circle {
        private static int circleCount;

        private final double radius;

        private Circle() {
            this(1.0);
        }

        private Circle(double radius) {
            this.radius = radius;
            circleCount++;
        }

        private double getRadius() {
            return radius;
        }

        private static int getCircleCount() {
            return circleCount;
        }
    }

    private static class Employee {
        private static int nextEmployeeId = 1;

        private final int employeeId;
        private final String name;
        private final double salary;

        private Employee() {
            this("Unassigned", 0.0);
        }

        private Employee(String name) {
            this(name, 0.0);
        }

        private Employee(String name, double salary) {
            this.employeeId = nextEmployeeId++;
            this.name = name;
            this.salary = salary;
        }

        private Employee(Employee other) {
            this(other.name, other.salary);
        }

        @Override
        public String toString() {
            return "Employee{id=" + employeeId
                    + ", name='" + name + '\''
                    + ", salary=" + salary + '}';
        }
    }

    private static class InitializationOrder {
        static {
            System.out.println("First static block");
        }

        static {
            System.out.println("Second static block");
        }

        {
            System.out.println("First instance block");
        }

        {
            System.out.println("Second instance block");
        }

        private InitializationOrder() {
            System.out.println("Constructor");
        }
    }

    private static class Temperature {
        private final double celsius;

        private Temperature(double celsius) {
            this.celsius = celsius;
        }

        private static Temperature fromCelsius(double celsius) {
            return new Temperature(celsius);
        }

        private static Temperature fromFahrenheit(double fahrenheit) {
            return new Temperature((fahrenheit - 32.0) * 5.0 / 9.0);
        }

        private double toCelsius() {
            return celsius;
        }

        private double toFahrenheit() {
            return celsius * 9.0 / 5.0 + 32.0;
        }
    }
}

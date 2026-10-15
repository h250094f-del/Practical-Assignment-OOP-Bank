import java.time.LocalDate;

public class PracticalOOPExamples {

    public static void main(String[] args) {
        // Run examples of objects, references, and composition.
        demonstrateCars();
        demonstrateRectangles();
        demonstrateRectangleReference();
        demonstrateAddressAndPerson();
    }

    private static void demonstrateCars() {
        System.out.println("CARS (OBJECT INSTANTIATION & BEHAVIOR)");
        Car[] cars = {
            new Car("Toyota", 1990, 180000),
            new Car("Honda", 2001, 95000),
            new Car("Ford", 2018, 42000)
        };

        for (Car car : cars) {
            car.display();
            System.out.println("  Antique: " + car.isAntique());
        }
    }

    private static void demonstrateRectangles() {
        System.out.println("\nRECTANGLES (ARRAY OF OBJECTS & AGGREGATION)");
        // Track the largest area and perimeter independently.
        Rectangle[] rectangles = {
            new Rectangle(4, 5),
            new Rectangle(8, 3),
            new Rectangle(6, 7),
            new Rectangle(10, 2),
            new Rectangle(5, 5)
        };

        Rectangle largest = rectangles[0];
        Rectangle largestPerimeter = rectangles[0];
        for (Rectangle rectangle : rectangles) {
            if (rectangle.area() > largest.area()) {
                largest = rectangle;
            }
            if (rectangle.perimeter() > largestPerimeter.perimeter()) {
                largestPerimeter = rectangle;
            }
        }

        System.out.printf("  Largest area:      %.2f (width=%.2f, height=%.2f)%n",
                largest.area(), largest.getWidth(), largest.getHeight());
        System.out.printf("  Largest perimeter: %.2f (width=%.2f, height=%.2f)%n",
                largestPerimeter.perimeter(), largestPerimeter.getWidth(), largestPerimeter.getHeight());
    }

    private static void demonstrateRectangleReference() {
        System.out.println("\nRECTANGLE REFERENCE (ALIASING)");
        Rectangle r1 = new Rectangle(4, 6);
        // Both variables refer to the same Rectangle object.
        Rectangle r2 = r1;
        System.out.println("  Initial r1 width: " + r1.getWidth());
        System.out.println("  Changing r2.setWidth(9.0)...");
        r2.setWidth(9.0);
        System.out.println("  r1 width after modifying through r2: " + r1.getWidth() + " (both point to same memory address)");
    }

    private static void demonstrateAddressAndPerson() {
        System.out.println("\nPERSON ADDRESS (COMPOSITION & NULL-SAFETY)");
        // A Person contains an Address, which may be absent.
        Person person = new Person("Amina", new Address("Kampala"));
        System.out.println("  Person: " + person.getName() + " in " + person.getAddress().getCity());

        Person personWithoutAddress = new Person("Brian", null);
        try {
            System.out.println(personWithoutAddress.getAddress().getCity());
        } catch (NullPointerException exception) {
            System.out.println("  Caught expected NullPointerException when accessing city on null address.");
        }
    }

    public static class Car {
        private final String brand;
        private final int year;
        private final double mileage;

        public Car(String brand, int year, double mileage) {
            this.brand = brand;
            this.year = year;
            this.mileage = mileage;
        }

        public void display() {
            System.out.printf("  %s, year %d, mileage %.0f km%n", brand, year, mileage);
        }

        public boolean isAntique() {
            return LocalDate.now().getYear() - year > 25;
        }
    }

    public static class Rectangle {
        private double width;
        private final double height;

        public Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public double getHeight() {
            return height;
        }

        public double area() {
            return width * height;
        }

        public double perimeter() {
            return 2 * (width + height);
        }
    }

    public static class Address {
        private final String city;

        public Address(String city) {
            this.city = city;
        }

        public String getCity() {
            return city;
        }
    }

    public static class Person {
        private final String name;
        private final Address address;

        public Person(String name, Address address) {
            this.name = name;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public Address getAddress() {
            return address;
        }
    }
}

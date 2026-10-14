import java.time.LocalDate;

public class PracticalOOPExamples {

    public static void main(String[] args) {
        demonstrateCars();
        demonstrateRectangles();
        demonstrateRectangleReference();
        demonstrateAddressAndPerson();
    }

    private static void demonstrateCars() {
        System.out.println("CARS");
        Car[] cars = {
            new Car("Toyota", 1990, 180000),
            new Car("Honda", 2001, 95000),
            new Car("Ford", 2018, 42000)
        };

        for (Car car : cars) {
            car.display();
            System.out.println("Antique: " + car.isAntique());
        }
    }

    private static void demonstrateRectangles() {
        System.out.println("\nRECTANGLES");
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

        System.out.printf(
                "Largest area: %.2f (width=%.2f, height=%.2f)%n",
                largest.area(), largest.width, largest.height);
        System.out.printf(
                "Largest perimeter: %.2f (width=%.2f, height=%.2f)%n",
                largestPerimeter.perimeter(), largestPerimeter.width, largestPerimeter.height);
    }

    private static void demonstrateRectangleReference() {
        System.out.println("\nRECTANGLE REFERENCE");
        Rectangle r1 = new Rectangle(4, 6);
        Rectangle r2 = r1;
        r2.width = 9;
        System.out.println("r1.width after changing r2.width: " + r1.width);
    }

    private static void demonstrateAddressAndPerson() {
        System.out.println("\nPERSON ADDRESS");
        Person person = new Person("Amina", new Address("Kampala"));
        System.out.println(person.address.city);

        Person personWithoutAddress = new Person("Brian", null);
        try {
            System.out.println(personWithoutAddress.address.city);
        } catch (NullPointerException exception) {
            System.out.println("Accessing city when address is null throws NullPointerException.");
        }
    }

    private static class Car {
        private final String brand;
        private final int year;
        private final double mileage;

        private Car(String brand, int year, double mileage) {
            this.brand = brand;
            this.year = year;
            this.mileage = mileage;
        }

        private void display() {
            System.out.printf(
                    "%s, year %d, mileage %.0f km%n",
                    brand, year, mileage);
        }

        private boolean isAntique() {
            return LocalDate.now().getYear() - year > 25;
        }
    }

    private static class Rectangle {
        private double width;
        private final double height;

        private Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }

        private double area() {
            return width * height;
        }

        private double perimeter() {
            return 2 * (width + height);
        }
    }

    private static class Address {
        private final String city;

        private Address(String city) {
            this.city = city;
        }
    }

    private static class Person {
        private final String name;
        private final Address address;

        private Person(String name, Address address) {
            this.name = name;
            this.address = address;
        }
    }
}

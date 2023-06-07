public class City {
    public static void main(String[] args) {

        String cityName = "Russe";
        String country = "Bulgaria";
        int population = 143325;
        int area = 127120;
        double populationDensity = (double)population / area * 1000;
        String mayor = "Pencho Milkov";

        System.out.printf("City name: %s%n", cityName);
        System.out.printf("Country: %s%n", country);
        System.out.printf("Population: %d%n", population );
        System.out.printf("Area: %d%n", area);
        System.out.printf("Population density: %.2f%n", populationDensity);
        System.out.printf("Mayor: %s", mayor);

    }
}

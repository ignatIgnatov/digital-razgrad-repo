public class Countries {
    public static void main(String[] args) {

        String firstCountry = "Bulgaria";
        int firstCountryArea = 110994;
        int firstCountryPopulation = 6520314;
        double firstCountryPopulationDensity = (double)firstCountryPopulation / firstCountryArea * 1000;

        String secondCountry = "Rumania";
        int secondCountryArea = 238397;
        int secondCountryPopulation = 19186201;
        double secondCountryPopulationDensity = (double)secondCountryPopulation / secondCountryArea * 1000;

        System.out.println("Population density of " + firstCountry + ": " + firstCountryPopulationDensity);
        System.out.println("Population density of " + secondCountry + ": " + secondCountryPopulationDensity);

        System.out.println("Population density of " + firstCountry + ": " + (int)firstCountryPopulationDensity);
        System.out.println("Population density of " + secondCountry + ": " + (int)secondCountryPopulationDensity);

        System.out.println("Larger population: " + Math.max(firstCountryPopulation, secondCountryPopulation));



    }
}

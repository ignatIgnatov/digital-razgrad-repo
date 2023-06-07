import java.util.Scanner;

public class Homework_07 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Enter brand! ***");
        System.out.println("/You can choose one of the following: Apple, HP, Lenovo, Dell, Acer, Asus or other:/");
        String brand = scanner.nextLine();
        int brandPoints = 0;
        if (!brand.isEmpty()) {
            switch (brand) {
                case "Apple":
                    brandPoints = 6;
                    break;
                case "HP":
                case "Lenovo":
                    brandPoints = 4;
                    break;
                case "Dell":
                case "Acer":
                case "Asus":
                    brandPoints = 2;
                    break;
                default:
                    brandPoints = 1;
                    break;
            }
        }

        System.out.println("*** Enter processor! ***");
        System.out.println("/You can choose one of the following: AMD Ryzen 9, Intel Core i9," +
                "AMD Ryzen 7, Intel Core i7, Apple M1, Intel Core i5 or other:/");
        String processor = scanner.nextLine();
        int processorPoints = 0;
        if (!processor.isEmpty()) {
            switch (processor) {
                case "AMD Ryzen 9":
                case "Intel Core i9":
                    processorPoints = 6;
                    break;
                case "AMD Ryzen 7":
                case "Intel Core i7":
                    processorPoints = 4;
                    break;
                case "Apple M1":
                case "Intel Core i5":
                    processorPoints = 2;
                    break;
                default:
                    processorPoints = 1;
                    break;
            }
        }

        System.out.println("*** Enter RAM size /GB/! ***");
        System.out.println("/You can choose one of the following: 64, 32, 16, 8, 4 or other:/");
        String ramSize = scanner.nextLine();
        int ramSizePoints = 0;
        if (!ramSize.isEmpty()) {
            switch (ramSize) {
                case "64":
                    ramSizePoints = 6;
                    break;
                case "32":
                    ramSizePoints = 5;
                    break;
                case "16":
                    ramSizePoints = 4;
                    break;
                case "8":
                    ramSizePoints = 3;
                    break;
                case "4":
                    ramSizePoints = 2;
                    break;
                default:
                    ramSizePoints = 1;
                    break;
            }
        }


        System.out.println("*** Enter HDD type! ***");
        System.out.println("/You can choose one of the following: SSD, SATA, PATA or other:/");
        String hddType = scanner.nextLine();
        int hddTypePoints = 0;
        if (!hddType.isEmpty()) {
            switch (hddType) {
                case "SSD":
                    hddTypePoints = 6;
                    break;
                case "SATA":
                case "PATA":
                    hddTypePoints = 2;
                    break;
                default:
                    hddTypePoints = 1;
                    break;
            }
        }

        System.out.println("*** Enter HDD size /GB/! ***");
        System.out.println("/You can choose one of the following: 2000, 1000, 512, 256, 128 or other:/");
        String hddSize = scanner.nextLine();
        int hddSizePoints = 0;
        if (!hddSize.isEmpty()) {
            switch (hddSize) {
                case "2000":
                    hddSizePoints = 6;
                    break;
                case "1000":
                    hddSizePoints = 5;
                    break;
                case "512":
                    hddSizePoints = 4;
                    break;
                case "256":
                    hddSizePoints = 3;
                    break;
                case "128":
                    hddSizePoints = 2;
                    break;
                default:
                    hddSizePoints = 1;
                    break;
            }
        }

        System.out.println("*** Enter video card brand! ***");
        System.out.println("/You can choose one of the following: NVIDIA, AMD or other:/");
        String videoCardBrand = scanner.nextLine();
        int videoCardBrandPoints = 0;
        if (!videoCardBrand.isEmpty()) {
            switch (videoCardBrand) {
                case "NVIDIA":
                case "AMD":
                    videoCardBrandPoints = 4;
                    break;
                default:
                    videoCardBrandPoints = 1;

            }
        }

        System.out.println("*** Enter video card size /GB/! ***");
        System.out.println("/You can choose one of the following: 2000, 1000, 512, 256, 128 or other:/");
        String videoCardSize = scanner.nextLine();
        int videoCardSizePoints = 0;
        if (!videoCardSize.isEmpty()) {
            switch (videoCardSize) {
                case "2000":
                    videoCardSizePoints = 6;
                    break;
                case "1000":
                    videoCardSizePoints = 5;
                    break;
                case "512":
                    videoCardSizePoints = 4;
                    break;
                case "256":
                    videoCardSizePoints = 3;
                    break;
                case "128":
                    videoCardSizePoints = 2;
                    break;
                default:
                    videoCardSizePoints = 1;
                    break;

            }
        }

        int totalPoints = brandPoints
                + processorPoints
                + ramSizePoints
                + hddTypePoints
                + hddSizePoints
                + videoCardBrandPoints
                + videoCardSizePoints;


        System.out.println("***** *** ***** *** ***** *** ***** *** ***** *** *****");
        if (totalPoints >= 36) {
            System.out.println("You have an excellent computer! Let's freak out!");
        } else if (totalPoints >= 15) {
            System.out.println("You have a good computer!");
        } else {
            System.out.println("Your computer is poor. You have to buy a new one soon.");
        }
        System.out.println("***** *** ***** *** ***** *** ***** *** ***** *** *****");

    }
}

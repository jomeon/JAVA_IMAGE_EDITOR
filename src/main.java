
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
public class main {

    public static void main(String[] args) {
        String inputFilePath= "";
        System.out.println("Podaj metode od 1-4 [1-normalizacja, 2-filtracja entropii, 3-otwarcie elementem kolowym, 4-wypelnianie dziur");
        Scanner scanner = new Scanner(System.in);
        int numb = scanner.nextInt();
        switch (numb){
            case 1:
                inputFilePath = "C:/Users/hello/IdeaProjects/zad/Projekt_Obrazy/src/F_dzieciol.png";
            case 2:
                inputFilePath = "C:/Users/hello/IdeaProjects/zad/Projekt_Obrazy/src/F_dzieciol.png";
            case 3:
                inputFilePath = "C:/Users/hello/IdeaProjects/zad/Projekt_Obrazy/src/shape (1)";
            case 4:
                inputFilePath = "C:/Users/hello/IdeaProjects/zad/Projekt_Obrazy/src/dziury.bmp";
        }
        //String inputFilePath = "C:/Users/hello/IdeaProjects/zad/Projekt_Obrazy/src/F_dzieciol.png"; // ścieżka do pliku wejściowego
        String outputFilePath = "output"; // ścieżka do pliku wyjściowego
        System.out.println("Podaj metode od 1-4 [1-normalizacja, 2-filtracja entropii, 3-otwarcie elementem kolowym, 4-wypelnianie dziur");
        //Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        switch (number) {
            case 1:
            System.out.println("Podaj trzy pary punktów normalizacji w formacie 'wejscie1 wyjscie1 wejscie2 wyjscie2 wejscie3 wyjscie3':");
            int input1 = scanner.nextInt();
            int input11 = scanner.nextInt();
            int input2 = scanner.nextInt();
            int input22 = scanner.nextInt();
            int input3 = scanner.nextInt();
            int input33 = scanner.nextInt();

            int[][] normalizationPoints = { // łamana normalizacji
                    {input1, input11},
                    {input2, input22},
                    {input3, input33}
            };

            try {
                System.out.println("Podaj czy chcesz robić dla BW czy RGB (0-BW,1-RGB)");
                BufferedImage inputImage = ImageIO.read(new File(inputFilePath));
                Scanner scan = new Scanner(System.in);
                int inp = scan.nextInt();
                if (inp == 0) {
                    BufferedImage outputImage = ImageNormalizer.normalizeBW(inputImage, normalizationPoints);
                    ImageIO.write(outputImage, "png", new File(outputFilePath + "_bw.png"));
                    System.out.println("Image BW normalized successfully!");
                    break;
                } else if (inp == 1) {
                    BufferedImage outputImage = ImageNormalizer.normalizeRGB(inputImage, normalizationPoints);
                    ImageIO.write(outputImage, "png", new File(outputFilePath + "_rgb.png"));
                    System.out.println("Image RGB normalized successfully!");
                    break;
                } else {
                    System.out.println("ERROR, WRONG NUMBER");
                    break;
                }
            } catch (IOException e) {
                System.out.println("Error occurred: " + e.getMessage());
                break;
            }
            case 2:
                try {
                    System.out.println("Podaj czy chcesz robić dla BW czy RGB (0-BW,1-RGB)");
                    BufferedImage inputImage = ImageIO.read(new File(inputFilePath));
                    Scanner scan = new Scanner(System.in);
                    int inp = scan.nextInt();
                    if (inp == 0) {
                        System.out.println("Podaj rozmiar okna");
                        int size= scan.nextInt();
                        BufferedImage outputImage = entropyFilter.entropyFilterRGB(inputImage,size);
                        ImageIO.write(outputImage, "png", new File(outputFilePath + "_bw_entropy.png"));
                        System.out.println("Image BW entropy successfully!");
                        break;
                    } else if (inp == 1) {
                        System.out.println("Podaj rozmiar okna");
                        int size= scan.nextInt();
                        BufferedImage outputImage = entropyFilter.entropyFilterBW(inputImage,size);
                        ImageIO.write(outputImage, "png", new File(outputFilePath + "_rgb_entropy.png"));
                        System.out.println("Image RGB entropy successfully!");
                        break;
                    } else {
                        System.out.println("ERROR, WRONG NUMBER");
                        break;
                    }
                } catch (IOException e) {
                    System.out.println("Error occurred: " + e.getMessage());
                    break;
                }
            case 3:
                try {
                    System.out.println("Podaj czy chcesz robić dla BW czy RGB (0-BW,1-RGB)");
                    BufferedImage inputImage = ImageIO.read(new File(inputFilePath));
                    Scanner scan = new Scanner(System.in);
                    int inp = scan.nextInt();
                    if (inp == 0) {
                        System.out.println("Podaj radius");
                        int radius= scan.nextInt();
                        BufferedImage outputImage = morphOpening.morphologicalOpening(inputImage,radius);
                        ImageIO.write(outputImage, "png", new File(outputFilePath + "_bw_and.png"));
                        System.out.println("Image BW and successfully!");
                    }
                    else {
                        System.out.println("ERROR, WRONG NUMBER");
                    } break;
                } catch (IOException e) {
                    System.out.println("Error occurred: " + e.getMessage());
                    break;
                }
            case 4:
                try {
                    BufferedImage inputImage = ImageIO.read(new File(inputFilePath));
                    BufferedImage outputImage = ObjectHoleFilling.fillHoles( inputImage);
                    ImageIO.write(outputImage, "png", new File(outputFilePath + "_bw_entropy_filled.png"));
                    System.out.println("Image BW filled successfully!");
                    break;

                } catch (IOException e) {
                    System.out.println("Error occurred: " + e.getMessage());
                    break;
                }

        }


    }
}
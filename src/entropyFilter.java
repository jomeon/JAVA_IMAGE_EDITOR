import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
public class entropyFilter{

    public static BufferedImage entropyFilterRGB(BufferedImage inputImage, int windowSize) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());

        // obliczenie połowy wielkości okna
        int halfWindowSize = windowSize / 2;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // inicjalizacja listy wartości pikseli w oknie
                List<Integer> pixelValues = new ArrayList<Integer>();

                // iteracja po pikselach w oknie
                for (int k = -halfWindowSize; k <= halfWindowSize; k++) {
                    for (int l = -halfWindowSize; l <= halfWindowSize; l++) {
                        int x = j + l;
                        int y = i + k;

                        // pominięcie pikseli znajdujących się poza obrazem
                        if (x < 0 || y < 0 || x >= width || y >= height) {
                            continue;
                        }

                        // pobranie wartości piksela i dodanie do listy
                        int pixel = inputImage.getRGB(x, y);
                        int red = (pixel >> 16) & 0xff;
                        int green = (pixel >> 8) & 0xff;
                        int blue = pixel & 0xff;
                        pixelValues.add(red);
                        pixelValues.add(green);
                        pixelValues.add(blue);
                    }
                }

                // obliczenie entropii na podstawie wartości pikseli w oknie
                double entropy = calculateEntropy(pixelValues);

                // normalizacja wartości entropii do zakresu 0-255
                int normalizedEntropy = (int) (entropy * 255);

                // ustawienie wartości piksela w wynikowym obrazie na wartość entropii
                int outputPixel = (normalizedEntropy << 16) | (normalizedEntropy << 8) | normalizedEntropy;
                outputImage.setRGB(j, i, outputPixel);
            }
        }

        return outputImage;
    }

    // metoda pomocnicza do obliczenia entropii na podstawie wartości pikseli
    private static double calculateEntropy(List<Integer> pixelValues) {
        Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
        for (int pixelValue : pixelValues) {
            if (counts.containsKey(pixelValue)) {
                counts.put(pixelValue, counts.get(pixelValue) + 1);
            } else {
                counts.put(pixelValue, 1);
            }
        }
        double entropy = 0.0;
        int totalPixels = pixelValues.size();

        for (int count : counts.values()) {
            double probability = (double) count / totalPixels;
            entropy -= probability * Math.log(probability);
        }

        return entropy;
    }
    public static BufferedImage entropyFilterBW(BufferedImage inputImage, int windowSize) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());

        // obliczenie połowy wielkości okna
        int halfWindowSize = windowSize / 2;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // inicjalizacja listy wartości pikseli w oknie
                List<Integer> pixelValues = new ArrayList<Integer>();

                // iteracja po pikselach w oknie
                for (int k = -halfWindowSize; k <= halfWindowSize; k++) {
                    for (int l = -halfWindowSize; l <= halfWindowSize; l++) {
                        int x = j + l;
                        int y = i + k;

                        // pominięcie pikseli znajdujących się poza obrazem
                        if (x < 0 || y < 0 || x >= width || y >= height) {
                            continue;
                        }

                        // pobranie wartości piksela i dodanie do listy
                        int pixel = inputImage.getRGB(x, y) & 0xff;
                        pixelValues.add(pixel);
                    }
                }

                // obliczenie entropii na podstawie wartości pikseli w oknie
                double entropy = calculateEntropy(pixelValues);

                // normalizacja wartości entropii do zakresu 0-255
                int normalizedEntropy = (int) (entropy * 255);

                // ustawienie wartości piksela w wynikowym obrazie na wartość entropii
                int outputPixel = (normalizedEntropy << 16) | (normalizedEntropy << 8) | normalizedEntropy;
                outputImage.setRGB(j, i, outputPixel);
            }
        }

        return outputImage;
    }



}

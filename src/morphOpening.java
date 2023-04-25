import java.awt.image.BufferedImage;

public class morphOpening {


    public static BufferedImage morphologicalOpening(BufferedImage inputImage, int radius) {
        // konwersja obrazu na obraz binarny
        BufferedImage binaryImage = convertToBinary(inputImage);

        // utworzenie elementu strukturalnego w kształcie koła o zadanym promieniu
        int[][] structureElement = createCircleStructuringElement(radius);


        // wykonanie erozji na obrazie binarnym za pomocą elementu strukturalnego
        BufferedImage erodedImage = morphologyErosion(binaryImage, structureElement);

        // wykonanie dylatacji na wyniku erozji za pomocą elementu strukturalnego
        BufferedImage outputImage = morphologyDilation(erodedImage, structureElement);

        // wykonanie operacji logicznej AND pomiędzy oryginalnym obrazem a wynikiem otwarcia
        BufferedImage resultImage = logicalAnd(inputImage, outputImage);

        return resultImage;
    }

    // metoda pomocnicza do wykonania operacji logicznej AND
    private static BufferedImage logicalAnd(BufferedImage image1, BufferedImage image2) {
        int width = image1.getWidth();
        int height = image1.getHeight();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel1 = image1.getRGB(j, i) & 0xff;
                int pixel2 = image2.getRGB(j, i) & 0xff;
                int resultPixel = pixel1 & pixel2;
                outputImage.setRGB(j, i, resultPixel);
            }
        }

        return outputImage;
    }

    // metoda pomocnicza do konwersji obrazu na obraz binarny
    private static BufferedImage convertToBinary(BufferedImage inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        BufferedImage binaryImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = inputImage.getRGB(j, i) & 0xff;
                if (pixel > 128) {
                    binaryImage.setRGB(j, i, 255);
                } else {
                    binaryImage.setRGB(j, i, 0);
                }
            }
        }

        return binaryImage;
    }

    private static int[][] createCircleStructuringElement(int radius) {
        // Tworzenie elementu strukturalnego jako koła o zadanym promieniu
        int diameter = radius * 2;
        int[][] structureElement = new int[diameter][diameter];
        int centerX = radius;
        int centerY = radius;
        for (int i = 0; i < diameter; i++) {
            for (int j = 0; j < diameter; j++) {
                double distance = Math.sqrt(Math.pow(i - centerX, 2) + Math.pow(j - centerY, 2));
                if (distance <= radius) {
                    structureElement[i][j] = 1;
                }
            }
        }
        return structureElement;
    }

    private static BufferedImage morphologyErosion(BufferedImage inputImage, int[][] structuringElement) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        // Wykonanie erozji
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int minVal = 255;
                for (int k = 0; k < structuringElement.length; k++) {
                    for (int l = 0; l < structuringElement[k].length; l++) {
                        int x = j + l - structuringElement[0].length / 2;
                        int y = i + k - structuringElement.length / 2;
                        if (x >= 0 && y >= 0 && x < width && y < height) {
                            int pixel = inputImage.getRGB(x, y) & 0xff;
                            if (structuringElement[k][l] == 1) {
                                if (pixel < minVal) {
                                    minVal = pixel;
                                }
                            }
                        }
                    }
                }
                outputImage.setRGB(j, i, (minVal << 16) | (minVal << 8) | minVal);
            }
        }

        return outputImage;
    }


    private static BufferedImage morphologyDilation(BufferedImage inputImage, int[][] structuringElement) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        // Wykonanie dylatacji
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int maxVal = 0;
                for (int k = 0; k < structuringElement.length; k++) {
                    for (int l = 0; l < structuringElement[k].length; l++) {
                        int x = j + l - structuringElement[0].length / 2;
                        int y = i + k - structuringElement.length / 2;
                        if (x >= 0 && y >= 0 && x < width && y < height) {
                            int pixel = inputImage.getRGB(x, y) & 0xff;
                            if (structuringElement[k][l] == 1) {
                                if (pixel > maxVal) {
                                    maxVal = pixel;
                                }
                            }
                        }
                    }
                }
                outputImage.setRGB(j, i, (maxVal << 16) | (maxVal << 8) | maxVal);
            }
        }
        return outputImage;
    }
}
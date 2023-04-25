import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class ImageNormalizer {



    public static BufferedImage normalizeRGB(BufferedImage inputImage, int[][] normalizationPoints) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());

        int[][][] pixelData = new int[height][width][3]; // trzy warstwy kolorów RGB
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = inputImage.getRGB(j, i);
                pixelData[i][j][0] = (pixel >> 16) & 0xff; // warstwa R
                pixelData[i][j][1] = (pixel >> 8) & 0xff; // warstwa G
                pixelData[i][j][2] = pixel & 0xff; // warstwa B
            }
        }

        // wyznaczenie min i max dla każdej warstwy kolorów RGB
        int[] min = {255, 255, 255};
        int[] max = {0, 0, 0};
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < 3; k++) {
                    if (pixelData[i][j][k] < min[k]) {
                        min[k] = pixelData[i][j][k];
                    }
                    if (pixelData[i][j][k] > max[k]) {
                        max[k] = pixelData[i][j][k];
                    }
                }
            }
        }

        // normalizacja pikseli dla każdej warstwy kolorów RGB
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < 3; k++) {
                    int pixel = pixelData[i][j][k];
                    int normalizedPixel;
                    if (pixel <= normalizationPoints[0][0]) {
                        normalizedPixel = normalizationPoints[0][1];
                    } else if (pixel <= normalizationPoints[1][0]) {
                        normalizedPixel = (int) ((pixel - normalizationPoints[0][0]) * (normalizationPoints[1][1] - normalizationPoints[0][1]) / (double) (normalizationPoints[1][0] - normalizationPoints[0][0]) + normalizationPoints[0][1]);
                    } else if (pixel <= normalizationPoints[2][0]) {
                        normalizedPixel = (int) ((pixel - normalizationPoints[1][0]) * (normalizationPoints[2][1] - normalizationPoints[1][1]) / (double) (normalizationPoints[2][0] - normalizationPoints[1][0]) + normalizationPoints[1][1]);
                    } else {
                        normalizedPixel = (int) ((pixel - normalizationPoints[2][0]) * (255 - normalizationPoints[2][1]) / (double) (255 - normalizationPoints[2][0]) + normalizationPoints[2][1]);
                    }
                    pixelData[i][j][k] = normalizedPixel;
                }
                // utworzenie koloru na podstawie znormalizowanych wartości RGB
                int red = pixelData[i][j][0];
                int green = pixelData[i][j][1];
                int blue = pixelData[i][j][2];
                int rgb = (red << 16) | (green << 8) | blue;
                outputImage.setRGB(j, i, rgb);
            }
        }
        return outputImage;
    }
    public static BufferedImage normalizeBW(BufferedImage inputImage, int[][] normalizationPoints) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster inputRaster = inputImage.getRaster();
        WritableRaster outputRaster = outputImage.getRaster();
        int[] pixel = new int[1];
        int[] normalizedPixel = new int[1];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                inputRaster.getPixel(j, i, pixel);
                if (pixel[0] <= normalizationPoints[0][0]) {
                    normalizedPixel[0] = normalizationPoints[0][1];
                } else if (pixel[0] <= normalizationPoints[1][0]) {
                    normalizedPixel[0] = (int) ((pixel[0] - normalizationPoints[0][0]) * (normalizationPoints[1][1] - normalizationPoints[0][1]) / (double) (normalizationPoints[1][0] - normalizationPoints[0][0]) + normalizationPoints[0][1]);
                } else if (pixel[0] <= normalizationPoints[2][0]) {
                    normalizedPixel[0] = (int) ((pixel[0] - normalizationPoints[1][0]) * (normalizationPoints[2][1] - normalizationPoints[1][1]) / (double) (normalizationPoints[2][0] - normalizationPoints[1][0]) + normalizationPoints[1][1]);
                } else {
                    normalizedPixel[0] = (int) ((pixel[0] - normalizationPoints[2][0]) * (255 - normalizationPoints[2][1]) / (double) (255 - normalizationPoints[2][0]) + normalizationPoints[2][1]);
                }
                outputRaster.setPixel(j, i, normalizedPixel);
            }
        }
        return outputImage;
    }
}
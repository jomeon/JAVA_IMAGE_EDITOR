import java.awt.*;
import java.awt.image.BufferedImage;

public class ObjectHoleFilling {

    public static BufferedImage fillHoles(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();

        //stworz nowy binarny obraz zeby zapisac wynik
        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);

        // skopuiuj wrzucany plik do obrazu wynikowego
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int rgb = img.getRGB(x, y);
                result.setRGB(x, y, rgb);
            }
        }

        // znajdz wszystkie dziury w obrazku i je wypelnij
        for (int y = 1; y < h - 1; y++) {
            for (int x = 1; x < w - 1; x++) {
                // jesli pixel jest czarny i ma bialych sasiadow, to dziura
                if (img.getRGB(x, y) == Color.BLACK.getRGB()) {
                    boolean hasWhiteNeighbor = false;

                    for (int ny = -1; ny <= 1; ny++) {
                        for (int nx = -1; nx <= 1; nx++) {
                            if (img.getRGB(x + nx, y + ny) == Color.WHITE.getRGB()) {
                                hasWhiteNeighbor = true;
                                break;
                            }
                        }
                    }

                    if (hasWhiteNeighbor) {
                        // zapelnij dziure bialymi pixelami
                        for (int ny = -1; ny <= 1; ny++) {
                            for (int nx = -1; nx <= 1; nx++) {
                                result.setRGB(x + nx, y + ny, Color.WHITE.getRGB());
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
}
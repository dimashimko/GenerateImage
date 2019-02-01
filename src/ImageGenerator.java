import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ImageGenerator {

    private final int HEIGHT_IMAGE = 100;
    private final int WIDTH_IMAGE = 100;
    private final int HEIGHT_PATTERN = 5;
    private final int WIDTH_PATTERN = 5;
    private final int[] RGB_PAINTS = new int[]{0, 25, 50, 75, 100, 125, 150};

    /**
     * Using the username generates a cool avatar.
     *
     * @throws IOException errors write to file
     */
    void generateImage() throws IOException {
        String name = getName();
        boolean[][] pattern = getPattern(name);
        printConsole(pattern);
        int colorBackground = getColorBakground();
        int colorSquares = getColorSquares(name);

        BufferedImage img = drawImage(pattern, colorBackground, colorSquares);

        // you can use 2 mode for output filename
        File outputFile = new File("image.jpg");
//        File outputFile = new File(name + ".jpg");

        ImageIO.write(img, "jpg", outputFile);
    }

    /**
     * Using the username generates a color of squares
     *
     * @param name Username that user entered.
     * @return color of colored squares
     */
    private int getColorSquares(String name) {
        int r = RGB_PAINTS[name.toCharArray()[0] % RGB_PAINTS.length];
        int g = RGB_PAINTS[name.toCharArray()[1] % RGB_PAINTS.length];
        int b = RGB_PAINTS[name.toCharArray()[2] % RGB_PAINTS.length];
        int colorSquares = (r << 16) | (g << 8) | b;
        return colorSquares;
    }

    /**
     * Generates a color of background.
     *
     * @return color of background.
     */
    private int getColorBakground() {
        int r = 240;
        int g = 240;
        int b = 240;
        return (r << 16) | (g << 8) | b;
    }

    /**
     * Creates an image following the pattern.
     *
     * @param pattern
     * @param colorBackground
     * @param colorSquares
     * @return
     */
    private BufferedImage drawImage(boolean[][] pattern, int colorBackground, int colorSquares) {
        BufferedImage img = new BufferedImage(WIDTH_IMAGE, HEIGHT_IMAGE, BufferedImage.TYPE_INT_RGB);
        int heightRect = (int) Math.ceil((double) HEIGHT_IMAGE / pattern.length);
        int widthRect = (int) Math.ceil((double) WIDTH_IMAGE / pattern[0].length);

        for (int i = 0; i < HEIGHT_IMAGE; i++) {
            for (int j = 0; j < WIDTH_IMAGE; j++) {
                if (pattern[i / heightRect][j / widthRect]) {
                    img.setRGB(j, i, colorSquares);
                } else {
                    img.setRGB(j, i, colorBackground);
                }
            }
        }
        return img;
    }

    private void printConsole(boolean[][] pattern) {
        for (int i = 0; i < pattern.length; i++) {
            System.out.println();
            for (int j = 0; j < pattern[0].length; j++) {
                if (pattern[i][j]) {
                    System.out.print("█");
                } else {
                    System.out.print("░");

                }
            }
        }
    }

    /**
     * Generates a pattern.
     *
     * @param name - username for which you want to generate a pattern.
     * @return - pattern.
     */
    private boolean[][] getPattern(String name) {
        name = name.toLowerCase();
        int middlePattern = WIDTH_PATTERN / 2 + 1;
        while (name.length() < HEIGHT_PATTERN * middlePattern) {
            name += name;
        }
        // create left part pattern
        char[] arrayLetter = name.toCharArray();
        boolean[][] pattern = new boolean[HEIGHT_PATTERN][WIDTH_PATTERN];
        for (int i = 0; i < HEIGHT_PATTERN; i++) {
            for (int j = 0; j < WIDTH_PATTERN / 2 + 1; j++) {
                pattern[i][j] = isVowel(arrayLetter[i * middlePattern + j]);
            }
        }
        // create right part pattern
        for (int i = 0; i < middlePattern; i++) {
            for (int j = 0; j < HEIGHT_PATTERN; j++) {
                pattern[j][WIDTH_PATTERN - 1 - i] = pattern[j][i];
            }
        }

        return pattern;
    }

    private boolean isVowel(char c) {
        return c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u';
    }

    /**
     * Requests user name
     */
    private String getName() {
        Scanner sc = new Scanner(System.in);
        String name = "";
        do {
            System.out.print("please, enter your name: ");
            name = sc.nextLine();
            System.out.println(name);
            if (name.length() < 1) System.out.println("incorrect input");
        } while (name.length() < 1);

        return name;
    }
}

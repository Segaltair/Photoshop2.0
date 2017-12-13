package Controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Converting {
    private static int red;
    private static int green;
    private static int blue;
    private static int gray;

    static Image fast(Image imageView){
        BufferedImage image = SwingFXUtils.fromFXImage(imageView, null);

        for(int i=0; i<image.getHeight(); i++) {
            for(int j=0; j<image.getWidth(); j++) {
                Color c = new Color(image.getRGB(j,i));
                red = c.getRed();
                green = c.getGreen();
                blue = c.getBlue();
                gray = (red + green + blue) / 3;
                image.setRGB(j, i, new Color(gray, gray, gray).getRGB());
            }
        }
        return SwingFXUtils.toFXImage(image, null);
    }

    static Image correctionForTheHumanEye(Image imageView){
        BufferedImage image = SwingFXUtils.fromFXImage(imageView, null);

        for(int i=0; i<image.getHeight(); i++) {
            for(int j=0; j<image.getWidth(); j++) {
                Color c = new Color(image.getRGB(j,i));
                red = c.getRed();
                green = c.getGreen();
                blue = c.getBlue();
                gray = (int) (red*0.3 + green*0.59 + blue*0.11);
                image.setRGB(j, i, new Color(gray, gray, gray).getRGB());
            }
        }
        return SwingFXUtils.toFXImage(image, null);
    }

    static Image desaturation(Image imageView){
        BufferedImage image = SwingFXUtils.fromFXImage(imageView, null);

        for(int i=0; i<image.getHeight(); i++) {
            for(int j=0; j<image.getWidth(); j++) {
                Color c = new Color(image.getRGB(j,i));
                red = c.getRed();
                green = c.getGreen();
                blue = c.getBlue();
                gray = (Math.max(Math.max(red, green), blue) + Math.min(Math.min(red, green), blue)) / 2;
                image.setRGB(j, i, new Color(gray, gray, gray).getRGB());
            }
        }
        return SwingFXUtils.toFXImage(image, null);
    }

    static Image gradationByMinimum(Image imageView){
        BufferedImage image = SwingFXUtils.fromFXImage(imageView, null);

        for(int i=0; i<image.getHeight(); i++) {
            for(int j=0; j<image.getWidth(); j++) {
                Color c = new Color(image.getRGB(j,i));
                red = c.getRed();
                green = c.getGreen();
                blue = c.getBlue();
                gray = Math.min(Math.min(red, green), blue);
                image.setRGB(j, i, new Color(gray, gray, gray).getRGB());
            }
        }
        return SwingFXUtils.toFXImage(image, null);
    }

    static Image gradationByMaximum(Image imageView){
        BufferedImage image = SwingFXUtils.fromFXImage(imageView, null);

        for(int i=0; i<image.getHeight(); i++) {
            for(int j=0; j<image.getWidth(); j++) {
                Color c = new Color(image.getRGB(j,i));
                red = c.getRed();
                green = c.getGreen();
                blue = c.getBlue();
                gray = Math.max(Math.max(red, green), blue);
                image.setRGB(j, i, new Color(gray, gray, gray).getRGB());
            }
        }
        return SwingFXUtils.toFXImage(image, null);
    }

    static Image matrixFilter(Image imageView, int[][] matrix, int divider){
        BufferedImage originalImage = SwingFXUtils.fromFXImage(imageView, null);
        BufferedImage image = SwingFXUtils.fromFXImage(imageView, null);

        if (isGray(originalImage))
            for(int i=0; i<originalImage.getHeight(); i++) {
                for(int j=0; j<originalImage.getWidth(); j++) {
                    double anotherGray = 0;
                    for (int k = 0; k < matrix.length; k++) {
                        for (int l = 0; l < matrix[k].length; l++) {
                            if (((i+k-matrix.length/2 < 0))
                                    ||(j+l-matrix[k].length/2 < 0)
                                    ||(i+k-matrix.length/2 >= originalImage.getHeight())
                                    ||(j+l-matrix[k].length/2 >= originalImage.getWidth()))
                                continue;
                            Color c = new Color(originalImage.getRGB(j+l-matrix[k].length/2, i+k-matrix.length/2));
                            anotherGray += matrix[k][l]*c.getRed();
                        }

                    }
                    anotherGray = anotherGray / divider;

                    if (anotherGray > 255) anotherGray = 255;
                    if (anotherGray < 0) anotherGray = 0;


                    image.setRGB(j, i, new Color((int) anotherGray,(int) anotherGray,(int) anotherGray).getRGB());
                }
            }
        return SwingFXUtils.toFXImage(image, null);
    }

    static int[] histogram(Image imageView){
        BufferedImage image = SwingFXUtils.fromFXImage(imageView, null);
        int[] hist = new int[256];
        if (isGray(image))
            for(int i=0; i<image.getHeight(); i++) {
                for(int j=0; j<image.getWidth(); j++) {
                    Color c = new Color(image.getRGB(j,i));
                    hist[c.getRed()]++;
                }
            }
        return hist;
    }

    static Image histogramEqualization(Image imageView){
        int[] hist = histogram(imageView);
        int maxGray = -1;
        int minGray = 255;
        for (int i = 0; i < hist.length; i++) {
            if ((i > maxGray)&&(hist[i] != 0))  maxGray = i;
            if ((i < minGray)&&(hist[i] != 0)) minGray = i;
        }

        BufferedImage image = SwingFXUtils.fromFXImage(imageView, null);
        if (isGray(image)){
            for(int i=0; i<image.getHeight(); i++) {
                for(int j=0; j<image.getWidth(); j++) {
                    Color c = new Color(image.getRGB(j,i));
                    gray = (c.getRed() - minGray) * (hist.length - 1) / (maxGray - minGray);
                    image.setRGB(j, i, new Color(gray, gray, gray).getRGB());
                }
            }
        }
        return SwingFXUtils.toFXImage(image, null);
    }

    static Image binarization(Image imageView, Integer treshold){
        BufferedImage image = SwingFXUtils.fromFXImage(imageView, null);

        if (treshold > 255)
            treshold = 255;
        if (treshold < 0)
            treshold = 0;

        if (isGray(image)){
            for(int i=0; i<image.getHeight(); i++) {
                for(int j=0; j<image.getWidth(); j++) {
                    Color c = new Color(image.getRGB(j,i));
                    gray = c.getRed();
                    if (gray < treshold)
                        image.setRGB(j, i, new Color(0, 0, 0).getRGB());
                    else
                        image.setRGB(j, i, new Color(255, 255, 255).getRGB());
                }
            }
        }

        return SwingFXUtils.toFXImage(image, null);
    }

    static Image dilation(Image imageView, int[][] structureElement, int xCenter, int yCenter){
        BufferedImage originalImage = SwingFXUtils.fromFXImage(imageView, null);
        BufferedImage image = SwingFXUtils.fromFXImage(imageView, null);

        if(isBinary(originalImage)){
            for(int i=0; i<originalImage.getHeight(); i++) {
                for(int j=0; j<originalImage.getWidth(); j++) {
                    Color pixel = new Color(originalImage.getRGB(j,i));
                    gray = pixel.getRed();

                    if (pixel.getRed() == 0)
                        for (int l = 0; l < structureElement.length; l++) {
                            for (int k = 0; k < structureElement[0].length; k++) {
                                if ((j+k - yCenter) > 0
                                        && (i+l - xCenter) > 0
                                        && (j+k - yCenter) <= originalImage.getWidth()
                                        && (i+l - xCenter) <= originalImage.getHeight())
                                    if (structureElement[l][k] == 1)
                                        image.setRGB(j + k - yCenter, i + l - xCenter, new Color(0,0,0).getRGB());
                            }
                        }
                }
            }
        }

        return SwingFXUtils.toFXImage(image, null);
    }

    static boolean isGray(BufferedImage image){
        for(int i=0; i<image.getHeight(); i++) {
            for(int j=0; j<image.getWidth(); j++) {
                Color c = new Color(image.getRGB(j,i));
                red = c.getRed();
                green = c.getGreen();
                blue = c.getBlue();
                if (!((red == green) && (red == blue))) return false;
            }
        }
        return true;
    }

    static boolean isBinary(BufferedImage image){
        for(int i=0; i<image.getHeight(); i++) {
            for(int j=0; j<image.getWidth(); j++) {
                Color c = new Color(image.getRGB(j,i));
                red = c.getRed();
                green = c.getGreen();
                blue = c.getBlue();
                if ((red != 0)
                        && (red != 255)
                        && (green != 0)
                        && (green != 255)
                        && (blue != 0)
                        && (blue != 255)) return false;
            }
        }
        return true;
    }
}
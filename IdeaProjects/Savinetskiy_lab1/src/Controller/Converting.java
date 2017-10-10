package Controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Converting {
    static int red;
    static int green;
    static int blue;
    static int gray;

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
}
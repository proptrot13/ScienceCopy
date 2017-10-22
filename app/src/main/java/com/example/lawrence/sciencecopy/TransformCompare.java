package com.example.lawrence.sciencecopy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.provider.Settings;

/**
 * Created by Lawrence on 10/2/2017.
 */

public class TransformCompare {
    Bitmap imageOne;
    Bitmap imageTwo;
    int combo1;
    int combo2;
    int combo3;
    final int POSSIBLE_COMBINATIONS = 100000;
    final double DISTANCE_BETWEEN_CAMERAS;
    Bitmap.Config conf = Bitmap.Config.ARGB_8888;
    Util util;
    double leastDistance;
    double[][] depthMap;

    public TransformCompare(Bitmap imageOne, Bitmap imageTwo, double distanceBetweenCameras) {
        this.imageOne = imageOne;
        this.imageTwo = imageTwo;
        util = new Util();
        this.DISTANCE_BETWEEN_CAMERAS = distanceBetweenCameras;
        depthMap = new double[imageOne.getHeight()][imageOne.getWidth()];
    }

    public double offsetCompare() {
        boolean[][] depths = new boolean[imageOne.getWidth()][imageOne.getHeight()];
        for (int a = -10; a < 10; a++) {
            for (int b = -100; b < 100; b++) {
                for (int c = 0; c < POSSIBLE_COMBINATIONS; c++) {
                    depths = detectSamePortion(rotation(translation(colorChange(imageOne, a), b), c));
                    if (detectCenter(depths)) {
                        for(int x = 0; x < imageOne.getWidth(); x++) {
                            for(int y = 0; y < imageOne.getHeight(); y++) {
                                if(depths[x][y] == true) {
                                    depthMap[x][y] = detectDistance(a, b, 10);
                                }
                            }
                        }
                        if(detectDistance(a, b, 10) < leastDistance) {
                            leastDistance = detectDistance(a, b, 10);
                        }
                    }

                }
            }
        }


        return leastDistance;
    }

    public Bitmap colorChange(Bitmap bitmap, int number) {


        return  bitmap;
    }

    public Bitmap translation(Bitmap bitmap, int number) {
        return translation(bitmap, number, 90);
    }

    public Bitmap translation(Bitmap bitmap, double number, double angle) {
        Bitmap output = Bitmap.createBitmap(MainActivity.getScreenWidth(), MainActivity.getScreenHeight(), conf);
        angle = Math.toRadians(90 - angle);
        double outy;
        double outx;
        int test;
        for(int x = 0; x < bitmap.getWidth(); x++) {
            for(int y = 0; y < bitmap.getWidth(); y++) {
                try {
                    output.setPixel((int) (Math.cos(angle) * number + x), (int) (Math.sqrt(Math.pow(number, 2) - Math.pow(Math.cos(angle) * number, 2)) + y), bitmap.getPixel(x, y));
                }
                catch(IllegalArgumentException e) {

                }
                test = (int)(Math.sqrt(Math.pow(number, 2) - Math.pow(Math.cos(angle) * number, 2)) + y);
                int hi = 10;
            }
        }

        return  output;
    }

    public Bitmap rotation(Bitmap bitmap, double angle) {

        Bitmap output = Bitmap.createBitmap(MainActivity.getScreenWidth(), MainActivity.getScreenHeight(), conf);
        for(int x = 0; x < bitmap.getWidth(); x++) {
            for(int y = 0; y < bitmap.getHeight(); y++) {
                double radius = x - bitmap.getWidth()/2;
                output.setPixel((int)(Math.sin(Math.toRadians(90 - angle)) * radius + bitmap.getWidth()/2), y, bitmap.getPixel(x, y));
            }
        }
        return output;
    }

    public boolean[][] detectSamePortion(Bitmap bitmap) {
        boolean[][] output = new boolean[bitmap.getWidth()][bitmap.getHeight()];
        for (int a = 0; a < imageTwo.getWidth(); a++) {
            for (int b = 0; b < imageTwo.getWidth(); b++) {
                if (bitmap.getPixel(a, b) == imageTwo.getPixel(a, b)) {
                    output[a][b] = true;
                }
            }
        }
        return output;
    }

    public boolean detectCenter(boolean[][] input) {


        return true;
    }

    //distance in cm
    public double detectDistance(double translation, double rotation, double betweenCameras) {
        return Math.tan(rotation) * betweenCameras;
    }

}
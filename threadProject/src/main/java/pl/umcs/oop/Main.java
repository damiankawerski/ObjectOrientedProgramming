package pl.umcs.oop;

import org.knowm.xchart.CategoryChart;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ImageProcessor ip = new ImageProcessor();

//        try {
//            long start1 = System.currentTimeMillis();
//            ip.loadImage("car.jpg");
//            ip.increaseBrightness(100);
//            ip.saveImageJpg("carB.jpg");
//            long end1 = System.currentTimeMillis();
//
//            long start2 = System.currentTimeMillis();
//            ip.loadImage("car.jpg");
//            ip.increaseBrightnessThreadsPool(100);
//            ip.saveImageJpg("carB.jpg");
//            long end2 = System.currentTimeMillis();
//            System.out.println(end1 - start1);
//            System.out.println(end2 - start2);
//        } catch (IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }


        try {
            ip.loadImage("car.jpg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int[] arr = ip.calculateHistogram("red");
        System.out.println(Arrays.toString(arr));

        CategoryChart example = ip.generateOverlappedHistogram();
    }
}
package pl.umcs.oop;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.Histogram;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ImageProcessor {
    private BufferedImage image;

    public void loadImage(String path) throws IOException {
        File file = new File(path);
        image = ImageIO.read(file);
    }

    public void saveImageJpg(String path) throws IOException {
        File file = new File(path);
        ImageIO.write(image, "jpg", file);
    }

    public void saveImagePng(String path) throws IOException {
        File file = new File(path);
        ImageIO.write(image, "png", file);
    }

    public void increaseBrightness(int factor) {
        for(int x = 0 ; x < image.getWidth(); x++) {
            for(int y = 0 ; y < image.getHeight() ; y++) {
                int pixel = image.getRGB(x, y);
                pixel = brightenPixel(pixel, factor);
                image.setRGB(x, y, pixel);
            }
        }
    }

    public void increaseBrightnessThreads(int factor) throws InterruptedException {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        Thread threads[] = new Thread[availableProcessors];

        for(int i = 0; i < threads.length; i++) {
            final int finalI = i;

            threads[i] = new Thread(() -> {
                int start = image.getWidth() / availableProcessors * finalI;
                int end = start + image.getWidth() / availableProcessors;
                System.out.println(finalI);
                if(finalI == availableProcessors - 1) {
                    end = image.getWidth();
                }
                for(int x = start ; x < end ; x++) {
                    for(int y = 0 ; y < image.getHeight() ; y++) {
                        int pixel = image.getRGB(x, y);
                        pixel = brightenPixel(pixel, factor);
                        image.setRGB(x, y, pixel);
                    }
                }
            });

            threads[i].start();
        }
        for(int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
    }

    public void increaseBrightnessThreadsPool(int factor) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        executorService.execute(() -> {
            for(int y = 0 ; y < image.getHeight() ; y++) {
                for(int x = 0 ; x < image.getWidth() ; x++) {
                    int pixel = image.getRGB(x, y);
                    pixel = brightenPixel(pixel, factor);
                    image.setRGB(x, y, pixel);
                }
            }
        });
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.HOURS);
   }

   public int[] calculateHistogram(String color) {
        int colorI;
        int mask = 255;
        if(color.equals("blue")) {
            colorI = 0;
        } else if (color.equals("green")) {
            colorI = 1;
        } else if (color.equals("red")) {
            colorI = 2;
        } else {
            return new int[256];
        }
        int[] result = new int[256];
        for(int i : result) {
            result[i] = 0;
        }
        for(int x = 0 ; x < image.getWidth() ; x++) {
            for(int y = 0 ; y < image.getHeight() ; y++) {
                int pixel = image.getRGB(x, y);
                int colorIndexer = (pixel >> (8 * colorI)) & mask;
                result[colorIndexer]++;
            }
        }
        return result;
   }

    private int brightenPixel(int pixel, int factor) {
        int mask = 255;
        int blue = pixel & mask;
        int green = (pixel >> 8) & mask;
        int red = (pixel >> 16) & mask;
        blue = brightenPixelPart(blue, factor);
        green = brightenPixelPart(green, factor);
        red = brightenPixelPart(red, factor);
        return blue + (green << 8) + (red << 16);
    }

    private int brightenPixelPart(int color, int factor) {
        color += factor;
        if(color > 255) {
            return 255;
        } else {
            return color;
        }
    }

    public CategoryChart generateOverlappedHistogram() {
        int[] redHistogramData = calculateHistogram("red");
        int[] greenHistogramData = calculateHistogram("green");
        int[] blueHistogramData = calculateHistogram("blue");

        List<Integer> redHistogramDataCollection = Arrays.stream(redHistogramData).boxed().toList();
        List<Integer> greenHistogramDataCollection = Arrays.stream(greenHistogramData).boxed().toList();
        List<Integer> blueHistogramDataCollection = Arrays.stream(blueHistogramData).boxed().toList();

        CategoryChart chart = new CategoryChartBuilder().width(800).height(300).title("Histogram").xAxisTitle("Shade").yAxisTitle("Count").build();
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setAvailableSpaceFill(.96);
        chart.getStyler().setOverlapped(true);

        List<String> xAxisLabels = createXAxisLabels(256);

        Histogram redHistogram = new Histogram(redHistogramDataCollection, 20);
        Histogram greenHistogram = new Histogram(greenHistogramDataCollection, 20);
        Histogram blueHistogram = new Histogram(blueHistogramDataCollection, 20);

        chart.addSeries("Red", createXAxisLabels(256), redHistogramDataCollection).setFillColor(new Color(255, 0, 0, 100));
        chart.addSeries("Green", createXAxisLabels(256), greenHistogramDataCollection).setFillColor(new Color(0, 255, 0, 200));
        chart.addSeries("Blue", createXAxisLabels(256), blueHistogramDataCollection).setFillColor(new Color(0, 0, 255, 150));


        new SwingWrapper<>(chart).displayChart();

        return chart;
    }

    private List<String> createXAxisLabels(int length) {
        List<Integer> xAxisLabels = new ArrayList<>(length);
        for(int i = 0; i < length; i++) {
            xAxisLabels.add(i);
        }
        return xAxisLabels.stream().map(String::valueOf).collect(Collectors.toList());
    }
}
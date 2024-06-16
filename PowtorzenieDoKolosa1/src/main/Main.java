package main;
import source.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String icdCode;
        Scanner sc = new Scanner(System.in);
        icdCode = sc.nextLine();
        String desc = new ICDCodeTabularOptimizedForMemory().getDescription(icdCode);
        System.out.println(desc);
    }
}
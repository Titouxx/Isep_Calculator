package org.isep.cleancode;

import java.util.Scanner;

import org.isep.cleancode.calculator.Calculator;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        System.out.println("Bienvenue dans la Calculatrice Java");
        System.out.println("Tapez une expression mathématique (ou 'exit' pour quitter) :");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Au revoir !");
                break;
            }

            try {
                double result = calculator.evaluateMathExpression(input);
                System.out.println("= " + result);
            } catch (Exception e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }

        scanner.close();
    }
}
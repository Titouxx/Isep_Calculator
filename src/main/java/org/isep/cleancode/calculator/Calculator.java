package org.isep.cleancode.calculator;

public class Calculator {

    public double evaluateMathExpression(String expression) {
        expression = expression.replaceAll("\\s+", "");
        return evaluate(expression);
    }

    private double evaluate(String expr) {
        return evaluateAddition(expr);
    }

    private double evaluateAddition(String expr) {
        double result = 0;
        int index = 0;
        boolean subtract = false;
        StringBuilder current = new StringBuilder();

        if (expr.startsWith("-")) {
            current.append("-");
            index++;
        }

        while (index < expr.length()) {
            char ch = expr.charAt(index);

            if (ch == '+' || (ch == '-' && index > 0 && !isOperator(expr.charAt(index - 1)))) {
                double value = evaluateMultiplication(current.toString());
                result += subtract ? -value : value;
                subtract = ch == '-';
                current.setLength(0);
                index++;
            } else if (ch == '(') {
                int closingIndex = findClosingParenthesis(expr, index);
                String subExpr = expr.substring(index, closingIndex + 1);
                current.append(subExpr);
                index = closingIndex + 1;
            } else {
                current.append(ch);
                index++;
            }
        }

        double value = evaluateMultiplication(current.toString());
        result += subtract ? -value : value;
        return result;
    }

    private double evaluateMultiplication(String expr) {
        double result = 1;
        int index = 0;
        StringBuilder current = new StringBuilder();
        @SuppressWarnings("unused")
        boolean multiply = true;

        while (index < expr.length()) {
            char ch = expr.charAt(index);

            if (ch == '*') {
                double value = parseFactor(current.toString());
                result *= value;
                current.setLength(0);
                index++;
            } else if (ch == '(') {
                int closingIndex = findClosingParenthesis(expr, index);
                String subExpr = expr.substring(index, closingIndex + 1);
                current.append(subExpr);
                index = closingIndex + 1;
            } else {
                current.append(ch);
                index++;
            }
        }

        result *= parseFactor(current.toString());
        return result;
    }

    private double parseFactor(String token) {
        token = token.trim();
        if (token.startsWith("(")) {
            String inside = token.substring(1, token.length() - 1);
            return evaluate(inside);
        }
        return Double.parseDouble(token);
    }

    private int findClosingParenthesis(String expr, int startIndex) {
        int level = 1;
        for (int i = startIndex + 1; i < expr.length(); i++) {
            char ch = expr.charAt(i);
            if (ch == '(') level++;
            else if (ch == ')') level--;
            if (level == 0) return i;
        }
        throw new IllegalArgumentException("Erreur : parenthèse non fermée.");
    }

    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }
}
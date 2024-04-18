package com.financeiro.gestao.utils;

public class ValidCPF {
    public static boolean isValidCPF(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.matches(cpf.charAt(0)+"{11}")) return false;

        int[] weightsFirstDigit = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weightsSecondDigit = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        try {
            Long.parseLong(cpf); // Ensures CPF contains only numeric digits
        } catch (NumberFormatException e) {
            return false; // Non-numeric character found
        }

        if (!checkDigit(cpf, weightsFirstDigit, 9)) return false;
        return checkDigit(cpf, weightsSecondDigit, 10);
    }

    private static boolean checkDigit(String cpf, int[] weights, int length) {
        int sum = 0;
        for (int i = 0; i < length - 1; i++) {
            int digit = Integer.parseInt(cpf.substring(i, i+1));
            sum += digit * weights[i];
        }
        int remainder = (sum * 10) % 11;
        if (remainder == 10) remainder = 0;
        return remainder == Integer.parseInt(cpf.substring(length - 1, length));
    }
}

package com.financeiro.gestao.util;

import java.util.ArrayList;
import java.util.List;

public class ValidPassword {

    public static List<String> validatePassword(String password) {
        List<String> errors = new ArrayList<>();

        if (password == null || password.length() < 8) {
            errors.add("A senha deve ter pelo menos 8 caracteres.");
        }

        if (!password.matches(".*[A-Z].*")) {
            errors.add("A senha deve conter pelo menos uma letra maiúscula.");
        }

        if (!password.matches(".*[a-z].*")) {
            errors.add("A senha deve conter pelo menos uma letra minúscula.");
        }

        if (!password.matches(".*\\d.*")) {
            errors.add("A senha deve conter pelo menos um número.");
        }

        if (!password.matches(".*[!@#$%^&*()\\-+].*")) {
            errors.add("A senha deve conter pelo menos um caractere especial.");
        }

        return errors;
    }

    public static boolean isValidPassword(String password) {
        return validatePassword(password).isEmpty();
    }
}

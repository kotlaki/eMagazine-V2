package com.kurganov.webserver.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Scanner;

public class BCryptUtil {
    public static void main(String[] args) {
        System.out.println("Введите пароль: ");
        Scanner scanner = new Scanner(System.in);
        String password = scanner.nextLine();
        String hashing = new BCryptPasswordEncoder().encode(password);
        System.out.println("Encrypt password = " + hashing);
    }
}

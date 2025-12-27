package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Testing jackson-databind " + mapper.version());
    }
}

package com.example.frontend;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FrontendApp {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Frontend module using jackson-databind " + 
                          mapper.version());
    }
}


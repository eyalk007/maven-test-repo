package com.example;

import org.apache.commons.fileupload.FileUpload;

public class App {
    public static void main(String[] args) {
        FileUpload upload = new FileUpload();
        System.out.println("Testing commons-fileupload version from dependencyManagement");
    }
}

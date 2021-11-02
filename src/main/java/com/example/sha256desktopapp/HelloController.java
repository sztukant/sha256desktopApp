package com.example.sha256desktopapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HelloController {
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    File file;
    File checksum;

    @FXML
    private Label filePathField;

    @FXML
    private Label checksumPathField;

    @FXML
    private Label check;


    @FXML
    protected void onCheckButtonClick() throws IOException, NoSuchAlgorithmException {

        if (file != null && checksum != null) {
            FileInputStream sourceFile = new FileInputStream(file);
            FileInputStream hashFile = new FileInputStream(checksum);

            MessageDigest digest = MessageDigest.getInstance("SHA-256");


            byte[] encodedhash = digest.digest(sourceFile.readAllBytes());
            String encoded = bytesToHex(encodedhash);                    //String z hexem hasha
            String hashFromFile = "";                  //Hash z pliku
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(hashFile, StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) try {
                sb.append(line, 0, 64);
                hashFromFile = sb.toString();
            } catch (IndexOutOfBoundsException exception){
                check.setText("Incorrect file");
            }
            br.close();
            sourceFile.close();
            boolean flag = encoded.equals(hashFromFile);
            if (flag){
                check.setText("Correct checksum");
            } else {

                check.setText("Incorrect checksum");
            }

        }else{
            check.setText("Choose files");
        }
    }

    @FXML
    protected void onAddFileButtonClick() {
        Window fileChoseWindow = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
         file = fileChooser.showOpenDialog(fileChoseWindow);
        if (file != null) {
            String filePath = file.getPath();
            filePathField.setText(filePath);
        }
    }
    @FXML
    protected void onAddChecksumButtonClick() {
        Window fileChoseWindow = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open checksum file");
        checksum = fileChooser.showOpenDialog(fileChoseWindow);
        if (checksum != null) {
            String checksumPath = checksum.getPath();
            checksumPathField.setText(checksumPath);
        }
    }
}
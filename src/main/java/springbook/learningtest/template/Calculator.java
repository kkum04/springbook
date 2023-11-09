package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public int calcSumOfNumbers(String filepath) throws IOException {

        return fileReadTemplate(filepath, br -> {
            int sum = 0;
            String line = null;
            while ((line = br.readLine()) != null) {
                sum += Integer.parseInt(line);
            }

            return sum;
        });
    }

    public int calcMultiplyOfNumbers(String filepath) throws IOException {
        return fileReadTemplate(filepath, br -> {
            int multiply = 1;
            String line = null;
            while ((line = br.readLine()) != null) {
                multiply *= Integer.parseInt(line);
            }

            return multiply;
        });
    }

    public int fileReadTemplate(String filePath, BufferedReaderCallback callback) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            return callback.doSomethingWithReader(br);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try { br.close(); }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}

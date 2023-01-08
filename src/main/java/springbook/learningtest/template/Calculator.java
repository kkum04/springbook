package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
  public Integer calcSum(String filepath) throws IOException {
    BufferedReader br = null;

    try {
      br = new BufferedReader(new FileReader(filepath));
      Integer sum = 0;
      String line = null;
      while ((line = br.readLine()) != null) {
        sum += Integer.valueOf(line);
      }

      return sum;
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
      throw ex;
    } finally {
      if (br != null) {
        try { br.close(); }
        catch (IOException ex) { System.out.println(ex.getMessage());}
      }
    }
  }
}

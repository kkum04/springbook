package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
  public Integer fileReadTemplate(String filepath, BufferedReaderCallback callback) throws IOException {
    BufferedReader br = null;

    try {
      br = new BufferedReader(new FileReader(filepath));
      return callback.doSomethingWithReader(br);
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

  public Integer lineReadTemplate(String filepath, LineCallback lineCallback, int initVal) throws IOException {
    BufferedReader br = null;

    try {
      br = new BufferedReader(new FileReader(filepath));
      String line;
      Integer res = initVal;
      while ((line = br.readLine()) != null)
        res = lineCallback.doSomethingWithLine(line, res);

      return res;
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

  public Integer calcSum(String filepath) throws IOException {
    LineCallback sumCallback = ((line, value) -> Integer.parseInt(line) + value) ;

    return lineReadTemplate(filepath, sumCallback, 0);
  }

  public Integer calcMultiply(String filePath) throws IOException {
    LineCallback callback = (((line, value) -> Integer.parseInt(line) * value));

    return lineReadTemplate(filePath, callback, 1);
  }
}

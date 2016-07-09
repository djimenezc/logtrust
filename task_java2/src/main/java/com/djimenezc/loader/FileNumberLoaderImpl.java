package com.djimenezc.loader;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class to read number from a file
 * Created by david on 09/07/2016.
 */
public class FileNumberLoaderImpl implements NumberLoader {

  @Override
  public List<Double> readNumbers(String path) throws Exception {

    List<Double> numberList = new ArrayList<>();

    ClassLoader classLoader = getClass().getClassLoader();
    URL resource = classLoader.getResource(path);
    File file = resource != null ? new File(resource.getFile()) : null;

    if (file != null) {
      Scanner scanner = new Scanner(file);

      while (scanner.hasNextDouble()) {
        numberList.add(scanner.nextDouble());
      }
    }

    return numberList;
  }
}

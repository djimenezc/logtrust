package com.djimenezc.loader;

import java.io.File;
import java.math.BigDecimal;
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
  public List<BigDecimal> readNumbers(String path) throws Exception {

    List<BigDecimal> numberList = new ArrayList<>();

    ClassLoader classLoader = getClass().getClassLoader();
    URL resource = classLoader.getResource(path);
    File file = resource != null ? new File(resource.getFile()) : null;

    if (file != null) {
      Scanner scanner = new Scanner(file);

      while (scanner.hasNextDouble()) {
        numberList.add(BigDecimal.valueOf(scanner.nextDouble()));
      }
    }

    return numberList;
  }
}

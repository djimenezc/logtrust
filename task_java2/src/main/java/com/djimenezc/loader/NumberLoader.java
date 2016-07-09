package com.djimenezc.loader;

import java.util.List;

/**
 * Interface that defines the operations to load a list of number
 * from different sources
 * Created by david on 09/07/2016.
 */
public interface NumberLoader<T> {

  /**
   * Read a list of number from a url or a file system path
   *
   * @param path url or file path
   * @return list of number read from the source
   */
  List<T> readNumbers(String path) throws Exception;
}

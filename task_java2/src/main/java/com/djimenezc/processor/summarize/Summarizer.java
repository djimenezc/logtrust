package com.djimenezc.processor.summarize;

import java.util.List;

/**
 * Interface that specify an summarizer of numbers
 * Created by david on 09/07/2016.
 */
interface Summarizer<E> {

  E sumNumbers(List<E> numbers);
}

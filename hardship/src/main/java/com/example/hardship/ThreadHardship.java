package com.example.hardship;

import java.util.Random;

/**
 * Method to cause thread hardship
 */
public class ThreadHardship {

  private final Random random = new Random();

  public void chaos() {
    if(random.nextInt() % 2 == 0) {
      stackOverflow();
    } else {
      block();
    }
  }

  public void stackOverflow() {
    stackOverflow();
  }

  public void block() {
    try {
      long time = 1000 * random.nextInt(20);
      Thread.sleep(time);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

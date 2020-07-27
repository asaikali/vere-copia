package com.example.hardship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

/**
 * Class to generate memory hardship for the app using this class.
 */
public class MemoryHardship {

  private final List<Object> memory = new ArrayList<>();
  private final Random random = new Random();

  public void clear()  {
    this.memory.clear();
  }

  public void leakRandomMegaBytes() {
    this.leakRandomMegaBytes(15);
  }

  public void leakRandomMegaBytes(int bound) {
    this.leakMegaBytes(random.nextInt(bound));
  }

  public void leakMegaBytes(int amountMB) {
    this.leakBytes(amountMB * 1024 * 1024);
  }

  public void leakBytes(int bytes) {
    byte[] mem = new byte[bytes];
    for (int i = 0; i < bytes ; i++) {
      mem[i] = (byte)i;
    }
    memory.add(mem);
  }
}

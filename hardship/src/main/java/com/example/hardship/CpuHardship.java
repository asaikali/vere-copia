package com.example.hardship;

import java.math.BigInteger;
import java.util.Random;
import org.springframework.stereotype.Component;

/**
 * Make the CPU busy
 */
public class CpuHardship {

  private final Random random = new Random();

  public BigInteger fibonacci()  {
    return this.fibonacci(random.nextInt(1000) * 1000);
  }

  public BigInteger fibonacci(int n)  {
    BigInteger n1 =  BigInteger.ZERO;
    BigInteger  n2 =  BigInteger.ONE;
    BigInteger n3 = BigInteger.ONE;

    for(int i =0; i <  n; i++){
      n3 = n1.add(n2);
      n1 = n2;
      n2 = n3;
    }

    return n3;
  }
}

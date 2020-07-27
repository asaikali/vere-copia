package com.example.hardship;


import java.time.Instant;

class UseLotsOfCpu {

  public static void main(String[] args) {
    CpuHardship cpuHardship = new CpuHardship();
    var start = System.currentTimeMillis();
    cpuHardship.fibonacci(1000_000);
    var duration = System.currentTimeMillis()  - start;
    System.out.println(duration + " ms");
  }
}

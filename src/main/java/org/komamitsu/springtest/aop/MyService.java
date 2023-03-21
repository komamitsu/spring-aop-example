package org.komamitsu.springtest.aop;

import org.springframework.stereotype.Service;

@Service
public class MyService {
  int sum(int a, int b) {
    return a + b;
  }

  int sumWithTrace(int a, int b) {
    return a + b;
  }
}

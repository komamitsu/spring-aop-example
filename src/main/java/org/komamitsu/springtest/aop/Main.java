package org.komamitsu.springtest.aop;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import org.aopalliance.intercept.MethodInterceptor;
import org.komamitsu.springtest.aop.MyAdder.DefaultAddr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  @Autowired
  MyService myService;

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

  private static final List<String> NUMBERS = Arrays.asList(
      "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"
  );

  @Bean
  public CommandLineRunner run() throws Exception {
    return (String[] args) -> {
      // With AspectJ style
      System.out.println("Main: myService=" + myService);

      System.out.println("Main: Calling sum()");
      System.out.println(myService.sum(40, 2));

      System.out.println("Main: Calling sumWithTrace()");
      System.out.println(myService.sumWithTrace(40, 2));

      // With ProxyFactory
      MyAdder myAdder;
      {
        ProxyFactory factory = new ProxyFactory(new DefaultAddr());
        factory.addInterface(MyAdder.class);
        factory.addAdvice((MethodInterceptor) invocation -> {
          Method method = invocation.getMethod();
          if (!(method.getReturnType().isAssignableFrom(int.class) && method.getName().contains("Plus"))) {
            return invocation.proceed();
          }
          String[] numbers = method.getName().split("Plus");
          return Arrays.stream(numbers).mapToInt(x -> NUMBERS.indexOf(x.toLowerCase())).sum();
        });
        myAdder = (MyAdder) factory.getProxy();
      }

      System.out.println("Main: myAdder=" + myAdder);

      System.out.println("Main: Calling onePlusTwoPlusThree()");
      System.out.println(myAdder.onePlusTwoPlusThree());

      System.out.println("Main: Calling tenPlusNine()");
      System.out.println(myAdder.tenPlusNine());
    };
  }
}

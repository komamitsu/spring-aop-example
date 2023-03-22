package org.komamitsu.springtest.aop;

public interface MyAdder {
  int onePlusTwoPlusThree();

  int tenPlusNine();

  class DefaultAdder implements MyAdder {

    @Override
    public int onePlusTwoPlusThree() {
      throw new IllegalStateException("Shouldn't be called");
    }

    @Override
    public int tenPlusNine() {
      throw new IllegalStateException("Shouldn't be called");
    }

    @Override
    public String toString() {
      return "[I'm a proxy] " + super.toString();
    }
  }
}

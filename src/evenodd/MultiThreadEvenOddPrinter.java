package evenodd;

public class MultiThreadEvenOddPrinter {

  private volatile int pointer;

  private final int LIMIT;

  public MultiThreadEvenOddPrinter(int start, int end) {
    this.pointer = start;
    this.LIMIT = end;
  }

  public synchronized void printOdd() {
    while (this.pointer <= LIMIT) {
      if ((this.pointer & 1) == 1) {
        System.out.print(this.pointer + " ");
        this.pointer++;
        notify();
      }
      if (this.pointer > LIMIT) {
        break;
      }
      try {
        wait();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  public synchronized void printEven() {
    while (this.pointer <= LIMIT) {
      if ((this.pointer & 1) == 0) {
        System.out.print(this.pointer + " ");
        this.pointer++;
        notify();
      }
      if (this.pointer > LIMIT) {
        break;
      }
      try {
        wait();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

}

import channel.singleconsumer.OneChannel;
import evenodd.MultiThreadEvenOddPrinter;

import java.util.stream.IntStream;

public class Main {

  public static void main(String[] args) {
//    singleConsumerChannel();
    multiThreadedEvenOddPrinter();
  }

  public static void singleConsumerChannel() {
    final var channel = new OneChannel<Integer>();
    final var producer = channel.getProducer();
    final var consumer = channel.getConsumer();

    Runnable generate = () -> IntStream.range(1, 11).forEach(v -> {
      try {
        Thread.sleep(1000);
        producer.produce(v);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });

    Runnable receiver = () -> {
      Integer val;
      while ((val = consumer.consume()) != null) {
        System.out.println(val);
      }
    };

    Thread t1 = new Thread(generate, "channel.singleconsumer.Producer Thread");
    Thread t2 = new Thread(receiver, "channel.singleconsumer.Consumer Thread");
    t1.start();
    t2.start();
  }

  private static void multiThreadedEvenOddPrinter() {
    MultiThreadEvenOddPrinter printer = new MultiThreadEvenOddPrinter(1, 10);
    Thread evenThread = new Thread(printer::printEven, "Even Thread");
    Thread oddThread = new Thread(printer::printOdd, "Odd Thread");
    evenThread.start();
    oddThread.start();
  }

}
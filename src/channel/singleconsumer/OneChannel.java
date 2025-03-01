package channel.singleconsumer;

import java.util.stream.IntStream;

/**
 * Will produce and consume one value at time
 * There will only be one producer and one consumer
 */
public class OneChannel<T> {

  private final Producer<T> producer;

  private final Consumer<T> consumer;

  public OneChannel() {
    final SharedResource<T> sharedResource = new SharedResource<>();
    this.producer = new Producer<>(sharedResource);
    this.consumer = new Consumer<>(sharedResource);
  }

  // get publisher
  // tx
  public Producer<T> getProducer() {
    return this.producer;
  }

  // get subscriber
  // rx
  public Consumer<T> getConsumer() {
    return this.consumer;
  }

  public static void singleConsumerChannel() {
    final var channel = new OneChannel<Integer>();
    final var producer = channel.getProducer();
    final var consumer = channel.getConsumer();

    Runnable generate = () -> {
      IntStream.range(1, 11).forEach(v -> {
        try {
          Thread.sleep(1000);
          producer.produce(v);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      });
      producer.complete();
    };

    Runnable receiver = () -> {
      Integer val;
      while ((val = consumer.consume()) != null) {
        try {
          System.out.println(val);
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    };

    Thread t1 = new Thread(generate, "Producer Thread");
    Thread t2 = new Thread(receiver, "Consumer Thread");
    t1.start();
    t2.start();
  }

}


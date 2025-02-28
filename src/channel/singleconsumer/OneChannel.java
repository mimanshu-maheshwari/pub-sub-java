package channel.singleconsumer;

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
    this.consumer= new Consumer<>(sharedResource);
  }

  // get publisher
  // tx
  public Producer<T> getProducer(){
    return this.producer;
  }

  // get subscriber
  // rx
  public Consumer<T> getConsumer(){
    return this.consumer;
  }
}


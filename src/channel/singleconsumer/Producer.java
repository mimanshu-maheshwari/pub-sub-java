package channel.singleconsumer;

public class Producer<T> {

  private final SharedResource<T> sharedResource;

  public Producer(SharedResource<T> sharedResource) {
    this.sharedResource = sharedResource;
  }

  public void complete() {
    synchronized (this.sharedResource) {
      this.sharedResource.complete();
    }
  }

  public void produce(T data) {
    synchronized (sharedResource) {
      while (this.sharedResource.hasData()) {
        if (this.sharedResource.isDone()) {
          this.notifyAll();
          break;
        }
        try {
          System.out.println("INFO: Waiting to produce data in " + Thread.currentThread().getName());
          this.sharedResource.wait();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
      var message = new Message<>(data);
      this.sharedResource.setMessage(message);
      System.out.println("INFO: Produced value: " + message);
      this.sharedResource.notifyAll();
    }
  }

}

package channel.singleconsumer;

public class Consumer<T> {

  private final SharedResource<T> sharedResource;

  public Consumer(SharedResource<T> sharedResource) {
    this.sharedResource = sharedResource;
  }

  public T consume() {
    synchronized (sharedResource){
      // process data
      while(!this.sharedResource.hasData()) {
        if (this.sharedResource.isDone()) {
          return null; // Stop consuming if producer is done
        }
        try{
          System.out.println("INFO: Waiting to consume data in " + Thread.currentThread().getName());
          this.sharedResource.wait();
        } catch (InterruptedException e){
          Thread.currentThread().interrupt();
        }
      }
      var message = this.sharedResource.getMessage();
      System.out.println("INFO: Consumed message: " + message);
      this.sharedResource.notifyAll();
      return message.data();
    }
  }
}

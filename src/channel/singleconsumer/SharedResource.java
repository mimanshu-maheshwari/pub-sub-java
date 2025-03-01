package channel.singleconsumer;

import java.util.Objects;

public class SharedResource<T> {
  private volatile Message<T> message;
  private volatile boolean done;

  public SharedResource(){
    this.message = null;
    this.done = false;
  }

  public void complete(){
    this.done = true;
    this.notifyAll();
  }

  public boolean isDone(){
    return done;
  }

  public boolean hasData(){
    return Objects.nonNull(this.message);
  }

  public Message<T> getMessage(){
    var messageLocal = this.message;
    this.message = null;
    return messageLocal;
  }

  public void setMessage(Message<T> message){
    this.message = message;
  }

}

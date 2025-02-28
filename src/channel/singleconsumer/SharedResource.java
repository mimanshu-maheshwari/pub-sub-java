package channel.singleconsumer;

import java.util.Objects;

public class SharedResource<T> {
  private volatile Message<T> message;

  public SharedResource(){
    this.message = null;
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

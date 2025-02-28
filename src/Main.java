import channel.singleconsumer.OneChannel;

import java.util.stream.IntStream;

import static channel.singleconsumer.OneChannel.singleConsumerChannel;
import static evenodd.MultiThreadEvenOddPrinter.multiThreadedEvenOddPrinter;

public class Main {

  public static void main(String[] args) {
    multiThreadedEvenOddPrinter();
//    singleConsumerChannel();
  }


}
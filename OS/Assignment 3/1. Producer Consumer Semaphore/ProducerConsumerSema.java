import java.util.*;
import java.util.concurrent.Semaphore;

class Queue {
  int value;

  static Semaphore semProd = new Semaphore(1);   
  static Semaphore semCon = new Semaphore(0);
  
  void get() {
    try {
      semCon.acquire();
    } catch (InterruptedException e) {
      
    }
    System.out.println("Consumer Consumed : " + value);
    semProd.release();
  }

  void put(int n) {
    try {
      semProd.acquire();
    } catch (InterruptedException e) {
      
    }

    this.value = n;
    System.out.println("Producer Produced : " + n);
    semCon.release();
  }
}

class Producer implements Runnable {
  Queue q;

  Producer(Queue q) {
    this.q = q;
    Thread t1 = new Thread(this);
    t1.start();
  }

  public void run() {
    for (int i = 1; i < 11; i++){
      q.put(i);
      try {Thread.sleep(2000);}
      catch(InterruptedException e){}
    } 
  }
}

class Consumer implements Runnable {
  Queue q;

  Consumer(Queue q) {
    this.q = q;
    Thread t1 = new Thread(this);
    t1.start();
  }

  public void run() {
    for (int i = 1; i < 11; i++){
      q.get();
      try {Thread.sleep(5000);}
      catch(InterruptedException e){}
    } 
  }
}

public class ProducerConsumerSema {
  public static void main(String args[]) {
    Queue q = new Queue();
    new Consumer(q);
    new Producer(q);
  }
}
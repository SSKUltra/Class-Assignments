import java.util.*;

public class ProducerConsumer1{
    public static void main(String[] args) {
        Buffer B = new Buffer();
        new Producer(B);
        new Consumer(B);
    }
}

class Producer implements Runnable{
    Buffer B;

    public Producer(Buffer B){
        this.B = B;
        Thread t = new Thread(this);
        t.start();
    }
    
    public void run(){
        int item = 0;

        while(true){
            B.put(item++);
            try {Thread.sleep(2000);}
            catch(InterruptedException e){} 
        } 
    }
}

class Consumer implements Runnable{
    Buffer B;

    public Consumer(Buffer B){
        this.B = B;
        Thread t = new Thread(this);
        t.start();
    }
    
    public void run(){
        while(true){
            B.get();
            try {Thread.sleep(5000);}
            catch(InterruptedException e){}
        }    
    }
}

class Buffer{
    LinkedList<Integer> Boundbuff = new LinkedList<>();
    int capacity = 8;

    public synchronized void put(int item){
        while(Boundbuff.size() == capacity){
            try { wait(); } 
            catch(InterruptedException e){}
        }
        Boundbuff.add(item);
        System.out.println("Producer produced item : " + item);
        notify();
    }

    public synchronized void get(){
        while(Boundbuff.size() == 0){
            try { wait(); } 
            catch(InterruptedException e){}
        } 
        int val = Boundbuff.removeFirst();
        System.out.println("Consumer consumed item : " + val);
        notify();
    }
}
import java.util.concurrent.Semaphore;

class SharedResource{
    int resource = 1;
    static Semaphore ChangeValue = new Semaphore(1, true);
    static Semaphore PrintValue = new Semaphore(1, false);

    void change(int value){
        try{ChangeValue.acquire();}
        catch(InterruptedException e){}
        this.resource = value;
        PrintValue.release();
    }

    void print(){
        try{PrintValue.acquire();}
        catch(InterruptedException e){}
        System.out.println(this.resource);
        ChangeValue.release();
    }
}

class Updater implements Runnable{
    int value = 0;
    SharedResource R;
    Updater(SharedResource R){
        this.R = R; 
        Thread t1 = new Thread(this);
        t1.start();
    }

    public void run(){
        for(; value<10; value++){
            R.change(value);
            try{Thread.sleep(2000);}
            catch(InterruptedException e){}
        }
    }
}

class Printer implements Runnable{
    SharedResource R;
    Printer(SharedResource R){
        this.R = R;
        Thread t1 = new Thread(this);
        t1.start();
    }

    public void run(){
        while(true){
            R.print();
            try{Thread.sleep(2000);}
            catch(InterruptedException e){}
        }
    }
}

public class BinaryUsingCounting{
    public static void main(String[] args) {
        SharedResource R =  new SharedResource();
        Updater U = new Updater(R);
        Printer P = new Printer(R);  
    } 
}
// First way
class MyThread extends Thread {
    public void run() {
      // code to be executed in this thread
    }
  }
  
  MyThread t = new MyThread();
  t.start(); // starts the thread

  //second way
  class MyRunnable implements Runnable {
    public void run() {
      // code to be executed in this thread
    }
  }
  
  Thread t = new Thread(new MyRunnable());
  t.start(); // starts the thread

  Synchronization
// As I mentioned earlier, threads share the same memory space and can access and modify the same data. 
// This can lead to synchronization issues if not handled properly. For example, consider the following Java code:
class Counter {
    private int count;
  
    public void increment() {
      count++;
    }
  
    public int getCount() {
      return count;
    }
  }
  
  Counter c = new Counter();
  
  Thread t1 = new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
      c.increment();
    }
  });
  
  Thread t2 = new Thread(() -> {
    for (int i = 0; i < 1000; i++) {
      c.increment();
    }
  });
  
  t1.start();
  t2.start();
  
  t1.join();
  t2.join();
  
  System.out.println(c.getCount()); // may print 1995, 2000, or some other value
//   This code creates two threads that increment a shared counter 1000 times each. However, since the increment() method is not synchronized, the threads may interfere with each other and cause the counter to end up with an unexpected value.
//  To fix this, you can use synchronization to ensure that only one thread can access the shared data at a time. 
// Here's an example of synchronizing the increment() method:  
class Counter {
    private int count;
  
    public synchronized void increment() {
      count++;
    }
  
    public int getCount() {
      return count;
    }
  }
  
  Counter c = new Counter();
  
  Thread t1 = new Thread(() -> {
    for (int i = 0; i < 1000; i++){
        c.increment();
    }
    });
    t1.start();t2.start();
    t1.join();t2.join();
  
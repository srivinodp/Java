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
}
// Deadlocks
// Another synchronization issue that can occur in multithreaded programs is a deadlock. 
// A deadlock happens when two or more threads are blocked waiting for each other to release a resource. 
// This can happen when multiple threads are trying to acquire multiple locks in different orders. Here's an example of Java code that can lead to a deadlock:
Object lock1 = new Object();
Object lock2 = new Object();

Thread t1 = new Thread(() -> {
  synchronized (lock1) {
    System.out.println("Thread 1 acquired lock 1");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    synchronized (lock2) {
      System.out.println("Thread 1 acquired lock 2");
    }
  }
});

Thread t2 = new Thread(() -> {
  synchronized (lock2) {
    System.out.println("Thread 2 acquired lock 2");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    synchronized (lock1) {
      System.out.println("Thread 2 acquired lock 1");
    }
  }
});

t1.start();
t2.start();

t1.join();
t2.join();
// This code creates two threads that try to acquire two locks in different orders. If both threads start at the same time, they can end up waiting for each other indefinitely, leading to a deadlock.

// Thread Pools
// Creating a new thread for each task can be inefficient, especially if the tasks are short-lived. To improve performance, you can use a thread pool, which is a collection of pre-allocated threads that can be reused to execute multiple tasks. In Java, you can use the Executor framework to create and manage thread pools. Here's an example of using a thread pool in Java:
ExecutorService executor = Executors.newFixedThreadPool(4);

for (int i = 0; i < 10; i++) {
  executor.execute(() -> {
    // code to be executed in a thread
  });
}

executor.shutdown(); // shuts down the thread pool
// Thread Safety
// When writing multithreaded code, it's important to ensure that your code is thread-safe, meaning that it can be safely executed by multiple threads without causing synchronization issues. Here are some tips for writing thread-safe code:

// Use synchronization when accessing shared data.
// Avoid using mutable shared data, such as lists or dictionaries, if possible.
// Use immutable data structures when possible.
// Use atomic operations when modifying shared data, such as using AtomicInteger instead of int.
// Avoid relying on thread scheduling, as it can be unpredictable and can lead to synchronization issues.
// Conclusion
// Multithreading is a powerful tool for achieving concurrency in your programs, but it can also lead to synchronization issues if not handled properly. 
// By using synchronization, thread pools, and writing thread-safe code, you can ensure that your multithreaded programs are efficient and reliable.
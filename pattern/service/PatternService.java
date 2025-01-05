package pattern.service;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import pattern.constants.Type;
import pattern.model.Semaphore;

public class PatternService {
    private Semaphore semaphore;
    public PatternService(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    // public void printPattern(Type currentType, Type nextType, 
    // AtomicInteger currentNumber, AtomicInteger maxNumber) {
    public void printPattern(Type currentType, Type nextType, 
        int currentNumber, int maxNumber) {
    
        // while(currentNumber.get() < maxNumber.get()) {
            while(currentNumber < maxNumber) {
            System.out.println(Thread.currentThread().getName() + " initiated loop");
            synchronized(this.semaphore) {
                System.out.println("I am : " + Thread.currentThread().getName());
                while(this.semaphore.getSharedType() != currentType) {
                   try {
                    System.out.println("Waiting " + Thread.currentThread().getName());
                    this.semaphore.wait();   
                    // wait();
                   } catch (Exception e) {
                    System.out.println("Waiting exception: " + Thread.currentThread().getName());
                       // TODO Auto-generated catch block
                       System.out.println("Waiting exception: " + e.getCause() + "\n msg :" + e.getMessage());   
                   }
                }   
               
            //    System.out.println(Thread.currentThread().getName() + " " + currentType.name() + ": " + currentNumber.get());
            System.out.println(Thread.currentThread().getName() + " " + currentType.name() + ": " + currentNumber);
            //    currentNumber.getAndAdd(2);
            currentNumber += 2;
               this.semaphore.setSharedType(nextType);
               
               try {
                // System.out.println(Thread.currentThread().getName() + " before notify " + currentType.name() + ": " + currentNumber.get());
                System.out.println(Thread.currentThread().getName() + " before notify " + currentType.name() + ": " + currentNumber); 
                this.semaphore.notify();
                // notify();   
            } catch (Exception e) {
                System.out.println("Notifying exception " + Thread.currentThread().getName()); 
                // TODO: handle exception
                System.out.println("Notifying exception: " + e.getCause() + "\n msg :" + e.getMessage());
               }
                 
            }     
        }        
    }
}

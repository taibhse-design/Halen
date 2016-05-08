package Downloader;

/**
 * @author TAIBHSE
 */
//package com.journaldev.threadpool;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleThreadPool
{

    public static void main(String[] args)
    {
        ExecutorService executor = Executors.newFixedThreadPool(27);
        Thread r = new Thread(new Runnable()
        {

            @Override
            public void run()
            {

                System.out.println("jkhsdailfnejfhg");
            }
        });
        for (int i = 0; i < 10; i++)
        {
            Runnable worker = new WorkerThread("" + i);
            executor.execute(worker);
        }
        executor.execute(r);
        
        executor.shutdown();
        while (!executor.isTerminated())
        {
            //pauses code execution here until all threads terminate
        }
        System.out.println("Finished all threads");
    }

}

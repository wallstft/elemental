package org.neostorm.producer_consumer;

/*
   Copyright 2018 Wall Street Fin Tech

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. 
   
    
    */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.neostorm.Util;
import org.neostorm.threads.ThreadPoolExecutor;
import org.neostorm.threads.ThreadPoolFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;

public class ProducerConsumerController<Message> {

    static private Logger logger = LogManager.getLogger(ProducerConsumerController.class.getName());
    int thread_pool_size = 10;

    protected BlockingQueue<Message> queue = null;
    ThreadPoolExecutor consumer_thread_pool = null;
    ThreadPoolExecutor controller = null;
    boolean running = false;
    ConsumerHandler<Message> consumer_handler = null;

    public ProducerConsumerController( int queue_size ) {
        queue = new LinkedBlockingDeque<>(queue_size);
    }

    private void process_message ( final Message msg )
    {
        if ( consumer_handler != null && consumer_thread_pool != null ) {
            consumer_thread_pool.execute(new Runnable() {
                @Override
                public void run() {
                    consumer_handler.handle_message(msg);
                }
            });
        }
    }

    private void main_message_loop()
    {
        if( queue != null ) {
            while (running) {
                try {
                    Message m = queue.take();
                    process_message (m);
                }
                catch ( Exception x ) {
                    logger.error(x);
                }
            }
        }
    }

    public int getThread_pool_size() {
        return thread_pool_size;
    }

    public void setThread_pool_size(int thread_pool_size) {
        this.thread_pool_size = thread_pool_size;
    }

    protected ThreadPoolExecutor createConsumerThreadPool (int size )
    {
        return ThreadPoolFactory.getFixedThreadPool(size);
    }

    protected ThreadPoolExecutor createControllerThreadPool ( int size )
    {
        return ThreadPoolFactory.getFixedThreadPool(size );
    }

    public void start( ConsumerHandler<Message> consumer_handler )
    {
        this.consumer_handler = consumer_handler;
        controller = createControllerThreadPool(1);
        consumer_thread_pool = createConsumerThreadPool( thread_pool_size );
        running = true;
        controller.execute(new Runnable() {
            @Override
            public void run() {
                main_message_loop();
            }
        });
    }

    public void stop ()
    {
        running= false;
        if ( consumer_thread_pool != null ) {
            consumer_thread_pool.shutdown();
            consumer_thread_pool = null;
        }
        if ( controller != null ) {
            controller.shutdown();;
            controller = null;
        }
    }
}

package io.at.learn.lld03;

import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

// Lazy Initialization
class ConfigurationManager {

    public static ConfigurationManager instance;
    private ConfigurationManager() {} // To prevent construction initialization

    public static ConfigurationManager getInstance() {
        if(instance == null) {
            // Since this is not thread safe,
            // This will run multiple times in multithreaded env
            System.out.println("Creating new Configuration Manager by " + Thread.currentThread().getName());
            instance = new ConfigurationManager();
        }
        return instance;
    }
}

// Eager Initialization
class ConfigurationManagerEagerInit {

    @Getter
    public static ConfigurationManagerEagerInit instance = new ConfigurationManagerEagerInit();
    private ConfigurationManagerEagerInit() {} // To prevent construction initialization
}

/*
    Initialization on demand (Lazy initialization)
    Inner static class is loaded when someone calls getInstance()
    Class loading in Java is thread safe by JVM design,
    So this is lazy loading, which being thread safe
*/
class ConfigurationHolder {

    private ConfigurationHolder() {}// To prevent construction initialization

    private static class ConfigurationHolderInstanceKeeper {
        private static final ConfigurationHolder instance = new ConfigurationHolder();
    }

    public static ConfigurationHolder getInstance() {
        return ConfigurationHolderInstanceKeeper.instance;
    }
}

/*
    This is thread-safe lazy initialization.
    getInstance() is global entry point, and is synchronized,
    so even after the instance is initialized, every thread will queue to run getInstance.
    This will hinder the performance
*/
class ConfigurationManagementThreadSafe {

    private ConfigurationManagementThreadSafe() {}
    private static ConfigurationManagementThreadSafe instance;

    public static synchronized ConfigurationManagementThreadSafe getInstance() {
        if(instance == null) {
            System.out.println("Creating new Configuration Manager by " + Thread.currentThread().getName());
            instance = new ConfigurationManagementThreadSafe();
        }
        return instance;
    }
}

/*
    This is the same as the above method, but getInstance method is not synchronized.
    Only after the threads check that instance is null, they will go into synchronized block.
    So, after the instance creation, no thread will have to queue up.

    Double-checking is required, since initially multiple threads can face the first "instance == null",
    so multiple threads will go inside synchronized block. But we require instance to be initialized only once.
    So the second "instance == null" will check if some other thread has already initialized the instance or not.

    Volatile keyword is used since line 101, new Object() is not atomic operation.
    So, Volatile keyword will make instance have instant visibility to other threads.
    Normally, till the new Object() is fully built, it is not shown to other threads,
    but Volatile keyword enforces a memory visibility guarantee.
*/
class ConfigurationManagementThreadSafeDoubleCheck {

    private ConfigurationManagementThreadSafeDoubleCheck() {}
    private static volatile ConfigurationManagementThreadSafeDoubleCheck instance;

    public static ConfigurationManagementThreadSafeDoubleCheck getInstance() {
        if(instance == null) {
            synchronized (ConfigurationManagementThreadSafeDoubleCheck.class) {
                if(instance == null) {
                    System.out.println("Creating new Configuration Manager by " + Thread.currentThread().getName());
                    instance = new ConfigurationManagementThreadSafeDoubleCheck();
                }
            }
        }
        return instance;
    }
}

public class Singleton {

    private static final int TOTAL_THREADS = 1000;

    public static void main(String[] args) throws InterruptedException {
        testInMultiThreadedEnv(ConfigurationManager::getInstance);

        testInMultiThreadedEnv(ConfigurationManagerEagerInit::getInstance);

        testInMultiThreadedEnv(ConfigurationHolder::getInstance);

        testInMultiThreadedEnv(ConfigurationManagementThreadSafe::getInstance);

        testInMultiThreadedEnv(ConfigurationManagementThreadSafeDoubleCheck::getInstance);
    }

    private static void testInMultiThreadedEnv(Supplier<Object> supplier) throws InterruptedException {
        Set<Object> set = ConcurrentHashMap.newKeySet();
        List<Thread> list = new ArrayList<>();
        LocalDateTime startTime = LocalDateTime.now();
        for(int i=0; i<TOTAL_THREADS; i++) {
            var thread = new Thread(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                set.add(supplier.get().hashCode());
            });
            list.add(thread);
            thread.start();
        }
        list.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        LocalDateTime endTime = LocalDateTime.now();

        System.out.println("------------------------------------");
        System.out.println("Total Objects Created : " + set.size() + " , Time Taken :" + Duration.between(startTime, endTime).getNano() / 1000);
        System.out.println("------------------------------------");
    }

}

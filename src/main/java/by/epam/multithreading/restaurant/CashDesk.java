package by.epam.multithreading.restaurant;

import by.epam.multithreading.entity.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CashDesk implements Runnable{

    private static final int MAX_PROCESSING_CLIENTS = 3;
    private final Semaphore semaphore = new Semaphore(MAX_PROCESSING_CLIENTS);
    private static final Lock lock = new ReentrantLock();
    private List<Client> clientQueue = new ArrayList<>();
    private int numberOfCashDesk;

    public CashDesk(int numberOfCashDesk) {
        this.numberOfCashDesk = numberOfCashDesk;
    }

    public int getNumberOfCashDesk() {
        return numberOfCashDesk;
    }

    public List<Client> getClientQueue() {
        return clientQueue;
    }

    public void addClient(Client client) {
        lock.lock();
        try {
            if (client.getPreOrder()) {
                clientQueue.add(0, client);
            } else {
                clientQueue.add(client);
            }
        } finally {
            lock.unlock();
        }

    }

    public int getCountOfClients() {
        lock.lock();
        try {
            return clientQueue.size();
        } finally {
            lock.unlock();
        }
    }

    private void serveClients() {
        for (Client client: clientQueue) {
            client.receiveOrder();
            System.out.println("Client " + client.getName() + " from " + (numberOfCashDesk + 1) + " cash desk" + " is done");
            clientQueue.remove(client);
        }

    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.lock();
        try {
            serveClients();
        } finally {
            lock.unlock();
            semaphore.release();
        }
    }
}

package by.epam.multithreading.restaurant;

import by.epam.multithreading.entity.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurant {

    public static final int COUNT_OF_CASH_DESKS = 7;
    private List<CashDesk> cashDesks = new ArrayList<>(COUNT_OF_CASH_DESKS);
    private static final Lock lock = new ReentrantLock();
    private static Restaurant INSTANCE;

    private Restaurant() {
        for (int i = 0; i < COUNT_OF_CASH_DESKS + 1; i++) {
            cashDesks.add(new CashDesk(i));
        }
    }

    public int getCountOfClients() {
        int countOfClients = 0;
        for (CashDesk cashDesk: cashDesks) {
            for (Client client: cashDesk.getClientQueue()) {
                countOfClients++;
            }
        }
        return countOfClients;
    }

    public List<CashDesk> getCashDesks() {
        return cashDesks;
    }

    public void addClientToDefiniteCashDesk(int numberOfCashDesk, Client client) {
        lock.lock();
        try {
            CashDesk cashDesk = cashDesks.get(numberOfCashDesk);
            cashDesk.addClient(client);
        } finally {
            lock.unlock();
        }
    }

    public static Restaurant getInstance() {
        Restaurant localInstance = INSTANCE;
        if (localInstance == null) {
            lock.lock();
            try {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    INSTANCE = localInstance = new Restaurant();
                }
            } finally {
                lock.unlock();
            }
        }
        return localInstance;
    }
}

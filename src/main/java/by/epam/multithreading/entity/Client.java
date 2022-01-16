package by.epam.multithreading.entity;

import java.util.Objects;

public class Client{
    private static final int MIN_COUNT_OF_CASH_DESKS = 1;
    private static final int MAX_COUNT_OF_CASH_DESKS = 7;

    private long id;
    private String name;
    private boolean isPreOrder;
    private State state;

    public Client(long id, String name, boolean isPreOrder, State state) {
        this.id = id;
        this.name = name;
        this.isPreOrder = isPreOrder;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPreOrder() {
        return isPreOrder;
    }

    public void setPreOrder(Boolean preOrder) {
        isPreOrder = preOrder;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Client client = (Client) object;
        return id == client.id && isPreOrder == client.isPreOrder && Objects.equals(name, client.name) && state == client.state;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int)id + (isPreOrder ? 1 : 0) + ((name == null) ? 0 : name.hashCode()) + ((state == null) ? 0 : state.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Client: {" +
                "id= " + id +
                ", name= '" + name + '\'' +
                ", isPreOrder= " + isPreOrder +
                ", state= " + state +
                '}';
    }

    public int calculateNumberOfCashDesk() {
        return (int) (Client.MIN_COUNT_OF_CASH_DESKS + Math.random() * Client.MAX_COUNT_OF_CASH_DESKS);
    }

    public void receiveOrder() {
        setState(State.DONE);
    }
}

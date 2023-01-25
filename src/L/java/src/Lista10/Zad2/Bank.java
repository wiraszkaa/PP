package Lista10.Zad2;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private int clients;
    private final Map<Integer, Double> clientsBalance;

    public Bank() {
        this.clientsBalance = new HashMap<>();
    }

    public boolean addClient(int clientId) {
        return addClient(clientId, 0D);
    }

    public boolean addClient(int clientId, double balance) {
        if (clientsBalance.containsKey(clientId)) {
            return false;
        } else {
            clientsBalance.put(clientId, balance);
            clients += 1;
            return true;
        }
    }

    public double getBalance(int clientId) {
        if (clientsBalance.containsKey(clientId)) {
            return clientsBalance.get(clientId);
        }
        return -1;
    }

    public boolean addMoney(Client client, double value) {
        return addMoney(client.getId(), value);
    }

    private boolean addMoney(int clientId, double value) {
        if (!clientsBalance.containsKey(clientId) || value < 0) {
            return false;
        }
        clientsBalance.replace(clientId, clientsBalance.get(clientId) + value);
        return true;
    }

    public boolean withdrawMoney(Client client, double value) {
        if (!clientsBalance.containsKey(client.getId())) {
            return false;
        }
        double balance = clientsBalance.get(client.getId());
        if (value < 0 || balance - value < 0) {
            return false;
        }
        clientsBalance.replace(client.getId(), balance - value);
        return true;
    }

    public boolean transferMoney(Client client, int clientId, double value) {
        if (client.getId() == clientId) {
            return false;
        }
        if (!withdrawMoney(client, value)) {
            return false;
        }
        return addMoney(clientId, value);
    }

    public void printClientsBalance() {
        for (int id: clientsBalance.keySet()) {
            System.out.printf("Client %s: %.2fzł\n", id, getBalance(id));
        }
    }

    public int getClientsNumber() {
        return clients;
    }
}

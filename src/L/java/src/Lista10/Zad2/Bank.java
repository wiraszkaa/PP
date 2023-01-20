package Lista10.Zad2;

import java.util.HashMap;
import java.util.Map;

public class Bank {
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
        if (!clientsBalance.containsKey(client.getId()) || value < 0) {
            return false;
        }
        clientsBalance.replace(client.getId(), clientsBalance.get(client.getId()) + value);
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

    public void printClientsBalance() {
        for (int id: clientsBalance.keySet()) {
            System.out.printf("Client %s: %.2fzł\n", id, getBalance(id));
        }
    }
}

package Lista10.Zad3;

import akka.actor.typed.ActorSystem;

public class Main {
    public static void main(String[] args) {
        Storage storage = new Storage(40, 10, 10, 20);
        ActorSystem<Storager.Provider> storager = ActorSystem.create(Storager.create(storage, 100_000), "storager");
        storager.tell(new Storager.StartProduction());
    }
}

package Lista10.Zad3;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.time.Duration;
import java.util.Random;

public class Bottler extends AbstractBehavior<Storager.Provider> {
    private final ActorRef<Storager.Provider> storager;
    private final long speed;
    private final int slots;
    private int freeSlots;
    private final double neededFilteredWine;
    private final int neededBottles;
    private final long neededTime;
    private final int createdWineBottles;
    private final double failureChance;

    private Bottler(ActorContext<Storager.Provider> context, ActorRef<Storager.Provider> storager, long speed) {
        super(context);
        this.storager = storager;
        this.speed = speed;
        slots = 10;
        freeSlots = slots;
        neededFilteredWine = 1;
        neededBottles = 1;
        neededTime = 1000;
        createdWineBottles = 1;
        failureChance = 0.05;
    }

    public static Behavior<Storager.Provider> create(ActorRef<Storager.Provider> storager, long speed) {
        return Behaviors.setup(context -> new Bottler(context, storager, speed));
    }

    @Override
    public Receive<Storager.Provider> createReceive() {
        return newReceiveBuilder()
                .onMessage(ProvideBottlingSupplies.class, this::bottle)
                .onMessage(BottlingFinished.class, this::provideWineBottles)
                .build();
    }

    public static final class ProvideBottlingSupplies implements Storager.Provider {
        public final double filteredWine;
        public final int bottles;

        public ProvideBottlingSupplies(double filteredWine, int bottles) {
            this.filteredWine = filteredWine;
            this.bottles = bottles;
        }
    }

    public static final class BottlingFinished implements Storager.Provider {}

    private Behavior<Storager.Provider> bottle(ProvideBottlingSupplies p) {
        System.out.println("BOTTLING");
        double filteredWine = p.filteredWine;
        int bottles = p.bottles;
        while (freeSlots > 0 && filteredWine >= neededFilteredWine && bottles >= neededBottles) {
            filteredWine -= neededFilteredWine;
            bottles -= neededBottles;
            freeSlots -= 1;
            getContext().scheduleOnce(Duration.ofMillis(neededTime / speed), getContext().getSelf(), new BottlingFinished());
        }
        storager.tell(new Storager.GetBottlingSupplies(filteredWine, bottles));
        return this;
    }

    private Behavior<Storager.Provider> provideWineBottles(BottlingFinished f) {
        freeSlots += 1;

        Random random = new Random();
        double value = random.nextDouble();

        if (failureChance > value) {
            System.out.println("Bottling error occurred");
        } else {
            storager.tell(new Storager.GetWineBottles(createdWineBottles));
            System.out.println("Bottling Success");
        }
        return this;
    }
}

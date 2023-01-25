package Lista10.Zad3;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.time.Duration;
import java.util.Random;

public class Filterer extends AbstractBehavior<Storager.Provider> {
    private final ActorRef<Storager.Provider> storager;
    private final long speed;
    private final int slots;
    private int freeSlots;
    private final double neededUnfilteredWine;
    private final long neededTime;
    private final double createdfilteredWine;
    private final double failureChance;

    private Filterer(ActorContext<Storager.Provider> context, ActorRef<Storager.Provider> storager, long speed) {
        super(context);
        this.storager = storager;
        this.speed = speed;
        slots = 10;
        freeSlots = slots;
        neededUnfilteredWine = 25;
        neededTime = 1000;
        createdfilteredWine = 20;
        failureChance = 0;
    }

    public static Behavior<Storager.Provider> create(ActorRef<Storager.Provider> storager, long speed) {
        return Behaviors.setup(context -> new Filterer(context, storager, speed));
    }

    @Override
    public Receive<Storager.Provider> createReceive() {
        return newReceiveBuilder()
                .onMessage(ProvideUnfilteredWine.class, this::filter)
                .onMessage(FilteringFinished.class, this::provideFilteredWine)
                .build();
    }

    public static final class ProvideUnfilteredWine implements Storager.Provider {
        public final double unfilteredWine;

        public ProvideUnfilteredWine(double unfilteredWine) {
            this.unfilteredWine = unfilteredWine;
        }
    }

    public static final class FilteringFinished implements Storager.Provider {}

    private Behavior<Storager.Provider> filter(ProvideUnfilteredWine p) {
        System.out.println("FILTERING");
        double unfilteredWine = p.unfilteredWine;
        while (freeSlots > 0 && unfilteredWine >= neededUnfilteredWine) {
            unfilteredWine -= neededUnfilteredWine;
            freeSlots -= 1;
            getContext().scheduleOnce(Duration.ofMillis(neededTime / speed), getContext().getSelf(), new FilteringFinished());
        }
        if (unfilteredWine > 0) {
            storager.tell(new Storager.GetUnfilteredWine(unfilteredWine));
        }
        return this;
    }

    private Behavior<Storager.Provider> provideFilteredWine(FilteringFinished f) {
        freeSlots += 1;

        Random random = new Random();
        double value = random.nextDouble();

        if (failureChance > value) {
            System.out.println("Filtering error occurred");
        } else {
            storager.tell(new Storager.GetFilteredWine(createdfilteredWine));
            System.out.println("Filtering Success");
        }
        return this;
    }
}

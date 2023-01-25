package Lista10.Zad3;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.time.Duration;
import java.util.Random;

public class Fermenter extends AbstractBehavior<Storager.Provider> {
    private final ActorRef<Storager.Provider> storager;
    private final long speed;
    private final int slots;
    private int freeSlots;
    private final double neededJuice;
    private final double neededWater;
    private final double neededSugar;
    private final long neededTime;
    private final double createdUnfilteredWine;
    private final double failureChance;

    private Fermenter(ActorContext<Storager.Provider> context, ActorRef<Storager.Provider> storager, long speed) {
        super(context);
        this.storager = storager;
        this.speed = speed;
        slots = 10;
        freeSlots = slots;
        neededJuice = 15;
        neededWater = 8;
        neededSugar = 2;
        neededTime = 1000;
        createdUnfilteredWine = 25;
        failureChance = 0.05;
    }

    public static Behavior<Storager.Provider> create(ActorRef<Storager.Provider> storager, long speed) {
        return Behaviors.setup(context -> new Fermenter(context, storager, speed));
    }

    @Override
    public Receive<Storager.Provider> createReceive() {
        return newReceiveBuilder()
                .onMessage(ProvideSupplies.class, this::ferment)
                .onMessage(FermentingFinished.class, this::provideUnfilteredWine)
                .build();
    }

    public static final class ProvideSupplies implements Storager.Provider {
        public final double juice;
        public final double sugar;
        public final double water;

        public ProvideSupplies(double juice, double sugar, double water) {
            this.juice = juice;
            this.sugar = sugar;
            this.water = water;
        }
    }

    public static final class FermentingFinished implements Storager.Provider {}

    private Behavior<Storager.Provider> ferment(ProvideSupplies p) {
        System.out.println("FERMENTING");
        double juice = p.juice;
        double water = p.water;
        double sugar = p.sugar;
        while (freeSlots > 0 && juice >= neededJuice && water >= neededWater && sugar >= neededSugar) {
            juice -= neededJuice;
            water -= neededWater;
            sugar -= neededSugar;
            freeSlots -= 1;
            getContext().scheduleOnce(Duration.ofMillis(neededTime / speed), getContext().getSelf(), new FermentingFinished());
        }
        storager.tell(new Storager.GetFermentingSupplies(juice, water, sugar));
        return this;
    }

    private Behavior<Storager.Provider> provideUnfilteredWine(FermentingFinished f) {
        freeSlots += 1;

        Random random = new Random();
        double value = random.nextDouble();

        if (failureChance > value) {
            System.out.println("Fermentation error occurred");
        } else {
            storager.tell(new Storager.GetUnfilteredWine(createdUnfilteredWine));
            System.out.println("Fermentation Success");
        }
        return this;
    }
}

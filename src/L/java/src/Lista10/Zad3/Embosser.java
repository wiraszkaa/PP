package Lista10.Zad3;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.time.Duration;
import java.util.Random;

public class Embosser extends AbstractBehavior<Storager.Provider> {
    private final ActorRef<Storager.Provider> storager;
    private final long speed;
    private final int slots;
    private int freeSlots;
    private final double neededGrapes;
    private final long neededTime;
    private final double createdJuice;
    private final double failureChance;
    private boolean noGrapes;

    private Embosser(ActorContext<Storager.Provider> context, ActorRef<Storager.Provider> storager, long speed) {
        super(context);
        this.storager = storager;
        this.speed = speed;
        slots = 1;
        freeSlots = slots;
        neededGrapes = 15;
        neededTime = 720 * 60 * 1000;
        createdJuice = 10;
        failureChance = 0;
    }

    public static Behavior<Storager.Provider> create(ActorRef<Storager.Provider> storager, long speed) {
        return Behaviors.setup(context -> new Embosser(context, storager, speed));
    }

    @Override
    public Receive<Storager.Provider> createReceive() {
        return newReceiveBuilder()
                .onMessage(ProvideGrapes.class, this::emboss)
                .onMessage(EmbossingFinished.class, this::provideJuice)
                .build();
    }

    public static final class ProvideGrapes implements Storager.Provider {
        public final double grapes;

        public ProvideGrapes(double grapes) {
            this.grapes = grapes;
        }
    }

    public static final class EmbossingFinished implements Storager.Provider {}

    private Behavior<Storager.Provider> emboss(ProvideGrapes p) {
        double grapes = p.grapes;
        while (freeSlots > 0 && grapes >= neededGrapes) {
            System.out.println("EMBOSSING");
            grapes -= neededGrapes;
            freeSlots -= 1;
            getContext().scheduleOnce(Duration.ofMillis(neededTime / speed), getContext().getSelf(), new Embosser.EmbossingFinished());
        }
        if (grapes >= neededGrapes) {
            storager.tell(new Storager.GetGrapes(grapes));
        } else {
            noGrapes = true;
            System.out.println(grapes + " grapes left");
        }
        return this;
    }

    private Behavior<Storager.Provider> provideJuice(EmbossingFinished e) {
        freeSlots += 1;

        Random random = new Random();
        double value = random.nextDouble();

        if (failureChance > value) {
            System.out.println("Embossing error occurred");
        } else {
            storager.tell(new Storager.GetJuice(createdJuice));
            System.out.println("Embossing Success");
        }

        if (slots == freeSlots && noGrapes) {
            System.out.println("Embossing stopped");
            return Behaviors.stopped();
        }
        return this;
    }
}

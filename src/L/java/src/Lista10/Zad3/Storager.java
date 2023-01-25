package Lista10.Zad3;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;
import akka.actor.typed.javadsl.Behaviors;

public class Storager extends AbstractBehavior<Storager.Provider> {
    private final Storage storage;
    private final ActorRef<Provider> embosser;
    private final ActorRef<Provider> fermenter;
    private final ActorRef<Provider> filterer;
    private final ActorRef<Provider> bottler;

    private Storager(ActorContext<Provider> context, Storage storage, long speed) {
        super(context);
        this.storage = storage;
        embosser = context.spawn(Embosser.create(getContext().getSelf(), speed), "embosser");
        fermenter = context.spawn(Fermenter.create(getContext().getSelf(), speed), "fermenter");
        filterer = context.spawn(Filterer.create(getContext().getSelf(), speed), "filterer");
        bottler = context.spawn(Bottler.create(getContext().getSelf(), speed), "bottler");
        getContext().watch(embosser);
        getContext().watch(fermenter);
        getContext().watch(filterer);
        getContext().watch(bottler);
    }

    public static Behavior<Provider> create(Storage storage, long speed) {
        return Behaviors.setup(context -> new Storager(context, storage, speed));
    }

    @Override
    public Receive<Provider> createReceive() {
        return newReceiveBuilder()
                .onMessage(StartProduction.class, this::startProduction)
                .onMessage(GetGrapes.class, this::getGrapes)
                .onMessage(GetFermentingSupplies.class, this::getFermentingSupplies)
                .onMessage(GetBottlingSupplies.class, this::getBottlingSupplies)
                .onMessage(GetJuice.class, this::getJuice)
                .onMessage(GetUnfilteredWine.class, this::getUnfilteredWine)
                .onMessage(GetFilteredWine.class, this::getFilteredWine)
                .onMessage(GetWineBottles.class, this::getWineBottles)
                .build();
    }

    public interface Provider {}

    public static final class GetGrapes implements Provider {
        public final double grapes;

        public GetGrapes(double grapes) {
            this.grapes = grapes;
        }
    }

    public static final class GetFermentingSupplies implements Provider {
        public final double juice;
        public final double water;
        public final double sugar;

        public GetFermentingSupplies(double juice, double water, double sugar) {
            this.juice = juice;
            this.water = water;
            this.sugar = sugar;
        }

    }

    public static final class GetBottlingSupplies implements Provider {
        public final double filteredWine;
        public final int bottles;

        public GetBottlingSupplies(double filteredWine, int bottles) {
            this.filteredWine = filteredWine;
            this.bottles = bottles;
        }
    }

    public static final class GetJuice implements Provider {
        public final double juice;

        public GetJuice(double juice) {
            this.juice = juice;
        }

    }

    public static final class GetUnfilteredWine implements Provider {
        public final double unfilteredWine;

        public GetUnfilteredWine(double unfilteredWine) {
            this.unfilteredWine = unfilteredWine;
        }

    }

    public static final class GetFilteredWine implements Provider {
        public final double filteredWine;

        public GetFilteredWine(double filteredWine) {
            this.filteredWine = filteredWine;
        }

    }

    public static final class GetWineBottles implements Provider {
        public final int wineBottles;

        public GetWineBottles(int wineBottles) {
            this.wineBottles = wineBottles;
        }
    }

    public static class StartProduction implements Provider {}

    private Behavior<Provider> startProduction(StartProduction p) {
        System.out.println("STARTING PRODUCTION");

        embosser.tell(new Embosser.ProvideGrapes(storage.grapes));
        storage.grapes = 0;

        return this;
    }

    private Behavior<Provider> getGrapes(GetGrapes g) {
        storage.grapes = g.grapes;
        System.out.println("Getting " + storage.grapes + " grapes back to storage");
        return this;
    }

    private Behavior<Provider> getFermentingSupplies(GetFermentingSupplies g) {
        storage.juice = g.juice;
        storage.water = g.water;
        storage.sugar = g.sugar;
        System.out.printf("Getting %.1f juice, %.1f water and %.1f sugar back to storage\n", storage.juice, storage.water, storage.sugar);
        return this;
    }

    private Behavior<Provider> getBottlingSupplies(GetBottlingSupplies g) {
        storage.filteredWine = g.filteredWine;
        storage.bottles = g.bottles;
        System.out.printf("Getting %.1f filteredWine and %d bottles back to storage\n", storage.filteredWine, storage.bottles);
        return this;
    }

    private Behavior<Provider> getJuice(GetJuice g) {
        storage.juice += g.juice;
        System.out.printf("Created %.1f juice\n", storage.juice);

        embosser.tell(new Embosser.ProvideGrapes(storage.grapes));
        storage.grapes = 0;

        fermenter.tell(new Fermenter.ProvideSupplies(storage.juice, storage.sugar, storage.water));
        storage.juice = 0;
        storage.sugar = 0;
        storage.water = 0;

        return this;
    }

    private Behavior<Provider> getUnfilteredWine(GetUnfilteredWine g) {
        storage.unfilteredWine += g.unfilteredWine;
        System.out.printf("Created %.1f unfilteredWine\n", storage.unfilteredWine);

        fermenter.tell(new Fermenter.ProvideSupplies(storage.juice, storage.sugar, storage.water));
        storage.juice = 0;
        storage.sugar = 0;
        storage.water = 0;

        filterer.tell(new Filterer.ProvideUnfilteredWine(storage.unfilteredWine));
        storage.unfilteredWine = 0;

        return this;
    }

    private Behavior<Provider> getFilteredWine(GetFilteredWine g) {
        storage.filteredWine += g.filteredWine;
        System.out.printf("Created %.1f filteredWine\n", storage.filteredWine);

        filterer.tell(new Filterer.ProvideUnfilteredWine(storage.unfilteredWine));
        storage.unfilteredWine = 0;

        bottler.tell(new Bottler.ProvideBottlingSupplies(storage.filteredWine, storage.bottles));
        storage.filteredWine = 0;
        storage.bottles = 0;

        return this;
    }

    private Behavior<Provider> getWineBottles(GetWineBottles g) {
        storage.wineBottle += g.wineBottles;
        System.out.printf("Created %d WineBottles\n", storage.wineBottle);

        if (storage.bottles > 0) {
            bottler.tell(new Bottler.ProvideBottlingSupplies(storage.filteredWine, storage.bottles));
        }
        storage.filteredWine = 0;
        storage.bottles = 0;

        return this;
    }
}

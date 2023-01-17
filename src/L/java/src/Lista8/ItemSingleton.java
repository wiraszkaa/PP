package Lista8;

import java.util.LinkedList;
import java.util.List;

public interface ItemSingleton {
    default List<Item> removeSingletonDuplicates(List<Item> items, Class itemType) {
        List<Item> result = new LinkedList<>();
        Item lastOccurence = null;
        for (Item i: items) {
            if (itemType.isInstance(i)) {
                lastOccurence = i;
            } else {
                result.add(i);
            }
        }
        if (lastOccurence != null) {
            result.add(lastOccurence);
        }

        return result;
    }
}

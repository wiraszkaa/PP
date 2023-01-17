package Lista8;

import java.util.List;

public class SceneDecorator implements SceneInterface {
    private final SceneInterface scene;
    public SceneDecorator(SceneInterface scene) {
        this.scene = scene;
    }

    @Override
    public List<Item> getItems() {
        return scene.getItems();
    }

    @Override
    public void draw() {
        ItemsList itemsList = new ItemsList(getItems(), e -> scene.draw());
        itemsList.show();
    }
}

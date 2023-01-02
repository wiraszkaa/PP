package Lista8;

class TextItem extends Item {
    private String text;
    
    public TextItem(Point position, String text) {
        anchorPoints.add(position);
        this.text = text;
    }

    public String getText() {
        return text;
    }
    
    @Override
    public void draw() {
        // TODO Auto-generated method stub
        
    }
}

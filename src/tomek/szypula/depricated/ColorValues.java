package tomek.szypula.depricated;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ColorValues {
    private IntegerProperty r = new SimpleIntegerProperty(0);
    private IntegerProperty g = new SimpleIntegerProperty(255);
    private IntegerProperty b = new SimpleIntegerProperty(0);

    public ColorValues() {
    }

    public int getR() {
        return r.get();
    }

    public IntegerProperty rProperty() {
        return r;
    }

    public void setR(int r) {
        this.r.set(r);
    }

    public int getG() {
        return g.get();
    }

    public IntegerProperty gProperty() {
        return g;
    }

    public void setG(int g) {
        this.g.set(g);
    }

    public int getB() {
        return b.get();
    }

    public IntegerProperty bProperty() {
        return b;
    }

    public void setB(int b) {
        this.b.set(b);
    }
}

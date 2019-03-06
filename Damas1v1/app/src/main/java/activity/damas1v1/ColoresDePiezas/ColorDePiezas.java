package activity.damas1v1.ColoresDePiezas;

import java.io.Serializable;

public abstract class ColorDePiezas implements Serializable {
    ColorDePiezas(){ }
    public abstract int getPeon();
    public abstract int getDama();
}

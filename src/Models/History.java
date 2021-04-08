package Models;

import java.io.Serializable;

public class History implements Serializable {
    public int i;
    public int j;
    public int value;

    public History(int i, int j, int value) {
        this.i = i;
        this.j = j;
        this.value = value;
    }
}

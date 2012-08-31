package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

public class GuiList extends Observable implements Iterable<Gui>{
    private List<Gui> list = new ArrayList<Gui>();

    public void add(Gui gui) {
        list.add(gui);
        setChanged();
        notifyObservers();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public Gui last() {
        return list.get(list.size() - 1);
    }

    public void remove(Gui gui) {
        list.remove(gui);
        setChanged();
    }

    public void setChanged() {
        super.setChanged();
        notifyObservers();
    }

    public Iterator<Gui> iterator() {
        return list.iterator();
    }
}
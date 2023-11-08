package model;

public class Prize extends Toy {


    public Prize(int id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "\nНомер: " + getId() + "," + " Название: " + "\"" + getName() + "\"";
    }

}
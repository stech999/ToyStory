package model;

public record Buyer(int id, String name, String surname, int checkNumber, int phoneNumber) {


    @Override
    public String toString() {
        return "\nПокупатель " +
                "№ " + id +
                ", '" + name + '\'' +
                ", '" + surname + '\'' +
                ", чек: " + checkNumber +
                ", телефон: " + phoneNumber;
    }
}
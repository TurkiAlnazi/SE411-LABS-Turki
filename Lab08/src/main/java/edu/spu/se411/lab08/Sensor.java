package edu.spu.se411.lab08;

import java.util.ArrayList;
import java.util.List;

public abstract class Sensor implements Subject, Cloneable {
    private final String name;
    private double reading;
    private List<Observer> observers = new ArrayList<>();

    protected Sensor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getReading() {
        return reading;
    }

    public void setReading(double reading) {
        if (Double.compare(this.reading, reading) != 0) {
            this.reading = reading;
            notifyObservers();
        }
    }

    @Override
    public void register(Observer o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void unregister(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : new ArrayList<>(observers)) {
            observer.update(this);
        }
    }

    public int getObserverCount() {
        return observers.size();
    }

    @Override
    public Sensor clone() {
        try {
            Sensor copy = (Sensor) super.clone();
            copy.observers = new ArrayList<>();
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

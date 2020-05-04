package org.challenges.sn;

public class SparkProductImpl implements SparkProduct {

    private final String name;
    private final int price;

    public SparkProductImpl(String name, int price){
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int price() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SparkProductImpl)) return false;

        SparkProductImpl that = (SparkProductImpl) o;

        if (price != that.price) return false;
        return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + price;
        return result;
    }
}

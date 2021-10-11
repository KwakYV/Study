package ru.geekbrains.lesson2;

public class Notebook {
    private int memory;
    private int price;
    private String name;

    public Notebook(String name, int price, int memory){
        this.name = name;
        this.price = price;
        this.memory = memory;
    }

    public int getMemory() {
        return memory;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getRank(String name){
        int result=0;
        switch(name){
            case("Lenuvo"):
                result = 1;
                break;
            case("Asos"):
                result = 2;
                break;
            case("MacNote"):
                result = 3;
                break;
            case("Eser"):
                result = 4;
                break;
            case("Xamiou"):
                result = 5;
                break;
        }
        return result;
    }

    public boolean greater(Notebook aOther){
        if (aOther == null){
            return true;
        }
        if (this.price > aOther.price){
            return true;
        } else if (this.price < aOther.price){
            return false;
        } else {
            if (this.memory > aOther.memory){
                return true;
            } else if (this.memory < aOther.memory){
                return false;
            } else {
                if (this.getRank(this.name) > aOther.getRank(aOther.getName())){
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Notebook{" +
                "memory=" + memory +
                ", price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}

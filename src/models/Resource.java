package models;

public class Resource {
    protected int gold;
    protected int elixir;

    public Resource(int gold, int elixir) {
        this.gold = gold;
        this.elixir = elixir;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold){
        this.gold = gold;
    }

    public int getElixir() {
        return elixir;
    }

    public void setElixir(int elixir) {
        this.elixir = elixir;
    }

    public void add(int elixir, int gold) {
        this.elixir += elixir;
        this.gold += gold;
    }


    public void addExilir(int value) {
        elixir += value;
    }

    public void addGold(int value) {
        gold += value;
    }

    public void spendElixir(int value) {
        elixir -= value;
    }

    public void spendGold(int value) {
        gold -= value;
    }
}

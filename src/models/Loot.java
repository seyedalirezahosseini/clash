package models;

public class Loot extends Resource{
    private int score;

    public Loot(int gold, int elixir, int score) {
        super(gold , elixir);
        this.score = score;

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addLoot(Loot loot) {
        score += loot.getScore();
        this.add(loot.getElixir(), loot.getGold());
    }
}

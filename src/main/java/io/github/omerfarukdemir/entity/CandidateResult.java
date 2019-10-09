package io.github.omerfarukdemir.entity;

public abstract class CandidateResult {

    private final String name;
    private final int vote;
    private final double percentage;

    public CandidateResult(String name, int vote, int totalVote) {
        this.name = name;
        this.vote = vote;
        this.percentage = ((double) vote / totalVote) * 100;
    }

    public abstract void select();
    public abstract int getSeat();

    public String getName() {
        return name;
    }

    public int getVote() {
        return vote;
    }

    public double getPercentage() {
        return percentage;
    }

}

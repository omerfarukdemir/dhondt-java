package io.github.omerfarukdemir.entity;

public abstract class Candidate {

    private final String name;
    private final int vote;

    Candidate(String name, int vote) {
        this.name = name;
        this.vote = vote;
    }

    public String getName() {
        return name;
    }

    public int getVote() {
        return vote;
    }

}

package io.github.omerfarukdemir.entity;

public class PoliticalPartyResult extends CandidateResult {

    private int seat = 0;

    public PoliticalPartyResult(String name, int vote, int totalVote) {
        super(name, vote, totalVote);
    }

    @Override
    public void select() {
        seat++;
    }

    @Override
    public int getSeat() {
        return seat;
    }

}

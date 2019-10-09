package io.github.omerfarukdemir.entity;

public class IndependentPoliticianResult extends CandidateResult {

    private boolean elected = false;

    public IndependentPoliticianResult(String name, int vote, int totalVote) {
        super(name, vote, totalVote);
    }

    @Override
    public void select() {
        if (elected) {
            throw new IllegalStateException();
        }

        this.elected = true;
    }

    @Override
    public int getSeat() {
        return elected ? 1 : 0;
    }

    public boolean isElected() {
        return elected;
    }

}

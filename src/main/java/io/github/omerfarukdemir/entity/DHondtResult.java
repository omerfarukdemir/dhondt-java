package io.github.omerfarukdemir.entity;

import java.util.Collection;

public class DHondtResult {

    private final Collection<CandidateResult> candidateResults;
    private final int totalVote;
    private final int totalSeat;

    public DHondtResult(Collection<CandidateResult> candidateResults, int totalVote, int totalSeat) {
        this.candidateResults = candidateResults;
        this.totalVote = totalVote;
        this.totalSeat = totalSeat;
    }

    public Collection<CandidateResult> getCandidateResults() {
        return candidateResults;
    }

    public int getTotalVote() {
        return totalVote;
    }

    public int getTotalSeat() {
        return totalSeat;
    }

}

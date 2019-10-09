package io.github.omerfarukdemir;

public interface Threshold {

    boolean filter(int totalSeat, int totalVote, Class candidateType, int candidateVote);

}

package io.github.omerfarukdemir;

import io.github.omerfarukdemir.entity.*;

import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DHondt {

    private final List<Candidate> candidates;
    private final int seat;
    private final Threshold threshold;
    private final VoteDivider voteDivider;
    private final int totalVote;

    public DHondt(List<Candidate> candidates, int seat, Threshold threshold, VoteDivider voteDivider) {
        this.candidates = candidates;
        this.seat = seat;
        this.threshold = threshold;
        this.voteDivider = voteDivider;
        this.totalVote = candidates.stream()
                .map(Candidate::getVote)
                .reduce(Integer::sum)
                .orElse(0);
    }

    public DHondtResult apply() {
        Map<String, CandidateResult> results = candidates.stream()
                .collect(Collectors.toMap(Candidate::getName, candidate -> candidate instanceof PoliticalParty ?
                        new PoliticalPartyResult(candidate.getName(), candidate.getVote(), totalVote) :
                        new IndependentPoliticianResult(candidate.getName(), candidate.getVote(), totalVote)));

        List<Candidate> selectableCandidates = candidates.stream()
                .filter(candidate -> threshold.filter(seat, totalVote, candidate.getClass(), candidate.getVote()))
                .collect(Collectors.toList());

        IntStream.range(0, seat).mapToObj(i -> selectableCandidates
                .stream()
                .map(candidate -> new SimpleEntry<>(candidate.getName(), (double) candidate.getVote()))
                .filter(entry -> {
                    CandidateResult result = results.get(entry.getKey());
                    return !(result instanceof IndependentPoliticianResult && ((IndependentPoliticianResult) result).isElected());
                })
                .peek(entry -> {
                    int currentSeat = results.get(entry.getKey()).getSeat();
                    entry.setValue(entry.getValue() / voteDivider.next(currentSeat).doubleValue());
                })
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(IllegalStateException::new))
                .forEach(candidate -> results.get(candidate).select());

        return new DHondtResult(results.values(), totalVote, seat);
    }

}

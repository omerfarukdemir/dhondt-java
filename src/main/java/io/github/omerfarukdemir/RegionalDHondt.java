package io.github.omerfarukdemir;

import io.github.omerfarukdemir.entity.Candidate;
import io.github.omerfarukdemir.entity.DHondtResult;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RegionalDHondt {

    private final Map<String, List<Candidate>> regionCandidatesMapping;
    private final Map<String, Integer> regionSeatMapping;
    private final Threshold regionalThreshold;
    private final Threshold threshold;
    private final VoteDivider voteDivider;

    public RegionalDHondt(Map<String, List<Candidate>> regionCandidatesMapping,
                          Map<String, Integer> regionSeatMapping,
                          Threshold regionalThreshold,
                          Threshold threshold,
                          VoteDivider voteDivider) {
        this.regionCandidatesMapping = regionCandidatesMapping;
        this.regionSeatMapping = regionSeatMapping;
        this.regionalThreshold = regionalThreshold;
        this.threshold = threshold;
        this.voteDivider = voteDivider;
    }

    public Map<String, DHondtResult> apply() {
        Map<String, Integer> candidateVoteMapping = regionCandidatesMapping.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Candidate::getName, Candidate::getVote, Integer::sum));

        int totalVote = candidateVoteMapping.values().stream().reduce(Integer::sum).orElse(0);
        int totalSeat = regionSeatMapping.values().stream().reduce(Integer::sum).orElse(0);

        return regionCandidatesMapping.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> {
                    List<Candidate> selectableCandidates = entry.getValue().stream()
                            .filter(candidate -> regionalThreshold.filter(totalSeat, totalVote, candidate.getClass(), candidateVoteMapping.get(candidate.getName())))
                            .collect(Collectors.toList());

                    return new DHondt(selectableCandidates, regionSeatMapping.get(entry.getKey()), threshold, voteDivider).apply();
                }));
    }

}

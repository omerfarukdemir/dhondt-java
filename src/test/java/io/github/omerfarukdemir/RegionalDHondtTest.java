package io.github.omerfarukdemir;

import io.github.omerfarukdemir.entity.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class RegionalDHondtTest {

    @Test
    public void test() {
        List<Candidate> candidates = new ArrayList<>();

        candidates.add(new PoliticalParty("A", 900));
        candidates.add(new IndependentPolitician("B", 600));
        candidates.add(new PoliticalParty("C", 500));
        candidates.add(new PoliticalParty("D", 300));
        candidates.add(new IndependentPolitician("E", 200));
        candidates.add(new PoliticalParty("F", 100));
        candidates.add(new PoliticalParty("G", 10));

        Threshold regionalThreshold = (a, b, c, d) -> true;
        Threshold threshold = (a, b, c, d) -> true;

        // 1, 2, 3, 4 ...
        VoteDivider voteDivider = iteration -> iteration + 1;

        Map<String, List<Candidate>> regionCandidatesMapping = Collections.singletonMap("Bilecik", candidates);
        Map<String, Integer> regionSeatMapping = Collections.singletonMap("Bilecik", 45);

        Map<String, DHondtResult> regionalResult = new RegionalDHondt(regionCandidatesMapping, regionSeatMapping, regionalThreshold, threshold, voteDivider).apply();

        DHondtResult result = regionalResult.get("Bilecik");

        Assert.assertEquals(result.getTotalSeat(), 45);
        Assert.assertEquals(result.getTotalVote(), 2610);

        Iterator<CandidateResult> resultIterator = result.getCandidateResults().iterator();

        PoliticalPartyResult resultA = (PoliticalPartyResult) resultIterator.next();

        Assert.assertEquals(resultA.getName(), "A");
        Assert.assertEquals(resultA.getVote(), 900);
        Assert.assertEquals(resultA.getPercentage(), 34.48, 0.01);
        Assert.assertEquals(resultA.getSeat(), 22);

        IndependentPoliticianResult resultB = (IndependentPoliticianResult) resultIterator.next();

        Assert.assertEquals(resultB.getName(), "B");
        Assert.assertEquals(resultB.getVote(), 600);
        Assert.assertEquals(resultB.getPercentage(), 22.98, 0.01);
        Assert.assertEquals(resultB.getSeat(), 1);

        PoliticalPartyResult resultC = (PoliticalPartyResult) resultIterator.next();

        Assert.assertEquals(resultC.getName(), "C");
        Assert.assertEquals(resultC.getVote(), 500);
        Assert.assertEquals(resultC.getPercentage(), 19.15, 0.01);
        Assert.assertEquals(resultC.getSeat(), 12);

        PoliticalPartyResult resultD = (PoliticalPartyResult) resultIterator.next();

        Assert.assertEquals(resultD.getName(), "D");
        Assert.assertEquals(resultD.getVote(), 300);
        Assert.assertEquals(resultD.getPercentage(), 11.49, 0.01);
        Assert.assertEquals(resultD.getSeat(), 7);

        IndependentPoliticianResult resultE = (IndependentPoliticianResult) resultIterator.next();

        Assert.assertEquals(resultE.getName(), "E");
        Assert.assertEquals(resultE.getVote(), 200);
        Assert.assertEquals(resultE.getPercentage(), 7.66, 0.01);
        Assert.assertEquals(resultE.getSeat(), 1);

        PoliticalPartyResult resultF = (PoliticalPartyResult) resultIterator.next();

        Assert.assertEquals(resultF.getName(), "F");
        Assert.assertEquals(resultF.getVote(), 100);
        Assert.assertEquals(resultF.getPercentage(), 3.83, 0.01);
        Assert.assertEquals(resultF.getSeat(), 2);

        PoliticalPartyResult resultG = (PoliticalPartyResult) resultIterator.next();

        Assert.assertEquals(resultG.getName(), "G");
        Assert.assertEquals(resultG.getVote(), 10);
        Assert.assertEquals(resultG.getPercentage(), 0.38, 0.01);
        Assert.assertEquals(resultG.getSeat(), 0);
    }

}

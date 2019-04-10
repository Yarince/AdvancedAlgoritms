import java.util.ArrayList;
import java.util.HashSet;

public class NewIdea {

    private HashSet<Integer> team1 = new HashSet<>();
    private HashSet<Integer> team2 = new HashSet<>();
    private ArrayList<HashSet<Integer>> sameSets = new ArrayList<>();
    private ArrayList<HashSet<Integer>> notSameSets = new ArrayList<>();

    @SuppressWarnings("Duplicates")
    public void addPair(int delegate1, int delegate2, boolean isSame) {
        if (isSame) {

            HashSet<Integer> teamFor1 = findTeam(delegate1);
            HashSet<Integer> teamFor2 = findTeam(delegate2);

            HashSet<Integer> setFor1 = findSet(delegate1);
            HashSet<Integer> setFor2 = findSet(delegate2);

            if (teamFor1 != null) {
                teamFor1.add(delegate2);
            } else if (teamFor2 != null) {
                teamFor2.add(delegate1);
            } else if (setFor1 != null) {
                setFor1.add(delegate2);
            } else if (setFor2 != null) {
                setFor2.add(delegate1);
            } else {
                HashSet<Integer> newSet = new HashSet<>();
                newSet.add(delegate1);
                newSet.add(delegate2);
                sameSets.add(newSet);
            }
        } else {

            HashSet<Integer> teamFor1 = findOppositeTeam(delegate1);
            HashSet<Integer> teamFor2 = findOppositeTeam(delegate2);
            HashSet<Integer> set1 = findOpositeSet(delegate1);
            HashSet<Integer> set2 = findOpositeSet(delegate2);
            if (teamFor1 != null) {
                teamFor1.add(delegate2);

            } else if (teamFor2 != null) {
                teamFor2.add(delegate1);
            } else if (set1 != null) {
                set1.add(delegate2);
            } else if (set2 != null) {
                set2.add(delegate1);
            } else {
                HashSet<Integer> newSet = new HashSet<>();
                newSet.add(delegate1);
                newSet.add(delegate2);
                notSameSets.add(newSet);
            }
        }
        resolveSets(delegate1, delegate2, isSame);
    }

    private void resolveSets(int delegate1, int delegate2, boolean isSame) {
        HashSet<Integer> teamFor1 = findTeam(delegate1);
        HashSet<Integer> teamFor2 = findTeam(delegate2);
        if (isSame) {
            resolveSet(delegate1, delegate2, teamFor2, teamFor1);
            resolveSet(delegate1, delegate2, teamFor1, teamFor2);
        }else {

            // TODO Everything
            resolveSet(delegate1, delegate2, teamFor2, teamFor1);





        }
    }

    private void resolveSet(int delegate1, int delegate2, HashSet<Integer> teamFor1, HashSet<Integer> teamFor2) {
        if (teamFor1 != null) {
            ArrayList<HashSet<Integer>> allSets = findAllSets(delegate1);
            for (HashSet<Integer> setFor1 : allSets) {

                teamFor1.addAll(setFor1);
                sameSets.remove(setFor1);
            }
        }

        if (teamFor2 != null) {
            for (HashSet<Integer> setFor2 : findAllSets(delegate2)) {
                teamFor2.addAll(setFor2);
                sameSets.remove(setFor2);
            }
        }
    }

    private HashSet<Integer> findTeam(int delegate) {

        if (team1.contains(delegate)) {
            return team1;
        } else if (team2.contains(delegate))
            return team2;
        else return null;
    }

    private HashSet<Integer> findOppositeTeam(int delegate) {

        if (team1.contains(delegate)) {
            return team2;
        } else if (team2.contains(delegate))
            return team1;
        else return null;
    }

    // TODO find a oposite set
    // TODO match not same sets if a team is linked
    private HashSet<Integer> findOpositeSet(int delegate) {


        HashSet<Integer> set = null;
        for (HashSet<Integer> sameSet : sameSets) {
            if (sameSet.contains(delegate))
                set = sameSet;
        }
        if (set != null) {
            for (Integer integer : set) {


            }
        }

        return null;
    }

    private HashSet<Integer> findSet(int delegate) {

        for (HashSet<Integer> set : sameSets) {
            if (set.contains(delegate))
                return set;
        }
        return null;
    }

    private ArrayList<HashSet<Integer>> findAllSets(int delegate) {
        ArrayList<HashSet<Integer>> result = new ArrayList<>();

        for (HashSet<Integer> set : sameSets) {
            if (set.contains(delegate))
                result.add(set);
        }
        return result;
    }
}
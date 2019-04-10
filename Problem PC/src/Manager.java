import java.util.HashSet;

public class Manager {

    private HashSet<Pair> team1 = new HashSet<>();
    private HashSet<Pair> team2 = new HashSet<>();
    private HashSet<Pair> samePairs = new HashSet<>();
    private HashSet<Pair> oppositePairs = new HashSet<>();

    public void addPair(Pair pair, boolean isSame) {

        if (isSame) {
            // First set will become team 1
            if (team1.isEmpty() && team2.isEmpty() && samePairs.isEmpty()) {
                team1.add(pair);
            } else if (!team1.contains(pair) && isInTeam(team1, pair)) {
                team1.add(pair);
            } else if (!team2.contains(pair) && isInTeam(team2, pair)) {
                team2.add(pair);
            } else {
                samePairs.add(pair);
            }

            resolveFoundSamePairs();
        } else {
            // Add a ordering to reduce sorting (hopefully)
            int delegate1 = pair.getLeft();
            int delegate2 = pair.getRight();
            if (delegate1 < delegate2) {
                // If delegate 1 is in team 1 then delegate 2 must be added to team 2
                checkOppositeTeam(delegate1, delegate2);
            } else {
                // If delegate 2 is in team 1 then delegate 1 must be added to team 2
                checkOppositeTeam(delegate2, delegate1);
            }
            resolveFoundOppositePairs();
        }
    }

    private void checkOppositeTeam(int delegate1, int delegate2) {
        if (isInTeam(team1, delegate1)) {
            int existingDelegate = team2.iterator().next().getLeft();
            team2.add(new Pair(existingDelegate, delegate2));
        }
        // If delegate 1 is in team 2 then delegate 2 must be added to team 1
        else if (isInTeam(team2, delegate1)) {
            int existingDelegate = team1.iterator().next().getLeft();
            team1.add(new Pair(existingDelegate, delegate2));
        }
        // If delegate 2 is in team 1 then delegate 1 must be added to team 2
        else if (isInTeam(team1, delegate2)) {
            int existingDelegate = team2.iterator().next().getLeft();
            team2.add(new Pair(existingDelegate, delegate1));
        }
        // If delegate 2 is in team 2 then delegate 1 must be added to team 1
        else if (isInTeam(team2, delegate2)) {
            int existingDelegate = team1.iterator().next().getLeft();
            team1.add(new Pair(existingDelegate, delegate1));
        } else {
            // Not found so it will be saved
            oppositePairs.add(new Pair(delegate1, delegate2));
        }
    }

    public boolean isInTeam(HashSet<Pair> team, Pair pair) {
        boolean isPart = false;

        for (Pair pair1 : team) {
            if (isPart)
                break;
            isPart = pair1.partOfPair(pair.getLeft());

            if (!isPart) {
                isPart = pair1.partOfPair(pair.getRight());
            }
        }
        return isPart;
    }

    public boolean isUnknown(Pair pair) {
        return samePairs.contains(pair) || oppositePairs.contains(pair) ||
                !isInTeam(team1, pair) || !isInTeam(team2, pair);
    }

    public boolean isContradicting(Pair pair, boolean isSame) {
        if (isSame) {
            if (oppositePairs.contains(pair)) {
                return true;
            } else return (isInTeam(team1, pair.getLeft()) && isInTeam(team2, pair.getRight())) ||
                    (isInTeam(team2, pair.getLeft()) && isInTeam(team1, pair.getRight()));
        } else {
            if (samePairs.contains(pair))
                return true;
            else return (isInTeam(team1, pair) || isInTeam(team2, pair));
        }
    }


    private boolean isInTeam(HashSet<Pair> team, int delegate) {
        boolean isPart = false;

        for (Pair pair1 : team) {
            if (isPart)
                break;
            isPart = pair1.partOfPair(delegate);
        }
        return isPart;
    }

    private void resolveFoundSamePairs() {
        for (Pair pair : samePairs) {
            if (!team1.contains(pair) && isInTeam(team1, pair)) {
                team1.add(pair);
                samePairs.remove(pair);
            } else if (!team2.contains(pair) && isInTeam(team2, pair)) {
                team2.add(pair);
                samePairs.remove(pair);
            }
        }
    }

    // TODO Make this like way way way more efficient
    private void resolveFoundOppositePairs() {
        for (Pair oppositePair : oppositePairs) {

            int delegate1 = oppositePair.getLeft();
            int delegate2 = oppositePair.getRight();

            if (isInTeam(team1, delegate1)) {
                int existingDelegate = team2.iterator().next().getLeft();
                team2.add(new Pair(existingDelegate, delegate2));
                oppositePairs.remove(oppositePair);
            }
            // If delegate 1 is in team 2 then delegate 2 must be added to team 1
            else if (isInTeam(team2, delegate1)) {
                int existingDelegate = team1.iterator().next().getLeft();
                team1.add(new Pair(existingDelegate, delegate2));
                oppositePairs.remove(oppositePair);
            }
            // If delegate 2 is in team 1 then delegate 1 must be added to team 2
            else if (isInTeam(team1, delegate2)) {
                int existingDelegate = team2.iterator().next().getLeft();
                team2.add(new Pair(existingDelegate, delegate1));
                oppositePairs.remove(oppositePair);
            }
            // If delegate 2 is in team 2 then delegate 1 must be added to team 1
            else if (isInTeam(team2, delegate2)) {
                int existingDelegate = team1.iterator().next().getLeft();
                team1.add(new Pair(existingDelegate, delegate1));
                oppositePairs.remove(oppositePair);
            }
        }
    }

    public boolean isInSameTeam(Pair pair) {
// TODO Check if they are in the same team. DUHhh

        return false;
    }
}

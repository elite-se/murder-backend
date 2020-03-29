package de.marvinbrieger.toothbrushgame.services;

import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.domain.KillAssignment;
import de.marvinbrieger.toothbrushgame.domain.KillAssignmentStatus;
import de.marvinbrieger.toothbrushgame.domain.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AssignmentGeneratorService {

    private int[] initalizeIntArray(int length) {
        int[] arr = new int[length];
        for (int k = 0; k < length; k++)
            arr[k] = k;

        return arr;
    }

    private void swap(int[] arr, int first, int second) {
        int temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;
    }

    /**
     * Generates a random cycle of the given length.
     *
     * The implemented algorithm is called sattolo's algorithm. It is a variant of
     * the Fisher-Yates-Algorithm for generating random permutations that generates
     * a permutation with exactly one cycle.
     *
     * @param length
     * @return
     */
    private int[] generatRandomCycle(int length) {
        Random random = new Random();
        int[] arr = initalizeIntArray(length);

        for (int k = length - 1; k >= 1; k--) {
            int t = random.nextInt(k);
            swap(arr, k, t);
        }

        return arr;
    }

    /**
     * Returns random kill assignments for the given game.
     *
     * @param game
     * @return
     */
    public List<KillAssignment> generateKillAssignments(Game game) {
        List<Player> players = game.getPlayers();
        List<KillAssignment> assignments = new ArrayList();
        int[] randomCycle = generatRandomCycle(players.size());

        for (int k = 0; k < players.size(); k++)
            assignments.add(new KillAssignment(null, game, players.get(k), players.get(randomCycle[k]), KillAssignmentStatus.PENDING, null));

        return assignments;
    }

    private KillAssignment findSuccessor(List<KillAssignment> assignments, KillAssignment source) {
        for (KillAssignment potentialSuccessor : assignments)
            if (source.hasSuccessor(potentialSuccessor))
                return potentialSuccessor;

        throw new IllegalArgumentException();
    }

    public void linkKillAssignments(List<KillAssignment> assignments) {
        for (KillAssignment source : assignments)
            source.setSuccessor(findSuccessor(assignments, source));
    }

}

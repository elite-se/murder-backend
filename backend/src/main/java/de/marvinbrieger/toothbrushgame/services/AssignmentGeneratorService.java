package de.marvinbrieger.toothbrushgame.services;

import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.domain.MurderAssignment;
import de.marvinbrieger.toothbrushgame.domain.MurderAssignmentStatus;
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
    public List<MurderAssignment> generateKillAssignments(Game game) {
        List<Player> players = game.getPlayers();
        List<MurderAssignment> assignments = new ArrayList();
        int[] randomCycle = generatRandomCycle(players.size());

        for (int k = 0; k < players.size(); k++)
            assignments.add(new MurderAssignment(null, game, players.get(k), players.get(randomCycle[k]), MurderAssignmentStatus.PENDING, null));

        return assignments;
    }

}

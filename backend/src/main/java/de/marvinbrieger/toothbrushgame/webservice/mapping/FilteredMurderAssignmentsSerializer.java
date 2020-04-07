package de.marvinbrieger.toothbrushgame.webservice.mapping;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import de.marvinbrieger.toothbrushgame.domain.ApplicationUser;
import de.marvinbrieger.toothbrushgame.domain.MurderAssignment;
import de.marvinbrieger.toothbrushgame.domain.MurderAssignmentStatus;
import de.marvinbrieger.toothbrushgame.services.exceptions.UserNotFoundException;
import de.marvinbrieger.toothbrushgame.services.interfaces.CurrentUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serializer that filters the assignments before serializing them.
 *
 * Assignments will only be serialized if they belong to the current user or if they are already fulfilled.
 */
@Component
@AllArgsConstructor
public class FilteredMurderAssignmentsSerializer extends JsonSerializer<List<MurderAssignment>> {
    private CurrentUserService currentUserService;

    @Override
    public void serialize(List<MurderAssignment> assignments, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // fetch current user
        ApplicationUser currentUser;
        try {
            currentUser = currentUserService.getCurrentUser();
        } catch (UserNotFoundException e) {
            currentUser = null;
        }

        // filter away assignments that neither belong to the current user nor are fulfilled
        final var currentUserFinal = currentUser;
        List<MurderAssignment> filteredAssignments = assignments.parallelStream()
                .filter(ass -> ass.getKiller().getUser().equals(currentUserFinal) ||
                        ass.getAssignmentStatus() == MurderAssignmentStatus.FULFILLED)
                .collect(Collectors.toList());

        // serialize the filtered assignments
        serializers.defaultSerializeValue(filteredAssignments, gen);
    }
}

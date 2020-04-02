package de.marvinbrieger.toothbrushgame.persistence;

import de.marvinbrieger.toothbrushgame.domain.MurderAssignment;
import de.marvinbrieger.toothbrushgame.domain.MurderAssignmentStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MurderAssignmentRepository extends JpaRepository<MurderAssignment, Long> {

    Optional<MurderAssignment> findByIdAndAssignmentStatus(Long id,
            MurderAssignmentStatus murderAssignmentStatus);

}

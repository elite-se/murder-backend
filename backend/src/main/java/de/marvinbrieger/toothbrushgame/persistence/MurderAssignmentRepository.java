package de.marvinbrieger.toothbrushgame.persistence;

import de.marvinbrieger.toothbrushgame.domain.MurderAssignment;
import de.marvinbrieger.toothbrushgame.domain.MurderAssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MurderAssignmentRepository extends JpaRepository<MurderAssignment, Long> {

    Optional<MurderAssignment> findByIdAndAssignmentStatus(Long id, MurderAssignmentStatus murderAssignmentStatus);

}

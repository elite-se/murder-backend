package de.marvinbrieger.toothbrushgame.controller;

import de.marvinbrieger.toothbrushgame.domain.Murder;
import de.marvinbrieger.toothbrushgame.services.interfaces.MurderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MurderController {

    private final MurderService murderService;

    public MurderController(MurderService murderService) {
        this.murderService = murderService;
    }

    @PutMapping("/games/{gameId}/murderAssignments/{assignmentId}/murder")
    Murder commitMurder(@PathVariable Long gameId, @PathVariable Long assignmentId) {
        return murderService.commitMurder(gameId, assignmentId);
    }

}

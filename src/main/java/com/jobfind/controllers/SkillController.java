package com.jobfind.controllers;

import com.jobfind.dto.response.SuccessResponse;
import com.jobfind.models.Skill;
import com.jobfind.services.ISkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skill")
@RequiredArgsConstructor
public class SkillController {
    private final ISkillService skillServiceImpl;

    @GetMapping("/all")
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(skillServiceImpl.getAllSkills());
    }

    @PostMapping("/add")
    public ResponseEntity<SuccessResponse> addSkill(@RequestBody String skillName) {
        skillServiceImpl.addSkill(skillName);
        return ResponseEntity.ok(new SuccessResponse("Skill added successfully"));
    }

    @DeleteMapping("/delete/{skillId}")
    public ResponseEntity<SuccessResponse> deleteSkill(@PathVariable Integer skillId) {
        skillServiceImpl.deleteSkill(skillId);
        return ResponseEntity.ok(new SuccessResponse("Skill deleted successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity<SuccessResponse> updateSkill(
            @RequestParam Integer skillId,
            @RequestParam String skillName
    ) {
        skillServiceImpl.updateSkill(skillId, skillName);
        return ResponseEntity.ok(new SuccessResponse("Skill updated successfully"));
    }
}

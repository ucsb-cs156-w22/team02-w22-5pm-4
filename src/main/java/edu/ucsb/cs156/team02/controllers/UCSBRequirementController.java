package edu.ucsb.cs156.team02.controllers;

import edu.ucsb.cs156.team02.entities.UCSBRequirement;
import edu.ucsb.cs156.team02.repositories.UCSBRequirementRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "UCSBRequirementController")
@RequestMapping("/api/UCSBRequirements")
@RestController
public class UCSBRequirementController extends ApiController {

    // public class UCSBRequirementOrError {
    //     Long id;
    //     UCSBRequirement ucsbRequirement;
    //     // ResponseEntity<String> error;

    //     public UCSBRequirementOrError (Long id) {
    //         this.id = id;
    //     }
    // }

    @Autowired
    UCSBRequirementRepository UCSBRequirementRepository;

    @Autowired
    ObjectMapper mapper;

    @ApiOperation(value = "List all Subreddits")
    @GetMapping("/all")
    public Iterable<UCSBRequirement> index() {
        loggingService.logMethod();
        Iterable<UCSBRequirement> list = UCSBRequirementRepository.findAll();
        return list;
    }

    // @ApiOperation(value = )

    @ApiOperation(value = "Create a new entry")
    @PostMapping("/post")
    public UCSBRequirement createEntry(
            @ApiParam("Requirement Code")        @RequestParam String requirementCode,
            @ApiParam("Requirement Translation") @RequestParam String requirementTranslation,
            @ApiParam("College Code")            @RequestParam String collegeCode,
            @ApiParam("Degree Code")             @RequestParam String objCode,
            @ApiParam("Course Count")            @RequestParam int courseCount,
            @ApiParam("Units")                   @RequestParam String units,
            @ApiParam("Inactive")                @RequestParam boolean inactive) {
        loggingService.logMethod();
        UCSBRequirement todo = new UCSBRequirement();
        todo.setRequirementCode(requirementCode);
        todo.setRequirementTranslation(requirementTranslation);
        todo.setCollegeCode(collegeCode);
        todo.setObjCode(objCode);
        todo.setCourseCount(courseCount);
        todo.setUnits(units);
        todo.setInactive(inactive);
        UCSBRequirement savedSub = UCSBRequirementRepository.save(todo);
        return savedSub;
    }
}
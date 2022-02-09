package edu.ucsb.cs156.team02.controllers;

import edu.ucsb.cs156.team02.entities.UCSBRequirement;
import edu.ucsb.cs156.team02.entities.User;
import edu.ucsb.cs156.team02.models.CurrentUser;
import edu.ucsb.cs156.team02.repositories.UCSBRequirementRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@Api(description = "UCSBRequirementController")
@RequestMapping("/api/UCSBRequirements")
@RestController
@Slf4j
public class UCSBRequirementController extends ApiController {

    public class UCSBRequirementOrError {
        Long id;
        UCSBRequirement UCSBrequirement;
        ResponseEntity<String> error;

        public UCSBRequirementOrError (Long id) {
            this.id = id;
        }
    }

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

    // get for single based on ID
    @ApiOperation(value = "Get a single UCSBRequirement based on id (if it exists)")
    
    @GetMapping ("")
    public ResponseEntity<String> getUCSBRequirementById(
            @ApiParam("id") @RequestParam Long id) throws JsonProcessingException {
        loggingService.logMethod();
        UCSBRequirementOrError roe = new UCSBRequirementOrError(id);

        roe = doesUCSBRequirementExist(roe);
        if (roe.error != null){

            return roe.error;
        }
        String body = mapper.writeValueAsString(roe.UCSBrequirement);
        return ResponseEntity.ok().body(body);
    }

	@ApiOperation(value = "Create a new entry")
    @PostMapping("/post")
    public UCSBRequirement createEntry(
            @ApiParam("Requirement Code")        @RequestParam String requirementCode,
            @ApiParam("Requirement Translation") @RequestParam String requirementTranslation,
            @ApiParam("College Code")            @RequestParam String collegeCode,
            @ApiParam("Degree Code")             @RequestParam String objCode,
            @ApiParam("Course Count")            @RequestParam int courseCount,
            @ApiParam("Units")                   @RequestParam int units,
            @ApiParam("Inactive")                @RequestParam boolean inactive) {
        loggingService.logMethod();
        UCSBRequirement ucsbRequirement = new UCSBRequirement();
        ucsbRequirement.setRequirementCode(requirementCode);
        ucsbRequirement.setRequirementTranslation(requirementTranslation);
        ucsbRequirement.setCollegeCode(collegeCode);
        ucsbRequirement.setObjCode(objCode);
        ucsbRequirement.setCourseCount(courseCount);
        ucsbRequirement.setUnits(units);
        ucsbRequirement.setInactive(inactive);
        UCSBRequirement savedSub = UCSBRequirementRepository.save(ucsbRequirement);
        return savedSub;
    }
    
    // put
    @ApiOperation(value = "Update a single UCSB Requirement")
    @PutMapping("")
    public ResponseEntity<String> putUCSBRequirementById_admin(
        @ApiParam("id") @RequestParam Long id,
        @RequestBody @Valid UCSBRequirement incomingUCSBRequirement) throws JsonProcessingException {
        loggingService.logMethod();

        UCSBRequirementOrError roe = new UCSBRequirementOrError(id);

        roe = doesUCSBRequirementExist(roe);
        if (roe.error != null) {
            return roe.error;
        }

        incomingUCSBRequirement.setId(id);
        UCSBRequirementRepository.save(incomingUCSBRequirement);

        String body = mapper.writeValueAsString(incomingUCSBRequirement);
        return ResponseEntity.ok().body(body);
    }

    // delete
    @ApiOperation(value = "Delete a Requirement by ID")
    @DeleteMapping("")
    public ResponseEntity<String> deleteUCSBRequirement(
        @ApiParam("id") @RequestParam Long id) {
        loggingService.logMethod();

        UCSBRequirementOrError roe = new UCSBRequirementOrError(id);

        roe = doesUCSBRequirementExist(roe);
        if (roe.error != null) {
            return roe.error;
        }

        UCSBRequirementRepository.deleteById(id);
        return ResponseEntity.ok().body(String.format("record %d deleted", id));

    }
    
    public UCSBRequirementOrError doesUCSBRequirementExist(UCSBRequirementOrError roe) {
		Optional<UCSBRequirement> optionalUCSBRequirement = UCSBRequirementRepository.findById(roe.id);
        if (optionalUCSBRequirement.isEmpty()) {
            roe.error = ResponseEntity
                    .badRequest()
                    .body(String.format("UCSBRequirement with id %d not found", roe.id));
        } else {
            roe.UCSBrequirement = optionalUCSBRequirement.get();
        }
        return roe;
	}

package edu.ucsb.cs156.team02.controllers;

import edu.ucsb.cs156.team02.entities.CollegeSubreddit;
import edu.ucsb.cs156.team02.entities.User;
import edu.ucsb.cs156.team02.models.CurrentUser;
import edu.ucsb.cs156.team02.repositories.CollegeSubredditRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

@Api(description = "CollegeSubreddits")
@RequestMapping("/api/collegesubreddits")
@RestController
@Slf4j
public class CollegeSubredditController extends ApiController {
    /**
     * This inner class helps us factor out some code for checking
     * whether college subreddits exist, and whether they belong to the current user,
     * along with the error messages pertaining to those situations. It
     * bundles together the state needed for those checks.
     */
     public class CollegeSubredditOrError {
        Long id;
        CollegeSubreddit collegesubreddit;
        ResponseEntity<String> error;

        public CollegeSubredditOrError(Long id) {
            this.id = id;
        }
    }

    @Autowired
    CollegeSubredditRepository collegeSubredditRepository;

    @Autowired
    ObjectMapper mapper;

    @ApiOperation(value = "List all collegesubreddits")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/all")
    public Iterable<CollegeSubreddit> allUsersCollegeSubreddits() {
        loggingService.logMethod();
        Iterable<CollegeSubreddit> collegesubreddits = collegeSubredditRepository.findAll();
        return collegesubreddits;
    }

    @ApiOperation(value = "Create a new CollegeSubreddit")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/post")
    public CollegeSubreddit postCollegeSubreddit(
            @ApiParam("College Name") @RequestParam String name,
            @ApiParam("College Location") @RequestParam String location,
            @ApiParam("College Subreddit") @RequestParam String subreddit)
    {
        loggingService.logMethod();

        CollegeSubreddit collegesubreddit = new CollegeSubreddit();
        collegesubreddit.setName(name);
        collegesubreddit.setLocation(location);
        collegesubreddit.setSubreddit(subreddit);

        CollegeSubreddit savedCollegeSubreddit = collegeSubredditRepository.save(collegesubreddit);
        return savedCollegeSubreddit;
    }
}
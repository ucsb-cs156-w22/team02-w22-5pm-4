package edu.ucsb.cs156.team02.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import edu.ucsb.cs156.team02.ControllerTestCase;
import edu.ucsb.cs156.team02.entities.UCSBRequirement;
import edu.ucsb.cs156.team02.repositories.UCSBRequirementRepository;
import edu.ucsb.cs156.team02.repositories.UserRepository;
import edu.ucsb.cs156.team02.testconfig.TestConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;


@WebMvcTest(controllers = UCSBRequirementController.class)
@Import(TestConfig.class)

public class UCSBRequirementControllerTests extends ControllerTestCase{
    @MockBean
    UCSBRequirementRepository UCSBRequirementRepository;

    @MockBean
    UserRepository userRepository; 

    //Authorization tests for /api/UCSBRequirements/all

    @Test
    public void api_UCSBRequirement_all__logged_out__returns_403() throws Exception {
        mockMvc.perform(get("/api/UCSBRequirements/all"))
                .andExpect(status().is(403));
    }
    
    // @WithMockUser(roles = { "USER", "ADMIN" })
    // @Test
    // public void api_UCSBRequirement_all__user_logged_in__returns_200() throws Exception {
    //     mockMvc.perform(get("/api/UCSBRequirements/all"))
    //             .andExpect(status().isOk());
    // }

    // // Authorization tests for /api/todos/post

    @Test
    public void api_UCSBRequirement_post__logged_out__returns_403() throws Exception {
        mockMvc.perform(post("/api/UCSBRequirements/post"))
                .andExpect(status().is(403));
    }

    @WithMockUser(roles = { "USER", "ADMIN" })
    @Test
    public void api_UCSBRequirement_post__logged_out__returns_200() throws Exception {
        mockMvc.perform(get("/api/UCSBRequirements/all"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = { "USER", "ADMIN" })
    @Test
    public void api_UCSBRequirement_post() throws Exception{
        // arrange

        UCSBRequirement expectedUCSBRequirement = UCSBRequirement.builder()
            .requirementCode("ReqCode1")
            .requirementTranslation("ReqTrans1")
            .collegeCode("CollegeCode1")
            .objCode("objCode1")
            .courseCount(4)
            .units(4)
            .inactive(true)
            .build();

        when(UCSBRequirementRepository.save(eq(expectedUCSBRequirement))).thenReturn(expectedUCSBRequirement);

        // act
        MvcResult response = mockMvc.perform(
                post("/api/UCSBRequirements/post?collegeCode=CollegeCode1&courseCount=4&inactive=true&objCode=objCode1&requirementCode=ReqCode1&requirementTranslation=ReqTrans1&units=4")
                        .with(csrf()))
                .andExpect(status().isOk()).andReturn();

        // assert
        verify(UCSBRequirementRepository, times(1)).save(expectedUCSBRequirement);
        String expectedJson = mapper.writeValueAsString(expectedUCSBRequirement);
        String responseString = response.getResponse().getContentAsString();
        assertEquals(expectedJson, responseString);
    }

    @WithMockUser(roles = { "USER", "ADMIN" })
    @Test
    public void api_UCSBRequirement_get_all_UCSBRequirements() throws Exception{
        // arrange

        UCSBRequirement UCSBRequirement1 = UCSBRequirement.builder()
            .requirementCode("ReqCode1")
            .requirementTranslation("ReqTrans1")
            .collegeCode("CollegeCode1")
            .objCode("objCode1")
            .courseCount(4)
            .units(4)
            .inactive(true)
            .build();
        UCSBRequirement UCSBRequirement2 = UCSBRequirement.builder()
            .requirementCode("ReqCode2")
            .requirementTranslation("ReqTrans2")
            .collegeCode("CollegeCode2")
            .objCode("objCode2")
            .courseCount(4)
            .units(4)
            .inactive(true)
            .build();
        ArrayList<UCSBRequirement> expectedUCSBRequirements = new ArrayList<>();
        expectedUCSBRequirements.addAll(Arrays.asList(UCSBRequirement1, UCSBRequirement2));
        when(UCSBRequirementRepository.findAll()).thenReturn(expectedUCSBRequirements);

        // act
        MvcResult response = mockMvc.perform(get("/api/UCSBRequirements/all"))
                .andExpect(status().isOk()).andReturn();

        // assert

        verify(UCSBRequirementRepository, times(1)).findAll();
        String expectedJson = mapper.writeValueAsString(expectedUCSBRequirements);
        String responseString = response.getResponse().getContentAsString();
        assertEquals(expectedJson, responseString);
    }

    // @WithMockUser(roles = { "USER","ADMIN" })
    // @Test
    // public void api_UCSBRequirements_search_for_UCSBRequirement_that_exists() throws Exception {

    //     UCSBRequirement UCSBRequirement1 = UCSBRequirement.builder().requirementCode("RequirementCode 1").requirementTranslation("RequirementTranslation 1").collegeCode("CollegeCode 1").objCode("ObjCode 1").courseCount(3).units(4).inactive(true).build();
    //     when(UCSBRequirementRepository.findById(eq(1L))).thenReturn(Optional.of(UCSBRequirement1));

    //     // act
    //     MvcResult response = mockMvc.perform(get("/api/UCSBRequirements/getbyid?id=0"))
    //             .andExpect(status().isOk()).andReturn();

    //     // assert

    //     verify(UCSBRequirementRepository, times(1)).findById(eq(1L));
    //     String expectedJson = mapper.writeValueAsString(UCSBRequirement1);
    //     String responseString = response.getResponse().getContentAsString();
    //     assertEquals(expectedJson, responseString);
    // }
    
}


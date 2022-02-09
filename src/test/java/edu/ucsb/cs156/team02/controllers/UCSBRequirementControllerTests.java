package edu.ucsb.cs156.team02.controllers;

import edu.ucsb.cs156.team02.repositories.UserRepository;
import edu.ucsb.cs156.team02.testconfig.TestConfig;
import edu.ucsb.cs156.team02.ControllerTestCase;
import edu.ucsb.cs156.team02.entities.UCSBRequirement;
import edu.ucsb.cs156.team02.entities.User;
import edu.ucsb.cs156.team02.repositories.UCSBRequirementRepository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


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
    
    @WithMockUser(roles = { "USER", "ADMIN" })
    @Test
    public void api_UCSBRequirement_all__user_logged_in__returns_200() throws Exception {
        mockMvc.perform(get("/api/UCSBRequirements/all"))
                .andExpect(status().isOk());
    }

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

    // post test
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

    // get all test
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

    // check exists test
    @WithMockUser(roles = {"USER"})
    @Test
    public void api_UCSBRequirements_search_for_UCSBRequirement_that_exists() throws Exception {

        UCSBRequirement UCSBRequirement1 = UCSBRequirement.builder().requirementCode("RequirementCode 1").requirementTranslation("RequirementTranslation 1").collegeCode("CollegeCode 1").objCode("ObjCode 1").courseCount(3).units(4).inactive(true).build();
        when(UCSBRequirementRepository.findById(eq(1L))).thenReturn(Optional.of(UCSBRequirement1));

        // act
        MvcResult response = mockMvc.perform(get("/api/UCSBRequirements?id=1"))
                .andExpect(status().isOk()).andReturn();

        // assert

        verify(UCSBRequirementRepository, times(1)).findById(eq(1L));
        String expectedJson = mapper.writeValueAsString(UCSBRequirement1);
        String responseString = response.getResponse().getContentAsString();
        assertEquals(expectedJson, responseString);
    }

    // does not exist test
    @WithMockUser(roles = { "USER" })
    @Test
    public void api_ucsbRequirement__user_logged_in__search_for_ucsbRequirement_that_does_not_exist()
            throws Exception {



        when(UCSBRequirementRepository.findById(eq(7L))).thenReturn(Optional.empty());


        MvcResult response = mockMvc.perform(get("/api/UCSBRequirements?id=7"))
                .andExpect(status().isBadRequest()).andReturn();



        verify(UCSBRequirementRepository, times(1)).findById(eq(7L));
        String responseString = response.getResponse().getContentAsString();
        assertEquals("id 7 not found", responseString);
    }

    @Test
    public void api_requirement__delete_requirement() throws Exception {

    // arrange

    UCSBRequirement req1 = UCSBRequirement.builder()
    .requirementCode("subjectCode")
    .requirementTranslation("subjectTranslation")
    .collegeCode("collegeCode")
    .objCode("collegeCode")
    .courseCount(4)
    .units(4)
    .inactive(false)
    .id(123L).build();

    when(UCSBRequirementRepository.findById(eq(123L))).thenReturn(Optional.of(req1));

    // act
    MvcResult response = mockMvc.perform(
        delete("/api/UCSBRequirements?id=123")
                .with(csrf()))
        .andExpect(status().isOk()).andReturn();

    // assert

    verify(UCSBRequirementRepository, times(1)).findById(123L);
    verify(UCSBRequirementRepository, times(1)).deleteById(123L);
    String responseString = response.getResponse().getContentAsString();
    assertEquals("record 123 deleted", responseString);
    }

    @Test
    public void api_requirement__delete_requirement_that_does_not_exist() throws Exception {

    // arrange

    when(UCSBRequirementRepository.findById(eq(123L))).thenReturn(Optional.empty());

    MvcResult response = mockMvc.perform(
    delete("/api/UCSBRequirements?id=123")
    .with(csrf()))
    .andExpect(status().isBadRequest()).andReturn();

    // assert

    verify(UCSBRequirementRepository, times(1)).findById(123L);
    String responseString = response.getResponse().getContentAsString();
    assertEquals("record 123 not found", responseString);
    }

    @WithMockUser(roles = { "USER","ADMIN" })
    @Test
    public void api_requirement__user_logged_in__put_requirement() throws Exception {
        // arrange


        UCSBRequirement UCSBRequirement7 = UCSBRequirement.builder().requirementCode("UCSBRequirement Code 7")
                .requirementTranslation("UCSBRequirement Translation 7").collegeCode("UCSBRequirement collegeCode 7").objCode("UCSBRequirement objCode 7").courseCount(4).units(4).inactive(false).id(7L).build();

        UCSBRequirement updatedUCSBRequirement = UCSBRequirement.builder().requirementCode("New UCSBRequirement Code 7")
                .requirementTranslation("New UCSBRequirement Translation 7").collegeCode("New UCSBRequirement collegeCode 7").objCode("New UCSBRequirement objCode 7").courseCount(4).units(4).inactive(false).id(7L).build();

        UCSBRequirement correctUCSBRequirement = UCSBRequirement.builder().requirementCode("New UCSBRequirement Code 7")
                .requirementTranslation("New UCSBRequirement Translation 7").collegeCode("New UCSBRequirement collegeCode 7").objCode("New UCSBRequirement objCode 7").courseCount(4).units(4).inactive(false).id(7L).build();

        String requestBody = mapper.writeValueAsString(updatedUCSBRequirement);
        String expectedReturn = mapper.writeValueAsString(correctUCSBRequirement);

        when(UCSBRequirementRepository.findById(eq(7L))).thenReturn(Optional.of(UCSBRequirement7));

        // act
        MvcResult response = mockMvc.perform(
                put("/api/UCSBRequirements?id=7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(status().isOk()).andReturn();

        // assert
        verify(UCSBRequirementRepository, times(1)).findById(7L);
        verify(UCSBRequirementRepository, times(1)).save(correctUCSBRequirement); // should be saved with correct user
        String responseString = response.getResponse().getContentAsString();
        assertEquals(expectedReturn, responseString);
    }

    @WithMockUser(roles = { "ADMIN" })
    @Test
    public void api_requirement__user_logged_in__cannot_put_requirement_that_does_not_exist() throws Exception {
        // arrange

        UCSBRequirement updatedUCSBRequirement = UCSBRequirement.builder().requirementCode("UCSBRequirement Code 7")
        .requirementTranslation("UCSBRequirement Translation 7").collegeCode("UCSBRequirement collegeCode 7").objCode("UCSBRequirement objCode 7").courseCount(4).units(4).inactive(false).id(7L).build();

        String requestBody = mapper.writeValueAsString(updatedUCSBRequirement);

        when(UCSBRequirementRepository.findById(eq(67L))).thenReturn(Optional.empty());

        // act
        MvcResult response = mockMvc.perform(
                put("/api/UCSBRequirements?id=7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(status().isBadRequest()).andReturn();

        // assert
        verify(UCSBRequirementRepository, times(1)).findById(7L);
        String responseString = response.getResponse().getContentAsString();
        assertEquals("id 7 not found", responseString);
    }
    
}


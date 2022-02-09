package edu.ucsb.cs156.team02.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

import edu.ucsb.cs156.team02.ControllerTestCase;
import edu.ucsb.cs156.team02.repositories.UCSBRequirementRepository;
import edu.ucsb.cs156.team02.repositories.UserRepository;
import edu.ucsb.cs156.team02.testconfig.TestConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = UCSBRequirementController.class)
@Import(TestConfig.class)

public class UCSBRequirementControllerTests extends ControllerTestCase{
    @MockBean
    UCSBRequirementRepository UCSBRequirementRepository;

    @MockBean
    UserRepository userRepository; 

    // Authorization tests for /api/UCSBRequirements/all

    @Test
    public void api_UCSBRequirement_all__logged_out__returns_403() throws Exception {
        mockMvc.perform(get("/api/UCSBRequirements/all"))
                .andExpect(status().is(403));
    }
    

    @WithMockUser(roles = { "USER" })
    @Test
    public void api_UCSBRequirement_all__user_logged_in__returns_200() throws Exception {
        mockMvc.perform(get("/api/UCSBRequirements/all"))
                .andExpect(status().isOk());
    }

    // Authorization tests for /api/todos/post

    @Test
    public void api_UCSBRequirement_post__logged_out__returns_403() throws Exception {
        mockMvc.perform(post("/api/UCSBRequirements/post"))
                .andExpect(status().is(403));
    }

    @WithMockUser(roles = { "USER" })
    @Test
    public void api_UCSBRequirement_post__logged_out__returns_200() throws Exception {
        mockMvc.perform(get("/api/UCSBRequirements/all"))
                .andExpect(status().isOk());
    }


}

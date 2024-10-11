package swin.swe4006.c6g1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import swin.swe4006.c6g1.dto.StaffDto;
import swin.swe4006.c6g1.entity.Staff;
import swin.swe4006.c6g1.exception.AppEntityNotFound;
import swin.swe4006.c6g1.service.StaffService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StaffController.class)
class StaffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StaffService staffService; // This mocks StaffService

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testGetAllStaffs() throws Exception {
        List<Staff> staffList = Arrays.asList(
                new Staff(1L, "John Doe", 30),
                new Staff(2L, "Jane Doe", 25)
        );

        when(staffService.findAll()).thenReturn(staffList); // Mocking service response

        mockMvc.perform(get("/staff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfully retrieved all staffs"))
                .andExpect(jsonPath("$.data[0].name").value("John Doe"))
                .andExpect(jsonPath("$.data[1].name").value("Jane Doe"));
    }

    @Test
    void testCreateStaff() throws Exception {
        StaffDto staffDto = new StaffDto();
        staffDto.setAge(30);
        staffDto.setName("John Doe");
        Staff staff = new Staff(1L, "John Doe", 30);

        when(staffService.save(any(StaffDto.class))).thenReturn(staff);

        mockMvc.perform(post("/staff")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(staffDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Successfully create a new staff"))
                .andExpect(jsonPath("$.data.name").value("John Doe"));
    }

    @Test
    void testDeleteStaffWithWrongId() throws Exception {
        doThrow(new AppEntityNotFound("Entity not found"))
                .when(staffService)
                .delete(1L);

        mockMvc.perform(delete("/staff/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Entity not found"));
    }

    @Test
    void testDeleteStaff() throws Exception {
        doNothing().when(staffService).delete(1L);

        mockMvc.perform(delete("/staff/1"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.message").value("Successfully deleted staff"));
    }

    @Test
    void testUpdateStaff() throws Exception {
        StaffDto staffDto = new StaffDto();
        staffDto.setAge(30);
        staffDto.setName("John Doe");
        Staff updatedStaff = new Staff(1L, "John Doe", 30);

        when(staffService.updateById(1L, staffDto)).thenReturn(updatedStaff);

        mockMvc.perform(put("/staff/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(staffDto)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.message").value("Successfully update a new staff"))
                .andExpect(jsonPath("$.data.name").value("John Doe"));
    }
}

package swin.swe4006.c6g1.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import swin.swe4006.c6g1.dto.ResponseRestDto;
import swin.swe4006.c6g1.dto.StaffDto;
import swin.swe4006.c6g1.entity.Staff;
import swin.swe4006.c6g1.service.StaffService;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/staff")
public class StaffController {
    private final StaffService staffService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseRestDto getAll(HttpServletRequest httpServlet) {
        log.info("Retrieve request to get all staffs with ip: {}", httpServlet.getRemoteAddr());
        List<Staff> all = staffService.findAll();
        return ResponseRestDto.builder().message("Successfully retrieved all staffs").data(all).build();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseRestDto deleteById(HttpServletRequest httpServletRequest, @PathVariable Long id) {
        log.info("Retrieve request to delete staff with ip: {}", httpServletRequest.getRemoteAddr());
        staffService.delete(id);
        return ResponseRestDto.builder().message("Successfully deleted staff").build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseRestDto createStaff(HttpServletRequest httpServlet, @Valid @RequestBody StaffDto dto) {
        log.info("Retrieve request to create a staff with ip: {}", httpServlet.getRemoteAddr());
        Staff staff = staffService.save(dto);
        return ResponseRestDto.builder().message("Successfully create a new staff").data(staff).build();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseRestDto updateById(HttpServletRequest httpServletRequest, @PathVariable Long id, @Valid @RequestBody StaffDto dto) {
        log.info("Retrieve request to update staff with ip: {}", httpServletRequest.getRemoteAddr());
        Staff staff = staffService.updateById(id, dto);
        return ResponseRestDto.builder().message("Successfully update a new staff").data(staff).build();
    }
}

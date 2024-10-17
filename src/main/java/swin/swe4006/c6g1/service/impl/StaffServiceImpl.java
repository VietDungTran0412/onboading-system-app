package swin.swe4006.c6g1.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swin.swe4006.c6g1.dto.StaffDto;
import swin.swe4006.c6g1.entity.Staff;
import swin.swe4006.c6g1.exception.AppEntityNotFound;
import swin.swe4006.c6g1.repository.StaffRepository;
import swin.swe4006.c6g1.service.StaffService;

import java.util.Optional;

/*
* Staff service handles Staff-business relevant operation*/
@Service
@Slf4j
public class StaffServiceImpl extends BaseServiceImpl<Staff, Long, StaffRepository> implements StaffService {
    protected StaffServiceImpl(StaffRepository repository) {
        super(repository);
    }
    private Staff mapStaff( StaffDto dto) {
        Staff staff = new Staff();
        staff.setAge(dto.getAge());
        staff.setName(dto.getName());
        staff.setAddress(dto.getAddress());
        staff.setRole(dto.getRole());
        staff.setEmail(dto.getEmail());
        return staff;
    }
    @Override   
    @Transactional(rollbackFor = Exception.class)
    public Staff save(StaffDto dto) {
        Staff staff = mapStaff(dto);
        log.info("Created new staff -- StaffServiceImpl");
        return save(staff);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Optional<Staff> staffWrapper = findById(id);
        if(staffWrapper.isEmpty()) {
            throw new AppEntityNotFound("Staff is not found with id: " + id);
        }
        log.info("Delete staff with id: {}", id);
        delete(staffWrapper.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Staff updateById(Long id, StaffDto dto) {
        Optional<Staff> staffWrapper = findById(id);
        if(staffWrapper.isEmpty()) {
            throw new AppEntityNotFound("Staff is not found with id: " + id);
        }
        log.info("Update staff with id: {}", id);
        Staff updateStaff = staffWrapper.get();
        updateStaff.setName(dto.getName());
        updateStaff.setAge(dto.getAge());
        updateStaff.setAddress(dto.getAddress());
        updateStaff.setRole(dto.getRole());
        updateStaff.setEmail(dto.getEmail());
        return save(updateStaff);
    }
}

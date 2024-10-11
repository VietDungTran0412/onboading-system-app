package swin.swe4006.c6g1.service;

import swin.swe4006.c6g1.dto.StaffDto;
import swin.swe4006.c6g1.entity.Staff;

public interface StaffService extends BaseService<Staff, Long> {
    Staff save(StaffDto dto);
    void delete(Long id);
    Staff updateById(Long id, StaffDto staffDto);
}

package swin.swe4006.c6g1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swin.swe4006.c6g1.entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
}

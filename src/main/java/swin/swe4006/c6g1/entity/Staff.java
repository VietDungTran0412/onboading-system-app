package swin.swe4006.c6g1.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "age", nullable = false)
    private Integer age;
    @Column(name = "address")
    private String address;
    @Column(name = "role")
    private String role;
    @Column(name = "email")
    private String email;
    public Staff() {}
    public Staff(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}

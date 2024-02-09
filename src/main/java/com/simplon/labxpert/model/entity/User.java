package com.simplon.labxpert.model.entity;
import com.simplon.labxpert.model.enums.EUserRole;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_id_sequence",
            sequenceName = "user_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_sequence"
    )
    @Column(name = "userID")
    private long userID;
    @Column(name = "email", unique = true , nullable = false)
    private String email;
    @Column(name = "emailVerified" , nullable = false)
    private Boolean isEmailVerified;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "userRole", nullable = false)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "userID"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @Column(name = "personalInfo")
    private String personalInfo;
    @OneToMany(mappedBy = "user")
    private List<Scheduling> schedulings;
}

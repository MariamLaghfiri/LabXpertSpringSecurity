package com.simplon.labxpert.model.entity;

import com.simplon.labxpert.model.enums.EUserRole;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EUserRole name;

    public Role(EUserRole name) {
        this.name = name;
    }
}

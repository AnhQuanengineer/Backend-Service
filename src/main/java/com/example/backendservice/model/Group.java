package com.example.backendservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_group")
public class Group extends AbstractEntity<Integer> implements Serializable {

    @Column(name = "name")
    private String name;

//    @Column(name = "description")
//    private String description;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;
}

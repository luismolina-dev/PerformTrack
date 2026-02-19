package com.app.performtrackapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Department department;

    @ManyToOne(optional = true)
    @JoinColumn(nullable = true)
    private Sub_department subDepartment;

    @Column(unique = true)
    private String name;
}

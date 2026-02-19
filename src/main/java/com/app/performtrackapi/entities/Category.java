package com.app.performtrackapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private Integer weight;

    private Integer order_index;

    @ManyToOne
    private Evaluation evaluation;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Question> questions;
}

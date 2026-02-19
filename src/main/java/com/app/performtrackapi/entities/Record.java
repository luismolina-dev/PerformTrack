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
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Evaluation evaluation;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Employee evaluator;

    private String period;

    private String comment;

    private String strengths;

    private String weakness;

    private String admin_training;

    private String technical_training;

    private Status status;

    @OneToMany
    private List<Answer> answers;
}

package com.realEstate.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int vote;
    @ManyToOne
    private SelectedForVoting selectedForVoting;
    @ManyToOne
    private Users expert;

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", vote=" + vote +
                ", selectedForVoting=" + selectedForVoting.getId() +
                ", expert=" + expert.getId() +
                '}';
    }
}

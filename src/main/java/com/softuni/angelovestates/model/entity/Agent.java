package com.softuni.angelovestates.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agents")
@DiscriminatorValue("AGENT")
public class Agent extends User{

    @Column
    private String company;

    @Column
    private String photoURL;
}

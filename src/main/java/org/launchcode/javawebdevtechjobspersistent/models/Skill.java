package org.launchcode.javawebdevtechjobspersistent.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Skill extends AbstractEntity {

    @Size(max = 200)
    private String description;

    @ManyToMany(mappedBy="skills")
    public List<Job> jobs;

    public Skill () {
    }
}
package com.miniproject.splitwise.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity (name = "userGroup")
public class Group extends BaseModel{
    private String name;
    private String description;
    private int amount;

    @OneToMany
    private List<User> users;

    @OneToMany (mappedBy = "group")
    private List<Expense> expenses;

    @ManyToOne
    private User admin;
}

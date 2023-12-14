package com.miniproject.splitwise.Services;

import com.miniproject.splitwise.Models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    private User from;
    private User to;
    private int amount;
}

package com.romansj.backend_hwk.accounts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Account implements IAccount {
    private double balance;
    private String accountNumber;
}

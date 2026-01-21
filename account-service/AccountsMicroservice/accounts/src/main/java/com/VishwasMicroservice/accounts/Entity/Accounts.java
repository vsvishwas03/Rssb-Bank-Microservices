package com.VishwasMicroservice.accounts.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Accounts extends BaseEntity {

    private Long customerId;

    @Id
    private  Long accountNumber;

    private String accountType;
    private String branchAddress;




}

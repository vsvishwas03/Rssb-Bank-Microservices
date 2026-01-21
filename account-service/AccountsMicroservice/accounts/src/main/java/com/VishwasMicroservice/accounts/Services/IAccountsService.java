package com.VishwasMicroservice.accounts.Services;

import com.VishwasMicroservice.accounts.Dto.CustomerDto;

public interface IAccountsService {

    /**
     * @param customerDto
     */
    void createAccount(CustomerDto customerDto);
    CustomerDto fetchAccount(String mobileNumber);
     boolean updateAccount(CustomerDto dto);

     boolean deleteAccount(String mobileNumber);


}

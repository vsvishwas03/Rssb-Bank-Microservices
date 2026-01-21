package com.VishwasMicroservice.accounts.Services.impl;

import com.VishwasMicroservice.accounts.Constants.AccountsConstants;
import com.VishwasMicroservice.accounts.Dto.AccountsDto;
import com.VishwasMicroservice.accounts.Dto.CustomerDto;
import com.VishwasMicroservice.accounts.Entity.Accounts;
import com.VishwasMicroservice.accounts.Entity.Customer;
import com.VishwasMicroservice.accounts.Exception.CustomerAlreadyExistsException;
import com.VishwasMicroservice.accounts.Exception.ResourceNotFoundException;
import com.VishwasMicroservice.accounts.Mapper.AccountsMapper;
import com.VishwasMicroservice.accounts.Mapper.CustomerMapper;
import com.VishwasMicroservice.accounts.Repository.AccountsRepository;
import com.VishwasMicroservice.accounts.Repository.CustomerRepository;
import com.VishwasMicroservice.accounts.Services.IAccountsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;


    /**
     * @param customerDto
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer =CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> OptCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(OptCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer Already exists with given Phone Number: "+customerDto.getMobileNumber());
        }

        Customer saved = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(saved));



    }

    /**
     * @param customer
     * @return
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccount;
    }


    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
       Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer" ,"Mobile Number", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Customer" ,"Mobile Number", mobileNumber)
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());

        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));




        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto dto) {
        boolean isUpdated= false;
        AccountsDto accountsDto= dto.getAccountsDto();
        if(accountsDto!=null){
            Accounts accounts= accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    ()-> new ResourceNotFoundException("Account","Account Number",accountsDto.getAccountNumber().toString())

            );
            AccountsMapper.mapToAccounts(accountsDto,accounts);
            accounts=accountsRepository.save(accounts);

            Long customerId= accounts.getCustomerId();
            Customer customer= customerRepository.findById(customerId).orElseThrow(
                    ()-> new ResourceNotFoundException("Customer","Customer Id",customerId.toString())

            );
            CustomerMapper.mapToCustomer(dto,customer);
            customerRepository.save(customer);
            isUpdated=true;



        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer= customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer","Mobile number",mobileNumber)
        );

        Long customerId = customer.getCustomerId();
        accountsRepository.deleteByCustomerId(customerId);
        customerRepository.deleteById(customerId);

        return true;
    }


}

package com.VishwasMicroservice.accounts.Dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "CUSTOMER",
        description = "Schema to hold customer and account Information"
)
public class CustomerDto {

    @Schema(
            description = "Name of the customer", example = "Vishwas Sharma"
    )
    @NotEmpty(message ="Name can not be null or empty")
    @Size(min = 5 , max = 30, message = "The length of customer name should be between 5 and 30")
    private String name;

    @Schema(
            description = "Email address of the customer", example = "vishwas@gmail.com"
    )
    @NotEmpty(message ="Email can not be null or empty")
    @Email(message = "Please check Email Format")
    private String email;
    @Schema(
            description = "Mobile Number of the customer", example = "9345432123"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message ="Mobile number must be 10 digits" )
    private String mobileNumber;
    @Schema(
            description = "Account details of the Customer"
    )
    private AccountsDto accountsDto;

}

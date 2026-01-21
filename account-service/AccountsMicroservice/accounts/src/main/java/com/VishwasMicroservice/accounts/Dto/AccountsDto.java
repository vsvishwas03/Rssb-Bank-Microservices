package com.VishwasMicroservice.accounts.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountsDto {

    @Schema(
            description = "Account Number of RSSB Bank account", example = "3454433243"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message ="Account number must be 10 digits" )
    @NotEmpty(message ="Account Number can not be null or empty")

    private  Long accountNumber;
    @Schema(
            description = "Account type of RSSB Bank account", example = "Savings"
    )
    @NotEmpty(message ="accountType  can not be null or empty")
    private String accountType;
    @Schema(
            description = "RSSB Bank branch address", example = "123 beas"
    )
    @NotEmpty(message ="branchAddress  can not be null or empty")
    private String branchAddress;

}

package com.kidsnara.library.dto.user;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {

    private String email;
    private String role;

}

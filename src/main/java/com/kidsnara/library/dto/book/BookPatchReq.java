package com.kidsnara.library.dto.book;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class BookPatchReq {

    private Long bookId;

    private int count;

    @NotBlank
    private String genre;
}

package dev.ifeoluwa.finestatecapitalapp.dto;

import lombok.*;

/**
 * @author on 25/04/2023
 * @project
 */

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    private String email;
    private String password;

}

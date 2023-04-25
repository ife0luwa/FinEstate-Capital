package dev.ifeoluwa.finestatecapitalapp.dto;

import lombok.*;

/**
 * @author on 24/04/2023
 * @project
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String picture;

}

package br.com.caelum.clines.api.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}

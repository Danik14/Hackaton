package diploma.project.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Length(min = 3, max = 35)
    private String username;

    @Email
    private String email;

    @Length(min = 8, max = 25)
    private String password;
}
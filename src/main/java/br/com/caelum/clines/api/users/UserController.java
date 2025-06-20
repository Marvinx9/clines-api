package br.com.caelum.clines.api.users;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/{id}")
    UserView details(@PathVariable Long id) {
        return service.detailUserByIdBy(id);
    }

    @GetMapping
    List<UserView> list() {
        return service.listAllUsers();
    }

    @PostMapping
    ResponseEntity<Long> createBy(@RequestBody @Valid UserForm form) {
        var id = service.createUserBy(form);

        var uri = URI.create("/user/").resolve(id.toString());

          return created(uri).build();
    }
}

package br.com.caelum.clines.api.users;

import br.com.caelum.clines.api.aircraft.AircraftView;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static br.com.caelum.clines.shared.util.StringNormalizer.normalize;

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
}

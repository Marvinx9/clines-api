package br.com.caelum.clines.api.users;

import br.com.caelum.clines.api.aircraft.AircraftView;
import br.com.caelum.clines.shared.domain.User;
import br.com.caelum.clines.shared.exceptions.AircraftModelNotFoundException;
import br.com.caelum.clines.shared.exceptions.ResourceAlreadyExistsException;
import br.com.caelum.clines.shared.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserViewMapper viewMapper;
    private final UserFormMapper formMapper;

    public List<UserView> listAllUsers() {
        return repository.findAll().stream().map(viewMapper::map).collect(Collectors.toList());
    }

    public UserView detailUserByIdBy(Long id) {
        var user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cannot find user"));

        return viewMapper.map(user);
    }

    public Long createUserBy(@Valid UserForm form) {
        User u = repository.findByEmail(form.getEmail().toUpperCase()).orElse(null);

        if(u != null) {
            throw new ResourceAlreadyExistsException("User already exists with this email");
        }

        var user = formMapper.map(form);

        repository.save(user);

        return user.getId();
    }
}

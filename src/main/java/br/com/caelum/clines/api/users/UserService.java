package br.com.caelum.clines.api.users;

import br.com.caelum.clines.api.aircraft.AircraftView;
import br.com.caelum.clines.shared.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserViewMapper viewMapper;

    public List<UserView> listAllUsers() {
        return repository.findAll().stream().map(viewMapper::map).collect(Collectors.toList());
    }

    public UserView detailUserByIdBy(Long id) {
        var user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cannot find user"));

        return viewMapper.map(user);
    }
}

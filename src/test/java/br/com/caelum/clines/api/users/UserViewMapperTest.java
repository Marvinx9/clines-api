package br.com.caelum.clines.api.users;

import br.com.caelum.clines.shared.domain.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserViewMapperTest {
    private final String NAME = "Nome Teste";
    private final String EMAIL = "teste@gmail.com";
    private final String PASSWORD = "teste123";

    private UserViewMapper mapper;

    @Test
    void shouldConvertUserToUserView() {
        var user = new User(NAME, EMAIL, PASSWORD);
        mapper = new UserViewMapper();

        var userView = mapper.map(user);
        assertEquals(NAME, userView.getName());
        assertEquals(EMAIL, userView.getEmail());
    }
}

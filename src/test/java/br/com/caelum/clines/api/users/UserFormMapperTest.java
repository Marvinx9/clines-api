package br.com.caelum.clines.api.users;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserFormMapperTest {
    private final String NAME = "nome de teste";
    private final String EMAIL = "email@gmail.com";
    private final String PASSWORD = "147258";

    private UserFormMapper mapper;

    @Test
    void shouldBeConvertUserFormToUser() {
        var userForm = new UserForm(NAME, EMAIL, PASSWORD);
        mapper = new UserFormMapper();

        var user = mapper.map(userForm);
        assertEquals(NAME, user.getName());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPassword());
        assertNull(user.getId());
    }
}

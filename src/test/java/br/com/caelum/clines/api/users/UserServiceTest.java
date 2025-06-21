package br.com.caelum.clines.api.users;

import br.com.caelum.clines.shared.domain.User;
import br.com.caelum.clines.shared.exceptions.ResourceAlreadyExistsException;
import br.com.caelum.clines.shared.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private static final Long USER_ID = 1L;
    private static final Long NON_USER_ID = 5L;
    private static final User DEFAULT_USER = new User(1L, "TESTE", "TESTE@GMAIL.COM", "14rr7");
    private static final List<User> ALL_USERS = List.of(DEFAULT_USER);
    private static final UserForm USER_FORM = new UserForm("TESTE", "TESTE@GMAIL.COM", "1de4f");

    @Spy
    private UserFormMapper formMapper;

    @Spy
    private UserViewMapper viewMapper;

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    void shouldReturnSingleAnUserViewWhenExistingInRepository() {
        given(repository.findById(USER_ID)).willReturn(Optional.of(DEFAULT_USER));

        var userView = service.detailUserByIdBy(USER_ID);

        then(repository).should(only()).findById(USER_ID);
        then(viewMapper).should(only()).map(DEFAULT_USER);
        then(formMapper).shouldHaveNoInteractions();

        Assertions.assertEquals("TESTE", userView.getName());
        Assertions.assertEquals("TESTE@GMAIL.COM", userView.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenUserNotExistingInRepository() {
        given(repository.findById(NON_USER_ID)).willReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.detailUserByIdBy(NON_USER_ID));

        then(repository).should(only()).findById(NON_USER_ID);
        then(viewMapper).shouldHaveNoInteractions();
        then(formMapper).shouldHaveNoInteractions();
    }

    @Test
    void shouldReturnAListOfUserViewForEachUserInRepository() {
        given(repository.findAll()).willReturn(ALL_USERS);

        var allUserViews = service.listAllUsers();

        then(repository).should(only()).findAll();
        then(viewMapper).should(only()).map(DEFAULT_USER);
        then(formMapper).shouldHaveNoInteractions();

        Assertions.assertEquals(ALL_USERS.size(), allUserViews.size());

        var userView = allUserViews.get(0);

        Assertions.assertEquals("TESTE", userView.getName());
        Assertions.assertEquals("TESTE@GMAIL.COM", userView.getEmail());
    }


    @Test
    public void shouldCreateAUser() {
        given(formMapper.map(USER_FORM)).willReturn(DEFAULT_USER);
        given(repository.findByEmail("TESTE@GMAIL.COM")).willReturn(Optional.empty());

        var createdUserId = service.createUserBy(USER_FORM);

        then(formMapper).should(only()).map(USER_FORM);
        then(repository).should().save(DEFAULT_USER);

        Assertions.assertEquals(USER_ID, createdUserId);
    }

    @Test
    void shouldThrowExceptionIfUserAlreadyExists() {
        given(repository.findByEmail("TESTE@GMAIL.COM")).willReturn(Optional.of(DEFAULT_USER));

        assertThrows(ResourceAlreadyExistsException.class, () -> service.createUserBy(USER_FORM));

        then(repository).should(only()).findByEmail("TESTE@GMAIL.COM");
        then(viewMapper).shouldHaveNoInteractions();
        then(formMapper).shouldHaveNoInteractions();
    }
}

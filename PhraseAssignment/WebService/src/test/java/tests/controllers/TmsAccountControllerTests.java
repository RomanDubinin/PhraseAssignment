package tests.controllers;

import domain.user.Password;
import domain.user.TmsAccount;
import domain.user.UserName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import repositories.TmsAccountRepository;
import webservice.contracts.parameters.TmsAccountParameter;
import webservice.controllers.TmsAccountController;
import webservice.services.TmsAccountService;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TmsAccountControllerTests {
    private TmsAccountController controller;
    private TmsAccountService service;
    @Mock
    private TmsAccountRepository repository;

    @Test
    public void tmsAccount_getNotExisting_exception() {
        when(repository.findById(any(Long.class))).thenReturn(Optional.empty());
        service = new TmsAccountService(repository);
        controller = new TmsAccountController(service);

        var exception = assertThrows(NoSuchElementException.class, () -> controller.getTmsAccount(1L));
        assertEquals(exception.getMessage(), "TMS account with id 1 does not exists");
    }

    @Test
    public void tmsAccount_getExisting_idIsCorrect() {
        var account1 = TmsAccount.builder().id(1L).userName(new UserName("user1")).password(new Password("*")).build();
        var account2 = TmsAccount.builder().id(2L).userName(new UserName("user2")).password(new Password("*")).build();

        Mockito.lenient().when(repository.findById(1L)).thenReturn(Optional.of(account1));
        Mockito.lenient().when(repository.findById(2L)).thenReturn(Optional.of(account2));
        service = new TmsAccountService(repository);
        controller = new TmsAccountController(service);

        var actualAccount = controller.getTmsAccount(2L);
        assertEquals(account2.getId(), actualAccount.getId());
    }

    @Test
    public void tmsAccount_create_savedToRepository() {
        var accountParameter = TmsAccountParameter.builder().userName("user").password("*").build();
        var account = TmsAccount.builder().id(1L).userName(new UserName("user")).password(new Password("*")).build();

        when(repository.save(any(TmsAccount.class))).thenReturn(account);
        service = new TmsAccountService(repository);
        controller = new TmsAccountController(service);

        var savedAccount = controller.createTmsAccount(accountParameter);
        assertEquals(savedAccount.getId(), account.getId());
        verify(repository).save(any(TmsAccount.class));
    }

    @Test
    public void tmsAccount_editNotExisting_exception() {
        var accountParameter = TmsAccountParameter.builder().userName("user").password("*").build();

        when(repository.existsById(1L)).thenReturn(false);
        service = new TmsAccountService(repository);
        controller = new TmsAccountController(service);

        var exception = assertThrows(NoSuchElementException.class, ()-> controller.editTmsAccount(1L, accountParameter));
        assertEquals(exception.getMessage(), "TMS account with id 1 does not exists");
    }

    @Test
    public void tmsAccount_editExisting_savedToRepository() {
        var accountParameter = TmsAccountParameter.builder().userName("user").password("*").build();
        var account = TmsAccount.builder().id(1L).userName(new UserName("user")).password(new Password("*")).build();

        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(any(TmsAccount.class))).thenReturn(account);
        service = new TmsAccountService(repository);
        controller = new TmsAccountController(service);

        var savedAccount = controller.editTmsAccount(1L, accountParameter);
        assertEquals(savedAccount.getId(), account.getId());
        verify(repository).save(any(TmsAccount.class));
    }

    @Test
    public void tmsAccount_deleteNotExisting_exception() {

        when(repository.existsById(1L)).thenReturn(false);
        service = new TmsAccountService(repository);
        controller = new TmsAccountController(service);

        var exception = assertThrows(NoSuchElementException.class, ()-> controller.deleteTmsAccount(1L));
        assertEquals(exception.getMessage(), "TMS account with id 1 does not exists");
    }

    @Test
    public void tmsAccount_deleteExisting_deletedFromRepository() {

        when(repository.existsById(1L)).thenReturn(true);
        service = new TmsAccountService(repository);
        controller = new TmsAccountController(service);

        controller.deleteTmsAccount(1L);
        verify(repository).deleteById(1L);
    }
}

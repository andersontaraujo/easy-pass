package br.com.devaware.easypass.passwords;

import br.com.devaware.easypass.exceptions.ResourceNotFoundException;
import br.com.devaware.easypass.passwords.dtos.request.PasswordPartialDTO;
import br.com.devaware.easypass.passwords.dtos.response.PasswordDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.devaware.easypass.passwords.PasswordMock.mockPassword;
import static br.com.devaware.easypass.passwords.PasswordMock.mockPasswordOutput;
import static br.com.devaware.easypass.passwords.PasswordMock.mockPasswordPartialInput;
import static br.com.devaware.easypass.passwords.PasswordMock.mockPasswordPartialInputMapping;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PasswordServiceTest {

    @InjectMocks
    private PasswordService service;

    @Mock
    private ModelMapper mapper;

    @Mock
    private PasswordRepository repository;

    @Test
    public void createPasswordTest() {

        PasswordPartialDTO passwordInput = mockPasswordPartialInput();

        Password passwordToSave = mockPasswordPartialInputMapping();

        Password savedPassword = mockPassword();

        PasswordDTO passwordOutputExpected = mockPasswordOutput();

        when(mapper.map(passwordInput, Password.class)).thenReturn(passwordToSave);

        when(repository.save(passwordToSave)).thenReturn(savedPassword);

        when(mapper.map(savedPassword, PasswordDTO.class)).thenReturn(passwordOutputExpected);

        PasswordDTO passwordOutputActual = service.createPassword(passwordInput);

        assertEquals(passwordOutputExpected, passwordOutputActual);

        verify(repository, times(1)).save(passwordToSave);
    }

    @Test
    public void findAllPasswordsTest() {

        Password password = mockPassword();

        List<Password> passwords = Collections.singletonList(password);

        PasswordDTO passwordOutput = mockPasswordOutput();

        List<PasswordDTO> passwordsOutputExpected = Collections.singletonList(passwordOutput);

        when(repository.findAll()).thenReturn(passwords);

        when(mapper.map(password, PasswordDTO.class)).thenReturn(passwordOutput);

        List<PasswordDTO> passwordsOutputActual = service.findAllPasswords();

        assertEquals(passwordsOutputExpected, passwordsOutputActual);

        verify(repository, times(1)).findAll();
    }

    @Test
    public void findPasswordSuccessTest() {

        Password password = mockPassword();

        PasswordDTO passwordOutputExpected = mockPasswordOutput();

        when(repository.findById(PasswordMock.ID)).thenReturn(Optional.of(password));

        when(mapper.map(password, PasswordDTO.class)).thenReturn(passwordOutputExpected);

        PasswordDTO passwordOutputActual = service.findPasswordById(PasswordMock.ID);

        assertEquals(passwordOutputExpected, passwordOutputActual);

        verify(repository, times(1)).findById(PasswordMock.ID);
    }

    @Test
    public void findPasswordThrowResourceNotFoundExceptionWhenNoPresentValueTest() {

        when(repository.findById(PasswordMock.ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findPasswordById(PasswordMock.ID));

        verify(repository, times(1)).findById(PasswordMock.ID);
    }

}

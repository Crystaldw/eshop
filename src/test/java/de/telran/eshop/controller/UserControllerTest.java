package de.telran.eshop.controller;

import de.telran.eshop.dto.UserDTO;
//import de.telran.eshop.entity.User;
import de.telran.eshop.entity.User;
import de.telran.eshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @Mock
    Model model;

    @Test
    void userListTest() {
        //Given
        when(userService.getAll()).thenReturn(Collections.emptyList());

        //When
        String viewName = userController.userList(model);

        //Then
        assertEquals("userlist", viewName);
        verify(model).addAttribute("users", Collections.emptyList());
    }

    @Test
    void newUserTest() {
        //When
        String viewName = userController.newUser(model);

        //Then
        assertEquals("user", viewName);
        verify(model).addAttribute("user", new UserDTO());
    }

    @Test
    void saveUser_SuccessTest() {
        //Given
        UserDTO userDTO = new UserDTO();
        when(userService.save(userDTO)).thenReturn(true);

        //When
        String viewName = userController.saveUser(userDTO, model);

        //Then
        assertEquals("redirect:/users", viewName);
        verify(userService).save(userDTO);
    }

    @Test
    void saveUser_FailureTest() {
        //Given
        UserDTO userDTO = new UserDTO();
        when(userService.save(userDTO)).thenReturn(false);

        //When
        String viewName = userController.saveUser(userDTO, model);

        //Then
        assertEquals("user", viewName);
        verify(model).addAttribute("user", userDTO);
    }

//    @Test
//    void getRolesTest(){
//        //Given
//        String userName = "testUser";
//        User testUser = new User();
//        testUser.setRole(User.role.ADMIN);
//        when(userService.findByName(userName)).thenReturn(testUser);
//
//        //When
//        String roles = userController.getRoles(userName);
//
//        //Then
//        assertEquals("ADMIN", roles);
//        verify(userService).findByName(userName);
//
//    }

    @Test
    void ProfileUserTest() {
        //Given
        User user = new User();
        user.setName("testUser");
        when(userService.findByName("testUser")).thenReturn(user);

        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("testUser");

        //When
        String viewName = userController.profileUser(model, principal);

        //Then
        assertEquals("profile", viewName);
        verify(model).addAttribute("user", new UserDTO(user.getName(), user.getEmail()));
    }

    @Test
    void updateProfileUser_SuccessTest() {
        //Given
        UserDTO userDTO = new UserDTO("testUser", "test@teest.pl");
        Principal principal = () -> "testUser";

        //When
        String viewName = userController.updateProfileUser(userDTO, model, principal);

        //Then
        assertEquals("redirect:/users/profile", viewName);
        verify(userService).updateProfile(userDTO);

    }

}
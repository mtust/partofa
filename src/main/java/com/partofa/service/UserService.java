package com.partofa.service;

import com.partofa.domain.User;
import com.partofa.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by tust on 09.09.2016.
 */
@Service
public interface UserService {

    User getUserByEmail(String email);


    User getCurrentUser();

    @Transactional
    RestMessageDTO signUp(UserRegistrationDTO userRegistrationDTO);
    List<UserDTO> getAllUsers();
    User getLoginUser();

    UserDTO getLoginUserDTO();

    RestMessageDTO createUser(UserCreateDTO userRegistrationDTO);

    RestMessageDTO deleteUser(Long userId);

    RestMessageDTO editUser(UserEditDTO userEditDTO);

    RestMessageDTO editUserMe(UserEditDTO userEditDTO);

    RestMessageDTO changePassword(ChangePasswordDTO changePasswordDTO);


    RestMessageDTO changePhoto(MultipartFile photo) throws IOException;

    byte[] getUserPhoto() throws SQLException, IOException;

    RestMessageDTO sendEmailWithPassword(String email);

}

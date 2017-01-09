package com.partofa.service.impl;

import com.partofa.domain.Document;
import com.partofa.domain.Region;
import com.partofa.domain.Role;
import com.partofa.domain.User;
import com.partofa.dto.*;
import com.partofa.exception.ObjectAlreadyExistException;
import com.partofa.repository.DocumentRepository;
import com.partofa.repository.RegionRepository;
import com.partofa.repository.UserRepository;
import com.partofa.security.SecurityUtils;
import com.partofa.service.UserService;
import com.partofa.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.LobCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by tust on 09.09.2016.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    private JavaMailSender javaMailService;


    @Transactional
    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
        }
        return user;
    }


    @Transactional
    @Override
    public User getCurrentUser() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentUserEmail);
        if (currentUser == null) {
            log.debug("anonymousUser detected");
            throw new RuntimeException();
        }
        return currentUser;
    }

    @Transactional
    @Override
    public RestMessageDTO signUp(UserRegistrationDTO userRegistrationDTO) throws ObjectAlreadyExistException{
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (UserUtil.isNotFilledFieldsExist(userRegistrationDTO)) {
            throw new ObjectAlreadyExistException("Неможливо створити користувача, заповність всі поля");
        }
        if (!userRegistrationDTO.getPassword().equals(userRegistrationDTO.getPasswordConfirm())) {
            throw new ObjectAlreadyExistException("Неможливо створити користувача, паролі не співпадають");
        }
        User existingUser = userRepository.findByEmail(userRegistrationDTO.getEmail());
        if (existingUser != null) {
            throw new ObjectAlreadyExistException("Такий користувач вже існує");
        }
        String hashedPassword = passwordEncoder.encode(userRegistrationDTO.getPassword());
        User user = new User();
        user.setEmail(userRegistrationDTO.getEmail());
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setPassword(hashedPassword);
        user.setRole(Role.ROLE_USER);
        user.setIsEnabled(false);
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return new RestMessageDTO("Success", true);
    }

    @Transactional
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<UserDTO>();
        users.forEach(user -> userDTOs.add(
                new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRegion() == null ? "Усі регіони" : user.getRegion().getName(),
                        user.getRole() == Role.ROLE_USER ? "Оператор" : "Адміністратор",
                        user.getIsEnabled() == true ? "Активний" : "Заблокований")));
        return userDTOs;
    }

    @Transactional
    @Override
    public User getLoginUser() {
        String userLogin = SecurityUtils.getCurrentUserLogin();
        return userRepository.findByEmail(userLogin);

    }


    @Override
    public UserDTO getLoginUserDTO() {
        User user = getLoginUser();
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRegion() == null ? "Усі регіони" : user.getRegion().getName(),
                user.getRole() == Role.ROLE_USER ? "Оператор" : "Адміністратор",
                user.getIsEnabled() == true ? "Активний" : "Заблокований");
    }

    @Transactional
    @Override
    public RestMessageDTO createUser(UserCreateDTO userRegistrationDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User existingUser = userRepository.findByEmail(userRegistrationDTO.getEmail());
        if (existingUser != null) {
            throw new ObjectAlreadyExistException("Користувач вже існує");
        }
        if (!userRegistrationDTO.getPassword().equals(userRegistrationDTO.getConfirmPassword())) {
            throw new RuntimeException("Неможливо створити користувача, паролі не співпадають");
        }
        String hashedPassword = passwordEncoder.encode(userRegistrationDTO.getPassword());
        User user = new User();
        user.setEmail(userRegistrationDTO.getEmail());
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setPassword(hashedPassword);
        user.setRole(Role.getRole(userRegistrationDTO.getRole()));
        user.setIsEnabled(true);
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return new RestMessageDTO("Success", true);
    }

    @Override
    public RestMessageDTO deleteUser(Long userId) {

        userRepository.delete(userId);

        return new RestMessageDTO("Success", true);
    }

    @Override
    public RestMessageDTO editUser(UserEditDTO userEditDTO) {

        Region region = null;
        if (!userEditDTO.getRegion().equals("all")) {
            region = regionRepository.findOne(Long.parseLong(userEditDTO.getRegion()));
        }
        User user = userRepository.findOne(userEditDTO.getId());
        log.info("user:" + user);
        log.info("userDTO" + userEditDTO);
        user.setEmail(userEditDTO.getEmail());
        user.setLastName(userEditDTO.getLastName());
        user.setRole(Role.getRole(userEditDTO.getRole()));
        user.setIsEnabled(userEditDTO.getIsEnabled());
        user.setFirstName(userEditDTO.getFirstName());
        user.setRegion(region);
        if(!userEditDTO.getPassword().equals("")) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(userEditDTO.getPassword());
            user.setPassword(hashedPassword);
        }
        log.info(user.toString());
        userRepository.save(user);

        return new RestMessageDTO("Success", true);
    }

    @Override
    public RestMessageDTO editUserMe(UserEditDTO userEditDTO) {
        User user = getLoginUser();
//        User userForVelidation = userRepository.findByEmail(userEditDTO.getEmail());
//        if(userForVelidation != null && user.getId() != userForVelidation.getId()){
//            new RuntimeException("Користувач з такою електронною адресою вже інсує");
//        }
//
//        if(!user.getEmail().equals(userEditDTO.getEmail())) {
//            user.setEmail(userEditDTO.getEmail());
//            SecurityUtils.getAuthentication().setAuthenticated(true);
//        }
        user.setFirstName(userEditDTO.getFirstName());
        user.setLastName(userEditDTO.getLastName());
        userRepository.save(user);


        return new RestMessageDTO("Success", true);
    }

    @Override
    public RestMessageDTO changePassword(ChangePasswordDTO changePasswordDTO) {
        User user = getLoginUser();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getNewPasswordConfirm())) {
            throw new RuntimeException("паролі не співпадають");
        }
        log.info("Зміни: " + changePasswordDTO.toString());
        log.info("пароль користувача: " + user.getPassword());
        log.info("пароль введений:" + passwordEncoder.encode(changePasswordDTO.getPassword()));
        if (!BCrypt.checkpw(changePasswordDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("неправельний пароль");
        }
        String hashedPassword = passwordEncoder.encode(changePasswordDTO.getNewPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return new RestMessageDTO("Success", true);
    }

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public RestMessageDTO changePhoto(MultipartFile photo) throws IOException {
        User user = getLoginUser();
        log.info("user: " + user);
        Session session = sessionFactory.getCurrentSession();
        LobCreator lobCreator = Hibernate.getLobCreator(session);
        Blob blob = lobCreator.createBlob(photo.getBytes());
        Document document = new Document();
        document.setFile(blob);
        //documentRepository.save(document);
        user.setPhoto(document);
        return new RestMessageDTO("Success", true);
    }

    @Override
    @Transactional
    public byte[] getUserPhoto() throws SQLException, IOException {
        User user = getLoginUser();
        if (user.getPhoto() == null) {
            return IOUtils.toByteArray(new URL("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQoiJVlwkYJvPNp7vjnrPPGEe3MDBvcDbaFjkBBjo5_OLlMGLrG_sMtMcCR").openStream());
        }
        log.info(user.toString());
        InputStream is = user.getPhoto().getFile().getBinaryStream();
        return IOUtils.toByteArray(is);
    }

    @Override
    public RestMessageDTO sendEmailWithPassword(String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        User user = userRepository.findByEmail(email);
        mailMessage.setTo(email);
        mailMessage.setSubject("Відновлення паролю");
        String password = UUID.randomUUID().toString();
        password = password.substring(0, 6);
        mailMessage.setText("Ваш новий пароль: " + password);
        javaMailService.send(mailMessage);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return new RestMessageDTO("Success", true);
    }

}
package com.softuni.angelovestates.service;

import com.softuni.angelovestates.model.DTO.UserRegisterDTO;
import com.softuni.angelovestates.model.entity.User;
import com.softuni.angelovestates.model.enums.RoleEnum;
import com.softuni.angelovestates.repository.RoleRepository;
import com.softuni.angelovestates.repository.UserRepository;
import com.softuni.angelovestates.util.EmailSender;
import com.softuni.angelovestates.util.FileUploadService;
import com.softuni.angelovestates.model.entity.Agent;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Transactional
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final FileUploadService fileUploadService;
    private final ModelMapper modelMapper;
    private final EmailSender emailSender;

    @Autowired
    public AuthService(UserRepository userRepository, RoleRepository roleRepository, FileUploadService fileUploadService, ModelMapper modelMapper, EmailSender emailSender) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.fileUploadService = fileUploadService;
        this.modelMapper = modelMapper;
        this.emailSender = emailSender;
    }

    public void register(UserRegisterDTO userRegisterDTO) throws IOException {

        if(userRegisterDTO.isAgent()) {
            Agent agent = this.modelMapper.map(userRegisterDTO, Agent.class);
            agent.setCompany(userRegisterDTO.getCompanyName());
            agent.setPhotoURL(this.fileUploadService.uploadAgentPhoto(userRegisterDTO.getMultipartFile()));
            agent.getRoles().add(this.roleRepository.getByRole(RoleEnum.AGENT));
            agent.getRoles().add(this.roleRepository.getByRole(RoleEnum.USER));

            this.userRepository.saveAndFlush(agent);
            this.emailSender.sendRegistrationSuccessEmail(agent.getEmail(), agent.getFirstName());
        } else {
            User user = this.modelMapper.map(userRegisterDTO, User.class);
            user.getRoles().add(this.roleRepository.getByRole(RoleEnum.USER));

            this.userRepository.saveAndFlush(user);
            this.emailSender.sendRegistrationSuccessEmail(user.getEmail(), user.getFirstName());
        }

    }
}


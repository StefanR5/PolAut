package com.polovni_automobili.auta.service.implementation;

import com.polovni_automobili.auta.domain.User;
import com.polovni_automobili.auta.dto.ApiResponse;
import com.polovni_automobili.auta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private JavaMailSender javaMailSender;
    private UserRepository userRepository;


    @Autowired
    public MessageService(JavaMailSender javaMailSender, UserRepository userRepository){
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
    }

    //https://www.google.com/settings/security/lesssecureapps
    public ResponseEntity<ApiResponse> sendMessage(Long id, String message) throws MailException {
        User user = this.userRepository.findById(id).orElse(null);


        SimpleMailMessage mail = new SimpleMailMessage();
        //mail.setTo(user.getEmail());
        mail.setTo("mambaa1312@gmail.com");
        mail.setFrom("stefan.roganovic@gmail.com");
        mail.setSubject(message);
        mail.setText(message);

        javaMailSender.send(mail);


        return new ResponseEntity<ApiResponse>( new ApiResponse( true, "Uspjesno poslata poruka" ), HttpStatus.OK );
    }

}

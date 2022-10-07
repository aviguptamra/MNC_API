package com.nvc.scanbar.api;

import com.nvc.scanbar.auth.JwtTokenUtil;
import com.nvc.scanbar.beans.user.*;
import com.nvc.scanbar.common.util.ScanbarUtil;
import com.nvc.scanbar.exceptions.*;
import com.nvc.scanbar.model.User;
import com.nvc.scanbar.services.UserService;
import com.zhaofujun.automapper.AutoMapper;
import com.zhaofujun.automapper.IMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    String traceIdentifier = UUID.randomUUID().toString();

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRequest user){
        try{
            if(user.getMobile() == null || user.getMobile().isEmpty() || !ScanbarUtil.isValidMobileString(user.getMobile())) {
                throw new ValidationException("Mobile Number format is wrong!", traceIdentifier);
            } else if (user.getEmail() == null || user.getEmail().isEmpty() || !ScanbarUtil.isValidEmailString(user.getEmail())) {
                throw new ValidationException("Email format is wrong!", traceIdentifier);
            }
            return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException constraintViolationException) {
            String reason =  ((ConstraintViolationException)constraintViolationException.getCause()).getConstraintName();
            LOGGER.error(traceIdentifier + " registerUser() " +constraintViolationException);
            if(reason.toLowerCase().contains("email")) {
                throw new DataConflictException("Email ID is already registered!", traceIdentifier);
            } else if (reason.toLowerCase().contains("mobile")) {
                throw new DataConflictException("Mobile is already registered!", traceIdentifier);
            } else {
                throw new DataConflictException("Registration failed because of constraint violation: "+reason, traceIdentifier);
            }
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e){
            LOGGER.error(traceIdentifier + " registerUser() " +e);
            throw new InternalServerError(traceIdentifier);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest request){
        LoginResponse loginResponse = null;
        try{
            final String token = jwtTokenUtil.generateToken(request);
            User userDo = userService.loginUser(request);
            if(userDo!=null){
                loginResponse = new LoginResponse();
                IMapper mapper = new AutoMapper();
                UserProfile up = mapper.map(userDo, UserProfile.class);
                loginResponse.setUserProfile(up);
                loginResponse.setToken(token);
            }
            else {
                throw new NullPointerException();
            }
        }catch (AuthException e){
            LOGGER.error(traceIdentifier + " loginUser() " +e);
            throw new AuthException(e.getMessage(), traceIdentifier);
        } catch (Exception e){
            LOGGER.error(traceIdentifier + " loginUser() " +e);
            throw new InternalServerError(traceIdentifier);
        }
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
    @GetMapping("/users")
    public ResponseEntity<List<UserProfile>> getAllUsers(){
        try{
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        } catch (NotFoundException e){
            throw e;
        } catch (Exception e){
            throw new InternalServerError(traceIdentifier);
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfile> getUserById(@PathVariable("userId") String userId){
        try{
            return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
        } catch (NotFoundException e){
            throw e;
        }catch (Exception e){
            e.printStackTrace();
            throw new InternalServerError(traceIdentifier);
        }
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<UserHistoryResponse> getRedeemHistory(@PathVariable("userId") String userId) {
        try {
            UserHistoryResponse responses = userService.getUserHistory(userId);
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError(traceIdentifier);
        }
    }

}

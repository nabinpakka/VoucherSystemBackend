package com.example.vouchersystemapiserver.controllers;

import com.example.vouchersystemapiserver.models.*;
import com.example.vouchersystemapiserver.services.AccountService;
import com.example.vouchersystemapiserver.services.UserService;
import com.example.vouchersystemapiserver.services.VoucherService;
import com.example.vouchersystemapiserver.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class baseController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    VoucherService voucherService;

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws  Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            throw  new Exception("Incorrect username or password",e);
        }

        final UserDetails userDetails=userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt=jwtUtil.generateToken(userDetails);

        System.out.println(userDetails.getAuthorities());
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @RequestMapping(value = "/signin",method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestParam("username") String username,@RequestParam("password") String password){
        //check if username already exists
        if(accountService.existsByUsername(username)){
            return new ResponseEntity<String>("Account already exists",HttpStatus.OK);
        }
        UUID id=accountService.createAccount(new Account(username,password));
        userService.createUser(new User(id,username));
        return new ResponseEntity<User>(userService.findUserByUsername(username).get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/redeem",method = RequestMethod.POST)
    public ResponseEntity<?> redeem(@RequestParam("code") String code){
        //get the user detail
        UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=userService.findUserByUsername(userDetails.getUsername()).get();
        //check if transaction is enabled or not
        if (user.isTransactionDisable()){
            return new ResponseEntity<String>("Transaction disabled",HttpStatus.OK);
        }
        //check if code is valid
        Optional<Voucher> voucher=voucherService.findVoucherByCode(code);
        if (voucher.isEmpty()){
            userService.updateInCorrectAttempt(user);
            return new ResponseEntity<String>("wrong code",HttpStatus.OK);
        }else if (voucher.get().isUsed()){
            userService.updateInCorrectAttempt(user);
            return new ResponseEntity<String>("voucher is already used.",HttpStatus.OK);
        }else if (voucher.get().getEndingTimeStamp()< System.currentTimeMillis()){
            userService.updateInCorrectAttempt(user);
            return new ResponseEntity<String>("voucher has been expired.",HttpStatus.OK);
        }else if(voucher.get().getStartingTimeStamp()>System.currentTimeMillis()){
            return new ResponseEntity<String>("voucher has not been activated.",HttpStatus.OK);
        }
        //if valid use it and update the amount to user
        int value=voucherService.redeemVoucher(voucher.get());
        userService.update(user,value);
        //if not valid , increase wrong attempt for user by 1
        //if wrong attempt exceed by 3 within 5 minutes, make account disable for transactions
        return new ResponseEntity<String>("successfully redeemed Rs."+value,HttpStatus.OK);
    }
}

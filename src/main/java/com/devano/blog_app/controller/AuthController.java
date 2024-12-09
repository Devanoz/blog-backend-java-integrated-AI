package com.devano.blog_app.controller;

import com.devano.blog_app.exception.ApiException;
import com.devano.blog_app.request.auth.AuthenticationRequest;
import com.devano.blog_app.response.auth.AuthenticationResponse;
import com.devano.blog_app.service.JwtService;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.BucketProxy;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private ProxyManager<byte[]> proxyManager;
    private BucketConfiguration bucketConfiguration;

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authRequest) throws AuthenticationException {
        BucketProxy bucketProxy = proxyManager.builder().build(authRequest.getUsername().getBytes(), () -> bucketConfiguration);
        if(!bucketProxy.tryConsume(1)) {
            throw new ApiException("Too many request",HttpStatus.TOO_MANY_REQUESTS);
        }

        try{
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);
            if(!authentication.isAuthenticated()) {
                throw new AuthenticationException("Username or password is invalid");
            }
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(AuthenticationResponse.builder().token(jwtToken).build());
        }catch (org.springframework.security.core.AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthenticationResponse());
        }

    }
}

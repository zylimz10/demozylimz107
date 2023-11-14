package com.login.app.api;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.app.DTO.AuthenticationResponse;
import com.login.app.DTO.LoginRequest;
import com.login.app.entity.Users;
import com.login.app.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Value("${jwt.secret}")
    private String jwtSecret;
	
	@Autowired
	private UserRepository userRepository;
	

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody LoginRequest loginrequest){
        Users user = userRepository.findByUsername(loginrequest.getUserName());
        
        System.out.println("Received Username: " + loginrequest.getUserName());
        System.out.println("Received Password: " + loginrequest.getPassword());

        if (user != null && user.getPassword().equals(loginrequest.getPassword())) {
            // Authentication successful
        	 String token = generateJwtToken(user);

             setAuthentication(user);

             // Return the token in the response
             AuthenticationResponse response = new AuthenticationResponse(token, user.getUsername(), user.getRole().name());
             return ResponseEntity.ok(response);
        } else {
            // Authentication failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

	    @GetMapping("/user/{userName}")
	    public ResponseEntity<Users> getUser(@PathVariable String userName) {
	        // Fetch user information from the database based on userName
	        Users user = userRepository.findByUsername(userName);
	        if (user != null) {
	            return ResponseEntity.ok(user);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    
	    
	    @GetMapping("/logout")
	    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
	        // Log the user out by clearing the security context
	        SecurityContextHolder.getContext().setAuthentication(null);

	        // Perform additional logout logic if needed (e.g., invalidate session)

	        return new ResponseEntity<>("User logged out successfully.", HttpStatus.OK);
	    }
	    
	    private String generateJwtToken(Users user) {
	        return Jwts.builder()
	                .setSubject(user.getUsername())
	                .claim("role", user.getRole().name())
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + 864000000)) // Token validity for 10 days
	                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256)
	                .compact();
	    }
	    
	    private void setAuthentication(Users user) {
	        List<GrantedAuthority> authorities = Collections.singletonList(
	                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
	        );

	        Authentication authentication = new UsernamePasswordAuthenticationToken(
	                user.getUsername(), null, authorities);

	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        System.out.println("Set Authentication for user: " + user.getUsername());
	        System.out.println("Authorities: " + authorities);
	    }


}

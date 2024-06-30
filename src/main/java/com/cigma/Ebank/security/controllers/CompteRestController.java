package com.cigma.Ebank.security.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cigma.Ebank.security.entities.AppRole;
import com.cigma.Ebank.security.entities.AppUser;
import com.cigma.Ebank.security.services.CompteService;
import com.cigma.Ebank.security.utilities.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class CompteRestController {

    private final CompteService compteService;

    public CompteRestController(CompteService compteService) {
        this.compteService = compteService;
    }



    @GetMapping(path = "/users")
//    @PreAuthorize("hasAuthority('AGENT_GUICHET')") // Autorisation requise pour lister les utilisateurs
    public List<AppUser> appUsers() {
        return compteService.listUsers();
    }

    /*@PostMapping(path = "/users")
    @PreAuthorize("hasAuthority('AGENT_GUICHET')") // Autorisation requise pour ajouter un utilisateur
    public AppUser saveUser(@RequestBody AppUser appUser) {
        return compteService.addNewUser(appUser);
    }*/

    /*@PostMapping(path = "/roles")
    @PreAuthorize("hasAuthority('AGENT_GUICHET')") // Autorisation requise pour ajouter un rôle
    public AppRole saveRole(@RequestBody AppRole appRole) {
        return compteService.addNewRole(appRole);
    }*/

    @PostMapping(path = "/addRoleToUser")
    @PreAuthorize("hasAuthority('AGENT_GUICHET')") // Autorisation requise pour ajouter un rôle à un utilisateur
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm) {
        compteService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRoleName());
    }

    @GetMapping(path = "/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String authToken = request.getHeader(JWTUtil.AUTH_HEADER);
        if (authToken != null && authToken.startsWith(JWTUtil.PREFIX)) {
            try {
                String jwt = authToken.substring(JWTUtil.PREFIX.length());
                Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                String username = decodedJWT.getSubject();
                AppUser appUser = compteService.loadUserByUsername(username);
                String jwtAccessToken = JWT.create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JWTUtil.EXPIRE_ACCESS_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", appUser.getRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> idToken = new HashMap<>();
                idToken.put("jwt", jwtAccessToken);
                idToken.put("refreshToken", jwt);
                idToken.put("roles", appUser.getRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toList()).toString());
                if (appUser.getRoles().stream().map(AppRole::getRoleName)
                        .collect(Collectors.toList()).contains("CLIENT")) {
                    idToken.put("id", appUser.getId().toString());
                }
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), idToken);

            } catch (Exception e) {
                throw e;
            }
        } else {
            throw new RuntimeException("Refresh token required!");
        }
    }

    @GetMapping(path = "/profile")
    public AppUser profile(Principal principal) {
        return compteService.loadUserByUsername(principal.getName());
    }
}

@Data
class RoleUserForm {
    private String username;
    private String roleName;
}

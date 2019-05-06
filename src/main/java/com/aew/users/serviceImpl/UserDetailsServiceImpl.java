package com.aew.users.serviceImpl;

import com.aew.users.domain.User;
import com.aew.users.repository.UserRepository;
import com.aew.users.security.UserPrinciple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authenticate a User or perform various role-based checks, Spring security
 * needs to load users details somehow.
 * 
 * @author Adrian
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    /**
     * Loads a user based on username or login id.
     * 
     * @param login
     * @return UserDetails object that Spring Security uses for performing various
     *         authentication and role based validations.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        User user = userRepository.findByLogin(login).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username or email : " + login));

        return UserPrinciple.build(user);
    }
}
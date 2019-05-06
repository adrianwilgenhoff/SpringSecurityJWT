package com.aew.users.serviceImpl;

import com.aew.users.domain.User;
import com.aew.users.domain.UserPrinciple;
import com.aew.users.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
*
* @author Adrian
*/


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    UserRepository userRepository;
 
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
      
        User user = userRepository.findByLogin(login)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with -> username or email : " + login));
 
        return UserPrinciple.build(user);
    }
}
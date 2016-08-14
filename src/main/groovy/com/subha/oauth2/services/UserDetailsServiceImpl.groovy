package com.subha.oauth2.services

import com.subha.oauth2.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * Created by user on 8/7/2016.
 */
@Service("userDetailsService")
class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    User user

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SecurityContextHolder.
        println "************ userDetailsService called............"

          return new User(
                    999,
                    "BOND",
                    "BOND007",
                    null,
                    null,
                    AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_A")
            );
        }
    }
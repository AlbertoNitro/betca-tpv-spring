package es.upm.miw.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.upm.miw.documents.core.Role;
import es.upm.miw.documents.core.User;
import es.upm.miw.repositories.core.UserRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final String TOKEN_PASSWORD = "";

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String mobileOrTokenValue) {
        User user = userRepository.findByTokenValue(mobileOrTokenValue);
        if (user != null) {
            return this.userBuilder(user.getMobile(), new BCryptPasswordEncoder().encode(TOKEN_PASSWORD), user.getRoles(), user.isActive());
        } else {
            user = userRepository.findByMobile(mobileOrTokenValue);
            if (user != null) {
                return this.userBuilder(String.valueOf(user.getMobile()), user.getPassword(), new Role[] {Role.AUTHENTICATED},
                        user.isActive());
            } else {
                throw new UsernameNotFoundException("Username Not Found. " + mobileOrTokenValue);
            }
        }
    }

    private org.springframework.security.core.userdetails.User userBuilder(String mobile, String password, Role[] roles, boolean active) {
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        boolean enabled = active;
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.roleName()));
        }
        return new org.springframework.security.core.userdetails.User(mobile, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
    }
}

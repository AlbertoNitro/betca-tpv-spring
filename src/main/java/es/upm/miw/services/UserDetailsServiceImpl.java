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

import es.upm.miw.documents.users.Role;
import es.upm.miw.documents.users.User;
import es.upm.miw.repositories.users.UserRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String mobileOrTokenValue) throws UsernameNotFoundException {
        User user = userRepository.findByTokenValue(mobileOrTokenValue);
        if (user != null) {
            return this.userBuilder(Long.toString(user.getMobile()), new BCryptPasswordEncoder().encode(""), user.getRoles());
        } else {
            try {
                user = userRepository.findOne(Long.valueOf(mobileOrTokenValue));
            } catch (NumberFormatException nfe) {
                throw new UsernameNotFoundException("Usuario no encontrado");
            }
            if (user != null) {
                return this.userBuilder(String.valueOf(user.getMobile()), user.getPassword(), new Role[] {Role.AUTHENTICATED});
            } else {
                throw new UsernameNotFoundException("Usuario no encontrado");
            }
        }
    }

    private org.springframework.security.core.userdetails.User userBuilder(String mobile, String password, Role[] roles) {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.roleName()));
        }
        return new org.springframework.security.core.userdetails.User(mobile, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
    }
}

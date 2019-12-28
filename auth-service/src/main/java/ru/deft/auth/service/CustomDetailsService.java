package ru.deft.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.deft.auth.model.UserEntity;
import ru.deft.auth.securiry.CustomUser;
import ru.deft.auth.securiry.dao.OAuthDao;
/*
 * Created by sgolitsyn on 12/11/19
 */

@Service
@RequiredArgsConstructor
public class CustomDetailsService implements UserDetailsService {
    private final OAuthDao oauthDao;

    @Override
    public CustomUser loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserEntity userEntity = null;
        try {
            userEntity = oauthDao.getUserDetails(username);
            CustomUser customUser = new CustomUser(userEntity);
            return customUser;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
    }
}

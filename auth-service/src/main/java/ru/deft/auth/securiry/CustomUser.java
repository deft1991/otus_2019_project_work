package ru.deft.auth.securiry;

import org.springframework.security.core.userdetails.User;
import ru.deft.auth.model.UserEntity;

import java.util.ArrayDeque;
import java.util.ArrayList;
/*
 * Created by sgolitsyn on 12/11/19
 */

public class CustomUser extends User {
    private static final long serialVersionUID = 1L;

    public CustomUser(UserEntity user) {
//        super(user.getUsername(), user.getPassword(), user.getGrantedAuthoritiesList());
        super("user.getUsername()", "user.getPassword()", new ArrayList<>());
    }
}

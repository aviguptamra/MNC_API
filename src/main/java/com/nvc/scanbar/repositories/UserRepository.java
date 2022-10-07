package com.nvc.scanbar.repositories;

import com.nvc.scanbar.beans.user.UserProfile;
import com.nvc.scanbar.model.User;
import com.nvc.scanbar.model.UserPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, UserPk> {
    User getUserByEmail(String email);

    User getUserByMobile(String mobile);

    User getUserByUserId(String userId);

    List<User> findAllByUserType(String type);
}

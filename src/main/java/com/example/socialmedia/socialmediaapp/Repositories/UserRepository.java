package com.example.socialmedia.socialmediaapp.Repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.socialmedia.socialmediaapp.DAO.Users;

public interface UserRepository extends JpaRepository<Users, BigInteger> {

    @Query(value = "SELECT * FROM users where users.email = :email", nativeQuery = true)
    Users findEmail(@Param("email") String email);

    // @Query(value = "SELECT * FROM users where users.email_verificationHash =
    // :emailHash", nativeQuery = true)
    // Users findByEmailHash(@Param("emailHash") String emailHash);

    @Modifying
    @Query(value = "UPDATE users set users.is_verified = 1 where users.email = :email", nativeQuery = true)
    void updateVerificationStatus(@Param("email") String email);

    @Modifying
    @Query(value = "UPDATE users set users.first_name = :firstname, users.last_name = :lastname, users.phone = :phone, users.address = :address where users.email = :email", nativeQuery = true)
    void updateProfile(@Param("firstname") String firstname, @Param("lastname") String lastname,
            @Param("phone") String phone, @Param("address") String address, @Param("email") String email);
}

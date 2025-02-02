package com.example.socialmedia.socialmediaapp.Repositories;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Query(value = "UPDATE users set users.is_verified = 1 , users.updated_at = CURRENT_TIMESTAMP where users.email = :email", nativeQuery = true)
    void updateVerificationStatus(@Param("email") String email);

    @Modifying
    @Query(value = "UPDATE users set users.first_name = :firstname, users.last_name = :lastname, users.phone = :phone, users.address = :address,users.profile_type = :profileType, users.updated_at = CURRENT_TIMESTAMP where users.email = :email", nativeQuery = true)
    void updateProfile(@Param("firstname") String firstname, @Param("lastname") String lastname,
            @Param("phone") String phone, @Param("address") String address, @Param("email") String email,
            @Param("profileType") int profileType);

    @Modifying
    @Query(value = "UPDATE users set users.last_login = CURRENT_TIMESTAMP where users.email = :email", nativeQuery = true)
    void updateLastLogin(@Param("email") String email);

    @Modifying
    @Query(value = "UPDATE users set users.profile_photo = :filename where users.email = :email", nativeQuery = true)
    void updateProfilePhoto(@Param("filename") String filename, @Param("email") String email);

    @Modifying
    @Query(value = "UPDATE users set users.password = :password where users.email = :email", nativeQuery = true)
    void updatePassword(@Param("password") String password, @Param("email") String email);

    // @Query(value = "SELECT * FROM users where id != :userid", nativeQuery = true)
    @Query(value = "SELECT users.*,CASE WHEN f.senderId = :userid THEN CASE WHEN f.current_status=1 THEN 1 ELSE 0 END ELSE -1 END AS approved from users LEFT JOIN friends as f on users.id=f.recieverId  AND f.senderId = :userid where users.id != :userid ORDER BY users.created_at DESC", nativeQuery = true)
    Page<Object[]> explore(@Param("userid") BigInteger userid, Pageable pageable);

    // @Modifying
    // @Query(value = "UPDATE users set users.email_verificationHash = :emailHash
    // where id = :userid", nativeQuery = true)
    // void updateEmailVerificationHash(@Param("userid") BigInteger userid,
    // @Param("emailHash") String emailHash);

    @Query(value = "SELECT * FROM users where users.id = :userid",nativeQuery = true)
    Users findByUserId(@Param("userid") BigInteger userid);

}

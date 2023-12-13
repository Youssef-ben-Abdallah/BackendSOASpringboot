package com.backend.miniProjetBack.repositories;

import com.backend.miniProjetBack.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    User login(@Param("email") String email, @Param("password") String password);

    @Query("SELECT u.role FROM User u WHERE u.id = :userId")
    Integer findRoleById(@Param("userId") Long userId);

    @Query("SELECT u.nom, SUM(f.amount) AS totalPayments FROM User u JOIN Facture f ON u.id = f.client.id GROUP BY u.id, u.nom ORDER BY amount DESC")
    List<Object[]> findTop5Clients();


}


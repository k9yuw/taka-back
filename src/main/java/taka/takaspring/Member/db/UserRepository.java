package taka.takaspring.Member.db;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Hidden
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(@Param("email") String email);
    Optional<UserEntity> findByEmail(@Param("email") String email);
    boolean existsByStudentNum(@Param("studentNum") String studentNum);
}
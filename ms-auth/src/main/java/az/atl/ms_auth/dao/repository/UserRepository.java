package az.atl.ms_auth.dao.repository;

import az.atl.ms_auth.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUserName(String userName);


    void deleteByUserName(String userName);


}

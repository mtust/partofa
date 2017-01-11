package com.partofa.repository;

import com.partofa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by tust on 09.09.2016.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findByEmail(String email);

    List<User> findByRegionId(Long regionId);

}

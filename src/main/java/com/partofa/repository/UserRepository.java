package com.partofa.repository;

import com.partofa.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tust on 09.09.2016.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    

}

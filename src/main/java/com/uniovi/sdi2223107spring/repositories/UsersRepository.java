package com.uniovi.sdi2223107spring.repositories;

import com.uniovi.sdi2223107spring.entities.User;
import org.springframework.data.repository.CrudRepository;
public interface UsersRepository extends CrudRepository<User, Long>{
}

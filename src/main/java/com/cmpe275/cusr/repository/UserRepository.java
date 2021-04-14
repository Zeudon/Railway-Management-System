package com.cmpe275.cusr.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cmpe275.cusr.model.User;
@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.email = ?1")
	public User findByEmail(String email);

	@Query("Select u from User u WHERE u.userId= ?1")
	public User findbyId(Long id);
}

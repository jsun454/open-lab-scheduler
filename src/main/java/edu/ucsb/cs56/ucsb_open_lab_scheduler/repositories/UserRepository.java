package edu.ucsb.cs56.ucsb_open_lab_scheduler.repositories;

import edu.ucsb.cs56.ucsb_open_lab_scheduler.entities.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  public List<User> findByEmail(String email);
}
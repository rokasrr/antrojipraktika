package lt.rokas.antrojipraktika.v2.v2.repository;

import lt.rokas.antrojipraktika.v2.v2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{

}
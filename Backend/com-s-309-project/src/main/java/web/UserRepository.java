package web;

import java.util.Collection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserRepository extends CrudRepository<CyDormUser, Integer> {

	Collection<CyDormUser> findAll();

	CyDormUser findById(@Param("netID") String netID);

	CyDormUser save(CyDormUser user);

}

package web;

import java.util.Collection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface GroceryInterface extends CrudRepository<Grocery, Integer> {

	Collection<Grocery> findAll();

	Grocery findById(@Param("id") int id);

	Grocery save(Grocery item);
	
    void delete(Grocery deleted);

}

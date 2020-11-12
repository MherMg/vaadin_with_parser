package am.test.repository;


import am.test.model.MaleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaleNameRepository extends JpaRepository<MaleName, String> {

}

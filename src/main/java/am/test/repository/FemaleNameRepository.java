package am.test.repository;


import am.test.model.FemaleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FemaleNameRepository extends JpaRepository<FemaleName, String> {

}

package am.test.repository;


import am.test.model.FemaleSurname;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FemaleSurnameRepository extends JpaRepository<FemaleSurname, String> {

}

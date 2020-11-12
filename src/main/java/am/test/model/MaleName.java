package am.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by
 * Mher Petrosyan
 * Email mher13.02.94@gmail.ru
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "male_name")
public class MaleName {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
}

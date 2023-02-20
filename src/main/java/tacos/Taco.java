package tacos;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import java.util.Date;

@Data
@Entity
public class Taco {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)//데이터베이스가 자동 생성
	private Long id;
	private Date createdAt;
	
	@NotNull
	@Size(min=5, message="Name must be at least 5 characters long")
	private String name;

	@ManyToMany(targetEntity = Ingredient.class)
	@Size(min=1, message="You must choose at least 1 ingredient")
	private List<Ingredient> ingredients;

	// Taco 객체 생성전에 createdAt 값 설정
	@PrePersist
	void createdAt() {
		this.createdAt = new Date();
	}
}
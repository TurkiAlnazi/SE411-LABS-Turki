package psu.edu.ch06.crud04.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("product")
public record Product (
		@Id Integer id,
		String name,
		String description,
		Double price
) {
	
}

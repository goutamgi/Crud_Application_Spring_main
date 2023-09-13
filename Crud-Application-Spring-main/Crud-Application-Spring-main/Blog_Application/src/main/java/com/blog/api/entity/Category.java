package com.blog.api.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="categories")
public class Category {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;
	
	@NotBlank
	@Size(min=4,message = "Min size of categoryTittle is 4")
	private String categoryTitle;
	@NotBlank
	@Size(min=10,message = "min size of categoryDescription is 10 ")
	private String categoryDescription;
	
	
	@OneToMany(mappedBy = "category",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	private List<PostEntity>post=new ArrayList<>();
	

}

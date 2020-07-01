package net.codejava;

import java.beans.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name="product")
public class Product {
		
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@Column(length=45, nullable=false, unique=false)
	private String name; 
	
	@Column(length=45, nullable=false, unique=false)
	private String brand; 
	
	@Column(length=45, nullable=false, unique=false)
	private String madein;
	
	@Column(nullable=false, unique=false)
	private float price;
	
	@Column(name = "product_photo", length=245, nullable=true)
	private String productphoto; 

	protected Product() {
	}
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getMadein() {
		return madein;
	}
	public void setMadein(String madein) {
		this.madein = madein;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}

 	public String getProductphoto() {
		return productphoto;
	}

	public void setProductphoto(String productphoto) {
		this.productphoto = productphoto;
	}  
	
	@Transient 
	public String getProductPhotoPath() {
		if (productphoto == null || id == null) return null;
		
		return "/product-photos/" + id + "/" + productphoto; 
	}
}

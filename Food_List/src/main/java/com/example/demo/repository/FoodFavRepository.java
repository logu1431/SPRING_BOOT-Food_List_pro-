package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.modal.Food_fav;
import java.util.List;

public interface FoodFavRepository extends JpaRepository<Food_fav, Integer> {
	List<Food_fav> findByVeg(String veg);
	List<Food_fav> findByNonVeg(String nonVeg);
	List<Food_fav> findByVegPriceBetween(int min, int max);
	List<Food_fav> findByReasonContaining(String keyword);
}

package com.cafehaland.users.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cafehaland.Dao.ProductForm;
import com.cafehaland.Dao.StatisticProduct;
import com.cafehaland.exception.ProductNotFoundException;
import com.cafehaland.pojo.Ingredient;
import com.cafehaland.pojo.OrderDetail;
import com.cafehaland.pojo.Product;
import com.cafehaland.pojo.ProductSize;
import com.cafehaland.pojo.Syntax;
import com.cafehaland.pojo.id.SyntaxId;
import com.cafehaland.users.repository.IngredientRepository;
import com.cafehaland.users.repository.OrderDetailRepository;
import com.cafehaland.users.repository.ProductRepository;
import com.cafehaland.users.repository.ProductSizeRepository;
import com.cafehaland.users.repository.SyntaxReopository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {
	public static final int PAGE_PRO_NUM = 4;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private ProductSizeRepository productSizeRepo;
	
	@Autowired 
	private IngredientRepository ingredientRepo;
	
	@Autowired
	private SyntaxReopository syntaxReopo;
	
	@Autowired
	private OrderDetailRepository orderDetailRepo;
	
	public Page<Product> listPage(int pageNum, String sortDir, String fieldName, String keyword) {
		Sort sort = Sort.by(fieldName);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		PageRequest pageRequest = PageRequest.of(pageNum - 1, PAGE_PRO_NUM, sort);
		Page<Product> listProducts = productRepo.findAll(keyword, pageRequest);
		return listProducts;
	}
	
	public Product save(Product product) {
		return productRepo.save(product);
	}
	
	public Product get(Integer id) throws ProductNotFoundException {
		try {
			return productRepo.findById(id).get();
		} catch (Exception e) {
			throw new ProductNotFoundException("Could not find any Product with ID " + id);
		}
	}
	
	public void updateEnabled(Integer id, boolean enabled) throws ProductNotFoundException {
		Long count = productRepo.countById(id);
		if (count == null || count ==0) {
			throw new ProductNotFoundException("Could not find any Product with ID " + id);
		} else {
			productRepo.updateEnabled(id, enabled);
		}
	}
	
	public void delete(Integer id) throws ProductNotFoundException {
		try {
			Product product = productRepo.findById(id).get();
			productRepo.deleteById(id);
		} catch (Exception e) {
			throw new ProductNotFoundException("Could not find any Product with ID " + id);
		}
	}
	
	public List<Ingredient> listIngredients(){
		return ingredientRepo.findAll();
	}
	
	public void saveProductSize(ProductSize productSize) {
		productSizeRepo.save(productSize);
	}
	
	public ProductSize getProductSize(Integer id) {
		return productSizeRepo.findById(id).get();
	}
	
	public void saveSyntax(Syntax syntax) {
		syntaxReopo.save(syntax);
	}
	
	public boolean inSyntaxs(SyntaxId id) {
		Long count = syntaxReopo.countById(id);
		if (count == null || count == 0) {
			return false;
		}
		return true;
	}
	
	public void deleteSyntax(SyntaxId id) {
		syntaxReopo.deleteByProductSizeAndIngredient(new ProductSize(id.getProductSizeId()), new Ingredient(id.getIngredientId()));
	}
	
	public boolean isUniqueName(Integer id, String name) {
		Product product = productRepo.findProductByName(name);
		if (product == null) return true;
		
		if(product != null && product.getId() != id) {
			return false;
		}
		
		return true;
	}
	
	public Product changeProductFormToProduct(ProductForm productForm) {
		Product product = new Product();
		product.setId(productForm.getId());
		product.setName(productForm.getName());
		product.setDescription(productForm.getDescription());
		product.setEnabled(productForm.isEnabled());
		product.setDate(new Date());
		product.setImage(productForm.getImage());
		
		List<ProductSize> productSizes = new ArrayList<>();
		
		for (var size : productForm.getSizes().entrySet()) {
			ProductSize productSize = new ProductSize();
			productSize.setPrice(size.getValue());
			productSize.setProduct(product);
			productSize.setSize(size.getKey());
			productSizes.add(productSize);
		}
		
		product.setProductSizes(productSizes);
		
		return product;
	}
	
	public List<Integer> listIngredientByProduct(Integer id){
		return productRepo.listIngredientByProduct(id);
	}
	
//	API
	public Product getApi(Integer id) throws ProductNotFoundException {
		try {
			Product product = productRepo.findById(id).get();
			Product productMain = new Product();
			productMain.setId(product.getId());
			productMain.setName(product.getName());
			productMain.setDescription(product.getDescription());
			productMain.setProductSizes(product.getProductSizes());
			productMain.setUser(null);
			
			return productMain;
		} catch (Exception e) {
			throw new ProductNotFoundException("Could not find any Product with ID " + id);
		}
	}
	
	public List<StatisticProduct> getStatisticProductsDay(){
		List<StatisticProduct> listStatisticProducts = new ArrayList<>();
		
		productRepo.findAll().forEach(
					pro -> {
						listStatisticProducts.add(new StatisticProduct(pro.getName(), 0, 0));
					}
				);
		
		Date dateNow = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = sdf.format(dateNow);
		List<OrderDetail> listOrderDetails = orderDetailRepo.findAll();
		listOrderDetails.forEach(
					orderDetail -> {
						String dateOrder = sdf.format(orderDetail.getOrder().getDate());
						if (dateString.equals(dateOrder)) {
							for (int i = 0 ; i < listStatisticProducts.size(); ++i) {
								if (listStatisticProducts.get(i).getNameProduct().equals(orderDetail.getProductSize().getProduct().getName())) {
									listStatisticProducts.get(i).setQuantity(listStatisticProducts.get(i).getQuantity() + orderDetail.getQuntity());
									listStatisticProducts.get(i).setTotalPrice(listStatisticProducts.get(i).getTotalPrice() + orderDetail.getProductSize().getPrice());
									break;
								}
							}
						}
					}
				);
		Collections.sort(listStatisticProducts, (o1, o2) -> o1.getNameProduct().compareTo(o2.getNameProduct()));
		return listStatisticProducts;
	}
	
}

/**
 * 
 */
package dataaccesslayer;

import java.util.List;

import model.Product;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon Chowdhury 
 *
 */
public interface ProductDBIF {
	List<Product> findAllProducts() throws DataAccessException;
	Product getProductBySku(int sku) throws DataAccessException;	
}


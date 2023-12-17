/**
 * 
 */
package control;

import java.util.ArrayList;
import java.util.List;

import dataaccesslayer.DataAccessException;
import dataaccesslayer.ProductDB;
import model.Product;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon Chowdhury 
 *
 */
public class ProductController {

	private ProductDB productDB;
	/**
	 * @throws DataAccessException 
	 * 
	 */
	public ProductController() throws DataAccessException {
		this.productDB = new ProductDB();
	}
	public ArrayList<Product> findAllProductFromDB() throws DataAccessException {
		return (ArrayList<Product>) productDB.findAllProducts();
	}

	public List<Product> getProductsBySkuList(List<Integer> skus) throws DataAccessException {
	    List<Product> products = new ArrayList<>();
	    for (int sku : skus) {
	        products.add(productDB.getProductBySku(sku));
	    }
	    return products;
	}

	public Product getProductBySku(int sku) throws DataAccessException {
	    return productDB.getProductBySku(sku);
	}


}
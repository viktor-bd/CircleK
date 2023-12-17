/**
 * 
 */
package control;

import java.util.ArrayList;

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
		productDB = new ProductDB();
	}
	public ArrayList<Product> findAllProductFromDB() throws DataAccessException {
		return (ArrayList<Product>) productDB.findAllProducts();
	}

}

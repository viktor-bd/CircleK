/**
 * 
 */
package dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import model.Ingredient;
import model.Product;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon
 *         Chowdhury
 *
 */
public class ProductDB implements ProductDBIF {
	private Connection connection;
	private static final String findAllProductsQ = "SELECT * From dbo.Product";
	private static final String getProductBySkuQ = "SELECT * FROM dbo.Product WHERE sku = ?";
	private static final String findAllIngredientsOfProductQ = "SELECT I.name, I.purchasePrice, I.supplier_id, I.ingredient_id, recipe.product_sku FROM dbo.Ingredient AS I INNER JOIN Ingredient_Product_Recipe AS recipe ON I.ingredient_id = recipe.ingredient_id WHERE recipe.product_sku = ?";
	private PreparedStatement findAllProducts;
	private PreparedStatement getProductBySku;
	private PreparedStatement findAllIngredientsOfProduct;

	public ProductDB() throws DataAccessException {
		try {
			connection = DBConnection.getInstance().getDBcon();
			findAllProducts = connection.prepareStatement(findAllProductsQ);
			getProductBySku = connection.prepareStatement(getProductBySkuQ);
			findAllIngredientsOfProduct = connection.prepareStatement(findAllIngredientsOfProductQ);
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not prepare statement");
		}
	}

	@Override
	public List<Product> findAllProducts() throws DataAccessException {
		ArrayList<Product> products = new ArrayList<>();

		try {
			ResultSet rs = findAllProducts.executeQuery();

			while (rs.next()) {
				Product product = buildProductObject(rs);
				ArrayList<Ingredient> ingredients = findIngredientByProductSku(product.getSku());
				product.setIngredients(ingredients);
				products.add(product);
			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception appropriately
		}
		return products;

	}

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Product buildProductObject(ResultSet rs) throws SQLException {
		Product product = new Product(rs.getInt("sku"), rs.getString("name"), rs.getDouble("salesPrice"),
				rs.getString("product_type"));
		return product;
	}

	@Override
	public Product getProductBySku(int sku) throws DataAccessException {
		Product product = null;
		try {
			ResultSet rs = getProductBySku.executeQuery();
			while(rs.next()) {
				product = buildProductObject(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;

	}

	public ArrayList<Ingredient> findIngredientByProductSku(int sku) throws DataAccessException {
		ArrayList<Ingredient> ingredients = new ArrayList<>();

		try {
			findAllIngredientsOfProduct.setInt(1, sku);
			ResultSet rs = findAllIngredientsOfProduct.executeQuery();

			while (rs.next()) {
				Ingredient ingredient = buildIngredientObject(rs);
				// adds product to list
				ingredients.add(ingredient);
			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception appropriately
		}

		return ingredients;

	}

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Ingredient buildIngredientObject(ResultSet rs) throws SQLException {
		Ingredient ingredient = new Ingredient(rs.getInt("ingredient_id"), rs.getString("name"),
				rs.getDouble("purchasePrice"), null);
		return ingredient;

	}
}


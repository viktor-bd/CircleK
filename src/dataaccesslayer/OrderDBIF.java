package dataaccesslayer;

import model.Order;
import model.Product;

import java.util.List;

public interface OrderDBIF {
    Order getOrderWithOrderId(int orderId, List<Product> products);
}

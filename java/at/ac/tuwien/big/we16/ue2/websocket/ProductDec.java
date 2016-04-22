package at.ac.tuwien.big.we16.ue2.websocket;

import at.ac.tuwien.big.we16.ue2.Product;
import at.ac.tuwien.big.we16.ue2.ProductPool;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Created by Marlene on 21.04.2016.
 */
public class ProductDec implements Decoder.Text<Product> {
    ProductPool productPool = ProductPool.getInstance();

    @Override
    public Product decode(String s) throws DecodeException {
        return productPool.getProductById(s);
    }

    @Override
    public boolean willDecode(String s) {
        Product product = new Product();
        product.setId(s);

        return productPool.getProducts().contains(product);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {

    }
}
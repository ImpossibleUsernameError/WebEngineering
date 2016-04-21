package at.ac.tuwien.big.we16.ue2.websocket;

import at.ac.tuwien.big.we16.ue2.Product;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Created by Marlene on 21.04.2016.
 */
public class ProductDec implements Decoder.Text<Product> {
    @Override
    public Product decode(String s) throws DecodeException {
        Product p=new Product();
        p.setId("kakhaufen");
        p.setName("scheißhaufen");
        return p;
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {

    }
}
package lv.javaguru.java2.console.businesslogic;

import lv.javaguru.java2.console.businesslogic.api.Error;
import lv.javaguru.java2.console.businesslogic.api.Response;
import lv.javaguru.java2.console.database.ProductDAO;
import lv.javaguru.java2.console.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

import static lv.javaguru.java2.console.domain.ProductBuilder.createProduct;

public interface AddProductService {

    Response addProduct(String title, String description);

}

@Component
class AddProductServiceImpl implements AddProductService {

    @Autowired private ProductDAO dao;
    @Autowired private AddProductValidator validator;

    @Override
    @Transactional
    public Response addProduct(String title, String description) {
        List<Error> validationErrors = validator.validate(title, description);
        if (!validationErrors.isEmpty()) {
            return Response.createFailResponse(validationErrors);
        }

        Product product = createProduct()
                .withTitle(title)
                .withDescription(description).build();

        dao.save(product);

        return Response.createSuccessResponse();
    }
}

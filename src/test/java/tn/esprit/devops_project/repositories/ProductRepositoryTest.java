package tn.esprit.devops_project.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;

import java.util.HashSet;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    StockRepository stockRepository;

    @Test
    public void itShouldFindProductByCategory() {
        //Arrange
        Product product = Product.builder()
                .idProduct(1L)
                .category(ProductCategory.BOOKS)
                .title("clean Code")
                .stock(null)
                .quantity(2)
                .price(17.99f)
                .build();
        Product savedProduct = productRepository.save(product);
        //Act
        List<Product> byCategory = productRepository.findByCategory(ProductCategory.BOOKS);
        //Assert
        Assertions.assertThat(byCategory.size()).isEqualTo(1);
        Assertions.assertThat(byCategory.get(0)).isEqualTo(savedProduct);
    }

    @Test
    public void itShouldFindTwoProductByCategory() {
        //Arrange
        Product product = Product.builder()
                .category(ProductCategory.BOOKS)
                .title("clean Code")
                .stock(null)
                .quantity(2)
                .price(17.99f)
                .build();

        Product product2 = Product.builder()
                .category(ProductCategory.BOOKS)
                .title("clean Code")
                .stock(null)
                .quantity(2)
                .price(17.99f)
                .build();
        Product savedProduct = productRepository.save(product);
        Product savedProduct2 = productRepository.save(product2);
        //Act
        List<Product> byCategory = productRepository.findByCategory(ProductCategory.BOOKS);
        //Assert
        Assertions.assertThat(byCategory.size()).isEqualTo(2);
    }

    @Test
   public void itShouldFindProductByStockId(){

        //Arrange
        Stock stock = Stock.builder()
                .idStock(1L)
                .title("books")
                .products(new HashSet<>())
                .build();
        stockRepository.save(stock);
        Product product = Product.builder()
                .category(ProductCategory.BOOKS)
                .title("clean Code")
                .stock(stock)
                .quantity(2)
                .price(17.99f)
                .build();
        Product product2 = Product.builder()
                .category(ProductCategory.BOOKS)
                .title("Book")
                .stock(stock)
                .quantity(1)
                .price(14.99f)
                .build();
        Product savedProd = productRepository.save(product);
        Product savedProd2 = productRepository.save(product2);

        //Act
        List<Product> byStockIdStock = productRepository.findByStockIdStock(stock.getIdStock());

        //Assert
        Assertions.assertThat(byStockIdStock.size()).isEqualTo(2);

    }


}

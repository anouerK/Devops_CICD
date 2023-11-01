package tn.esprit.devops_project.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {


    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void itShouldAddProduct() {
        //Arrange
        Product product = Product.builder()
                .idProduct(1L)
                .category(ProductCategory.BOOKS)
                .title("clean Code")
                .stock(null)
                .quantity(2)
                .price(17.99f)
                .build();
    when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
    when(stockRepository.findById(1L)).thenReturn(Optional.ofNullable(Mockito.mock(Stock.class)));
        //Act
        Product product1 = productService.addProduct(product, 1L);
        //Assert
        Assertions.assertThat(product1).isEqualTo(product);
    }

    @Test
    public void itShouldRetrieveProduct() {
        // Arrange
        Long productId = 1L;
        Product expectedProduct = Product.builder()
                .idProduct(productId)
                .category(ProductCategory.BOOKS)
                .title("Clean Code")
                .stock(null)
                .quantity(2)
                .price(17.99f)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));

        // Act
        Product actualProduct = productService.retrieveProduct(productId);

        // Assert
        Assertions.assertThat(actualProduct).isEqualTo(expectedProduct);
    }

    @Test
    public void itShouldRetrieveAllProducts() {
        // Arrange
        Product product = Product.builder()
                .idProduct(1L)
                .category(ProductCategory.BOOKS)
                .title("Clean Code")
                .stock(null)
                .quantity(2)
                .price(17.99f)
                .build();
        Product product1 = Product.builder()
                .idProduct(2L)
                .category(ProductCategory.BOOKS)
                .title("bad Code")
                .stock(null)
                .quantity(1)
                .price(14.99f)
                .build();
        List<Product> expectedProducts = Arrays.asList(
                product
        );
        when(productRepository.findAll()).thenReturn(expectedProducts);

        // Act
        List<Product> actualProducts = productService.retreiveAllProduct();

        // Assert
        Assertions.assertThat(actualProducts).isEqualTo(expectedProducts);
    }

    @Test
    public void itShouldDeleteProduct() {
        // Arrange
        Long productId = 1L;
        // Act
        productService.deleteProduct(productId);
        // Assert
        Assertions.assertThatCode(() -> productService.retrieveProduct(productId))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Product not found");
    }

    @Test
    public void itShouldRetrieveProductStock() {
        // Arrange
        Long stockId = 1L;

        Product product = Product.builder()
                .idProduct(1L)
                .category(ProductCategory.BOOKS)
                .title("Clean Code")
                .stock(null)
                .quantity(2)
                .price(17.99f)
                .build();
        List<Product> expectedProducts = Arrays.asList(
                product
        );
        when(productRepository.findByStockIdStock(stockId)).thenReturn(expectedProducts);

        // Act
        List<Product> actualProducts = productService.retreiveProductStock(stockId);

        // Assert
        Assertions.assertThat(actualProducts).isEqualTo(expectedProducts);
    }

    }
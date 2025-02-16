package ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import com.codeborne.selenide.SelenideElement;

public class ProductPage {
    private final SelenideElement productName = $("h1#name.h2.js-h1");
    private final SelenideElement productSize = $("label[data-selector='aspect-item'][data-aspect-name='37']");
    private final SelenideElement productPrice = $("div[data-selector='price']");
    private final SelenideElement addToCartButton = $("button[data-selector='add-to-cart-btn']");
    private final SelenideElement quantityInput = $("input[data-selector='quantity']");
    private final SelenideElement catalogButton = $("a[data-selector='catalog-menu']");
    private final SelenideElement productColor = $("div.product-color");
    private String savedName;
    private String savedSize;
    private String savedColor;
    private String savedPrice;
    private int savedQuantity;
    private final SelenideElement productTitle = $("#name");

    public String getProductName() {
        return productName.shouldBe(visible).text();
    }

    public String getProductSize() {
        return productSize.shouldBe(visible).text();
    }

    public String getProductPrice() {
        return productPrice.shouldBe(visible).text().replaceAll("[^0-9]", "");
    }

    public void setQuantity(int quantity) {
        quantityInput.shouldBe(visible).setValue(String.valueOf(quantity));
    }

    public void addToCart() {
        addToCartButton.shouldBe(visible).click();
    }

    public void returnToCatalog() {
        catalogButton.shouldBe(visible).click();
    }

    public void saveProductDetails(int quantity) {
        savedName = getProductName();
        savedSize = getProductSize();
        savedColor = productColor.shouldBe(visible).text();
        savedPrice = getProductPrice();
        savedQuantity = quantity;
    }

    public void verifyProductDetails() {
        String name = productTitle.getText();
        String color = productColor.getText();
        String size = productSize.getText();

        assertTrue(name.toLowerCase().contains(color.toLowerCase()),
            "Product color '" + color + "' should be mentioned in name '" + name + "'");

        assertTrue(!size.isEmpty(), "Size should be specified");

        String price = productPrice.getText().replaceAll("[^0-9]", "");
        assertTrue(!price.isEmpty() && Integer.parseInt(price) > 0,
            "Price should be a positive number");
    }

    public ProductDetails getSavedDetails() {
        return new ProductDetails(savedName, savedSize, savedColor, savedPrice, savedQuantity);
    }
} 
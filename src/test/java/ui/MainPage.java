package ui;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.openqa.selenium.By;

import com.codeborne.selenide.CollectionCondition;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import com.github.javafaker.Faker;

public class MainPage extends BaseTest {
    private final Faker faker = new Faker();
    
    private final SelenideElement productLink = $("a.product-listing-card__preview-link");
    private final SelenideElement addToCartButton = $("button[data-selector='add-to-cart-btn']");
    private final SelenideElement productName = $("#name");
    private final SelenideElement productSize = $("label[data-selector='aspect-item'][data-aspect-name='37']");
    private final SelenideElement cartLink = $("a[data-selector='basket-desktop']");
    private final SelenideElement cartProductName = $("a[data-selector='js-title']");
    private final ElementsCollection cartProductProps = $$(".cart-item-default__props-item");
    private final SelenideElement productPrice = $("div.price__regular");
    private final SelenideElement cartProductPrice = $("div.cart-item-default__price");

    private final SelenideElement catalogLink = $("a[href='/catalog']");
    private final SelenideElement sidebarTotalQuantity = $("span.js-more");
    private final ElementsCollection cartItems = $$(".cart-item-default.js-item");

    private final SelenideElement loginButton = $("a[data-selector='header-menu-profile-toggler']");
    private final SelenideElement emailTabButton = $("button[data-selector='toggle-group-navs-button'][data-tab-id='auth-tab-email']");
    private final SelenideElement emailInput = $("input[data-selector='auth-form-input-email']");
    private final SelenideElement passwordInput = $("input[data-selector='auth-form-input-password']");
    private final SelenideElement submitButton = $("button[data-selector='auth-form-email-submit']");

    private final SelenideElement goToCartButton = $("a[href='/my/shopping-cart']");

    private final SelenideElement catalogButton = $("#rubrics-toggle");
    private final SelenideElement catalogMenu = $(".mega-burger__content");
    private final SelenideElement tShirtsLink = $(".mega-burger-content-menu__link[href='/r-futbolki-1286190']");
    private final SelenideElement specificProduct = $(By.cssSelector("a[href='/p-4f-m-4fss23tftsm172-31s-cycling-jersey-V84836433']"));

    private final SelenideElement pantsCategory = $("a.mega-burger-content-menu__title[href='/r-bryuki-1286203']");

    private String selectedProductName;
    private String selectedProductSize;
    private String selectedProductColor;
    private String selectedProductPrice;

    private String firstProductName;
    private String secondProductName;
    private String thirdProductName;
    
    private final SelenideElement productTitle = $("#name");
    private final ElementsCollection cartItemTitles = $$("a.cart-item-default__title");

    private final SelenideElement plusButton = $("button[data-selector='quantity-group-plus']");
    
    private final SelenideElement productQuantityInput = $("input[data-selector='quantity-group-input'][name='basket_add[quantity]']");
    
    private final ElementsCollection cartQuantityInputs = $$("input[data-selector='quantity-group-input']");

    private String firstProductPrice;
    private String secondProductPrice;
    private String thirdProductPrice;
    
    private final ElementsCollection cartPrices = $$(".cart-item-default__price");

    private final SelenideElement totalSumElement = $("div.basket-summary__price.js-price");

    public void selectProduct() {
        productLink.shouldBe(visible).click();
    }

    public void selectSize() {
        SelenideElement availableSize = $("label[data-selector='aspect-item']:not([disabled])");
        
        availableSize.shouldBe(visible, Duration.ofSeconds(10))
                     .click();
        
        Selenide.sleep(1000);
    }

    public void saveSelectedProductDetails() {
        firstProductName = productTitle.getText().split("Цвет")[0].trim();
        firstProductPrice = productPrice.getText().replaceAll("[^0-9]", "");
        System.out.println("Saved first product: " + firstProductName + ", price: " + firstProductPrice);
    }

    public void saveSecondProductDetails() {
        secondProductName = productTitle.getText().split("Размер")[0].trim();
        secondProductPrice = productPrice.getText().replaceAll("[^0-9]", "");
        System.out.println("Saved second product: " + secondProductName + ", price: " + secondProductPrice);
    }

    public void saveThirdProductDetails() {
        thirdProductName = productTitle.getText().split("Цвет")[0].trim();
        thirdProductPrice = productPrice.getText().replaceAll("[^0-9]", "");
        System.out.println("Saved third product: " + thirdProductName + ", price: " + thirdProductPrice);
    }

    public void verifyProductsInCart() {
        $(".page-basket.js-basket-layout").shouldBe(visible, Duration.ofSeconds(15));
        
        cartItems.shouldHave(CollectionCondition.allMatch("Все элементы корзины должны быть видны", el -> el.isDisplayed()), Duration.ofSeconds(10));
        
        cartItems.shouldHave(CollectionCondition.size(3), Duration.ofSeconds(15));
        
        List<String> actualNames = cartItemTitles.texts();
        
        assertTrue(actualNames.contains(firstProductName), "Первый товар отсутствует в корзине");
        assertTrue(actualNames.contains(secondProductName), "Второй товар отсутствует в корзине");
        assertTrue(actualNames.contains(thirdProductName), "Третий товар отсутствует в корзине");
        
        for (int i = 0; i < cartItems.size(); i++) {
            String actualPrice = cartPrices.get(i).getText().replaceAll("[^0-9]", "");
            String currentCartProductName = cartItemTitles.get(i).getText();
            
            if (currentCartProductName.equals(firstProductName)) {
                assertEquals(firstProductPrice, actualPrice);
            } else if (currentCartProductName.equals(secondProductName)) {
                assertEquals(secondProductPrice, actualPrice);
            } else if (currentCartProductName.equals(thirdProductName)) {
                assertEquals(thirdProductPrice, actualPrice);
            }
        }
        
        String totalText = totalSumElement.getText().replaceAll("[^0-9]", "");
        int totalSum = Integer.parseInt(totalText);
        assertTrue(totalSum > 0, "Общая сумма должна быть больше нуля");
    }

    public void addToCart() {
        addToCartButton.shouldBe(visible, Duration.ofSeconds(10));
        addToCartButton.shouldBe(enabled);
        
        addToCartButton.click();
        Selenide.sleep(2000);
    }

    public void goToCart() {
        goToCartButton.shouldBe(visible, Duration.ofSeconds(15)).click();
        System.out.println("Current URL after click: " + Selenide.webdriver().driver().url());
    }

    public String getProductName(int index) {
        return selectedProductName;
    }

    public String getProductPrice(int index) {
        return selectedProductPrice;
    }

    public String getProductColor(int index) {
        return selectedProductColor;
    }

    public String getProductSize(int index) {
        return selectedProductSize;
    }

    public int getProductQuantity(int index) {
        SelenideElement quantityElement = cartItems.get(index).$("input[data-selector='quantity-input']");
        return Integer.parseInt(quantityElement.getValue());
    }

    public int getTotalQuantity() {
        try {
            sidebarTotalQuantity.should(exist, Duration.ofSeconds(15));
            String totalText = sidebarTotalQuantity.getText().split(" ")[0];
            
            if (DEBUG) {
                System.out.println("[DEBUG] Total quantity raw text: " + totalText);
            }
            
            return Integer.parseInt(totalText);
        } catch (com.codeborne.selenide.ex.ElementNotFound | NumberFormatException e) {
            System.out.println("Ошибка получения количества товаров: " + e.getMessage());
            return 0;
        }
    }

    public void setQuantity(int quantity) {
        plusButton.shouldBe(visible, Duration.ofSeconds(10));
        
        for (int i = 1; i < quantity; i++) {
            plusButton.click();
            Selenide.sleep(1000);
        }
    }

    public void returnToCatalog() {
        catalogLink.shouldBe(visible);
        
        executeJavaScript("arguments[0].click()", catalogLink);
        
        Selenide.sleep(2000);
    }

    public void verifyTotalQuantityInSidebar() {
        sidebarTotalQuantity.shouldBe(visible, Duration.ofSeconds(15))
                            .shouldHave(text("4 шт"));
    }

    public int getProductCount() {
        return cartItems.size();
    }

    public void login() {
        loginButton.shouldBe(visible, Duration.ofSeconds(10));
        executeJavaScript("arguments[0].click()", loginButton);
        Selenide.sleep(1000);
        
        emailTabButton.shouldBe(visible).click();
        
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 12);
        
        emailInput.shouldBe(visible).setValue(email);
        passwordInput.shouldBe(visible).setValue(password);
        
        submitButton.shouldBe(visible).click();
        
        Selenide.sleep(1000);
    }

    public void returnToMainPage() {
        // Кликаем по логотипу, чтобы вернуться на главную
        $("a.header-logo").click();
        Selenide.sleep(2000);
    }
    
    public void openCatalog() {
        catalogButton.shouldBe(visible, Duration.ofSeconds(10)).click();
        
        Selenide.sleep(1000);
        
        catalogMenu.should(exist, Duration.ofSeconds(10));
    }
    
    public void selectTShirtsCategory() {
        tShirtsLink.shouldBe(visible).click();
    }
    
    public void selectSpecificTShirt() {
        specificProduct.click();
    }

    public void selectPantsCategory() {
        pantsCategory.shouldBe(visible, Duration.ofSeconds(10))
                    .click();
        
        Selenide.sleep(1000);
    }

    public void verifyTotalSum() {
        String totalSumText = totalSumElement.getText().replaceAll("[^0-9]", "");
        int cartTotalSum = Integer.parseInt(totalSumText);
        
        int productsSum = Integer.parseInt(firstProductPrice) * 2 + 
                         Integer.parseInt(secondProductPrice) + 
                         Integer.parseInt(thirdProductPrice);
        
        int additionalCosts = cartTotalSum - productsSum;
        
        System.out.println("\n=== Итоговая информация ===");
        System.out.printf("""
            Проверено позиций: %d
            Общее количество товаров: %d
            Сумма товаров: %d руб.
            Общая сумма заказа: %d руб.
            Дополнительные расходы: %d руб.
            """, 
            cartItems.size(),
            getTotalQuantity(),
            productsSum,
            cartTotalSum,
            additionalCosts
        );
        
        assertTrue(cartTotalSum >= productsSum, 
            "Cart total (" + cartTotalSum + ") should be greater than or equal to products sum (" + productsSum + ")");
    }

    public int getCartTotal() {
        return Integer.parseInt(totalSumElement.getText().replaceAll("[^0-9]", ""));
    }
    
    public int getAdditionalCosts() {
        return getCartTotal() - (Integer.parseInt(firstProductPrice)*2 + 
               Integer.parseInt(secondProductPrice) + 
               Integer.parseInt(thirdProductPrice));
    }

    public void waitForCartToLoad() {
        Selenide.webdriver().shouldHave(urlContaining("/shopping-cart"), Duration.ofSeconds(20));
        $(".page-basket.js-basket-layout").shouldBe(visible, Duration.ofSeconds(15));
        cartItems.shouldHave(sizeGreaterThanOrEqual(1), Duration.ofSeconds(20));
    }
} 
package ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

import static config.TestConfig.NAVISALE_URL;
import io.qameta.allure.Story;

class NavisaleTest extends BaseTest {
    @BeforeEach
    public void setUpDriver() {
        setUp();
    }

    @Test
    @Story("Проверка добавления товаров в корзину на сайте Navisale")
    void addToCartTest() {
        open(NAVISALE_URL);
        
        MainPage mainPage = new MainPage();
        mainPage.login();
        
        mainPage.selectProduct();
        mainPage.selectSize();
        mainPage.saveSelectedProductDetails();
        mainPage.addToCart();
        mainPage.setQuantity(2);
        
        mainPage.returnToMainPage();
        
        mainPage.openCatalog();
        mainPage.selectTShirtsCategory();
        mainPage.selectSpecificTShirt();
        mainPage.selectSize();
        mainPage.saveSecondProductDetails();
        mainPage.addToCart();
        
        mainPage.returnToMainPage();
        
        mainPage.openCatalog();
        mainPage.selectPantsCategory();
        mainPage.selectProduct();
        mainPage.saveThirdProductDetails();
        mainPage.addToCart();
        
        mainPage.goToCart();
        mainPage.waitForCartToLoad();
        
        mainPage.verifyProductsInCart();
        mainPage.verifyTotalQuantityInSidebar();
        mainPage.verifyTotalSum();
    }
}
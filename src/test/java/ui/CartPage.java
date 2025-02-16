package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import com.codeborne.selenide.ElementsCollection;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import com.codeborne.selenide.SelenideElement;

public class CartPage {
    private final ElementsCollection cartItems = $$(".cart-item-default");
    private final SelenideElement totalQuantity = $("div.cart-sidebar__total-quantity");
    private final SelenideElement totalSumElement = $("div.basket-summary__price.js-price");
    private final SelenideElement checkoutButton = $("button.checkout");
    
    public void verifyCartItem(String name, String size, String color, String price, int quantity) {
        SelenideElement item = cartItems.findBy(text(name));
        item.$(".cart-item-default__props-item").shouldHave(text("Размер (EU/US): " + size));
        item.$(".cart-item-default__props-item").shouldHave(text("Цвет: " + color));
        item.$(".cart-item-default__price").shouldHave(text(price));
        item.$(".cart-item-default__quantity").shouldHave(text(String.valueOf(quantity)));
    }

    public void verifyTotalQuantity(int expected) {
        String actual = totalQuantity.shouldBe(visible).text().replaceAll("[^0-9]", "");
        if (!String.valueOf(expected).equals(actual)) {
            throw new AssertionError(String.format(
                "Общее количество в сайдбаре (%s) не соответствует ожидаемому (%d)",
                actual, expected
            ));
        }
    }

    public void verifyTotalSum(int expectedSum) {
        int actualSum = Integer.parseInt(totalSumElement.getText().replaceAll("[^0-9]", ""));
        assertEquals(expectedSum, actualSum, "Сумма в корзине не совпадает");
    }
} 
package ui;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import com.codeborne.selenide.Selenide;
import static com.codeborne.selenide.Selenide.$;
import com.codeborne.selenide.SelenideElement;

public class CatalogPage {
    private final SelenideElement catalogMenu = $("[data-selector='catalog-menu'], .catalog-menu, #catalog-menu");
    private final SelenideElement menCategory = $("a[data-selector='catalog-menu-item'][href*='men']");
    private final SelenideElement womenCategory = $("a[data-selector='catalog-menu-item'][href*='women']");
    private final SelenideElement firstProduct = $("a.product-listing-card__preview-link[data-layer-click-trigger]");

    public void openCatalogMenu() {
        catalogMenu.shouldBe(visible).click();
    }

    public void selectCategory(String category) {
        if (category.equalsIgnoreCase("men")) {
            menCategory.shouldBe(visible).click();
        } else if (category.equalsIgnoreCase("women")) {
            womenCategory.shouldBe(visible).click();
        }
    }

    public void selectProduct() {
        Selenide.sleep(1000);
        firstProduct.shouldBe(visible).scrollIntoView(true).click();
    }

    public void ensureCatalogLoaded() {
        catalogMenu.should(exist, Duration.ofSeconds(10));
    }
} 
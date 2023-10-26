import org.testng.annotations.Test;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Selectors.*;

public class SelenideTest {
    @Test // Первый тест, который проверяет и сравнивает кол-во превью на странице
    public void testPage() {
        open("https://angular.realworld.io");
        int ExpectedN = 12;
        $$("a.preview-link h1").shouldHave(size(ExpectedN));

    }

    @Test // Второй тест: Регистрация на сайте и проверка email адреса
    public void testSignUp() {
        open("https://angular.realworld.io");
        // Переход на старницу с регистрацией
        $("a.nav-link[routerlink='/register'][routerlinkactive='active'][href='/register']").click();
        // Заполнение данных
        $("input[formcontrolname='username']").val("TestingUsername123"); // Заполнение поля username
        $("input[formcontrolname='email']").val("TestingUsername@gmail.com"); // Заполнение поля email
        $("input[formcontrolname='password'][type='password']").val("password"); // Зполнение поля assword
        $("button.btn.btn-lg.btn-primary.pull-xs-right[type='submit']").click(); // Завершение регистрации

        //Проверка email адреса на старнице "Settings"
        $("a.nav-link[routerlink='/settings'][routerlinkactive='active'][href='/settings']").click();
        //Email на старнице "Settings" должен быть таким же как и при регистрации
        $("input[formcontrolname='email'][type='email']").shouldBe(Condition.value("generaltesting1@gmail.com"));

    }

    @Test // Трейтий тест: Добавить статью в Избранные и проверить наличие статьи в "Favorited Posts"
    public void testFavourites() {
        open("https://angular.realworld.io");
        // Вход в систему
        $("a.nav-link[routerlink='/login'][routerlinkactive='active'][href='/login']").click();
        $("input[formcontrolname='email'][type='text']").val("generaltesting1@gmail.com");
        $("input[formcontrolname='password'][type='password']").val("password");
        $("button.btn.btn-lg.btn-primary.pull-xs-right[type='submit']").click();
        // Добавление статьи в избранные
        $(byText("Global Feed")).click(); // Переход на старницу "Global Feed"
        $$(".article-preview h1")
                .filter(Condition.exactText("If we quantify the alarm, we can get to the FTP pixel through the online SSL interface!")) // Превью статьи, которую нужно добавить в избранное
                .first()
                .closest(".article-preview")
                .find(".btn-outline-primary") // кнопка "лайк"
                .click();
        // Проверка на наличие статьи в "Favorited Posts"
        $(byText("generaltesting1")).click();
        $(byText("Favorited Posts")).click();
        String selector = ".preview-link h1";
        $(selector).shouldHave(Condition.text("If we quantify the alarm, we can get to the FTP pixel through the online SSL interface!"));

    }

    @Test // Четвертый тест: Регистрация на сайте и создание статьи
    public void testCreatingArticle() {
        open("https://angular.realworld.io");
        // Регистрация на сайте
        $("a.nav-link[routerlink='/register'][routerlinkactive='active'][href='/register']").click();
        $("input[formcontrolname='username']").val("testingUsername");
        $("input[formcontrolname='email']").val("testingEmail@gmail.com");
        $("input[formcontrolname='password'][type='password']").val("password");
        $("button.btn.btn-lg.btn-primary.pull-xs-right[type='submit']").click();
        // Переход на старницу создание статьи и заполненние текстовых полей
        $("li.nav-item a[routerlink='/editor']").click();
        $("input[formcontrolname='title']").val("The Benefits of Mindfulness Meditation for Stress Reduction");
        $("input[formcontrolname='description']").val("The article is about the benefits of mindfulness meditation for stress reduction, highlighting how this ancient practice can help calm the mind, promote relaxation, enhance emotional regulation, improve resilience, boost clarity and focus, and lead to better sleep.");
        $("textarea[formcontrolname='body']").val("In our fast-paced world, stress is a constant companion. Mindfulness meditation is a practice that can help. By focusing on the present moment without judgment, it calms the mind, promotes relaxation, enhances emotional control, boosts resilience, improves focus, and leads to better sleep. With just a few minutes a day, you can find a sense of calm in the chaos.");
        $("input[placeholder='Enter tags']").val("Meditation");

        $("button.btn.btn-lg.pull-xs-right.btn-primary").click();

        $(byText("Home")).click();
        $(byText("Global Feed")).click();
        // Проверка наличие статьи в "Global Feed"
        String selector = ".preview-link h1";
        $(selector).shouldHave(Condition.text("The Benefits of Mindfulness Meditation for Stress Reduction"));

    }
}

package exselenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageLoginSignup {

    private WebDriver driver;

    public PageLoginSignup(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    @FindBy(css = "input[data-qa='login-email']")
    private WebElement txtEmailLogin;

    @FindBy(css = "input[data-qa='login-password']")
    private WebElement txtSenhaLogin;

    @FindBy(css = "button[data-qa='login-button']")
    private WebElement botaoLogin;


    @FindBy(xpath = "//p[contains(text(), 'Your email or password is incorrect!')]")
    private WebElement msgErroLogin;

    // --- ELEMENTOS DE NOVO CADASTRO (SIGNUP) ---
    @FindBy(css = "input[data-qa='signup-name']")
    private WebElement txtNomeSignup;

    @FindBy(css = "input[data-qa='signup-email']")
    private WebElement txtEmailSignup;

    @FindBy(css = "button[data-qa='signup-button']")
    private WebElement botaoSignup;

    @FindBy(xpath = "//p[contains(text(), 'Email Address already exist!')]")
    private WebElement msgErroSignup;


    public void fazerLogin(String email, String senha) {
        txtEmailLogin.sendKeys(email);
        txtSenhaLogin.sendKeys(senha);
        

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", botaoLogin);
    }

    public void iniciarCadastro(String nome, String email) {
        txtNomeSignup.sendKeys(nome);
        txtEmailSignup.sendKeys(email);
        

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", botaoSignup);
    }

    public String obterMensagemErroLogin() {
        return msgErroLogin.getText();
    }

    public String obterMensagemErroSignup() {
        return msgErroSignup.getText();
    }
}
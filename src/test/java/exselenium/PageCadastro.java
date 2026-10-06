package exselenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageCadastro {

    // 1. Adicionamos a variável do driver para poder usar o JavascriptExecutor depois
    private WebDriver driver;

    public PageCadastro(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // --- ELEMENTOS DO FORMULÁRIO ---
    @FindBy(id = "id_gender1")
    private WebElement radioTitleMr;

    @FindBy(id = "password")
    private WebElement txtSenha;

    @FindBy(id = "first_name")
    private WebElement txtPrimeiroNome;

    @FindBy(id = "last_name")
    private WebElement txtUltimoNome;

    @FindBy(id = "address1")
    private WebElement txtEndereco;

    @FindBy(id = "state")
    private WebElement txtEstado;

    @FindBy(id = "city")
    private WebElement txtCidade;

    @FindBy(id = "zipcode")
    private WebElement txtCep;

    @FindBy(id = "mobile_number")
    private WebElement txtCelular;

    @FindBy(css = "button[data-qa='create-account']")
    private WebElement botaoCriarConta;

    @FindBy(css = "h2[data-qa='account-created'] b")
    private WebElement msgContaCriada;

    // --- MÉTODOS DE AÇÃO ---
    public void preencherDadosContaEEndereco(String senha, String nome, String sobrenome, String endereco, String estado, String cidade, String cep, String celular) {
        radioTitleMr.click();
        txtSenha.sendKeys(senha);
        txtPrimeiroNome.sendKeys(nome);
        txtUltimoNome.sendKeys(sobrenome);
        txtEndereco.sendKeys(endereco);
        txtEstado.sendKeys(estado);
        txtCidade.sendKeys(cidade);
        txtCep.sendKeys(cep);
        txtCelular.sendKeys(celular);
        
        // 2. Trocamos o botaoCriarConta.click() por um clique via JavaScript para ignorar o Google Ads
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", botaoCriarConta);
    }

    public String obterMensagemSucesso() {
        return msgContaCriada.getText();
    }
}
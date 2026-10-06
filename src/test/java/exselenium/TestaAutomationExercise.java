package exselenium;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestaAutomationExercise {

    private WebDriver driver;
    private PageLoginSignup pageLogin;
    private PageCadastro pageCadastro;

    @Before
    public void setUp() {

        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.get("http://automationexercise.com/login");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));	
        pageLogin = new PageLoginSignup(driver);
        pageCadastro = new PageCadastro(driver);
    }



    @Test
    public void test01_Cadastro_BVA_SenhaMinimaValida() {

        String emailDinamico = "bva_min" + System.currentTimeMillis() + "@dominio.com";
        pageLogin.iniciarCadastro("Usuario BVA Min", emailDinamico);
        

        pageCadastro.preencherDadosContaEEndereco(
            "12345678", "Usuario", "Minimo", "Rua A", "SP", "SP", "00000", "11999999999"
        );
        Assert.assertEquals("ACCOUNT CREATED!", pageCadastro.obterMensagemSucesso());
    }

    @Test
    public void test02_Cadastro_BVA_NomeMaximoValido() {

        String emailDinamico = "bva_max" + System.currentTimeMillis() + "@dominio.com";
        

        String nome50Caracteres = "JoaoVitorEmanuelCarlosEduardoDeAlcantaraECarvalhoX";
        pageLogin.iniciarCadastro(nome50Caracteres, emailDinamico);
        
        pageCadastro.preencherDadosContaEEndereco(
            "Senha123", "Joao", "Carvalho", "Rua B", "RJ", "RJ", "11111", "21999999999"
        );
        Assert.assertEquals("ACCOUNT CREATED!", pageCadastro.obterMensagemSucesso());
    }

    @Test
    public void test03_Cadastro_EP_EmailJaExistente() {

        

        String emailGarantido = "duplicado" + System.currentTimeMillis() + "@dominio.com";
        pageLogin.iniciarCadastro("Original", emailGarantido);
        pageCadastro.preencherDadosContaEEndereco("Senha123", "Nome", "Sobrenome", "Rua", "SP", "SP", "00", "11");
        

        driver.manage().deleteAllCookies();

        
        driver.get("http://automationexercise.com/login");
        pageLogin.iniciarCadastro("Copia", emailGarantido);
        Assert.assertEquals("Email Address already exist!", pageLogin.obterMensagemErroSignup());
    }

    @Test
    public void test04_Cadastro_EP_EmailSemArroba() {


        pageLogin.iniciarCadastro("Sem Arroba", "emailinvalido.com");
        

        boolean continuaNaPagina = driver.getCurrentUrl().endsWith("/login");
        Assert.assertTrue("Deveria ter bloqueado e-mail sem @", continuaNaPagina);
    }

    @Test
    public void test05_Cadastro_EP_NomeEmBranco() {

        

        pageLogin.iniciarCadastro("", "novoemail" + System.currentTimeMillis() + "@dominio.com");
        

        boolean continuaNaPagina = driver.getCurrentUrl().endsWith("/login");
        Assert.assertTrue("Deveria ter bloqueado envio sem nome", continuaNaPagina);
    }



    @Test
    public void test06_Login_EP_Sucesso() {


        String emailValido = "login_sucesso" + System.currentTimeMillis() + "@dominio.com";
        String senhaValida = "Senha123";
        pageLogin.iniciarCadastro("Usuario Valido", emailValido);
        pageCadastro.preencherDadosContaEEndereco(senhaValida, "Nome", "Sobrenome", "Rua", "SP", "SP", "00", "11");
        driver.manage().deleteAllCookies();         

        driver.get("http://automationexercise.com/login");
        pageLogin.fazerLogin(emailValido, senhaValida);
        

        boolean saiuDaPaginaDeLogin = !driver.getCurrentUrl().endsWith("/login");
        Assert.assertTrue("O login válido deveria redirecionar o usuário", saiuDaPaginaDeLogin);
    }

    @Test
    public void test07_Login_EP_EmailNaoCadastrado() {

        

        pageLogin.fazerLogin("naoexiste_nunca@dominio.com", "senhaQualquer");
        Assert.assertEquals("Your email or password is incorrect!", pageLogin.obterMensagemErroLogin());
    }

    @Test
    public void test08_Login_EP_SenhaIncorreta() {


        String emailExistente = "senha_errada" + System.currentTimeMillis() + "@dominio.com";
        pageLogin.iniciarCadastro("Usuario Senha Errada", emailExistente);
        pageCadastro.preencherDadosContaEEndereco("SenhaCorreta1", "Nome", "Sobrenome", "Rua", "SP", "SP", "00", "11");
        driver.manage().deleteAllCookies(); 
        driver.get("http://automationexercise.com/login");
        


        pageLogin.fazerLogin(emailExistente, "SenhaERRADA2");
        Assert.assertEquals("Your email or password is incorrect!", pageLogin.obterMensagemErroLogin());
    }

    @Test
    public void test09_Login_EP_EmailSemArroba() {

        

        pageLogin.fazerLogin("loginsumarroba.com", "123456");
        

        boolean continuaNaPagina = driver.getCurrentUrl().endsWith("/login");
        Assert.assertTrue("Deveria ter bloqueado login sem @", continuaNaPagina);
    }

    @Test
    public void test10_Login_EP_CamposVazios() {


        pageLogin.fazerLogin("", "");
        

        boolean continuaNaPagina = driver.getCurrentUrl().endsWith("/login");
        Assert.assertTrue("Deveria ter bloqueado login com campos vazios", continuaNaPagina);
    }

    @After 
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
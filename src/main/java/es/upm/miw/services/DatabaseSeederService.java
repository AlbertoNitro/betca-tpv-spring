package es.upm.miw.services;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.FamilyArticle;
import es.upm.miw.documents.core.FamilyComposite;
import es.upm.miw.documents.core.FamilyType;
import es.upm.miw.documents.core.Provider;
import es.upm.miw.documents.core.Role;
import es.upm.miw.documents.core.User;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.ArticlesFamilyRepository;
import es.upm.miw.repositories.core.BudgetRepository;
import es.upm.miw.repositories.core.CashMovementRepository;
import es.upm.miw.repositories.core.CashierClosureRepository;
import es.upm.miw.repositories.core.FamilyArticleRepository;
import es.upm.miw.repositories.core.InvoiceRepository;
import es.upm.miw.repositories.core.OfferRepository;
import es.upm.miw.repositories.core.ProviderRepository;
import es.upm.miw.repositories.core.TicketRepository;
import es.upm.miw.repositories.core.UserRepository;
import es.upm.miw.repositories.core.VoucherRepository;
import es.upm.miw.utils.Barcode;

@Service
public class DatabaseSeederService {

    @Value("${miw.admin.mobile}")
    private String mobile;

    @Value("${miw.admin.username}")
    private String username;

    @Value("${miw.admin.password}")
    private String password;

    @Value("${miw.databaseSeeder.ymlFileName:#{null}}")
    private Optional<String> ymlFileName;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private CashMovementRepository cashMovementRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    public TicketRepository ticketRepository;

    @Autowired
    public InvoiceRepository invoiceRepository;

    @Autowired
    public CashierClosureRepository cashierClosureRepository;

    @Autowired
    public OfferRepository offerRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private ArticlesFamilyRepository articlesFamilyRepository;

    @Autowired
    private FamilyArticleRepository familyArticleRepository;

    private Long ean13;

    @PostConstruct
    public void seedDatabase() {
        if (ymlFileName.isPresent()) {
            this.deleteAllAndCreateAdmin();
            try {
                this.seedDatabase(ymlFileName.get());
            } catch (IOException e) {
                Logger.getLogger(this.getClass()).error("File " + ymlFileName + " doesn't exist or can't be opened");
            }
        } else {
            this.createAdminIfNotExist();
        }
        Article article = this.articleRepository.findFirstByCodeStartingWithOrderByCodeDesc("84000000");
        if (article == null) {
            this.ean13 = 840000000000L;
        } else {
            this.ean13 = Long.parseLong(article.getCode().substring(0, 12));
        }
    }

    public String createEan13() {
        this.ean13++;
        return new Barcode().generateEan13code(this.ean13);
    }

    public void seedDatabase(String ymlFileName) throws IOException {
        assert ymlFileName != null && !ymlFileName.isEmpty();
        Yaml yamlParser = new Yaml(new Constructor(DatabaseGraph.class));
        InputStream input = new ClassPathResource(ymlFileName).getInputStream();
        DatabaseGraph tpvGraph = (DatabaseGraph) yamlParser.load(input);

        // Save Repositories -----------------------------------------------------
        this.userRepository.save(tpvGraph.getUserList());
        this.voucherRepository.save(tpvGraph.getVoucherList());
        this.cashMovementRepository.save(tpvGraph.getCashMovementList());
        this.cashierClosureRepository.save(tpvGraph.getCashierClosureList());
        for (Provider provider : tpvGraph.getProviderList()) {
            Provider providerBd = this.providerRepository.findByCompany(provider.getCompany());
            if (providerBd == null) {
                this.providerRepository.save(provider);
            } else {
                for (Article article : tpvGraph.getArticleList()) {
                    if (article.getProvider().getCompany().equals(provider.getCompany())) {
                        article.setProvider(providerBd);
                    }
                }
            }
        }
        this.expandAllSizes(tpvGraph);
        this.articleRepository.save(tpvGraph.getArticleList());
        this.articlesFamilyRepository.save(tpvGraph.getFamilyArticleList());
        this.articlesFamilyRepository.save(tpvGraph.getFamilyCompositeList());
        this.ticketRepository.save(tpvGraph.getTicketList());
        this.invoiceRepository.save(tpvGraph.getInvoiceList());
        this.offerRepository.save(tpvGraph.getOfferList());
        // -----------------------------------------------------------------------

        Logger.getLogger(this.getClass()).warn("------------------------- Seed: " + ymlFileName + "-----------");
    }

    protected void expandAllSizes(DatabaseGraph tpvGraph) {
        List<Article> articleListForRemove = new ArrayList<>();
        List<Article> articleListForAdd = new ArrayList<>();
        for (Article article : tpvGraph.getArticleList()) {
            if (article.getReference() != null && article.getReference().indexOf("[") != -1) {
                List<Article> articleExpandList = this.expandArticle(article);
                FamilyComposite familyCompositeSizes = new FamilyComposite(FamilyType.SIZES, this.extractBase(article.getReference()),
                        this.extractBase(article.getDescription()));
                for (Article articleExpand : articleExpandList) {
                    FamilyArticle familyArticle = new FamilyArticle(articleExpand);
                    tpvGraph.getFamilyArticleList().add(familyArticle);
                    familyCompositeSizes.add(familyArticle);
                }
                articleListForAdd.addAll(articleExpandList);
                articleListForRemove.add(article);
                tpvGraph.getFamilyCompositeList().add(familyCompositeSizes);
            }
        }
        tpvGraph.getArticleList().removeAll(articleListForRemove);
        tpvGraph.getArticleList().addAll(articleListForAdd);
    }

    private String extractBase(String str) {
        return str.substring(0, str.indexOf("["));
    }

    protected List<Article> expandArticle(Article article) {
        List<Article> articlesExpanded = new ArrayList<>();
        String articleReferenceBase = this.extractBase(article.getReference());
        String articleDescriptionBase = this.extractBase(article.getDescription());
        String[] sizes = article.getReference().substring(article.getReference().indexOf("[") + 1, article.getReference().indexOf("]"))
                .split(",");
        String[] pricesInString = article.getDescription()
                .substring(article.getDescription().indexOf("[") + 1, article.getDescription().indexOf("]")).split(",");
        for (int i = 0; i < sizes.length; i++) {
            String subSizes[] = sizes[i].split(":");
            for (int size = Integer.parseInt(subSizes[0]); size <= Integer.parseInt(subSizes[1]); size += Integer.parseInt(subSizes[2])) {
                Article articleExpanded = new Article();
                articleExpanded.setCode(this.createEan13());
                articleExpanded.setReference(articleReferenceBase + "~T" + size);
                articleExpanded.setDescription(articleDescriptionBase + " T" + size);
                articleExpanded.setRetailPrice(new BigDecimal(pricesInString[i]));
                articleExpanded.setStock(30);
                articleExpanded.setProvider(article.getProvider());
                articlesExpanded.add(articleExpanded);
            }
        }
        return articlesExpanded;
    }

    public void deleteAllAndCreateAdmin() {
        Logger.getLogger(this.getClass()).warn("------------------------- delete All And Create Admin-----------");
        // Delete Repositories -----------------------------------------------------
        this.userRepository.deleteAll();
        this.ticketRepository.deleteAll();
        this.familyArticleRepository.deleteAll();
        this.articlesFamilyRepository.deleteAll();
        this.articleRepository.deleteAll();
        this.voucherRepository.deleteAll();
        this.cashMovementRepository.deleteAll();
        this.providerRepository.deleteAll();
        this.invoiceRepository.deleteAll();
        this.cashierClosureRepository.deleteAll();
        this.offerRepository.deleteAll();
        this.budgetRepository.deleteAll();
        this.createAdminIfNotExist();
        // -----------------------------------------------------------------------
    }

    public void createAdminIfNotExist() {
        if (this.userRepository.findByMobile(this.mobile) == null) {
            User user = new User(this.mobile, this.username, this.password);
            user.setRoles(new Role[] {Role.ADMIN});
            this.userRepository.save(user);
        }
    }

}

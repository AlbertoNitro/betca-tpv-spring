package es.upm.miw.services;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
import es.upm.miw.documents.core.CashierClosure;
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
import es.upm.miw.repositories.core.FamilyCompositeRepository;
import es.upm.miw.repositories.core.InvoiceRepository;
import es.upm.miw.repositories.core.OrderRepository;
import es.upm.miw.repositories.core.ProviderRepository;
import es.upm.miw.repositories.core.TagRepository;
import es.upm.miw.repositories.core.TicketRepository;
import es.upm.miw.repositories.core.UserRepository;
import es.upm.miw.repositories.core.VoucherRepository;
import es.upm.miw.utils.Barcode;

@Service
public class DatabaseSeederService {

    private static final String VARIOUS_CODE = "1";

    private static final String VARIOUS_NAME = "Varios";

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
    private BudgetRepository budgetRepository;

    @Autowired
    private ArticlesFamilyRepository articlesFamilyRepository;

    @Autowired
    private FamilyArticleRepository familyArticleRepository;

    @Autowired
    private FamilyCompositeRepository familyCompositeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TagRepository tagRepository;

    private long ean13;

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
        this.createArticleVariousIfNotExist();
        this.createCashierClosureIfNotExist();
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
        if (ymlFileName.indexOf("test") != -1) {
            this.providerRepository.save(tpvGraph.getProviderList());
            this.articleRepository.save(tpvGraph.getArticleList());
        } else {
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
            if (!tpvGraph.getArticleList().isEmpty()) {
                this.expandAllSizesAndCreateFamilyAndSaveAll(tpvGraph);
            }
        }
        this.ticketRepository.save(tpvGraph.getTicketList());
        this.invoiceRepository.save(tpvGraph.getInvoiceList());
        // -----------------------------------------------------------------------

        Logger.getLogger(this.getClass()).warn("------------------------- Seed: " + ymlFileName + "-----------");
    }

    protected void expandAllSizesAndCreateFamilyAndSaveAll(DatabaseGraph tpvGraph) {
        FamilyComposite root = this.familyCompositeRepository.findOne("root");
        if (root == null) {
            root = new FamilyComposite(FamilyType.ARTICLES, "root", "Root");
        }
        root.setId("root");
        FamilyComposite actualFamily = null;

        for (Article article : tpvGraph.getArticleList()) {

            if (article.getCode().split(":").length > 1) {
                if (actualFamily == null) {
                    actualFamily = new FamilyComposite(FamilyType.ARTICLES, article.getCode().split(":")[0],
                            article.getCode().split(":")[0]);
                } else if (!actualFamily.getReference().equals(article.getCode().split(":")[0])) {
                    this.articlesFamilyRepository.save(actualFamily);
                    root.add(actualFamily);
                    actualFamily = new FamilyComposite(FamilyType.ARTICLES, article.getCode().split(":")[0],
                            article.getCode().split(":")[0]);
                }
            } else {
                if (actualFamily != null) {
                    this.articlesFamilyRepository.save(actualFamily);
                    root.add(actualFamily);
                    actualFamily = null;
                }
            }

            if (article.getReference() != null && article.getReference().indexOf("[") != -1) {
                List<Article> articleExpandList = this.expandArticlewithSizes(article);
                FamilyComposite familyCompositeSizes = new FamilyComposite(FamilyType.SIZES,
                        this.extractBaseWithoutSizes(article.getReference()), this.extractBaseWithoutSizes(article.getDescription()));
                for (Article articleExpand : articleExpandList) {
                    FamilyArticle familyArticle = new FamilyArticle(articleExpand);
                    this.articleRepository.save(articleExpand);
                    this.articlesFamilyRepository.save(familyArticle);
                    familyCompositeSizes.add(familyArticle);
                }
                this.articlesFamilyRepository.save(familyCompositeSizes);
                if (actualFamily != null) {
                    actualFamily.add(familyCompositeSizes);
                }
            } else {
                article = Article.builder().code(this.createEan13()).reference(article.getReference()).description(article.getDescription())
                        .retailPrice(article.getRetailPrice()).provider(article.getProvider()).build();
                this.articleRepository.save(article);
                FamilyArticle familyArticle = new FamilyArticle(article);
                this.articlesFamilyRepository.save(familyArticle);
                if (actualFamily != null) {
                    actualFamily.add(familyArticle);
                }
            }
        }

        if (actualFamily != null) {
            this.articlesFamilyRepository.save(actualFamily);
            root.add(actualFamily);
        }
        this.articlesFamilyRepository.delete("root");
        this.articlesFamilyRepository.save(root);
    }

    private String extractBaseWithoutSizes(String str) {
        return str.substring(0, str.indexOf("["));
    }

    protected List<Article> expandArticlewithSizes(Article article) {
        List<String> sizesSML = Arrays.asList("XXS", "XS", "S", "M", "L", "XL", "XXL", "XXXL", "Especial");
        List<Article> articlesExpanded = new ArrayList<>();
        String articleReferenceBase = this.extractBaseWithoutSizes(article.getReference());
        String articleDescriptionBase = this.extractBaseWithoutSizes(article.getDescription());
        String[] sizes = article.getReference().substring(article.getReference().indexOf("[") + 1, article.getReference().indexOf("]"))
                .split(",");
        String[] pricesInString = article.getDescription()
                .substring(article.getDescription().indexOf("[") + 1, article.getDescription().indexOf("]")).split(",");
        for (int i = 0; i < sizes.length; i++) {
            String subSizes[] = sizes[i].split(":");
            int start, incremento = 2, end;
            boolean numeric;
            if (subSizes.length > 2) {
                incremento = Integer.parseInt(subSizes[2]);
            }
            if (sizesSML.contains(subSizes[0])) {
                incremento = 1;
                start = sizesSML.indexOf(subSizes[0]);
                end = sizesSML.indexOf(subSizes[1]);
                numeric = false;
            } else {
                start = Integer.parseInt(subSizes[0]);
                end = Integer.parseInt(subSizes[1]);
                numeric = true;
            }
            for (int size = start; size <= end; size += incremento) {
                String reference;
                String description;
                if (numeric) {
                    reference = articleReferenceBase + "~T" + size;
                    description = articleDescriptionBase + " T" + size;
                } else {
                    reference = articleReferenceBase + "~T" + sizesSML.get(size);
                    description = articleDescriptionBase + " T" + sizesSML.get(size);
                }
                Article articleExpanded = Article.builder().code(this.createEan13()).reference(reference).description(description)
                        .retailPrice(pricesInString[i]).provider(article.getProvider()).build();
                articlesExpanded.add(articleExpanded);
            }
        }
        return articlesExpanded;
    }

    public void deleteAllAndCreateAdmin() {
        Logger.getLogger(this.getClass()).warn("------------------------- delete All And Create Admin-----------");
        // Delete Repositories -----------------------------------------------------
        this.familyCompositeRepository.deleteAll();
        this.invoiceRepository.deleteAll();
        this.tagRepository.deleteAll();

        this.ticketRepository.deleteAll();
        this.orderRepository.deleteAll();
        this.familyArticleRepository.deleteAll();

        this.cashMovementRepository.deleteAll();
        this.articleRepository.deleteAll();

        this.voucherRepository.deleteAll();
        this.cashierClosureRepository.deleteAll();
        this.budgetRepository.deleteAll();
        this.providerRepository.deleteAll();
        this.userRepository.deleteAll();

        this.createAdminIfNotExist();
        this.createArticleVariousIfNotExist();
        this.createCashierClosureIfNotExist();
        this.ean13 = 840000000000L;
        // -----------------------------------------------------------------------
    }

    public void reset() {
        this.familyCompositeRepository.deleteAll();
        this.invoiceRepository.deleteAll();
        this.tagRepository.deleteAll();

        this.ticketRepository.deleteAll();
        this.orderRepository.deleteAll();
        this.familyArticleRepository.deleteAll();

        this.cashMovementRepository.deleteAll();

        this.voucherRepository.deleteAll();
        this.cashierClosureRepository.deleteAll();
        this.budgetRepository.deleteAll();
        this.articleRepository.deleteByCodeStartingWith("84000000");
        this.createCashierClosureIfNotExist();
        this.ean13 = 840000000000L;
    }

    public void createAdminIfNotExist() {
        if (this.userRepository.findByMobile(this.mobile) == null) {
            User user = new User(this.mobile, this.username, this.password);
            user.setRoles(new Role[] {Role.ADMIN});
            this.userRepository.save(user);
        }
    }

    private void createArticleVariousIfNotExist() {
        if (this.articleRepository.findOne(VARIOUS_CODE) == null) {
            Provider provider = Provider.builder().company(VARIOUS_NAME).build();
            this.providerRepository.save(provider);
            this.articleRepository.save(Article.builder().code(VARIOUS_CODE).reference(VARIOUS_NAME).description(VARIOUS_NAME)
                    .retailPrice("100.00").stock(1000).provider(provider).build());
        }
    }
    
    private void createCashierClosureIfNotExist() {
        CashierClosure cashierClosure = this.cashierClosureRepository.findFirstByOrderByOpeningDateDesc();
        if (cashierClosure == null) {
            cashierClosure = new CashierClosure(BigDecimal.ZERO);
            cashierClosure.close(BigDecimal.ZERO,BigDecimal.ZERO, BigDecimal.ZERO, "Initial");
            this.cashierClosureRepository.save(cashierClosure);
        }

    }

}

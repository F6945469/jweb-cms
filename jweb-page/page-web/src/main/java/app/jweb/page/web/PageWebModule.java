package app.jweb.page.web;

import app.jweb.ApplicationException;
import app.jweb.cache.CacheConfig;
import app.jweb.cache.CacheModule;
import app.jweb.cache.CacheOptions;
import app.jweb.message.MessageConfig;
import app.jweb.message.MessageModule;
import app.jweb.page.api.PageComponentWebService;
import app.jweb.page.api.PageSavedComponentWebService;
import app.jweb.page.api.PageTemplateWebService;
import app.jweb.page.api.component.CreateComponentRequest;
import app.jweb.page.api.component.SavedComponentChangedMessage;
import app.jweb.page.api.template.BatchCreateTemplateRequest;
import app.jweb.page.api.template.TemplateChangedMessage;
import app.jweb.page.api.template.TemplateResponse;
import app.jweb.page.api.template.TemplateType;
import app.jweb.page.api.variable.VariableChangedMessage;
import app.jweb.page.web.service.CategoryCacheService;
import app.jweb.page.web.service.CategorySitemap;
import app.jweb.page.web.service.HtmlComponentTemplateRepository;
import app.jweb.page.web.service.HtmlContentEngine;
import app.jweb.page.web.service.KeywordService;
import app.jweb.page.web.service.MarkdownContentEngine;
import app.jweb.page.web.service.PageSitemap;
import app.jweb.page.web.service.PageTemplateRepository;
import app.jweb.page.web.service.PageTemplateResourceConverter;
import app.jweb.page.web.service.PostService;
import app.jweb.page.web.service.TemplateCacheService;
import app.jweb.page.web.service.VariableCacheService;
import app.jweb.page.web.service.component.AuthorComponent;
import app.jweb.page.web.service.component.BannerComponent;
import app.jweb.page.web.service.component.BreadcrumbComponent;
import app.jweb.page.web.service.component.CategoryListComponent;
import app.jweb.page.web.service.component.CategoryPostListComponent;
import app.jweb.page.web.service.component.CategoryTreeComponent;
import app.jweb.page.web.service.component.ContentTableComponent;
import app.jweb.page.web.service.component.FooterComponent;
import app.jweb.page.web.service.component.HeaderComponent;
import app.jweb.page.web.service.component.PopularPostListComponent;
import app.jweb.page.web.service.component.PostCardComponent;
import app.jweb.page.web.service.component.PostComponent;
import app.jweb.page.web.service.component.PostLinkListComponent;
import app.jweb.page.web.service.component.PostListComponent;
import app.jweb.page.web.service.component.PostNavigationComponent;
import app.jweb.page.web.service.component.PostPaginationComponent;
import app.jweb.page.web.service.component.RecentPostListComponent;
import app.jweb.page.web.service.component.RelatedPostListComponent;
import app.jweb.page.web.service.component.SavedComponent;
import app.jweb.page.web.service.component.TagCloudComponent;
import app.jweb.page.web.service.component.TagPostListComponent;
import app.jweb.page.web.service.component.TrendingPostListComponent;
import app.jweb.page.web.service.component.UserPostLinkListComponent;
import app.jweb.page.web.service.message.CategoryCreatedMessageHandler;
import app.jweb.page.web.service.message.CategoryDeletedMessageHandler;
import app.jweb.page.web.service.message.CategoryUpdatedMessageHandler;
import app.jweb.page.web.service.message.KeywordChangedMessageHandler;
import app.jweb.page.web.service.message.SavedComponentChangedMessageHandler;
import app.jweb.page.web.service.message.TemplateChangedMessageHandler;
import app.jweb.page.web.service.message.VariableChangedMessageHandler;
import app.jweb.page.web.web.IndexController;
import app.jweb.page.web.web.PageController;
import app.jweb.page.web.web.TagController;
import app.jweb.post.api.PostKeywordWebService;
import app.jweb.post.api.category.CategoryCreatedMessage;
import app.jweb.post.api.category.CategoryDeletedMessage;
import app.jweb.post.api.category.CategoryResponse;
import app.jweb.post.api.category.CategoryUpdatedMessage;
import app.jweb.post.api.content.PostContentResponse;
import app.jweb.post.api.keyword.KeywordChangedMessage;
import app.jweb.post.api.post.PostResponse;
import app.jweb.resource.Resource;
import app.jweb.resource.ResourceRepository;
import app.jweb.template.Component;
import app.jweb.template.ComponentAttribute;
import app.jweb.template.TemplateEngine;
import app.jweb.web.AbstractWebModule;
import app.jweb.web.WebRoot;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author chi
 */
public class PageWebModule extends AbstractWebModule {
    @Override
    protected void configure() {
        message("conf/messages/page-web");

        CacheConfig cacheConfig = module(CacheModule.class);
        cacheConfig.create(PostResponse.class, new CacheOptions());
        cacheConfig.create(CategoryResponse.class, new CacheOptions());
        cacheConfig.create(VariableCacheService.VariableWrapper.class, new CacheOptions());
        cacheConfig.create(PostContentResponse.class, new CacheOptions());
        cacheConfig.create(TemplateResponse.class, new CacheOptions());

        PostService postService = requestInjection(new PostService());
        postService.add("markdown", requestInjection(new MarkdownContentEngine()));
        postService.add("html", requestInjection(new HtmlContentEngine()));
        bind(PostService.class).toInstance(postService);

        bind(CategoryCacheService.class);
        bind(VariableCacheService.class);
        bind(KeywordService.class).toInstance(new KeywordService());
        bind(TemplateCacheService.class);
        bind(PageTemplateResourceConverter.class);

        web().createCache("sitemap");
        web().addRepository(requestInjection(new HtmlComponentTemplateRepository()));
        web().addRepository(requestInjection(new PageTemplateRepository()));

        web().addComponent(requestInjection(new PostListComponent()));
        web().addComponent(requestInjection(new PostLinkListComponent()));
        web().addComponent(requestInjection(new PostLinkListComponent()));

        web().addComponent(requestInjection(new AuthorComponent()));
        web().addComponent(requestInjection(new BannerComponent()));
        web().addComponent(requestInjection(new BreadcrumbComponent()));
        web().addComponent(requestInjection(new CategoryPostListComponent()));
        web().addComponent(requestInjection(new RecentPostListComponent()));
        web().addComponent(requestInjection(new PostComponent()));
        web().addComponent(requestInjection(new PostCardComponent()));
        web().addComponent(requestInjection(new ContentTableComponent()));
        web().addComponent(requestInjection(new FooterComponent()));
        web().addComponent(requestInjection(new HeaderComponent()));
        web().addComponent(requestInjection(new PostNavigationComponent()));
        web().addComponent(requestInjection(new RelatedPostListComponent()));
        web().addComponent(requestInjection(new CategoryTreeComponent()));
        web().addComponent(requestInjection(new TagCloudComponent()));
        web().addComponent(requestInjection(new TagPostListComponent()));
        web().addComponent(requestInjection(new PopularPostListComponent()));
        web().addComponent(requestInjection(new TrendingPostListComponent()));
        web().addComponent(requestInjection(new UserPostLinkListComponent()));
        web().addComponent(requestInjection(new CategoryListComponent()));
        web().addComponent(requestInjection(new PostPaginationComponent()));

        MessageConfig messageConfig = module(MessageModule.class);
        messageConfig.listen(TemplateChangedMessage.class, requestInjection(new TemplateChangedMessageHandler()));
        messageConfig.listen(VariableChangedMessage.class, requestInjection(new VariableChangedMessageHandler()));
        messageConfig.listen(SavedComponentChangedMessage.class, requestInjection(new SavedComponentChangedMessageHandler()));
        messageConfig.listen(CategoryCreatedMessage.class, requestInjection(new CategoryCreatedMessageHandler()));
        messageConfig.listen(CategoryUpdatedMessage.class, requestInjection(new CategoryUpdatedMessageHandler()));
        messageConfig.listen(CategoryDeletedMessage.class, requestInjection(new CategoryDeletedMessageHandler()));
        messageConfig.listen(KeywordChangedMessage.class, requestInjection(new KeywordChangedMessageHandler()));

        web().controller(PageController.class);
        web().controller(IndexController.class);
        web().controller(TagController.class);

        web().addSiteMap(requestInjection(new PageSitemap()));
        web().addSiteMap(requestInjection(new CategorySitemap()));

        onStartup(this::start);
    }

    public void start() {
        require(KeywordService.class)
            .setPostKeywordWebService(require(PostKeywordWebService.class))
            .start();
        initTemplates();
        initComponents();
    }

    private void initTemplates() {
        WebRoot webRoot = require(WebRoot.class);
        PageTemplateWebService pageTemplateWebService = require(PageTemplateWebService.class);

        BatchCreateTemplateRequest request = new BatchCreateTemplateRequest();
        request.templates = Lists.newArrayList();
        Set<String> visited = Sets.newHashSet();
        for (ResourceRepository repository : webRoot.repositories()) {
            List<Resource> resources = repository.list("template/");
            for (Resource resource : resources) {
                if (!visited.contains(resource.path())) {
                    visited.add(resource.path());
                    BatchCreateTemplateRequest.TemplateView templateView = new BatchCreateTemplateRequest.TemplateView();
                    templateView.templatePath = resource.path();
                    templateView.title = resource.path();
                    templateView.type = TemplateType.DEFAULT;
                    templateView.requestBy = "SYS";
                    request.templates.add(templateView);
                }
            }
        }
        pageTemplateWebService.batchCreate(request);
    }

    private void initComponents() {
        TemplateEngine templateEngine = require(TemplateEngine.class);
        PageComponentWebService pageComponentWebService = require(PageComponentWebService.class);
        List<CreateComponentRequest> request = Lists.newArrayList();
        for (Component component : templateEngine.components()) {
            if (component instanceof AbstractPostComponent) {
                CreateComponentRequest createComponentRequest = new CreateComponentRequest();
                createComponentRequest.name = component.name();
                createComponentRequest.attributes = attributes(component.attributes());
                request.add(createComponentRequest);
            }
        }
        pageComponentWebService.batchCreate(request);

        PageSavedComponentWebService pageSavedComponentWebService = require(PageSavedComponentWebService.class);
        pageSavedComponentWebService.find().forEach(savedComponent -> {
            Component component = templateEngine.component(savedComponent.componentName).orElseThrow(() -> new ApplicationException("missing component, name={}", savedComponent.componentName));
            templateEngine.addComponent(new SavedComponent(component, savedComponent));
        });
    }

    private Map<String, Object> attributes(Map<String, ComponentAttribute<?>> attributes) {
        Map<String, Object> values = Maps.newHashMap();
        attributes.forEach((name, attribute) -> values.put(attribute.name(), attribute.defaultValue()));
        return values;
    }
}

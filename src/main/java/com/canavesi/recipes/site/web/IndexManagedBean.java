package com.canavesi.recipes.site.web;

import com.canavesi.recipes.site.dao.DaoConfigs;
import com.canavesi.recipes.site.dao.DaoRecipes;
import com.canavesi.recipes.site.dao.DaoRecipes3;
import com.canavesi.recipes.site.entities.RecipeEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * index.xhtml controller
 *
 * @author Andres Canavesi
 */
@Named(value = "indexManagedBean")
@SessionScoped
@ManagedBean
public class IndexManagedBean {

    private static final Logger LOG = Logger.getLogger(IndexManagedBean.class.getName());
    private List<RecipeEntity> recipes;
    private List<RecipeEntity> featuredRecipes;
    private RecipeEntity recipeToDisplay;
    private Long recipeId;
    private Boolean isProduction;
    private Boolean showAdFooter;
    private String baseUrl;
    private Boolean cleanCache = false;
    private String homeTitle;
    private String homeDescription;
    private String homeUrlImage;
    private String homeUpdatedAtFormatted;

    /**
     *
     */
    @PostConstruct
    public void init() {
        LOG.info("Init");
        isProduction = DaoConfigs.isProduction();
        showAdFooter = DaoConfigs.getShowAdFooter();
        baseUrl = DaoConfigs.getBaseUrl();

        try {
            loadRecipes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void loadRecipes() throws Exception {

        recipes = DaoRecipes.getInstance().findAll(0, 100);

        loadFeaturedRecipes();

        RecipeEntity firstRecipe = recipes.get(0);
        homeTitle = firstRecipe.getTitle();
        homeDescription = firstRecipe.getDescriptionMeta();
        homeUrlImage = firstRecipe.getFeaturedThumbnailImageUrl();
        homeUpdatedAtFormatted = firstRecipe.getUpdatedAtFormatted();

    }

    private void loadFeaturedRecipes() {
        List<RecipeEntity> recipesCopy = new ArrayList<>();
        for (RecipeEntity recipe : recipes) {
            recipesCopy.add(recipe);
        }
        Collections.shuffle(recipesCopy);
        featuredRecipes = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            featuredRecipes.add(recipes.get(i));

        }
    }

    public void install() {
        List<RecipeEntity> recipesToSave = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            RecipeEntity recipeEntity = new RecipeEntity();
            recipeEntity.setTitle("Recipe title " + i);
            recipeEntity.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. ");
            recipeEntity.setIngredients("ing1, ing2, ing3, ing4,ing5,ing6,ing7,ing8,ing9,ing10 ");
            recipeEntity.setSteps("step1, step2, step3step4,step5,step6,step7,step8,step9,step10");
            recipeEntity.setFeaturedFullImageUrl("http://cdn2.cocinadelirante.com/sites/default/files/styles/gallerie/public/images/2017/01/pizzapepperonni.jpg");
            recipeEntity.setTitleForUrl("recipe-title-" + i);
            recipeEntity.setActive(true);
            recipesToSave.add(recipeEntity);
        }
        DaoRecipes3 daoRecipes = new DaoRecipes3();
        daoRecipes.save(recipesToSave);

    }

    public void loadIndex() throws Exception {
        if (cleanCache != null && cleanCache) {
            DaoRecipes.getInstance().resetCache();
            loadRecipes();
            cleanCache = false;
        }
    }

    public void loadRecipe() {
        LOG.info("Getting recipe");

        if (recipeId == null) {
            throw new IllegalArgumentException("Missing recipeId");
        }

        for (RecipeEntity recipe : recipes) {
            if (recipe.getId().equals(recipeId)) {
                recipeToDisplay = recipe;
                break;
            }
        }
    }

    public List<RecipeEntity> getRecipes() {
        return recipes;
    }

    public RecipeEntity getRecipeToDisplay() {
        return recipeToDisplay;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public Boolean getIsProduction() {
        return isProduction;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Boolean getCleanCache() {
        return cleanCache;
    }

    public void setCleanCache(Boolean cleanCache) {
        this.cleanCache = cleanCache;
    }

    public List<RecipeEntity> getFeaturedRecipes() {
        return featuredRecipes;
    }

    public void setFeaturedRecipes(List<RecipeEntity> featuredRecipes) {
        this.featuredRecipes = featuredRecipes;
    }

    public Boolean getShowAdFooter() {
        return showAdFooter;
    }

    public String getHomeTitle() {
        return homeTitle;
    }

    public void setHomeTitle(String homeTitle) {
        this.homeTitle = homeTitle;
    }

    public String getHomeDescription() {
        return homeDescription;
    }

    public void setHomeDescription(String homeDescription) {
        this.homeDescription = homeDescription;
    }

    public String getHomeUrlImage() {
        return homeUrlImage;
    }

    public void setHomeUrlImage(String homeUrlImage) {
        this.homeUrlImage = homeUrlImage;
    }

    public String getHomeUpdatedAtFormatted() {
        return homeUpdatedAtFormatted;
    }

}

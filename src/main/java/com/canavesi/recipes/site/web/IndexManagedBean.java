package com.canavesi.recipes.site.web;

import com.canavesi.recipes.site.dao.DaoConfigs;
import com.canavesi.recipes.site.dao.DaoRecipes;
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
    private Boolean showAdIngredients;
    private Boolean showAdSteps;
    private Boolean showAdFixedLeft;
    private Boolean showAdFixedRight;
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
        showAdIngredients = DaoConfigs.getShowAdIngredients();
        showAdSteps = DaoConfigs.getShowAdSteps();
        showAdFixedLeft = DaoConfigs.getShowAdFixedLeft();
        showAdFixedRight = DaoConfigs.getShowAdFixedRight();
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

    public Boolean getShowAdIngredients() {
        return showAdIngredients;
    }

    public void setShowAdIngredients(Boolean showAdIngredients) {
        this.showAdIngredients = showAdIngredients;
    }

    public Boolean getShowAdSteps() {
        return showAdSteps;
    }

    public void setShowAdSteps(Boolean showAdSteps) {
        this.showAdSteps = showAdSteps;
    }

    public Boolean getShowAdFixedLeft() {
        return showAdFixedLeft;
    }

    public Boolean getShowAdFixedRight() {
        return showAdFixedRight;
    }

}

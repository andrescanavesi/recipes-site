package com.canavesi.recipes.site.web;

import com.canavesi.recipes.site.dao.DaoConfigs;
import com.canavesi.recipes.site.dao.DaoRecipes;
import com.canavesi.recipes.site.entities.BaseEntity;
import com.canavesi.recipes.site.entities.RecipeEntity;
import com.canavesi.recipes.site.exceptions.RecipeNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * index.xhtml controller
 *
 * @author Andres Canavesi
 */
@Named(value = "indexManagedBean")
@ViewScoped
@ManagedBean
public class IndexManagedBean {

    private static final Logger LOG = Logger.getLogger(IndexManagedBean.class.getName());
    private List<RecipeEntity> recipes;
    private List<RecipeEntity> allRecipes;
    private List<RecipeEntity> featuredRecipes;
    private RecipeEntity recipeToDisplay;
    private Boolean isProduction;
    private Boolean showAdFooter;
    private Boolean showAdIngredients;
    private Boolean showAdSteps;
    private Boolean showAdFixedLeft;
    private Boolean showAdFixedRight;
    private Boolean showAdRecipeRight;
    private Boolean showAdUnderRecipeTitle;
    private String baseUrl;
    private Boolean cleanCache = false;
    private String homeTitle;
    private String homeDescription;
    private String homeUrlImage;
    private String homeUpdatedAtFormatted;
    private Boolean onlyCeliacsRecipes = false;
    private String styleToHideHeaderCeliacos = "d-none";
    private String wordToSearch = null;
    private String keyword = null;

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
        showAdRecipeRight = DaoConfigs.getShowAdRecipeRight();
        showAdUnderRecipeTitle = DaoConfigs.getShowAdUnderRecipeTitle();
        baseUrl = DaoConfigs.getBaseUrl();

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();

        String recipeId = params.get("id");
        if (recipeId != null) {
            Long id = Long.valueOf(recipeId);
            try {
                recipeToDisplay = DaoRecipes.getInstance().find(id);
            } catch (RecipeNotFoundException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        String cleanCacheString = params.get("cleanCache");
        if (cleanCacheString != null) {
            DaoRecipes.getInstance().resetCache();
        }

        String onlyCeliacsRecipesString = params.get("onlyCeliacsRecipes");
        if (onlyCeliacsRecipesString != null) {
            onlyCeliacsRecipes = Boolean.valueOf(onlyCeliacsRecipesString);
            //empty to show
            styleToHideHeaderCeliacos = "";
        }

        String search = params.get("search");
        if (search != null) {
            this.wordToSearch = search;
        }

        homeTitle = "Recetas City | Las mejores recetas de cocina";
        homeDescription = "Recetas City. Las mejores y mas faciles recetas de cocina";
        if (onlyCeliacsRecipes) {
            homeTitle = "Recetas para celiacos | Recetas City";
            homeDescription += ". Tambien recetas para celiacos";
        }
        homeUrlImage = "https://res.cloudinary.com/dniiru5xy/image/upload/c_fill,g_auto/w_600,q_auto,f_auto/medialunas.jpg";
        homeUpdatedAtFormatted = BaseEntity.DATE_FORMAT.format(new Date());

        this.keyword = params.get("keyword");
        if (this.keyword != null) {
            homeTitle = "Recetas City | " + this.keyword;
            homeDescription += ". Recetas de " + this.keyword;
        }
        //homeTitle = "Recetas de " + this.wordToSearch + " | Recetas City";
        if (this.wordToSearch != null) {
            homeTitle = "Recetas City | " + this.wordToSearch;
            homeDescription += ". Recetas de " + this.wordToSearch;
        }
        try {
            loadRecipes();
            loadFeaturedRecipes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadRecipes() throws Exception {
        allRecipes = DaoRecipes.getInstance().find(0, 1000);
        if (onlyCeliacsRecipes) {
            recipes = DaoRecipes.getInstance().findOnlyCeliacs(0, DaoConfigs.getPageSizeDB());
        } else {
            if (this.wordToSearch != null || this.keyword != null) {
                recipes = DaoRecipes.getInstance().find(0, DaoConfigs.getPageSizeDB(), this.wordToSearch, this.keyword);
            } else {
                recipes = DaoRecipes.getInstance().find(0, DaoConfigs.getPageSizeDB());
            }
        }
    }

    private void loadFeaturedRecipes() throws Exception {
        List<RecipeEntity> recipesCopy = new ArrayList<>();
        for (RecipeEntity recipe : allRecipes) {
            recipesCopy.add(recipe);
        }
        Collections.shuffle(recipesCopy);
        featuredRecipes = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (i < allRecipes.size()) {
                featuredRecipes.add(allRecipes.get(i));
            }

        }
    }

    public List<RecipeEntity> getRecipes() {
        return recipes;
    }

    public RecipeEntity getRecipeToDisplay() {
        return recipeToDisplay;
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

    public Boolean getShowAdUnderRecipeTitle() {
        return showAdUnderRecipeTitle;
    }

    public void setShowAdUnderRecipeTitle(Boolean showAdUnderRecipeTitle) {
        this.showAdUnderRecipeTitle = showAdUnderRecipeTitle;
    }

    public Boolean getOnlyCeliacsRecipes() {
        return onlyCeliacsRecipes;
    }

    public void setOnlyCeliacsRecipes(Boolean onlyCeliacsRecipes) {
        this.onlyCeliacsRecipes = onlyCeliacsRecipes;
    }

    public String getStyleToHideHeaderCeliacos() {
        return styleToHideHeaderCeliacos;
    }

    public String getWordToSearch() {
        return wordToSearch;
    }

    public void setWordToSearch(String wordToSearch) {
        this.wordToSearch = wordToSearch;
    }

    public List<RecipeEntity> getAllRecipes() {
        return allRecipes;
    }

    public Boolean getShowAdRecipeRight() {
        return showAdRecipeRight;
    }

    public void setShowAdRecipeRight(Boolean showAdRecipeRight) {
        this.showAdRecipeRight = showAdRecipeRight;
    }

}

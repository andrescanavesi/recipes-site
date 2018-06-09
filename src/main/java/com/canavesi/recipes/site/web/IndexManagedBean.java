package com.canavesi.recipes.site.web;

import com.canavesi.recipes.site.dao.DaoConfigs;
import com.canavesi.recipes.site.dao.DaoRecipes;
import com.canavesi.recipes.site.entities.RecipeEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * index.xhtml controller
 *
 * @author Andres Canavesi
 */
@Named(value = "indexManagedBean")
@ApplicationScoped
@ManagedBean
public class IndexManagedBean {

    private static final Logger LOG = Logger.getLogger(IndexManagedBean.class.getName());
    private List<RecipeEntity> recipes;
    private List<RecipeEntity> featuredRecipes;
    private RecipeEntity recipeToDisplay;
    private Long recipeId;
    private Boolean isProduction;
    private String baseUrl;
    private Boolean cleanCache = false;

    /**
     *
     */
    @PostConstruct
    public void init() {
        LOG.info("Init");
        isProduction = DaoConfigs.isProduction();
        baseUrl = DaoConfigs.getBaseUrl();
        loadRecipes();
    }

    private void loadRecipes() {
        DaoRecipes daoRecipes = new DaoRecipes();
        recipes = daoRecipes.findAll(0, 100);
        featuredRecipes = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            boolean randomAgain = false;
            do {
                int random = new Random().nextInt(recipes.size());
                RecipeEntity recipe = recipes.get(random);
                if (!featuredRecipes.contains(recipe)) {
                    featuredRecipes.add(recipe);
                    randomAgain = false;
                }

            } while (randomAgain);

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
            recipeEntity.setFeaturedImage("http://cdn2.cocinadelirante.com/sites/default/files/styles/gallerie/public/images/2017/01/pizzapepperonni.jpg");
            recipeEntity.setTitleForUrl("recipe-title-" + i);
            recipeEntity.setActive(true);
            recipesToSave.add(recipeEntity);
        }
        DaoRecipes daoRecipes = new DaoRecipes();
        daoRecipes.save(recipesToSave);

    }

    public void loadIndex() {
        if (cleanCache != null && cleanCache) {
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

}

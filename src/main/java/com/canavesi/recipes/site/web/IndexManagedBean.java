package com.canavesi.recipes.site.web;

import com.canavesi.recipes.site.dao.DaoRecipes;
import com.canavesi.recipes.site.entities.RecipeEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
@SessionScoped
@ManagedBean
public class IndexManagedBean {

    private static final Logger LOG = Logger.getLogger(IndexManagedBean.class.getName());
    private List<RecipeEntity> recipes;
    private RecipeEntity recipeToDisplay;
    private Long recipeId;

    /**
     *
     */
    @PostConstruct
    public void init() {
        DaoRecipes daoRecipes = new DaoRecipes();
        recipes = daoRecipes.findAll(0, 100);
    }

    public void install() {
        List<RecipeEntity> recipesToSave = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            RecipeEntity recipeEntity = new RecipeEntity();
            recipeEntity.setTitle("recipe title " + i);
            recipeEntity.setDescription("recipe description " + i);
            recipeEntity.setIngredients("ing1, ing2, ing3, ing4 ");
            recipeEntity.setSteps("step1, step2, step3");
            recipesToSave.add(recipeEntity);
        }
        DaoRecipes daoRecipes = new DaoRecipes();
        daoRecipes.save(recipesToSave);

    }

    public void loadRecipe() {
        LOG.info("Getting recipe");

        if (recipeId == null) {
            throw new IllegalArgumentException("Missing recipeId");
        }

//        Map<String, String> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        if (paramMap.get("id") == null) {
//            throw new IllegalArgumentException("Missing recipe id");
//        }
//        String id = paramMap.get("id");
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

}

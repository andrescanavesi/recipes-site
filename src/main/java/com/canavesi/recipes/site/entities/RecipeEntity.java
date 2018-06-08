package com.canavesi.recipes.site.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Andres Canavesi
 */
@Entity
@Table(name = RecipeEntity.TABLE_NAME)
public class RecipeEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "recipes";

    private String title;
    private String titleForUrl;
    private String description;
    /**
     * comma separated
     */
    private String ingredients;
    /**
     * comma separated
     */
    private String steps;
    /**
     * Could be a url or base64 encoded image
     */
    private String featuredImage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleForUrl() {
        return titleForUrl;
    }

    public void setTitleForUrl(String titleForUrl) {
        this.titleForUrl = titleForUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String[] getIngredientsArray() {
        if (ingredients != null) {
            return ingredients.split(",");
        }
        return null;

    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public String[] getStepsArray() {
        if (steps != null) {
            return steps.split(",");
        }
        return null;

    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

}

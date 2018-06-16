package com.canavesi.recipes.site.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Andres Canavesi
 */
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
    private String featuredImageName;
    private String featuredFullImageUrl;
    private String featuredThumbnailImageUrl;
    private Boolean aptoCeliacos;

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

    public String getDescriptionMeta() {
        if (description == null) {
            return null;
        } else if (description.length() >= 100) {
            return description.substring(0, 99) + "...";
        } else {
            return description;
        }

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String[] getIngredientsArray() {
        if (ingredients != null) {
            return ingredients.split("\n");
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
            return steps.split("\n");
        }
        return null;

    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getFeaturedFullImageUrl() {
        return featuredFullImageUrl;
    }

    public void setFeaturedFullImageUrl(String featuredFullImageUrl) {
        this.featuredFullImageUrl = featuredFullImageUrl;
    }

    public String getFeaturedThumbnailImageUrl() {
        return featuredThumbnailImageUrl;
    }

    public void setFeaturedThumbnailImageUrl(String featuredThumbnailImageUrl) {
        this.featuredThumbnailImageUrl = featuredThumbnailImageUrl;
    }

    public String getFeaturedImageName() {
        return featuredImageName;
    }

    public void setFeaturedImageName(String featuredImageName) {
        this.featuredImageName = featuredImageName;
    }

    public Boolean getAptoCeliacos() {
        return aptoCeliacos;
    }

    public void setAptoCeliacos(Boolean aptoCeliacos) {
        this.aptoCeliacos = aptoCeliacos;
    }

}

package com.canavesi.recipes.site.entities;

import com.canavesi.recipes.site.dao.DaoConfigs;
import java.io.Serializable;

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
    /**
     * comma separated
     */
    private String keywords;
    private String featuredImageName;
    private String featuredFullImageUrl;
    private String featuredThumbnailImageUrl;
    private Boolean aptoCeliacos;
    /**
     * The total time required to perform instructions or a direction (including
     * time to prepare the supplies), in ISO 8601 duration format.
     *
     * Example: PT50M for 50 minutes
     */
    private String totalTimeMeta;
    /**
     * Example: 15 minutes or 2 hours
     */
    private String totalTimeText;
    private String category;

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

    public String[] getIngredientsArrayWithLinks() {
        String[] ingredientsArray = getIngredientsArray();
        String[] ingredientsWithLinks = new String[ingredientsArray.length];
        String[] mainIngredients = DaoConfigs.getMainIngredients();
        String baseUrl = DaoConfigs.getBaseUrl();
        for (int i = 0; i < ingredientsArray.length; i++) {
            ingredientsWithLinks[i] = ingredientsArray[i].toLowerCase();
            for (String mainIngredient : mainIngredients) {
                if (ingredientsWithLinks[i].contains(mainIngredient)) {
                    String link = " <a href=\"" + baseUrl + "recetas/con/" + mainIngredient + "\">" + mainIngredient + "</a> ";
                    ingredientsWithLinks[i] = ingredientsWithLinks[i].replace(mainIngredient, link);
                }

            }

        }
        return ingredientsWithLinks;

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

    public String[] getKeywordsArray() {
        if (keywords != null) {
            return keywords.replaceAll(", ", ",").split(",");
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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getTotalTimeMeta() {
        return totalTimeMeta;
    }

    public void setTotalTimeMeta(String totalTimeMeta) {
        this.totalTimeMeta = totalTimeMeta;
    }

    public String getIngredientesMeta() {
        String[] ingredientsArray = getIngredientsArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ingredientsArray.length; i++) {
            builder.append("\"").append(ingredientsArray[i]).append("\"");
            if (i < (ingredientsArray.length - 1)) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    public String getStepsMeta() {
        String[] stepsArray = getStepsArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < stepsArray.length; i++) {
            builder.append("{\"@type\":\"HowToStep\",\"text\":\"")
                    .append(stepsArray[i])
                    .append("\"}");
            if (i < (stepsArray.length - 1)) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    public String getTotalTimeText() {
        return totalTimeText;
    }

    public void setTotalTimeText(String totalTimeText) {
        this.totalTimeText = totalTimeText;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}

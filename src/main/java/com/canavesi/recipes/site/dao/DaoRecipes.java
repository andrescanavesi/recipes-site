package com.canavesi.recipes.site.dao;

import com.canavesi.recipes.site.entities.RecipeEntity;
import com.canavesi.recipes.site.util.DbHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andres Canavesi
 */
public class DaoRecipes {

    private static final Logger LOG = Logger.getLogger(DaoRecipes.class.getName());

    private static DaoRecipes instance;
    private List<RecipeEntity> recipesCache;

    /**
     *
     */
    private DaoRecipes() {
    }

    public static DaoRecipes getInstance() {
        if (instance == null) {
            instance = new DaoRecipes();
        }
        return instance;
    }

    private RecipeEntity convertToRecipeEntity(ResultSet resultSet) throws SQLException, ParseException {
        RecipeEntity recipe = new RecipeEntity();
        recipe.setId(resultSet.getLong("id"));
        String createdAt = resultSet.getString("createdAt");
        DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (createdAt != null) {
            recipe.setCreatedAt(DATE_FORMAT.parse(createdAt));
        }
        String updatedAt = resultSet.getString("updatedAt");
        if (updatedAt != null) {
            recipe.setUpdatedAt(DATE_FORMAT.parse(updatedAt));
        }

        recipe.setActive(resultSet.getBoolean("active"));
        recipe.setDescription(resultSet.getString("description"));
        recipe.setIngredients(resultSet.getString("ingredients"));
        recipe.setSteps(resultSet.getString("steps"));
        recipe.setTitle(resultSet.getString("title"));
        recipe.setTitleForUrl(resultSet.getString("titleforurl"));

        String imageName = resultSet.getString("featuredimagename");
        String imagesBaseUrl = DaoConfigs.getImagesBaseUrl();
        recipe.setFeaturedFullImageUrl(imagesBaseUrl + "c_fill,g_auto/w_600,q_auto,f_auto/" + imageName);
        recipe.setFeaturedThumbnailImageUrl(imagesBaseUrl + "c_fill,g_auto/w_300,q_auto,f_auto/" + imageName);

        return recipe;
    }

    public List<RecipeEntity> findAll(int start, int end) throws Exception {

        if (this.recipesCache != null) {
            return this.recipesCache;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbHelper.getInstance().getConnection();
            //TODO implement pagination
            preparedStatement = connection.prepareStatement("SELECT * FROM recipes WHERE active=TRUE ORDER BY createdAt DESC LIMIT 1000");
            LOG.log(Level.FINE, "\n{0}", preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();

            List<RecipeEntity> recipes = new ArrayList<>();
            while (resultSet.next()) {
                RecipeEntity recipeEntity = convertToRecipeEntity(resultSet);
                recipes.add(recipeEntity);
            }
            preparedStatement.close();
            this.recipesCache = recipes;

            return recipes;
        } catch (Exception e) {
            throw e;
        } finally {
            DbHelper.tryToCloseResources(resultSet, preparedStatement, connection);
        }
    }

    public void resetCache() {
        this.recipesCache = null;
    }

}

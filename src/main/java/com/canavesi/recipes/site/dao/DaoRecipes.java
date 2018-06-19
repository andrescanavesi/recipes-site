package com.canavesi.recipes.site.dao;

import com.canavesi.recipes.site.entities.RecipeEntity;
import com.canavesi.recipes.site.exceptions.DatabaseConfigurationException;
import com.canavesi.recipes.site.exceptions.RecipeNotFoundException;
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
        recipe.setAptoCeliacos(resultSet.getBoolean("aptoceliacos"));

        return recipe;
    }

    public List<RecipeEntity> findByWordInTitle(int start, int end, String word) throws Exception {
        LOG.log(Level.INFO, "Finiding receipes that contain {0}", word);
        List<RecipeEntity> allRecipes = find(start, end);
        List<RecipeEntity> result = new ArrayList<>();
        for (RecipeEntity recipe : allRecipes) {
            if (recipe.getTitle().toLowerCase().contains(word.toLowerCase())) {
                result.add(recipe);
            }
        }
        LOG.info("Recipes that contain '" + word + "' " + result.size());
        return result;
    }

    public List<RecipeEntity> findOnlyCeliacs(int start, int end) throws Exception {
        List<RecipeEntity> allRecipes = find(start, end);
        List<RecipeEntity> onlyCeliacs = new ArrayList<>();
        for (RecipeEntity recipe : allRecipes) {
            if (recipe.getAptoCeliacos()) {
                onlyCeliacs.add(recipe);
            }
        }
        return onlyCeliacs;
    }

    public List<RecipeEntity> find(int start, int end) throws Exception {

        if (this.recipesCache != null) {
            LOG.info("Returning recipes from cache");
            return this.recipesCache;
        }
        LOG.info("Getting recipes from DB");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbHelper.getInstance().getConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM recipes WHERE active=? ORDER BY createdAt DESC");
            LOG.log(Level.INFO, "\n{0}", preparedStatement.toString());
            preparedStatement.setBoolean(1, true);

            preparedStatement.setMaxRows(DaoConfigs.getPageSizeDB());
            preparedStatement.setFetchSize(DaoConfigs.getPageSizeDB());
            resultSet = preparedStatement.executeQuery();

            List<RecipeEntity> recipes = new ArrayList<>();
            while (resultSet.next()) {
                RecipeEntity recipeEntity = convertToRecipeEntity(resultSet);
                recipes.add(recipeEntity);
            }
            preparedStatement.close();
            this.recipesCache = recipes;

            return recipes;
        } catch (DatabaseConfigurationException | SQLException | ParseException e) {
            throw e;
        } finally {
            DbHelper.tryToCloseResources(resultSet, preparedStatement, connection);
        }
    }

    public void resetCache() {
        this.recipesCache = null;
    }

    public RecipeEntity find(Long id) throws Exception {
        this.find(0, DaoConfigs.getPageSizeDB());
        for (RecipeEntity recipe : this.recipesCache) {
            if (recipe.getId().equals(id)) {
                return recipe;
            }
        }
        throw new RecipeNotFoundException();
//        LOG.info("Getting recipe from DB");
//
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        try {
//            connection = DbHelper.getInstance().getConnection();
//
//            preparedStatement = connection.prepareStatement("SELECT * FROM recipes WHERE active=? AND id = ?");
//            LOG.log(Level.FINE, "\n{0}", preparedStatement.toString());
//            preparedStatement.setBoolean(1, true);
//            preparedStatement.setLong(2, id);
//            preparedStatement.setMaxRows(1);
//
//            resultSet = preparedStatement.executeQuery();
//            RecipeEntity recipeEntity = null;
//            if (resultSet.next()) {
//                recipeEntity = convertToRecipeEntity(resultSet);
//            } else {
//                throw new RecipeNotFoundException();
//            }
//            return recipeEntity;
//        } catch (DatabaseConfigurationException | RecipeNotFoundException | SQLException | ParseException e) {
//            throw e;
//        } finally {
//            DbHelper.tryToCloseResources(resultSet, preparedStatement, connection);
//        }
    }

}

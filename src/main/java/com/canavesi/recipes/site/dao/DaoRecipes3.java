package com.canavesi.recipes.site.dao;

import com.canavesi.recipes.site.entities.RecipeEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Andres Canavesi
 */
public class DaoRecipes3 extends DaoBase<RecipeEntity> {

    private static final Logger LOG = Logger.getLogger(DaoRecipes3.class.getName());

    /**
     *
     */
    public DaoRecipes3() {
        super(RecipeEntity.class);
    }

    public List<RecipeEntity> findAll(int start, int end) {
        LOG.info("Getting recipes...");
        StringBuilder query = new StringBuilder();
        query.append("SELECT u FROM ").append(type.getSimpleName()).append(" u ").append(" WHERE active=TRUE ORDER BY createdAt DESC ");
        Map parameters = new HashMap(); //no parameters
        return findWithQueryString(query.toString(), parameters, start, end);
    }

}

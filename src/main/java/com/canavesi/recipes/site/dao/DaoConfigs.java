package com.canavesi.recipes.site.dao;

import java.util.logging.Logger;

/**
 * We use environment variables, we do not store in DB so this class does not
 * extends from DaoBase.
 *
 * Go to README file to configure environment variables in Tomcat
 *
 * In Heroku you can add / edit an environment variable like this:
 *
 * heroku config:set CONFIG_1="your_value"
 *
 * @author Andres Canavesi
 */
public class DaoConfigs {

    private static final Logger LOG = Logger.getLogger(DaoConfigs.class.getName());

    /**
     * There is not public instances of this class
     */
    private DaoConfigs() {

    }

    /**
     *
     * @return
     */
    public static String getDataBaseUrl() {
        String value = System.getenv("DATABASE_URL");
        if (value == null) {
            //since this is a sensitive data we do not return a default value
            throw new IllegalStateException("DATABASE_URL environment variable not found");

        }
        return value;
    }

    public static String getBaseUrl() {
        String value = System.getenv("BASE_URL");
        if (value == null) {
            value = "http://localhost:8080/recipes-site/";
        }
        if (!value.endsWith("/")) {
            value += "/";
        }
        return value;
    }

    /**
     *
     * @return
     */
    public static boolean isProduction() {
        String value = System.getenv("IS_PRODUCTION");
        if (value == null) {
            return false;
        }
        return Boolean.valueOf(value);
    }

    public static boolean getShowAdFooter() {
        String value = System.getenv("SHOW_AD_FOOTER");
        if (value == null) {
            return false;
        }
        return Boolean.valueOf(value);
    }

    public static boolean getShowAdIngredients() {
        String value = System.getenv("SHOW_AD_INGREDIENTS");
        if (value == null) {
            return false;
        }
        return Boolean.valueOf(value);
    }

    public static boolean getShowAdSteps() {
        String value = System.getenv("SHOW_AD_STEPS");
        if (value == null) {
            return false;
        }
        return Boolean.valueOf(value);
    }

    public static String getImagesBaseUrl() {
        String value = System.getenv("IMAGES_BASE_URL");
        if (value == null) {
            value = "https://res.cloudinary.com/dniiru5xy/image/upload/";
        }
        if (!value.endsWith("/")) {
            value += "/";
        }
        return value;
    }

    public static boolean getEnableCompression() {
        String value = System.getenv("ENABLE_COMPRESSION");
        if (value == null) {
            return false;
        }
        return Boolean.valueOf(value);
    }

    public static boolean getShowAdFixedLeft() {
        String value = System.getenv("SHOW_AD_FIXED_LEFT");
        if (value == null) {
            return false;
        }
        return Boolean.valueOf(value);
    }

    public static boolean getShowAdFixedRight() {
        String value = System.getenv("SHOW_AD_FIXED_RIGHT");
        if (value == null) {
            return false;
        }
        return Boolean.valueOf(value);
    }

    public static boolean getShowAdRecipeRight() {
        String value = System.getenv("SHOW_AD_RECIPE_RIGHT");
        if (value == null) {
            return false;
        }
        return Boolean.valueOf(value);
    }

    public static boolean getShowAdUnderRecipeTitle() {
        String value = System.getenv("SHOW_AD_UNDER_RECIPE_TITLE");
        if (value == null) {
            return false;
        }
        return Boolean.valueOf(value);
    }

    public static int getPageSizeDB() {
        String value = System.getenv("PAGE_SIZE_DB");
        if (value == null) {
            return 100;
        }
        return Integer.valueOf(value);
    }

    public static boolean getEnablePushEngage() {
        String value = System.getenv("ENABLE_PUSH_ENGAGE");
        if (value == null) {
            return false;
        }
        return Boolean.valueOf(value);
    }

    public static String getDatagetPushEngageApiKey() {
        String value = System.getenv("PUSH_ENGAGE_API_KEY");
        if (value == null) {
            //since this is a sensitive data we do not return a default value
            throw new IllegalStateException("PUSH_ENGAGE_API_KEY environment variable not found");

        }
        return value;
    }

    public static int getQuantityFeaturedRecipes() {
        String value = System.getenv("QUANTITY_FEATURED_RECIPES");
        if (value == null) {
            return 15;
        }
        return Integer.valueOf(value);
    }

}

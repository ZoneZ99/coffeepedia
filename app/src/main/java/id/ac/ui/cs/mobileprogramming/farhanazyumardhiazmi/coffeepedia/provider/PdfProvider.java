package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.CoffeePediaApplication;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewMethod;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewRecipe;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.repository.BrewMethodRepository;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.repository.BrewRecipeRepository;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.repository.CoffeeBeanRepository;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.util.BrewMethodPdfExportable;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.util.CoffeeBeanPdfExportable;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.util.PdfExportable;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.util.PdfUtil;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PdfProvider extends ContentProvider {

    private CoffeeBeanRepository mCoffeeBeanRepository;

    private BrewMethodRepository mBrewMethodRepository;

    private BrewRecipeRepository mBrewRecipeRepository;

    public static final String URI =
            "content://id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.provider/pdf";

    public static final Uri CONTENT_URI = Uri.parse(URI);

    public static final String NAME = "PdfProvider";

    public static final String URI_KEY = "uri";

    public static final String CONTENT_TYPE = "content_type";

    public static final String CONTENT_ID = "content_id";

    public static final String COFFEE_BEAN = "coffee_bean";

    public static final String BREW_METHOD = "brew_method";

    public static final String BREW_RECIPE = "brew_recipe";

    private PdfUtil pdfUtil;

    @Override
    public boolean onCreate() {
        mCoffeeBeanRepository = ((CoffeePediaApplication) getContext()).getCoffeeBeanRepository();
        mBrewMethodRepository = ((CoffeePediaApplication) getContext()).getBrewMethodRepository();
        mBrewRecipeRepository = ((CoffeePediaApplication) getContext()).getBrewRecipeRepository();
        pdfUtil = new PdfUtil();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "application/pdf";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri pdfUri = Uri.parse(values.getAsString(URI_KEY));
        String contentType = values.getAsString(CONTENT_TYPE);
        long contentId = values.getAsLong(CONTENT_ID);

        PdfExportable pdfExportable = null;
        if (contentType.equals(COFFEE_BEAN)) {
            List<CoffeeBean> coffeeBeans;
            coffeeBeans = mCoffeeBeanRepository.getCoffeeBeans().getValue();
            if (contentId != 0) {
                pdfExportable = new CoffeeBeanPdfExportable(
                        coffeeBeans.stream().filter(
                                bean -> bean.getCoffeeBeanId() == contentId
                        ).collect(Collectors.toList())
                );
            } else {
                pdfExportable = new CoffeeBeanPdfExportable(coffeeBeans);
            }
        } else if (contentType.equals(BREW_METHOD)) {
            List<BrewMethod> brewMethods;
            brewMethods = mBrewMethodRepository.getBrewMethods().getValue();
            if (contentId != 0) {
                pdfExportable = new BrewMethodPdfExportable(
                        brewMethods.stream().filter(
                                brewMethod -> brewMethod.getBrewMethodId() == contentId
                        ).collect(Collectors.toList())
                );
            } else {
                pdfExportable = new BrewMethodPdfExportable(brewMethods);
            }
        } else {
            List<BrewRecipe> brewRecipes;
        }

        try {
            pdfUtil.getDocument(pdfExportable, getContext().getContentResolver().openOutputStream(pdfUri));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ContentUris.withAppendedId(CONTENT_URI, contentId);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

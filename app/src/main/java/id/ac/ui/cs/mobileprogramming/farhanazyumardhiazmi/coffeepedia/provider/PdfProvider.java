package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.itextpdf.layout.Document;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.CoffeePediaApplication;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.CoffeePediaDatabase;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.repository.CoffeeBeanRepository;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.util.CoffeeBeanPdfExportable;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.util.PdfExportable;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.util.PdfUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PdfProvider extends ContentProvider {

    private CoffeeBeanRepository mCoffeeBeanRepository;

    public static final String URI =
            "content://id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.provider/pdf";

    public static final Uri CONTENT_URI = Uri.parse(URI);

    public static final String NAME = "PdfProvider";

    public static final String CONTENT_TYPE = "content_type";

    public static final String CONTENT_ID = "content_id";

    public static final String COFFEE_BEAN = "coffee_bean";

    public static final String BREW_METHOD = "brew_method";

    public static final String BREW_RECIPE = "brew_recipe";

    private PdfUtil pdfUtil;

    @Override
    public boolean onCreate() {
        mCoffeeBeanRepository = ((CoffeePediaApplication) getContext()).getCoffeeBeanRepository();
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
        String title = values.getAsString("title");
        if (title == null) {
            title = "generated_pdf";
        }
        String contentType = values.getAsString(CONTENT_TYPE);
        long contentId = values.getAsLong(CONTENT_ID);

        PdfExportable pdfExportable = null;
        if (contentType.equals(COFFEE_BEAN)) {
            List<CoffeeBean> coffeeBeans;
            if (contentId == 0) {
                coffeeBeans = mCoffeeBeanRepository.getCoffeeBeans().getValue();
            } else {
                coffeeBeans = new ArrayList<>();
                coffeeBeans.add(mCoffeeBeanRepository.getCoffeeBeanById(contentId).getValue());
            }
            pdfExportable = new CoffeeBeanPdfExportable(coffeeBeans);
        }
        try {
            Log.d("CoffeePedia", uri.toString());
            Uri pdfUri = ContentUris.withAppendedId(CONTENT_URI, contentId);
            OutputStream out = null;
            BufferedInputStream inputStream;
            out = getContext().getContentResolver().openOutputStream(pdfUri, "w");
            File file = new File(pdfUri.getPath());
            inputStream = new BufferedInputStream(new FileInputStream(file));
            int available = inputStream.available();
            byte[] buffer;
            while (available > 0) {
                buffer = new byte[available];
                int i = inputStream.read(buffer, 0, available);
                out.write(buffer, 0, i);
                available = inputStream.available();
            }
            out.flush();
            pdfUtil.getDocument(title, pdfExportable, getContext().getContentResolver().openOutputStream(pdfUri));
            out.close();
            inputStream.close();
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

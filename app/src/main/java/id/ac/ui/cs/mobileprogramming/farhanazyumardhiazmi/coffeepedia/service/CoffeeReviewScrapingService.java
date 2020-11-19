package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.BuildConfig;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.service.util.CoffeeReview;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

import java.util.ArrayList;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class CoffeeReviewScrapingService extends IntentService {

	public static final String URL = "https://www.coffeereview.com/review/";
	public static final String SCRAPING_CONTENT = "scraping_content";
	public static final String RESULT = "result";

	public CoffeeReviewScrapingService() {
		super("CoffeeReviewScrapingService");
	}

	@Override
	protected void onHandleIntent(@Nullable Intent intent) {
		OkHttpClient okHttpClient = new OkHttpClient()
			.newBuilder()
			.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
			.build();

		Retrofit retrofit = new Retrofit.Builder()
			.addConverterFactory(ScalarsConverterFactory.create())
			.baseUrl(URL)
			.client(okHttpClient)
			.build();

		final ApiService apiService = retrofit.create(ApiService.class);

		Call<String> stringCall = apiService.getStringResponse();
		stringCall.enqueue(new Callback<String>() {
			@Override
			public void onResponse(Call<String> call, Response<String> response) {
				if (response.isSuccessful()) {
					String responseString = response.body();
					Document doc = Jsoup.parse(responseString);
					ArrayList<CoffeeReview> reviews = extractDataFromScraping(doc);
					publishResults(reviews);
				}
			}

			@Override
			public void onFailure(Call<String> call, Throwable t) {
				publishFailure();
			}
		});
	}

	private ArrayList<CoffeeReview> extractDataFromScraping(Document document) {
		ArrayList<CoffeeReview> reviews = new ArrayList<>();

		Element container = document.getElementById("genesis-content");
		Elements contents = container.getElementsByClass("entry-content");
		for (int i = 1; i < contents.size(); i++) {
			Element entryContent = contents.get(i);
			Element reviewTemplate = entryContent.getElementsByClass("review-template").first();
			Element row1 = reviewTemplate.getElementsByClass("row-1").first();
			Element col1 = row1.getElementsByClass("col-1").first();
			String score = col1.getElementsByClass("review-template-rating").first().text();
			Element col2 = row1.getElementsByClass("col-2").first();
			String roaster = col2.getElementsByClass("review-roaster").first()
				.getElementsByTag("a").first()
				.text();
			String name = col2.getElementsByClass("review-title").first()
				.getElementsByTag("a").first()
				.text();
			Element row2 = reviewTemplate.getElementsByClass("row-2").first();
			String reviewText = row2.getElementsByTag("p").first().text();

			CoffeeReview coffeeReview = CoffeeReview.builder()
				.score(score)
				.roaster(roaster)
				.name(name)
				.review(reviewText)
				.build();
			reviews.add(coffeeReview);
		}

		return reviews;
	}

	private void publishResults(ArrayList<? extends Parcelable> scrapingContent) {
		Intent intent = new Intent(BuildConfig.APPLICATION_ID + ".COFFEE_REVIEW_BROADCAST");
		intent.putParcelableArrayListExtra(SCRAPING_CONTENT, scrapingContent);
		intent.putExtra(RESULT, RESULT_OK);
		sendBroadcast(intent);
	}

	private void publishFailure() {
		Intent intent = new Intent(BuildConfig.APPLICATION_ID + ".COFFEE_REVIEW_BROADCAST");
		intent.putExtra(RESULT, RESULT_CANCELED);
		sendBroadcast(intent);
	}

	private interface ApiService {
		@GET(".")
		Call<String> getStringResponse();
	}
}

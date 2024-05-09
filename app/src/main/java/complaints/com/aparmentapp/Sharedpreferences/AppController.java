package complaints.com.aparmentapp.Sharedpreferences;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import complaints.com.aparmentapp.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class AppController extends Application {

	public static final String TAG = AppController.class
			.getSimpleName();
    private String baseURL;

    public String getbaseURL() {
        return baseURL;
    }

    public void setbaseURL(String baseURL) {
        this.baseURL = baseURL;
    }
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	private static Context appContext;

	private static AppController mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
				.setDefaultFontPath("Laila-Light.ttf")
				.setFontAttrId(R.attr.fontPath)
				.build()
		);
		mInstance = this;
		appContext = getApplicationContext();
		setAppContext(getApplicationContext());
	}
	private static void setAppContext(Context appContext) {
		AppController.appContext = appContext;
	}

	public static synchronized AppController getInstance() {
		return mInstance;
	}

	public static Context getAppContext() {
		return appContext;
	}
	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}
		return mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new LruBitmapCache());
		}
		return this.mImageLoader;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
}

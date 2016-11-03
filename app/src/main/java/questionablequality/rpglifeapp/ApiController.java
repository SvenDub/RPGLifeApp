package questionablequality.rpglifeapp;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.List;
import java.util.concurrent.ExecutionException;

import questionablequality.rpglifeapp.auth.AccountAuthenticator;
import questionablequality.rpglifeapp.data.Guild;
import questionablequality.rpglifeapp.data.Quest;
import questionablequality.rpglifeapp.data.User;

public class ApiController {

    /**
     * Base server URL.
     */
    private static final String API_URL = "http://svendubbeld.nl:12345/";

    private AccountManager mAccountManager;
    private Account mAccount;

    private Context mContext;

    public ApiController(Context context) {
        mContext = context;

        mAccountManager = AccountManager.get(mContext);

        isLoggedIn();
    }

    public boolean isLoggedIn() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            throw new RuntimeException("Missing permissions"); // TODO Handle gracefully
        }
        Account[] accounts = mAccountManager.getAccountsByType(AccountAuthenticator.ACCOUNT_TYPE);

        if (accounts.length > 0) {
            mAccount = accounts[0];
            Log.d("ACCESS TOKEN", mAccountManager.getPassword(mAccount));
        }

        return mAccount != null;
    }

    /**
     * Get the logged in user.
     *
     * @param callback The callback that will receive the result.
     */
    public void getUser(final FutureCallback<Response<User>> callback) {
        getObject("/user/me", User.class)
                .setCallback(new FutureCallback<Response<User>>() {
                    @Override
                    public void onCompleted(Exception e, Response<User> result) {
                        if (e != null || result.getException() != null || result.getResult() == null) {
                            Toast.makeText(mContext, R.string.error_login, Toast.LENGTH_LONG).show();
                        }
                        callback.onCompleted(e, result);
                    }
                });
    }

    /**
     * Get the user.
     *
     * @param callback The callback that will receive the result.
     */
    public void getUserById(final FutureCallback<Response<User>> callback, int id) {
        getUserById(id)
                .setCallback(callback);
    }

    /**
     * Get the users quests.
     *
     * @return The Future that will receive the result.
     */
    public Future<Response<User>> getUserById(int id) {
        return Ion.with(mContext)
                .load(API_URL + "/user/" + id)
                .addHeader("Authorization", mAccountManager.getPassword(mAccount))
                .as(new TypeToken<User>() {
                })
                .withResponse();
    }


    /**
     * Get the user's quests.
     *
     * @param callback The callback that will receive the result.
     */
    public void getQuests(final FutureCallback<Response<List<Quest>>> callback) {
        getQuests()
                .setCallback(callback);
    }

    /**
     * Get the users quests.
     *
     * @return The Future that will receive the result.
     */
    public Future<Response<List<Quest>>> getQuests() {
        return Ion.with(mContext)
                .load(API_URL + "/quest")
                .addHeader("Authorization", mAccountManager.getPassword(mAccount))
                .as(new TypeToken<List<Quest>>() {
                })
                .withResponse();
    }

    /**
     * Get the user's quests.
     *
     * @param callback The callback that will receive the result.
     */
    public void getQuest(final FutureCallback<Response<Quest>> callback, int id) {
        getQuest(id)
                .setCallback(callback);
    }

    /**
     * Get the users quests.
     *
     * @return The Future that will receive the result.
     */
    public Future<Response<Quest>> getQuest(int id) {
        return getObject("/quest/" + id, Quest.class);
    }

    public void addQuest(Quest quest, final FutureCallback<Response<Quest>> callback) {
        addQuest(quest).setCallback(callback);
    }

    /**
     * Add a new quest.
     *
     * @param quest The new quest.
     * @return The Future that will receive the saved quest.
     */
    public Future<Response<Quest>> addQuest(Quest quest) {
        return postObject("/quest", quest, Quest.class);
    }

    public void saveQuest(Quest quest, final FutureCallback<Response<Quest>> callback){
        saveQuest(quest).setCallback(callback);
    }


    /**
     *Saves the quest.
     */
    public Future<Response<Quest>> saveQuest(Quest quest){
        return postObject("/quest/" + quest.getId(), quest, Quest.class);
    }

    /**
     * Get the user's quests.
     *
     * @param callback The callback that will receive the result.
     */
    public void getGuildQuests(final FutureCallback<Response<List<Quest>>> callback) {
        getGuildQuests()
                .setCallback(callback);
    }

    /**
     * Get the guilds quests.
     *
     * @return The Future that will receive the result.
     */
    public Future<Response<List<Quest>>> getGuildQuests() {
        return Ion.with(mContext)
                .load(API_URL + "/guildquest")
                .addHeader("Authorization", mAccountManager.getPassword(mAccount))
                .as(new TypeToken<List<Quest>>() {
                })
                .withResponse();
    }

    public void addGuildQuest(Quest quest, final FutureCallback<Response<Quest>> callback) {
        addGuildQuest(quest).setCallback(callback);
    }

    /**
     * Add a new quest.
     *
     * @param quest The new quest.
     * @return The Future that will receive the saved quest.
     */
    public Future<Response<Quest>> addGuildQuest(Quest quest) {
        return postObject("/guildquest", quest, Quest.class);
    }

    public void saveGuildQuest(Quest quest, final FutureCallback<Response<Quest>> callback){
        saveGuildQuest(quest).setCallback(callback);
    }


    /**
     *Saves the quest.
     */
    public Future<Response<Quest>> saveGuildQuest(Quest quest){
        return postObject("/guildquest/" + quest.getId(), quest, Quest.class);
    }

    public void getGuilds(final FutureCallback<Response<List<Guild>>> callback) {
        getGuilds()
                .setCallback(callback);
    }

    /**
     * Get the guilds quests.
     *
     * @return The Future that will receive the result.
     */
    public Future<Response<List<Guild>>> getGuilds() {
        return Ion.with(mContext)
                .load(API_URL + "/guild")
                .addHeader("Authorization", mAccountManager.getPassword(mAccount))
                .as(new TypeToken<List<Guild>>() {
                })
                .withResponse();
    }



    /**
     * Attempts to login.
     *
     * @param email    The username.
     * @param password The password.
     * @return The access token, or the empty string if the login failed.
     */
    public String login(String email, String password) {
        JsonObject request = new JsonObject();
        request.addProperty("username", email);
        request.addProperty("password", password);

        try {
            Response<JsonObject> result = Ion.with(mContext)
                    .load(API_URL + "/login")
                    .setJsonObjectBody(request)
                    .asJsonObject()
                    .withResponse().get();

            if (result.getException() == null && result.getResult() != null) {
                return result.getResult().get("accessToken").getAsString();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Build request for getting an object.
     *
     * @param path  The API path.
     * @param clazz The type of object to receive.
     * @param <T>   The type of object to receive.
     * @return The request for getting an object.
     */
    private <T> Future<Response<T>> getObject(String path, Class<T> clazz) {
        return Ion.with(mContext)
                .load(API_URL + path)
                .addHeader("Authorization", mAccountManager.getPassword(mAccount))
                .as(clazz)
                .withResponse();
    }

    /**
     * Build request for posting a JSON object.
     *
     * @param path   The API path.
     * @param object The JSON object to post.
     * @param clazz  The type of object to receive.
     * @param <T>    The type of object to receive.
     * @return The request for getting an object.
     */
    private <T> Future<Response<T>> postJsonObject(String path, JsonObject object, Class<T> clazz) {
        return Ion.with(mContext)
                .load(API_URL + path)
                .addHeader("Authorization", mAccountManager.getPassword(mAccount))
                .setJsonObjectBody(object)
                .as(clazz)
                .withResponse();
    }

    /**
     * Build request for posting an object.
     *
     * @param path   The API path.
     * @param object The object to post.
     * @param clazz  The type of object to receive.
     * @param <T>    The type of object to receive.
     * @return The request for getting an object.
     */
    private <T> Future<Response<T>> postObject(String path, Object object, Class<T> clazz) {
        return Ion.with(mContext)
                .load(API_URL + path)
                .addHeader("Authorization", mAccountManager.getPassword(mAccount))
                .setJsonPojoBody(object)
                .as(clazz)
                .withResponse();
    }
}

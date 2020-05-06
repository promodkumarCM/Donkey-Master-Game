package com.sparklingapps.cardgamefrog;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.heroiclabs.nakama.*;
import com.heroiclabs.nakama.Error;
import com.heroiclabs.nakama.Error.ErrorCode;
import com.stumbleupon.async.*;

import java.util.List;

public class NakamaSessionManager {
  private final Client client;
  private final Callback<Error, Error> errCallback;
  private Session session;
  private Match newMatch;

  public NakamaSessionManager(){
   /* client = DefaultClient.builder("5LwHhurhMM")
            .host("104.196.168.44")
            .port(7350)
            .ssl(false)
            .listener(new ClientListener() {
              @Override
              public void onDisconnect() {

              }

              @Override
              public void onTopicMessage(TopicMessage message) {

              }

              @Override
              public void onTopicPresence(TopicPresence presence) {

              }

              @Override
              public void onMatchmakeMatched(MatchmakeMatched matched) {

              }

              @Override
              public void onMatchData(MatchData matchData) {

              }

              @Override
              public void onMatchPresence(MatchPresence matchPresence) {

              }

              @Override
              public void onNotifications(List<Notification> notifications) {

              }
            })
            .build();*/

    client = DefaultClient.builder("default_key")
            .host("192.168.1.77")
            .port(7351)
            .ssl(false)
            .listener(new ClientListener() {
              @Override
              public void onDisconnect() {

              }

              @Override
              public void onTopicMessage(TopicMessage message) {

              }

              @Override
              public void onTopicPresence(TopicPresence presence) {

              }

              @Override
              public void onMatchmakeMatched(MatchmakeMatched matched) {

              }

              @Override
              public void onMatchData(MatchData matchData) {

              }

              @Override
              public void onMatchPresence(MatchPresence matchPresence) {

              }

              @Override
              public void onNotifications(List<Notification> notifications) {

              }
            }).build();


    errCallback = new Callback<Error, Error>() {
      @Override
      public Error call(Error err) throws Exception {
        System.err.format("Error('%s', '%s')", err.getCode(), err.getMessage());
        return  err;
      }
    };
  }

  /** Get the Client reference to send/receive messages from the server. */
  public Client getClient(){
    return client;
  }


  public void logOut(){
    client.logout()
            .addErrback(new Callback<Error, Error>() {
              @Override
              public Error call (Error error) throws Exception{
                if(error.getCode() == ErrorCode.USER_NOT_FOUND){
                  // Login Failed this is a new user
                  System.err.format("Error('%s', '%s')", error.getCode(), error.getMessage());
                }
                throw error;
              }
            });
  }

  /**
   * Login and connect a user using an email and password.
   * @param activity The Activity calling this method.
   * @param email The email to login with.
   * @param password The password to login with
   */
  public void logIn(final Activity activity, String email, String password){
    final AuthenticateMessage msg = AuthenticateMessage.Builder.email(email, password);
    client.login(msg)
            .addCallbackDeferring(new Callback<Deferred<Session>, Session>() {
              @Override
              public Deferred<Session> call(Session session) throws Exception {
                // Log In was successful
                // Store session for later use
                String token = session.getToken();
                SharedPreferences pref = activity.getSharedPreferences("PREF_TOKEN", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("nk.session", session.getToken());

                if(pref.edit().putString("nk.session", token).commit()){
                  System.out.println(pref.getString("nk.session", ""));
                }

                return client.connect(session);
              }
            })
            .addErrback(new Callback<Error, Error>() {
              @Override
              public Error call (Error error) throws Exception{
                if(error.getCode() == ErrorCode.USER_NOT_FOUND){
                  // Login Failed this is a new user
                  System.err.format("Error('%s', '%s')", error.getCode(), error.getMessage());
                }
                throw error;
              }
            })
            .addCallback(new Callback<Session, Session>() {
              @Override
              public Session call(Session session) throws Exception{
                System.out.println("Connected");
                return session;
              }
            })
            .addErrback(errCallback);
  }

  public void register(final Activity activity, String email, String password){
    final AuthenticateMessage msg = AuthenticateMessage.Builder.email(email, password);
    client.register(msg)
            .addErrback(new Callback<Error, Error>() {
              @Override
              public Error call(Error error) throws Exception {
                System.err.format("Error('%s', '%s')", error.getCode(), error.getMessage());
                return error;
              }
            })
            .addCallbackDeferring(new Callback<Deferred<Session>, Session>() {
              @Override
              public Deferred<Session> call(Session session) throws Exception {
                // Registration has succeeded, try connecting again.
                // Store the session for later use.
                SharedPreferences pref = activity.getSharedPreferences("PREF_TOKEN", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("nk.session", session.getToken());
                edit.commit();

                return client.connect(session);
              }
            })
            .addCallback(new Callback<Session, Session>() {
              @Override
              public Session call(Session session) throws Exception {
                // We're connected to the server!
                System.out.println("Connected!");
                return session;
              }
            })
            .addErrback(errCallback);
  }

  /**
   * Attempt to restore a Session from SharedPreferences and connect.
   * @param activity The Activity calling this method.
   */
  public void restoreSessionAndConnect(Activity activity) {
    SharedPreferences pref = activity.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
    // Lets check if we can restore a cached session.
    String sessionString = pref.getString("nk.session", null);
    if (sessionString == null || sessionString.isEmpty()) {
      return; // We have no session to restore.
    }

    Session session = DefaultSession.restore(sessionString);
    if (session.isExpired(System.currentTimeMillis())) {
      return; // We can't restore an expired session.
    }

    final NakamaSessionManager self = this;
    client.connect(session)
            .addCallback(new Callback<Session, Session>() {
              @Override
              public Session call(Session session) throws Exception {
                System.out.format("Session connected: '%s'.", session.getToken());
                self.session = session;
                return session;
              }
            });
  }

  public void createMatch(){
    final CollatedMessage<Match> msg = MatchCreateMessage.Builder.build();
    Deferred<Match> deferred = client.send(msg);

    deferred
            .addCallbackDeferring(new Callback<Deferred<Boolean>, Match>() {
              @Override
              public Deferred<Boolean> call(Match match) throws Exception{
                newMatch = match;
                final UncollatedMessage data = MatchDataSendMessage.Builder
                        .newBuilder(match.getId())
                        .data("test".getBytes())
                        .build();
                return client.send(data);
              }
            });

    try {
      deferred.join(3000);
    }
    catch (Exception ex){
      ex.printStackTrace();
    }

  }

  public void joinMatch(String matchId){
    final CollatedMessage<ResultSet<Match>> matchJoin = MatchesJoinMessage.Builder.newBuilder().matchId(matchId).build();
    final Deferred<ResultSet<Match>> deferred = client.send(matchJoin);

    deferred
            .addCallbackDeferring(new Callback<Deferred<Boolean>, ResultSet<Match>>() {
              @Override
              public Deferred<Boolean> call(ResultSet<Match> matches) throws Exception {

                final UncollatedMessage data = MatchDataSendMessage.Builder
                        .newBuilder(matches.getResults().get(0).getId())
                        .data("Test".getBytes())
                        .build();
                return client.send(data);
              }
            });
    try {
      deferred.join(2000);
    }
    catch (Exception ex){
      ex.printStackTrace();
    }
  }

  public Match getNewMatch() {
    return newMatch;
  }
}
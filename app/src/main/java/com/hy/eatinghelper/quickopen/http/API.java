package com.hy.eatinghelper.quickopen.http;

import com.hy.eatinghelper.accesstoken.AccessToken;
import com.hy.eatinghelper.unit.Unit;
import com.hy.eatinghelper.unit.UnitBody;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by huyin on 2017/8/10.
 */

public class API {
     public static final String BASE_URL = "https://aip.baidubce.com";

     public static final String ACESSTOKEN_URL = "/oauth/2.0/token";

     public static final String UNIT_URL = "/rpc/2.0/solution/v1/unit_utterance";

     /**
      * 得到accesToken
      */
     public interface AccessTokenService {
          @GET(ACESSTOKEN_URL)
          Flowable<AccessToken> getAccessToken(@Query("grant_type") String grant_type,
                                               @Query("client_id") String client_id,
                                               @Query("client_secret") String client_secret);
     }

     /**
      * Unit交互
      */
     public interface UnitService {
          @POST(UNIT_URL)
          Flowable<Unit> getUnit(@Query("access_token") String accessToken,
                                 @Body UnitBody unitBody);
     }
}

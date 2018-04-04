package amudhan.httpserver.utils;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {
  public static int countOccurrencesOf(String str, String sub) {
    if (!hasLength(str) || !hasLength(sub)) {
      return 0;
    }

    int count = 0;
    int pos = 0;
    int idx;
    while ((idx = str.indexOf(sub, pos)) != -1) {
      ++count;
      pos = idx + sub.length();
    }
    return count;
  }

  public static boolean hasLength(String str) {
    return (str != null && !str.isEmpty());
  }

  public static Map<String, String> getQueryParameters(String query) {
    Map<String, String> queryParams = new HashMap<>();
    if (query != null && !query.isEmpty()) {
      for (String param : query.split("&")) {
        String queryPair[] = param.split("=");
        if (queryPair.length > 1) {
          queryParams.put(queryPair[0], queryPair[1]);
        } else {
          queryParams.put(queryPair[0], "");
        }
      }
    }
    return queryParams;
  }

}

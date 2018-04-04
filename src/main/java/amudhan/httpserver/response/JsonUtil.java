package amudhan.httpserver.response;

import amudhan.httpserver.utils.StringUtils;

/**
 * Utility class that helps parsing requests that contain json array/json objects
 * 
 * @author aravamudhan.a
 *
 */
public class JsonUtil {

  public boolean isEndOfRecord(String line) {
    boolean isRecordEnd = StringUtils.countOccurrencesOf(line, "{") == StringUtils
        .countOccurrencesOf(line, "}")
        && (line.trim().endsWith("}") || line.trim().endsWith(",") || line.trim().endsWith("]"));
    return isRecordEnd;
  }

}

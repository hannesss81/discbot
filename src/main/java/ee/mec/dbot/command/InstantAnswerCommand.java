package ee.mec.dbot.command;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import ee.mec.dbot.properties.DBotProperties;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HttpsURLConnection;
import org.tinylog.Logger;

/** Command for using https://www.microsoft.com/en-us/research/project/answer-search/ */
public class InstantAnswerCommand extends Command {

  private String host = "https://api.labs.cognitive.microsoft.com";
  private String path = "/answerSearch/v7.0/search";

  private String token = DBotProperties.MS_INSTANT_API_TOKEN;

  public InstantAnswerCommand() {
    this.name = "answer";
    this.help = "Uses MS Project Answer Search API to get an instant answer to whatever question.";
  }

  @Override
  protected void execute(CommandEvent commandEvent) {
    String searchQuery = commandEvent.getArgs();

    try {
      URL url =
          new URL(host + path + "?q=" + URLEncoder.encode(searchQuery, StandardCharsets.UTF_8) + "&mkt=en-us");
      HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
      connection.setRequestProperty("Ocp-Apim-Subscription-Key", token);

      InputStream stream = connection.getInputStream();
      JsonObject responseObject = JsonParser.object().from(stream);

      for (String answerType : responseObject.keySet()) {
        String response = "";
        switch (answerType) {
          case "webPages":
            response = searchResultToString(responseObject.getObject("webPages").getArray("value").getObject(0));
            break;
          case "computation":
            response = computationResultToString(responseObject.getObject("computation"));
            break;
          case "facts":
            response = factResultToString(responseObject.getObject("facts").getArray("value").getObject(0));
            break;
        }
        commandEvent.reply(response);
      }
    } catch (IOException e) {
      Logger.error(e);
      commandEvent.replyError("Error when opening connection to host.");
    } catch (JsonParserException e) {
      Logger.error(e);
      commandEvent.replyError("Error when parsing the response from host.");
    }
  }

  private String factResultToString(JsonObject factResult) {
    return String.format("`-> Fact result: %s`\n", factResult.getString("description"));
  }

  private String computationResultToString(JsonObject computationResult) {
    return String.format("`-> Computation result: %s`\n", computationResult.getString("value"));
  }

  private String searchResultToString(JsonObject searchResult) {
    return String.format("`-> Bing top result:  %s`\n", searchResult.getString("displayUrl"))
        + String.format("`Summary:: %s`", searchResult.getString("snippet"));
  }
}

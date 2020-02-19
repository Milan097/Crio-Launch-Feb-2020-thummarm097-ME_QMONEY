
package com.crio.warmup.stock;


import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TotalReturnsDto;
import com.crio.warmup.stock.log.UncaughtExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.client.RestTemplate;


public class PortfolioManagerApplication {







  // TODO: CRIO_TASK_MODULE_REST_API
  //  Copy the relavent code from #mainReadFile to parse the Json into PortfolioTrade list.
  //  Now That you have the list of PortfolioTrade already populated in module#1
  //  For each stock symbol in the portfolio trades,
  //  Call Tiingo api (https://api.tiingo.com/tiingo/daily/<ticker>/prices?startDate=&endDate=&token=)
  //  with
  //   1. ticker = symbol in portfolio_trade
  //   2. startDate = purchaseDate in portfolio_trade.
  //   3. endDate = args[1]
  //  Use RestTemplate#getForObject in order to call the API,
  //  and deserialize the results in List<Candle>
  //  Note - You may have to register on Tiingo to get the api_token.
  //    Please refer the the module documentation for the steps.
  //  Find out the closing price of the stock on the end_date and
  //  return the list of all symbols in ascending order by its close value on endDate
  //  Test the function using gradle commands below
  //   ./gradlew run --args="trades.json 2020-01-01"
  //   ./gradlew run --args="trades.json 2019-07-01"
  //   ./gradlew run --args="trades.json 2019-12-03"
  //  And make sure that its printing correct results.

  public static List<String> mainReadQuotes(String[] args) throws IOException, URISyntaxException {
     return Collections.emptyList();
  }









  public static void main(String[] args) throws Exception {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    ThreadContext.put("runId", UUID.randomUUID().toString());


    printJsonObject(mainReadQuotes(args));


  }
}


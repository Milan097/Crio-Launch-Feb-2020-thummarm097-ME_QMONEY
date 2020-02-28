
package com.crio.warmup.stock;

import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.crio.warmup.stock.dto.TotalReturnsDto;
import com.crio.warmup.stock.log.UncaughtExceptionHandler;
import com.crio.warmup.stock.portfolio.PortfolioManager;
import com.crio.warmup.stock.portfolio.PortfolioManagerFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.util.FileUtils;
import org.springframework.web.client.RestTemplate;


public class PortfolioManagerApplication {

  public static List<String> mainReadQuotes(String[] args) throws IOException, URISyntaxException {
    File file = resolveFileFromResources(args[0]);
    ObjectMapper objectMapper = getObjectMapper();
    List<PortfolioTrade> allJsonObjects = objectMapper.readValue(file,
                       new TypeReference<List<PortfolioTrade>>() {});
    List<String> allSymbols = new ArrayList<String>();
    List<TotalReturnsDto> mappingList = getSortedClosingPrice(objectMapper,allJsonObjects,args);
    Collections.sort(mappingList);
    for (TotalReturnsDto trDto : mappingList) {
      allSymbols.add(trDto.getSymbol());
    }
    //System.out.print(allSymbols);
    return allSymbols;
  }

  public static List<TotalReturnsDto> getSortedClosingPrice(ObjectMapper objectMapper,
                                      List<PortfolioTrade> allJsonObjects,String[] args)
                                      throws IOException, URISyntaxException {
    RestTemplate restTemplate = new RestTemplate();
    List<TotalReturnsDto> mappingList = new ArrayList<TotalReturnsDto>();
    for (PortfolioTrade obj : allJsonObjects) {
      String uri = "https://api.tiingo.com/tiingo/daily/" + obj.getSymbol()
                        + "/prices?startDate=" + obj.getPurchaseDate() + "&endDate="
                        + args[1] + "&token=366b6aa86c15fcbe47efcd6b4dc938a33de2f4e0";
      String result = (restTemplate.getForObject(uri,String.class));
      List<TiingoCandle> candleList = objectMapper.readValue(result,
                        new TypeReference<List<TiingoCandle>>() {});;
      TiingoCandle candleObj = candleList.get(candleList.size() - 1);
      TotalReturnsDto trDto = new TotalReturnsDto(obj.getSymbol(),candleObj.getClose());
      mappingList.add(trDto);
      //System.out.println(obj.getSymbol()+candleObj);
    }
    return mappingList;
  }

  public static List<String> mainReadFile(String[] args) throws IOException, URISyntaxException {
    File file = resolveFileFromResources(args[0]);
    ObjectMapper objectMapper = getObjectMapper();
    List<PortfolioTrade> allJsonObjects = objectMapper.readValue(file,
                       new TypeReference<List<PortfolioTrade>>() {});
    List<String> allSymbols = new ArrayList<String>();
    for (PortfolioTrade obj : allJsonObjects) {
      allSymbols.add(obj.getSymbol());
    }
    // System.out.print(allSymbols);
    return allSymbols;
  }

  private static void printJsonObject(Object object) throws IOException {
    Logger logger = Logger.getLogger(PortfolioManagerApplication.class.getCanonicalName());
    ObjectMapper mapper = new ObjectMapper();
    logger.info(mapper.writeValueAsString(object));
  }

  private static File resolveFileFromResources(String filename) throws URISyntaxException {
    return Paths.get(
        Thread.currentThread().getContextClassLoader().getResource(filename).toURI()).toFile();
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }

  public static List<String> debugOutputs() {

    String valueOfArgument0 = "trades.json";
    String resultOfResolveFilePathArgs0 = 
        "/home/crio-user/workspace/thummarm097-ME_QMONEY/qmoney/bin/main/trades.json";
    String toStringOfObjectMapper = "com.fasterxml.jackson.databind.ObjectMapper@373ebf74";
    String functionNameFromTestFileInStackTrace = "PortfolioManagerApplication.mainReadFile()";
    String lineNumberFromTestFileInStackTrace = "21";


    return Arrays.asList(new String[]{valueOfArgument0, resultOfResolveFilePathArgs0,
        toStringOfObjectMapper, functionNameFromTestFileInStackTrace,
        lineNumberFromTestFileInStackTrace});
  }

  public static List<AnnualizedReturn> mainCalculateSingleReturn(String[] args)
      throws IOException, URISyntaxException {
    File file = resolveFileFromResources(args[0]);
    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper objectMapper = getObjectMapper();
    List<PortfolioTrade> allJsonObjects = objectMapper.readValue(file,
                        new TypeReference<List<PortfolioTrade>>() {});
    List<AnnualizedReturn> annualizedReturns = new ArrayList<AnnualizedReturn>();
    for (PortfolioTrade obj : allJsonObjects) {
      String uri = "https://api.tiingo.com/tiingo/daily/" + obj.getSymbol()
                        + "/prices?startDate=" + obj.getPurchaseDate() + "&endDate="
                        + args[1] + "&token=366b6aa86c15fcbe47efcd6b4dc938a33de2f4e0";
      String result = (restTemplate.getForObject(uri,String.class));
      List<TiingoCandle> candleList = objectMapper.readValue(result,
                        new TypeReference<List<TiingoCandle>>() {});
      TiingoCandle candleObj = candleList.get(candleList.size() - 1);
      Double buyPrice = candleList.get(0).getOpen();
      Double sellPrice = candleObj.getClose();
      AnnualizedReturn anRet = calculateAnnualizedReturns(
                          candleObj.getDate(),obj,buyPrice,sellPrice);
      annualizedReturns.add(anRet);
    }
    Collections.sort(annualizedReturns,Collections.reverseOrder());
    // System.out.print(annualizedReturns);
    return annualizedReturns;
  }

  public static AnnualizedReturn calculateAnnualizedReturns(LocalDate endDate,
        PortfolioTrade trade, Double buyPrice, Double sellPrice) {
    Double totalReturn = (sellPrice - buyPrice) / buyPrice; 
    double totalNoOfYears = ChronoUnit.DAYS.between(trade.getPurchaseDate(),endDate) / 365.0;
    // System.out.println(totalNoOfYears);
    Double annualizedReturn = Math.pow((1 + totalReturn),(1.0 / totalNoOfYears)) - 1;
    return new AnnualizedReturn(trade.getSymbol(),annualizedReturn,totalReturn);
  }


  public static String readFileAsString(String file) throws IOException, URISyntaxException {
    return file;
  }

  public static List<AnnualizedReturn> mainCalculateReturnsAfterRefactor(String[] args)
      throws Exception {
    String file = args[0];
    LocalDate endDate = LocalDate.parse(args[1]);
    String contents = readFileAsString(file);
    File file1 = resolveFileFromResources(contents);
    ObjectMapper objectMapper = getObjectMapper();

    List<PortfolioTrade> portfolioTrades = objectMapper.readValue(file1,
                    new TypeReference<List<PortfolioTrade>>() {});
    RestTemplate restTemplate = new RestTemplate();
    PortfolioManager portfolioManager = PortfolioManagerFactory.getPortfolioManager(restTemplate);
    return portfolioManager.calculateAnnualizedReturn(portfolioTrades, endDate);
  }







  // new Incoming File.....

  public static void main(String[] args) throws Exception {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    ThreadContext.put("runId", UUID.randomUUID().toString());

  }
}


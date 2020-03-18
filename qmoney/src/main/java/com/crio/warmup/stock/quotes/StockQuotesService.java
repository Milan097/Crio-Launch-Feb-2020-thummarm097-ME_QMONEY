package com.crio.warmup.stock.quotes;

import com.crio.warmup.stock.dto.Candle;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDate;
import java.util.List;

// TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
//  The implementations of this class should strictly follow the contracts provided in this class

public interface StockQuotesService {

  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  The implementation of this functions will be doing following tasks
  //  1. Build the appropriate url to communicate with thirdparty.
  //  The url should consider startDate and endDate if it is supported by the provider.
  //  2. Perform thirdparty communication with the Url prepared in step#1
  //  3. Map the response and convert the same to List<Candle>
  //  4. If the provider does not support startDate and endDate, then the implementation
  //  shoould also filter the dates based on startDate and endDate.
  //  5. return a sorted List<Candle> sorted ascending based on Candle#getDate



  //CHECKSTYLE:OFF
  List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws JsonProcessingException
  ;
  //CHECKSTYLE:ON

}

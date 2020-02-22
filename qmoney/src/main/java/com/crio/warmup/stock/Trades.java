package com.crio.warmup.stock;

public class Trades {
  private String symbol;
  private int quantity;
  private String tradeType;
  private String purchaseDate;

  public String getSymbol() {
    return this.symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getTradeType() {
    return this.tradeType;
  }

  public void setTradeType(String tradeType) {
    this.tradeType = tradeType;
  }

  public String getPurchaseDate() {
    return this.purchaseDate;
  }
  
  public void setPurchaseDate(String purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public int getQuantity() {
    return this.quantity;
  }
  
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
  
}
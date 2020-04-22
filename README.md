# Crio-Launch-Feb-2020-thummarm097-ME_QMONEY
QMoney is a visual stock portfolio analyzer. It helps portfolio managers make trade recommendations for their clients.

During this Micro-Experience, I:

    Implemented the core logic of the portfolio manager and published it as a library.

    Refactored code to add support for multiple stock quote services.

    Improved application stability and performance.


QMoney Architecture<br/><br/>
![Basic Concept Image](https://github.com/Milan097/Crio-Launch-Feb-2020-thummarm097-ME_QMONEY/blob/master/report-image/image-0.png)

<br/>QMoney Portfolio Manager Interface<br/><br/>
![Basic Concept Image](https://github.com/Milan097/Crio-Launch-Feb-2020-thummarm097-ME_QMONEY/blob/master/report-image/image-1.png)



QMoney Modules

  1.  Fetch stock quotes and compute annualized stock returns

      - Scope of work:<br/><br/>
               1. Used Tiingo’s REST APIs to fetch stock quotes.<br/>
               2. Computed the annualized returns based on stock purchase date and holding period.<br/>
      - Skills Learned:<br/><br/>
               Java, REST API, Jackson
      


  2.  Refactor using Java interfaces and publish a JAR file

      - Scope of work:<br/><br/>
              1. Refactored code to adapt to an updated interface contract published by the backend team.<br/>
              2. Published the portfolio manager library as a JAR for easy versioning and distribution.<br/>
              3. Created examples to help document library (JAR) usage.<br/><br/>
      - Skills Learned:<br/><br/>
               Interfaces, Code Refactoring, Gradle   



  3.  Improve application availability and stability

      - Scope of work:<br/><br/>
              1. Added support for a backup stock quote service (Alpha Vantage) to improve service availability.<br/>
              2. Improved application stability with comprehensive error reporting and better exception handling.<br/><br/>              
      - Skills Learned:<br/><br/>
               Interfaces, Exception Handling

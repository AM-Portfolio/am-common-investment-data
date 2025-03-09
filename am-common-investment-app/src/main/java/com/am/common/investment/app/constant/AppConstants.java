package com.am.common.investment.app.constant;

public final class AppConstants {
    private AppConstants() {
        // Private constructor to prevent instantiation
    }

    public static final class InfluxDB {
        private InfluxDB() {}
        
        public static final String BUCKET = "investment_data";
        public static final String ORG = "org";
        public static final String MEASUREMENT_EQUITY_PRICE = "equity";
        public static final String MEASUREMENT_MARKET_INDEX = "market_index";
    }

    public static final class TestData {
        private TestData() {}
        
        public static final String SYMBOL_AAPL = "AAPL";
        public static final String SYMBOL_GOOGL = "GOOGL";
        public static final String ISIN_AAPL = "US0378331005";
        public static final String EXCHANGE_NASDAQ = "NASDAQ";
        public static final String CURRENCY_USD = "USD";
    }

    public static final class Query {
        private Query() {}
        
        public static final String EQUITY_PRICE_QUERY_TEMPLATE = 
            "from(bucket: \"%s\") " +
            "|> range(start: -%d%s) " +
            "|> filter(fn: (r) => r._measurement == \"%s\" and r.symbol == \"%s\") " +
            "|> filter(fn: (r) => r._field == \"open\" or r._field == \"high\" or r._field == \"low\" or " +
            "r._field == \"close\" or r._field == \"volume\" or r._field == \"exchange\") " +
            "|> last() " +
            "|> pivot(rowKey: [\"_time\"], " +
            "        columnKey: [\"_field\"], " +
            "        valueColumn: \"_value\")";

        public static final String MARKET_INDEX_QUERY_TEMPLATE = 
            "from(bucket: \"%s\") " +
            "|> range(start: -%d%s) " +
            "|> filter(fn: (r) => r._measurement == \"%s\" and r.index == \"%s\") " +
            "|> filter(fn: (r) => r._field == \"market_data_open\" or r._field == \"market_data_high\" or " +
            "r._field == \"market_data_low\" or r._field == \"market_data_close\" or " +
            "r._field == \"market_data_previous_close\" or r._field == \"market_data_percentage_change\" or " +
            "r._field == \"fundamental_price_earning\" or r._field == \"fundamental_price_book\" or " +
            "r._field == \"fundamental_dividend_yield\" or r._field == \"market_breadth_advances\" or " +
            "r._field == \"market_breadth_declines\" or r._field == \"market_breadth_unchanged\" or " +
            "r._field == \"historical_comparison_value\" or r._field == \"historical_comparison_per_change_365d\" or " +
            "r._field == \"historical_comparison_date_365d_ago\" or r._field == \"historical_comparison_per_change_30d\" or " +
            "r._field == \"historical_comparison_date_30d_ago\" or r._field == \"historical_comparison_previous_day\" or " +
            "r._field == \"historical_comparison_one_week_ago\" or r._field == \"historical_comparison_one_month_ago\" or " +
            "r._field == \"historical_comparison_one_year_ago\") " +
            "|> last() " +
            "|> pivot(rowKey: [\"_time\"], " +
            "        columnKey: [\"_field\"], " +
            "        valueColumn: \"_value\")";
    }
}

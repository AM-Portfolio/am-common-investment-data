package com.am.common.investment.service.impl;

import com.am.common.investment.model.equity.EquityPrice;
import com.am.common.investment.persistence.influx.measurement.EquityPriceMeasurement;
import com.am.common.investment.persistence.repository.measurement.EquityPriceMeasurementRepository;
import com.am.common.investment.service.EquityService;
import com.am.common.investment.service.mapper.EquityMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquityServiceImpl implements EquityService {
    private static final Logger logger = LoggerFactory.getLogger(EquityServiceImpl.class);

    private final EquityPriceMeasurementRepository priceRepository;
    private final EquityMapper mapper;

    @Override
    public void savePrice(EquityPrice price) {
        logger.debug("Saving equity price for symbol: {}, isin: {}", price.getSymbol(), price.getIsin());
        EquityPriceMeasurement measurement = mapper.toMeasurement(price);
        priceRepository.save(measurement);
        logger.info("Successfully saved equity price for symbol: {}, time: {}", price.getSymbol(), price.getTime());
    }

    @Override
    public void saveAllPrices(List<EquityPrice> prices) {
        if (prices == null || prices.isEmpty()) {
            logger.warn("No prices provided to save");
            return;
        }

        logger.debug("Saving batch of {} equity prices", prices.size());
        long startTime = System.currentTimeMillis();

        // Convert to measurements and save
        List<EquityPriceMeasurement> measurements = prices.stream()
            .map(mapper::toMeasurement)
            .collect(Collectors.toList());
        
        priceRepository.saveAll(measurements);

        // Group prices by symbol for better logging
        Map<String, Long> pricesPerSymbol = prices.stream()
            .collect(Collectors.groupingBy(EquityPrice::getSymbol, Collectors.counting()));
        
        long endTime = System.currentTimeMillis();
        
        // Log summary
        logger.info("Successfully saved {} prices for {} symbols in {}ms", 
            prices.size(), pricesPerSymbol.size(), (endTime - startTime));
        
        // Log details per symbol
        pricesPerSymbol.forEach((symbol, count) -> {
            Instant minTime = prices.stream()
                .filter(p -> p.getSymbol().equals(symbol))
                .map(EquityPrice::getTime)
                .min(Instant::compareTo)
                .orElse(null);
            Instant maxTime = prices.stream()
                .filter(p -> p.getSymbol().equals(symbol))
                .map(EquityPrice::getTime)
                .max(Instant::compareTo)
                .orElse(null);
                
            logger.debug("Symbol: {} - Saved {} prices between {} and {}", 
                symbol, count, minTime, maxTime);
        });
    }

    @Override
    public Optional<EquityPrice> getLatestPriceByKey(String key) {
        logger.debug("Fetching latest price for key: {}", key);
        long startTime = System.currentTimeMillis();
        
        Optional<EquityPrice> result = priceRepository.findLatestByKey(key)
            .map(mapper::toModel);
            
        long endTime = System.currentTimeMillis();
        if (result.isPresent()) {
            EquityPrice price = result.get();
            logger.debug("Latest price found for key: {}, symbol: {}, price: {}, time: {}, query duration: {}ms", 
                key, price.getSymbol(), price.getClose(), price.getTime(), 
                (endTime - startTime));
        } else {
            logger.debug("No price found for key: {}, query duration: {}ms", key, (endTime - startTime));
        }
        
        return result;
    }

    @Override
    public List<EquityPrice> getPriceHistoryByKey(String key, Instant startTime, Instant endTime) {
        logger.debug("Fetching price history for key: {} between {} and {}", key, startTime, endTime);
        long queryStartTime = System.currentTimeMillis();
        
        List<EquityPrice> prices = priceRepository.findByKeyAndTimeBetween(key, startTime, endTime)
            .stream()
            .map(mapper::toModel)
            .collect(Collectors.toList());
        
        return getPriceAndstampLog(key, queryStartTime, prices);
    }

    @Override
    public List<EquityPrice> getPricesByExchange(String exchange) {
        logger.debug("Fetching prices for exchange: {}", exchange);
        long queryStartTime = System.currentTimeMillis();
        
        List<EquityPrice> prices = priceRepository.findByExchange(exchange)
            .stream()
            .map(mapper::toModel)
            .collect(Collectors.toList());

        long queryEndTime = System.currentTimeMillis();
        if (!prices.isEmpty()) {
            // Group prices by symbol and find latest price for each
            Map<String, EquityPrice> latestBySymbol = new HashMap<>();
            for (EquityPrice price : prices) {
                latestBySymbol.merge(price.getSymbol(), price,
                    (existing, newPrice) -> newPrice.getTime().isAfter(existing.getTime()) ? newPrice : existing);
            }

            // Get first and last price for each symbol
            Map<String, EquityPrice> firstBySymbol = new HashMap<>();
            for (EquityPrice price : prices) {
                firstBySymbol.merge(price.getSymbol(), price,
                    (existing, newPrice) -> newPrice.getTime().isBefore(existing.getTime()) ? newPrice : existing);
            }

            logger.debug("Found {} total prices across {} symbols on exchange: {}", 
                prices.size(), latestBySymbol.size(), exchange);
            
            // Log details for each symbol
            List<String> symbols = new ArrayList<>(latestBySymbol.keySet());
            Collections.sort(symbols);
            logger.debug("Symbols found on exchange {}: {}", exchange, String.join(", ", symbols));
            
            for (String symbol : symbols) {
                EquityPrice firstPrice = firstBySymbol.get(symbol);
                EquityPrice lastPrice = latestBySymbol.get(symbol);
                logger.debug("Symbol: {} - First price: {} at {}, Latest price: {} at {}, Total records: {}", 
                    symbol,
                    firstPrice.getClose(), firstPrice.getTime(),
                    lastPrice.getClose(), lastPrice.getTime(),
                    prices.stream().filter(p -> p.getSymbol().equals(symbol)).count());
            }
        } else {
            logger.debug("No prices found for exchange: {}", exchange);
        }
        
        logger.debug("Query completed in {}ms", (queryEndTime - queryStartTime));
        return prices;
    }

    private List<EquityPrice> getPriceAndstampLog(String key, long queryStartTime, List<EquityPrice> prices) {
        long queryEndTime = System.currentTimeMillis();
        if (!prices.isEmpty()) {
            EquityPrice firstPrice = prices.get(0);
            EquityPrice lastPrice = prices.get(prices.size() - 1);

            logger.debug("Found {} prices for data: {}, symbol: {}", prices.size(), key, firstPrice.getSymbol());
            logger.debug("Price range - First: {} at {}, Last: {} at {}",
                    firstPrice.getClose(), firstPrice.getTime(),
                    lastPrice.getClose(), lastPrice.getTime());
        } else {
            logger.debug("No prices found for data: {}", key);
        }

        logger.debug("Query completed in {}ms", (queryEndTime - queryStartTime));
        return prices;
    }
}
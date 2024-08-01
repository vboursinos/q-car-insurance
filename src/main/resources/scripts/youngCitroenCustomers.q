// Ford customers aged 30 or younger
young_citroen_customers: select from citroen_customers where age <= 30

/// Ford customers with cars priced over 50000
/expensive_ford_customers: select from ford_customers where price > 50000
/
/// Average price of Ford cars by country
/avg_price_by_country: select avg price by country from ford_customers
/
/// Distribution of engine sizes for Ford cars
/engine_size_distribution: select count i by engine_size from ford_customers

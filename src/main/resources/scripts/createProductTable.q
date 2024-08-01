// createProductTable.q

// Define the number of rows
numRows: 13;

// Define the lists for each column
product_ids: 1111 1112 1211 1212 1237 4564 5789 8321 6549 9887 6584 3261 1233 ;
constructor_names: `Ford`VW`Audi`BMW`Toyota`Nissan`Honda`Mazda`Hyundai`Kia`Renault`Peugeot`Citroen;
product_prices: 10000 20000 30000 40000 50000 60000 70000 80000 90000 100000 110000 120000 130000;
engine_sizes: 1.0 1.2 1.4 1.6 1.8 2.0 2.2 2.4 2.6 2.8 3.0 3.2 3.4;

// Function to expand a list to the desired number of rows
expandList: {x@/: numRows?count x};

// Create the table
product: ([]
    id: expandList product_ids;
    constructor: expandList constructor_names;
    price: expandList product_prices;
    engine_size: expandList engine_sizes
);

// Verify table creation
product

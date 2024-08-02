// Display initial tables
show "Initial Customers Table:";
show customers;

show "Initial Product Table:";
show product;

show "Customers from the UK:";
uk_customers: select from customers where country = `UK;

n: 100;
show "100 Random Customers:";
do[n; show customers[.z.i]];

n: 100;
show "100 Random Products:";
do[n; show product[.z.i]];

n: 100;
show "100 Random citroen Customers:";
do[n; show citroen_customers[.z.i]];

n: 100;
show "100 Random citroen young Customers:";
do[n; show young_citroen_customers[.z.i]];

n: 100;
show "100 Random renault Customers:";
do[n; show renault_customers[.z.i]];

n: 100;
show "100 Random renault young Customers:";
do[n; show young_renault_customers[.z.i]];

n: 100;
show "100 UK Customers:";
do[n; show uk_customers[.z.i]];

show "Average Citroen price per country:";
complicated_avg_price_by_country

n: 100;
l: 100;
show "100 UK Customers details:";
do[n;
    // Get the i-th UK customer
   customer: uk_customers[.z.i];

    // Print the customer's details
   show customer;

   do[l;
      name: customer[`name];
      message: "Customer ", name, " is from the UK.";
      show message;
     ];
  ];

// Greek customers
show "Customers from Greece:";
greek_customers: select from customers where country = `Greece;
show greek_customers;

n: 100;
m: 100;
show "100 Greek Customers with Inner Loop:";
do[n;
    // Get the i-th Greek customer
   customer: greek_customers[.z.i];

    // Print the customer's details
   show customer;

   do[m;
      name: customer[`name];
      message: "Customer ", name, " is from Greece.";
      show message;
     ];
  ];

//Compare total prices for UK and Greek customers
// Join UK customers with the product table to get product prices
uk_customers_with_prices: uk_customers lj `product_id xkey product;

// Calculate total price for UK customers
total_price_uk: sum uk_customers_with_prices`price;
show "Total Price for UK Customers: ", string total_price_uk;

// Join Greek customers with the product table to get product prices
greek_customers_with_prices: greek_customers lj `product_id xkey product;

// Calculate total price for Greek customers
total_price_greece: sum greek_customers_with_prices`price;
show "Total Price for Greek Customers: ", string total_price_greece;

// Compare total prices and determine which country has a higher total price
if[total_price_uk > total_price_greece;
   show "UK has a higher total price.";
   show "Difference: ", string (total_price_uk - total_price_greece);
   ;
   show "Greece has a higher total price.";
   show "Difference: ", string (total_price_greece - total_price_uk);
  ];
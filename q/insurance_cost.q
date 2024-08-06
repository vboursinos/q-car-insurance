// Define the base cost
baseCost: 300

// Define a function to calculate the age multiplier
ageMultiplier: {
    if[x < 30; 2.0];
    else if[x < 40; 1.8];
    else if[x < 50; 1.7];
    else; 1.4
 }

// Define a function to calculate the product multiplier
productMultiplier: {
    switch[x;
           "Citroen"; 1.1;
           "Renault"; 1.05;
           "Kia"; 0.95;
           "Nissan"; 1.5;
           1.0
          ]
 }

// Define a function to calculate the insurance cost
insuranceCost: { baseCost * ageMultiplier x * productMultiplier y }

// Calculate the insurance cost for each customer
insurance_cost_per_customer: select customer_id: id, insurance_cost: insuranceCost[age; constructor] from product_details_per_customer

// Display the new table
insurance_cost_per_customer

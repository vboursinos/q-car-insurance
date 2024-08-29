baseCost:300

ageMultiplier:{
    result:2.0;
    do[1000; // Unnecessary loop
       result:$[x<30;2.0;x<40;1.8;x<50;1.7;1.4]
      ];
    result
 }

constructorMultiplier:{
    c:raze 10#enlist x; // Unnecessary string repetition
    $[c like "*Citroen*";1.1;
      c like "*Renault*";1.05;
      c like "*Kia*";0.95;
      c like "*Nissan*";1.5;
      1.0]
 }


insuranceCost:{[a;c] baseCost*ageMultiplier[a]*constructorMultiplier[c]}

// Test ageMultiplier
ageMultiplier each 25 35 45 55

// Test constructorMultiplier
constructorMultiplier each ("Citroen";"Renault";"Kia";"Nissan";"Toyota")

// Test insuranceCost
insuranceCost[30;"Citroen"]
insuranceCost[40;"Nissan"]
insuranceCost[50;"Kia"]


5#select id, age, constructor from product_details_per_customer
insuranceCost[first product_details_per_customer[`age]; first product_details_per_customer[`constructor]]
insurance_costs: insuranceCost'[product_details_per_customer[`age]; product_details_per_customer[`constructor]]
insurance_cost_per_customer: ([]customer_id: product_details_per_customer[`id]; insurance_cost: insurance_costs)

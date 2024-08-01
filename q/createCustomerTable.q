// createCustomerTable.q

// Define the number of rows
numRows: 1000000;

// Define the lists for each column
names: `Matthew`Nick`Andrew`Paul`George`Will`Maria`Antony`Bob;
surnames: `Brown`Dickson`Cornell`Rose`Gordon`Black`Down`Edwards`Toby;
countries: `Turkey`Russia`UK`Germany`Spain`Italy`Greece`Portugal;
ages: 25 21 40 32 54 23 58 66 41;
localTimes: 07:00 12:00 09:00 10:00 11:00;
tel_nums: `0784739287`0783472111`0784567584`0783334256`0785323433`0783334255`0783334251`0783334356`0784434256;
regions: `Central`Southeast`Northwest`Northeast`Central`South`Central;
product_ids: 1237 4564 5789 8321 6549 9887 6584 3261 1233;

// Function to expand a list to the desired number of rows
expandList: {x@/: numRows?count x};

// Create the table
customers: ([]
    id: 1+til numRows;
    name: expandList names;
    surname: expandList surnames;
    country: expandList countries;
    age: expandList ages;
    localTime: expandList localTimes;
    tel_num: expandList tel_nums;
    region: expandList regions;
    product_id: expandList product_ids
);

// Verify table creation
customers

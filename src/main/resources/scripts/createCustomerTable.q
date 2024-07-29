// createCustomerTable.q

// Define the number of rows
numRows: 1000000;

// Define the lists for each column
names: `Matthew`Nick`Andrew`Paul`George`Will`Maria`Antony`Bob;
surnames: `Brown`Dickson`Cornell`Rose`Gordon`Black`Down`Edwards`Toby;
countries: `Turkey`Russia`UK`Germany`Spain`Italy`Greece`Portugal;
populations: 15067724 12615279 9126366 5383890 3750000 3256000 2800000 3100000 300000;
localTimes: 07:00 12:00 09:00 10:00 11:00;
tel_nums: `0784739287`0783472111`0784567584`0783334256`0785323433`0783334255`0783334251`0783334356`0784434256;
regions: `Marmara`Central`Southeast`Northwest`Northeast`Central`South`Central`LisbonRegion;

// Function to expand a list to the desired number of rows
expandList: {x@/: numRows?count x};

// Create the table
customers: ([]
    id: 1+til numRows;
    name: expandList names;
    surname: expandList surnames;
    country: expandList countries;
    pop: expandList populations;
    localTime: expandList localTimes;
    tel_num: expandList tel_nums;
    region: expandList regions
);

// Verify table creation
customers

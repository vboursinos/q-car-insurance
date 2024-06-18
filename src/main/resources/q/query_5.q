show "Hello World";
data:1 3 5 7 9;
show data;
show til 5;
a:50;
show a;

r:"";
r: $[a>10; "true"; "false"];
show "r = ", r;

eachElement: {[i] show i};

each[eachElement; data];

n: 5;
do[n; show .z.i];


m:2;
q:3;

do[m;
  iVal:.z.i;
  do[q; show "m = ", string iVal, "; q = ", string .z.i];
 ];



 x:100;
 y:500;
 z:2;

 do[x;
   xVal:.z.i;
   do[y;
     yVal:.z.i;
     do[z; show "x = ", string xVal, "; y = ", string yVal, "; z = ", string .z.i];
   ];
 ];


 s:20;
 t:6;
 u:5;
 v:2;

 do[s;
   sVal:.z.i;
   do[t;
     tVal:.z.i;
     do[u;
       uVal:.z.i;
       do[v; show "s = ", string sVal, "; t = ", string tVal, "; u = ", string uVal, "; v = ", string .z.i];
     ];
   ];
 ];
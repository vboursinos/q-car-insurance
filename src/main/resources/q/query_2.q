joinTable2: aj[`id;
    select from
        update id: id, sales: sales + 0, dummy: sales * age from //Added unnecessary computation
            (select from
                update age: age + 0 from //Another unnecessary operation
                    (select from
                        (aj[`id; //Unnecessary joint
                            select from person where sales > 502, //Altered sales figure
                            age > 52; `id xkey (sale)
                        ])
                    ) where sales > 500, age > 50
            );
    `id xkey (sale)]
const db = require('../config/config');

const Product = {};


Product.findByCategory = (id_category) => {
    const sql = `
    SELECT
        P.id,
        P.name,
        P.description,
        P.price,
        P.image1,
        P.image2,
        P.image3,
        P.id_category,
        P.phone
    FROM
        products AS P
    INNER JOIN
        categories AS C
    ON
        P.id_category = C.id
    WHERE
        C.id = $1
    `;

    return db.manyOrNone(sql, id_category);
}


Product.create = (product) => {
    const sql = `
    INSERT INTO
        products(
            name,
            description,
            price,
            image1,
            image2,
            image3,
            id_category,
            created_at,
            updated_at,
            phone
        )
    VALUES($1, $2, $3, $4, $5, $6, $7, $8, $9, $10) RETURNING id
    `;
    return db.oneOrNone(sql, [
        product.name,
        product.description,
        product.price,
        product.image1,
        product.image2,
        product.image3,
        product.id_category,
        new Date(),
        new Date(),
        product.phone

    ]);
}

Product.update = (product) => {
    const sql = `
    UPDATE
        products
    SET
        name = $2,
        description = $3,
        price = $4,
        image1 = $5,
        image2 = $6,
        image3 = $7,
        id_category = $8,
        updated_at = $9,
        phone = $10
    WHERE
        id = $1
    `;
    return db.none(sql, [
        product.id,
        product.name,
        product.description,
        product.price,
        product.image1,
        product.image2,
        product.image3,
        product.id_category,
        new Date(),
        product.phone
    ]);
}


module.exports = Product;
-- Inserting admin user and test non-admin user
insert into users values (1, 'Gordon', 'Ramsey', true, 'admin', '123');
insert into users values (2, 'Anthony', 'Bourdain', false, 'ant', 'test');


-- Recipe 1
-- Inserting some base ingredients with recipe 1 in mind

INSERT INTO ingredients VALUES (1, 'Flour');
INSERT INTO ingredients VALUES (2, 'Sugar');
INSERT INTO ingredients VALUES (3, 'Salt');
INSERT INTO ingredients VALUES (4, 'Eggs');
INSERT INTO ingredients VALUES (5, 'Milk');
INSERT INTO ingredients VALUES (6, 'Butter');
INSERT INTO ingredients VALUES (7, 'Baking Powder');
INSERT INTO ingredients VALUES (8, 'Vanilla Extract');
INSERT INTO ingredients VALUES (9,'Chocolate Chips');

-- Inserting a chocolate chip cookie recipe

INSERT INTO recipes VALUES (1, 'Chocolate Chip Cookies', 'Classic and delicious chocolate chip cookies recipe.', '1. Preheat oven to 190 degrees C.\n2. In a medium bowl, whisk together the butter, white sugar, and brown sugar until smooth. \n3. Beat in the eggs one at a time, then stir in the vanilla. \n4. Dissolve baking soda in hot water. Add to batter along with salt. \n5. Stir in flour and chocolate chips\n6. Place handful of dough balls onto ungreased oven pan. \n7. Bake for about 10 minutes in the preheated oven, or until edges are nicely browned and crispy.', 24, 10, 15);

-- Inserting/associating the ingredients for that specific chocolate chip cookie recipe

INSERT INTO r_ingredients VALUES (1, 1, 2, 'cups');
INSERT INTO r_ingredients VALUES (2, 1, 1, 'cup');
INSERT INTO r_ingredients VALUES (3, 1, 1, 'tsp');
INSERT INTO r_ingredients (ingredient_id, recipe_id, qty) VALUES (4, 1, 2);
INSERT INTO r_ingredients VALUES (5, 1, 0.5, 'cup');
INSERT INTO r_ingredients VALUES (6, 1, 1, 'cup');
INSERT INTO r_ingredients VALUES (7, 1, 2, 'tsp');
INSERT INTO r_ingredients VALUES (8, 1, 1, 'tsp');
INSERT INTO r_ingredients VALUES (9, 1, 1.5, 'cups');


-- Recipe #2
-- Inserting base ingredients with recipe 2 in mind

INSERT INTO ingredients VALUES (10, 'Chicken Breast');
INSERT INTO ingredients VALUES (11, 'Curry Powder');
INSERT INTO ingredients VALUES (12, 'Tomato');
INSERT INTO ingredients VALUES (13, 'Onion');
INSERT INTO ingredients VALUES (14, 'Garlic');
INSERT INTO ingredients VALUES (15, 'Ginger');
INSERT INTO ingredients VALUES (16, 'Coconut Milk');

-- Inserting a chicken curry recipe

INSERT INTO recipes VALUES (2, 'Crazy Chicken Curry', 'A delicious chicken curry that you will never forget!', '1. In a pan, heat oil and fry onions until golden brown.\n2. Add garlic and ginger and fry for 1 minute.\n3. Add curry powder and fry for 1 minute.\n4. Add chopped tomatoes and cook until they break down and form a thick paste.\n 5. Add chicken and cook until it is browned on all sides.\n6. Add coconut milk and bring to a boil.\n7. Reduce heat and simmer until chicken is cooked through, about 20-25 minutes. Then serve.', 3, 25, 35);

-- Inserting/associating the ingredients for that specific chicken curry recipe

INSERT INTO r_ingredients VALUES (10, 2, 500, 'g');
INSERT INTO r_ingredients VALUES (11, 2, 2, 'tbsp');
INSERT INTO r_ingredients VALUES (12, 2, 400, 'g');
INSERT INTO r_ingredients VALUES (13, 2, 1, 'large');
INSERT INTO r_ingredients VALUES (14, 2, 4, 'cloves');
INSERT INTO r_ingredients VALUES (15, 2, 1, 'tbsp');
INSERT INTO r_ingredients VALUES (16, 2, 400, 'ml');


-- Recipe 3
-- Inserting base ingredients with recipe 3 in mind

INSERT INTO ingredients VALUES (17, 'Romaine Lettuce');
INSERT INTO ingredients VALUES (18, 'Caesar Dressing');
INSERT INTO ingredients VALUES (19, 'Parmesan Cheese');
INSERT INTO ingredients VALUES (20, 'Croutons');

-- Inserting a caesar salad recipe

INSERT INTO recipes VALUES (3, 'Classic Caesar Salad with Parmesan Croutons', 'This recipe highlights the classic ingredients of a caesar salad, with a cheesy twist on the croutons. It is  a simple and delicious salad perfect for any occasion.', '1. Wash and chop the romaine lettuce into bite-sized pieces.\n2. In a large mixing bowl, add the chopped romaine lettuce and 1/2 cup of Caesar dressing. Toss the lettuce until evenly coated with the dressing.\n3. Top the dressed lettuce with 1/2 cup of shaved or grated Parmesan cheese and 1 cup of croutons.\n4. Toss the salad gently to combine all the ingredients.\n5. Serve immediately and enjoy your delicious Classic Caesar Salad with Parmesan Croutons!', 4, 10, 0);

-- Inserting/associating the ingredients for that specific casear salad recipe

INSERT INTO r_ingredients VALUES (17, 3, 1, 'head');
INSERT INTO r_ingredients VALUES (18, 3, 0.5, 'cup');
INSERT INTO r_ingredients VALUES (19, 3, 0.5, 'cup');
INSERT INTO r_ingredients VALUES (20, 3, 1, 'cup');


-- Recipe 4
-- Inserting base ingredients with recipe 4 in mind (that do not already exist)

INSERT INTO ingredients VALUES (21, 'Fettuccine Pasta');
INSERT INTO ingredients VALUES (22, 'Heavy Cream');
INSERT INTO ingredients VALUES (23, 'Pepper');
INSERT INTO ingredients VALUES (24, 'Parsley');

-- Inserting a chicken alfredo recipe

INSERT INTO recipes VALUES (4, 'Creamy Chicken Fettuccine Alfredo', 'This creamy and delicious chicken fettuccine Alfredo recipe is sure to be a hit at any dinner table. The chicken and fettuccine pasta are cooked in a rich and creamy Parmesan sauce, with a touch of garlic and parsley for added flavor. It is a simple and easy-to-follow recipe that is perfect for a quick weeknight dinner or a special occasion. Enjoy!', '1. Cook fettuccine pasta according to package instructions. Drain and set aside.\n2. Season chicken strips with salt and pepper.\n3. Heat a large skillet over medium-high heat. Add chicken and cook until browned, about 5-7 minutes.\n4. Remove chicken from skillet and set aside.\n5. In the same skillet, melt butter over medium heat. Add garlic and cook for 1-2 minutes.\n6. Add heavy cream to the skillet and bring to a simmer.\n7. Stir in grated Parmesan cheese until melted and smooth.\n8. Add cooked fettuccine pasta and chicken strips to the skillet. Toss to coat with the sauce.\n9. Season with salt and pepper, then sprinkle with chopped parsley before serving.', 4, 10, 20);

-- Inserting/associating the ingredients for that specific chicken alfredo
-- Some ingredients in this recipe already exist, must reference ingredient_id from above
-- salt id: 3, butter id: 6, chicken breast id: 10, garlic id: 14, parmesan id: 19

INSERT INTO r_ingredients VALUES (10, 4, 500, 'g');
INSERT INTO r_ingredients VALUES (21, 4, 500, 'g');
INSERT INTO r_ingredients VALUES (6, 4, 115, 'g');
INSERT INTO r_ingredients VALUES (14, 4, 2, 'cloves');
INSERT INTO r_ingredients VALUES (22, 4, 500, 'ml');
INSERT INTO r_ingredients VALUES (19, 4, 120, 'g');
INSERT INTO r_ingredients VALUES (3, 4, 0.5, 'tsp');
INSERT INTO r_ingredients VALUES (23, 4, 0.25, 'tsp');
INSERT INTO r_ingredients VALUES (24, 4, 1, 'tbsp');

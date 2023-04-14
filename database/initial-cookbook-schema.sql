create table users (
user_id int not null auto_increment,
fname varchar(30) not null,
lname varchar(30) not null,
is_admin boolean not null,
username varchar(20) not null,
password varchar(20) not null,
primary key (user_id),
unique key (username)
);

create table recipes (
recipe_id int not null auto_increment,
recipe_name varchar(50) not null,
recipe_description TEXT not null,
servings int not null,
prep_time_minutes int not null,
cook_time_minutes int not null,
primary key (recipe_id)
);

create table tags (
tag_id int not null auto_increment,
tag_name varchar(20) not null,
primary key (tag_id),
unique key (tag_name)
);

create table recipe_tags (
recipe_id int not null,
tag_id int not null,
primary key (recipe_id, tag_id),
constraint `recipe_tag_fk1` foreign key (`recipe_id`) references `recipes` (`recipe_id`),
constraint `recipe_tag_fk2` foreign key (`tag_id`) references `tags` (`tag_id`)
);

create table custom_tags (
ctag_id int not null auto_increment,
user_id int not null,
ctag_name varchar(20) not null,
primary key (ctag_id, user_id),
constraint `custom_tag_fk1` foreign key (`user_id`) references `users` (`user_id`)
);

create table recipe_ctags (
recipe_id int not null,
ctag_id int not null,
primary key (recipe_id, ctag_id),
constraint `recipe_ctag_fk1` foreign key (`recipe_id`) references `recipes` (`recipe_id`),
constraint `recipe_ctag_fk2` foreign key (`ctag_id`) references `custom_tags` (`ctag_id`)
);

create table ingredients (
ingredient_id int not null auto_increment,
i_name varchar(20) not null,
primary key (ingredient_id),
unique key (i_name)
);

create table r_ingredients (
ingredient_id int not null,
recipe_id int not null,
qty int not null,
measurement varchar(10) null,
primary key (ingredient_id, recipe_id),
constraint `r_ingredients_fk1` foreign key (`ingredient_id`) references `ingredients` (`ingredient_id`),
constraint `r_ingredients_fk2` foreign key (`recipe_id`) references `recipes` (`recipe_id`)
);

create table comments (
comment_id int not null auto_increment,
user_id int not null,
recipe_id int not null,
content text not null,
comment_date date not null,
primary key (comment_id),
constraint `comments_fk1` foreign key (`user_id`) references `users` (`user_id`),
constraint `comments_fk2` foreign key (`recipe_id`) references `recipes` (`recipe_id`)
);

create table favorites (
user_id int not null,
recipe_id int not null,
primary key (user_id, recipe_id),
constraint `favorites_fk1` foreign key (`user_id`) references `users` (`user_id`),
constraint `favorites_fk2` foreign key (`recipe_id`) references `recipes` (`recipe_id`)
);

create table shopping_list (
list_id int not null auto_increment,
user_id int not null,
list_name varchar(20) not null,
date_created date not null,
primary key (list_id),
constraint `shopping_list_fk1` foreign key (`user_id`) references `users` (`user_id`)
);

create table list_items (
item_id int not null auto_increment,
list_id int not null,
ingredient_id int not null,
qty int not null,
measurement varchar(20) null,
item_purchased boolean not null,
primary key (item_id),
constraint `list_item_fk1` foreign key (`list_id`) references `shopping_list` (`list_id`),
constraint `list_item_fk2` foreign key (`ingredient_id`) references `ingredients` (`ingredient_id`)
);
 

create table week_plan (
week_id int not null auto_increment,
user_id int not null,
start_date date not null,
end_date date not null,
primary key (week_id)
);

create table week_recipes (
week_id int not null,
recipe_id int not null,
primary key (week_id, recipe_id),
constraint `week_recipes_fk1` foreign key (`week_id`) references `week_plan` (`week_id`),
constraint `week_recipes_fk2` foreign key (`recipe_id`) references `recipes` (`recipe_id`)
);

create table messages (
message_id int not null auto_increment,
sender_id int not null,
receiver_id int not null,
recipe_id int not null,
content text not null,
date_created datetime not null,
primary key (message_id),
constraint `messages_fk1` foreign key (`sender_id`) references `users` (`user_id`),
constraint `messages_fk2` foreign key (`receiver_id`) references `users` (`user_id`),
constraint `messages_fk3` foreign key (`recipe_id`) references `recipes` (`recipe_id`)
);